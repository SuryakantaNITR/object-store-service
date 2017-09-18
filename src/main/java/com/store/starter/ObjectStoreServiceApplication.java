package com.store.starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;


@ComponentScan("com.store")
@SpringBootApplication
//@EnableResourceServer
@EnableAsync
@EnableDiscoveryClient
public class ObjectStoreServiceApplication {

  public static void main(String[] args) {
    System.setProperty("spring.config.location", "/config");
    System.setProperty("spring.config.name", "config");
    SpringApplication.run(ObjectStoreServiceApplication.class, args);
  }
}
