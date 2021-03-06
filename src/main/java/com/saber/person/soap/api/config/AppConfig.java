package com.saber.person.soap.api.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.camel.CamelContext;
import org.apache.camel.component.metrics.routepolicy.MetricsRoutePolicyFactory;
import org.apache.camel.component.micrometer.messagehistory.MicrometerMessageHistoryFactory;
import org.apache.camel.component.micrometer.routepolicy.MicrometerRoutePolicyFactory;
import org.apache.camel.spring.boot.CamelContextConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.beans.PropertyVetoException;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableSwagger2
public class AppConfig {
    private static final String AUTHORIZATION = "Authorization";
    private static final String AUTHORIZATION_HEADER = "header";
    private final ComboPoolDataSourceConfig comboConfig;

    @Value(value = "${service.swagger.title}")
    private String swaggerTitle;
    @Value(value = "${service.swagger.version}")
    private String swaggerVersion;
    @Value(value = "${service.swagger.description}")
    private String swaggerDescription;
    @Value(value = "${service.swagger.packageController}")
    private String packageController;

    public AppConfig(ComboPoolDataSourceConfig comboConfig) {
        this.comboConfig = comboConfig;
    }

    @Bean(value = "personDataSource")
    @Primary
    public ComboPooledDataSource dataSource() throws PropertyVetoException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setDriverClass(comboConfig.getDriverClassName());
        dataSource.setJdbcUrl(comboConfig.getUrl());
        dataSource.setUser(comboConfig.getUsername());
        dataSource.setPassword(comboConfig.getPassword());

        dataSource.setPreferredTestQuery(comboConfig.getPreferredTestQuery());
        dataSource.setMinPoolSize(comboConfig.getMinPoolSize());
        dataSource.setMaxPoolSize(comboConfig.getMaxPoolSize());
        dataSource.setAcquireIncrement(comboConfig.getAcquireIncrement());
        dataSource.setCheckoutTimeout(comboConfig.getCheckTimeout());
        dataSource.setInitialPoolSize(comboConfig.getInitialPoolSize());
        dataSource.setAcquireRetryDelay(comboConfig.getAcquireRetryDelay());
        dataSource.setMaxConnectionAge(comboConfig.getMaxConnectionAge());
        dataSource.setMaxIdleTime(comboConfig.getMaxIdleTimeout());
        dataSource.setMaxStatementsPerConnection(comboConfig.getMaxStatementPerConnection());
        dataSource.setIdleConnectionTestPeriod(comboConfig.getIdleConnectionTestPeriod());
        dataSource.setTestConnectionOnCheckout(comboConfig.isTestConnectionOnCheckout());
        dataSource.setTestConnectionOnCheckin(comboConfig.isTestConnectionOnCheckin());
        dataSource.setNumHelperThreads(comboConfig.getNumHelperThreads());
        return dataSource;
    }

    @Bean
    public ObjectMapper mapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.configure(SerializationFeature.FAIL_ON_SELF_REFERENCES, false);
        mapper.configure(SerializationFeature.FAIL_ON_UNWRAPPED_TYPE_IDENTIFIERS, false);
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(DeserializationFeature.FAIL_ON_INVALID_SUBTYPE, false);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        return mapper;
    }

    @Bean
    public CamelContextConfiguration camelContextConfiguration() {
        return new CamelContextConfiguration() {
            @Override
            public void beforeApplicationStart(CamelContext camelContext) {
                camelContext.addRoutePolicyFactory(new MicrometerRoutePolicyFactory());
                camelContext.addRoutePolicyFactory(new MetricsRoutePolicyFactory());
                camelContext.setMessageHistoryFactory(new MicrometerMessageHistoryFactory());
            }

            @Override
            public void afterApplicationStart(CamelContext camelContext) {

            }
        };
    }

    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .securitySchemes(securitySchemeList())
                .securityContexts(securityContext())
                .select()
                .apis(RequestHandlerSelectors.basePackage(packageController))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

    private List<SecurityScheme> securitySchemeList() {
        SecurityScheme securityScheme = new ApiKey(AUTHORIZATION, AUTHORIZATION, AUTHORIZATION_HEADER);
        return Collections.singletonList(securityScheme);
    }


    private List<SecurityContext> securityContext() {
        return Collections.singletonList(SecurityContext.builder().securityReferences(defaultAuth()).forPaths(PathSelectors.any()).build());
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Collections.singletonList(new SecurityReference(AUTHORIZATION, authorizationScopes));
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(this.swaggerTitle)
                .version(swaggerVersion)
                .description(swaggerDescription)
                .build();
    }
}
