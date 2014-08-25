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

/**
 * In-memory DB config
 */
@Configuration
public class FongoDatabaseConfig extends AbstractMongoConfiguration {

    private static final String DB_NAME = "example";

    private final Mongo mongoInst;

    private final MongoDbFactory dbFactory;

    public FongoDatabaseConfig() {
        mongoInst = new Fongo("fongo").getMongo();
        dbFactory = new SimpleMongoDbFactory(mongoInst, DB_NAME);
    }

    @Bean
    @Override
    public MongoDbFactory mongoDbFactory() {
        return dbFactory;
    }

    @Override
    protected String getDatabaseName() {
        return DB_NAME;
    }

    @Override
    public Mongo mongo() {
        return mongoInst;
    }

    @Bean
    @Override
    @SuppressWarnings("PMD.AvoidCatchingGenericException")
    public MongoTemplate mongoTemplate() {
        try {
            return new MongoTemplate(dbFactory, mappingMongoConverter());
        } catch (final Exception exc) {
            throw new WrappedFongoException(exc);
        }
    }

    @Bean
    public MongoRepositoryFactory mongoRepositoryFactory() {
        return new MongoRepositoryFactory(mongoTemplate());
    }


    private static final class WrappedFongoException extends RuntimeException {
        public WrappedFongoException(final Exception exc) {
            super(exc);
        }
    }
}
