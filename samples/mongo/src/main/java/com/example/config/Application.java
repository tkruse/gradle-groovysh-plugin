package com.example.config;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories // scans this package by default
@ComponentScan(basePackageClasses = Application.class)
@SuppressWarnings("PMD")
public class Application {

    public static void main(String[] args) {
        run(args);
    }

    public static ApplicationContext run(String[] args) {
        SpringApplicationBuilder applicationBuilder = new SpringApplicationBuilder();
        return applicationBuilder.sources(Application.class).run(args);
    }
}
