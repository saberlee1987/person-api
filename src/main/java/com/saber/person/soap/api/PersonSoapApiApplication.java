package com.saber.person.soap.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan(basePackages = "com.saber.person.soap.api.config")
public class PersonSoapApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(PersonSoapApiApplication.class, args);
    }

}
