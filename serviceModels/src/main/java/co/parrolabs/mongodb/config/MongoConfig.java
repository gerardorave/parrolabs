package co.parrolabs.mongodb.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.bson.UuidRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.Collection;
import java.util.Collections;

@Configuration
//TODO: Enable reactive mongo db
@EnableMongoRepositories({"co.parrolabs.repository.mongodb"})
public class MongoConfig extends AbstractMongoClientConfiguration {

    @Value("${spring.data.mongodb.uri}")
    private String uriDB;
    @Value("${spring.data.mongodb.database}")
    private String database;

    @Override
    protected String getDatabaseName() {
        return database;
    }

    @Override
    public MongoClient mongoClient() {
         ConnectionString connectionString = new ConnectionString(uriDB);
               MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .uuidRepresentation(UuidRepresentation.JAVA_LEGACY)
                .build();

        return MongoClients.create(mongoClientSettings);
    }


    @Override
    public Collection getMappingBasePackages() {

        return Collections.singleton("co.parrolabs.model.mongodb");
    }
}