package com.saber.person.soap.api.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private ComboPoolDataSourceConfig comboConfig;

    @Bean(value = "personDataSource")
    @Primary
    public ComboPooledDataSource dataSource() throws PropertyVetoException {
        ComboPooledDataSource dataSource =new ComboPooledDataSource();
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
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .securitySchemes(securitySchemeList())
                .securityContexts(securityContext())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.saber.person.soap.api.controllers"))
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
                .title("Person Service APi")
                .version("version1.1 1400/08/05")
                .description("Person Service Api")
                .build();
    }
}
