package com.store.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class FileSystemsConfig {

  @Autowired
  Environment env;

  public String getBaseDir() {
    return env.getProperty("storage.base.dir");
  }

}
