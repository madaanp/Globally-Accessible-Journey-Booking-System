package com.roadpass.authorization.database;

import com.mongodb.MongoClientSettings;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;

import java.util.Arrays;

@Configuration
public class JbsMongoConfig extends AbstractMongoClientConfiguration {

    @Override
    protected String getDatabaseName() {
        return "JourneyBookingSystem";
    }

    @Override
    public MongoClient mongoClient() {

        MongoClientSettings settings =
                MongoClientSettings.builder()
                        .applyToConnectionPoolSettings(builder ->
                                builder.maxSize(40).minSize(10))
                        .applyToClusterSettings(builder ->
                                builder.hosts(Arrays.asList(new ServerAddress("localhost", 60000),
                                        new ServerAddress("localhost", 60001))))
                        .build();

        return MongoClients.create(settings);

    }
}
