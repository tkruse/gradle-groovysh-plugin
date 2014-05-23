package com.example.config;

import com.github.fakemongo.Fongo;
import com.mongodb.Mongo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.repository.support.MongoRepositoryFactory;

@Configuration
public class FongoDatabaseConfig extends AbstractMongoConfiguration {

    private static final String DB_NAME = "example";

    private final Mongo mongo;

    private final MongoDbFactory mongoDbFactory;

    public FongoDatabaseConfig() {
        mongo = new Fongo("fongo").getMongo();
        mongoDbFactory = new SimpleMongoDbFactory(mongo, DB_NAME);
    }

    @Bean
    @Override
    public MongoDbFactory mongoDbFactory() throws Exception {
        return mongoDbFactory;
    }

    @Override
    protected String getDatabaseName() {
        return DB_NAME;
    }

    @Override
    public Mongo mongo() throws Exception {
        return mongo;
    }

    @Bean
    @Override
    public MongoTemplate mongoTemplate() throws Exception {
        return new MongoTemplate(mongoDbFactory, mappingMongoConverter());
    }

    @Bean
    public MongoRepositoryFactory mongoRepositoryFactory() throws Exception {
        return new MongoRepositoryFactory(mongoTemplate());
    }

}
