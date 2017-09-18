package com.store.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;

//@EnableResourceServer
@Configuration
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class ResourceServer extends ResourceServerConfigurerAdapter {
  @Override
  public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
      resources.resourceId("object-store-service");
  }

  @Override
  public void configure(HttpSecurity http) throws Exception {
    http.anonymous().disable().requestMatchers().and().authorizeRequests().antMatchers("/file/**")
    .access("hasRole('ROLE_CLIENT')")
    .and().exceptionHandling().accessDeniedHandler(new OAuth2AccessDeniedHandler());
  }
 
}
