package com.store.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.MongoException;
import com.mongodb.ServerAddress;

@Configuration
@EnableMongoRepositories(basePackages = "com.store.repository")
public class MongoDbConfig {

  @Autowired
  Environment env;

  public @Bean MongoDbFactory mongoDbFactory() throws MongoException {

    // Set credentials
    MongoCredential credential = MongoCredential.createCredential(
        env.getProperty("mongo.server.user"), env.getProperty("mongo.database.name"),
        env.getProperty("mongo.server.password").toCharArray());
    ServerAddress serverAddress = new ServerAddress(env.getProperty("mongo.server.ip"),
        Integer.parseInt(env.getProperty("mongo.server.port")));

    // Mongo Client
    MongoClient mongoClient = new MongoClient(serverAddress, Arrays.asList(credential));

    return new SimpleMongoDbFactory(mongoClient, env.getProperty("mongo.database.name"));
  }


  public @Bean MongoTemplate mongoTemplate() throws Exception {
    return new MongoTemplate(mongoDbFactory());
  }
}
