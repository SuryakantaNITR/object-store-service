package com.store.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

public class MongoTemplateFactory {

  @Autowired
  MongoTemplate mongoTemplate;

  public MongoTemplate getMongoTemplate() {
    return mongoTemplate;
  }

}
