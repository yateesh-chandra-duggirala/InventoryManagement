package com.project.inventory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
/**
 * Main class.
*/

@SpringBootApplication
public class InventoryApplication { 
  /**
   * Main method.
  */
   
  public static void main(final String[] args) {
    SpringApplication.run(InventoryApplication.class, args);
  }
  /**
   * Configuration method for creating a bean that
   * configures CORS (Cross-Origin Resource Sharing).
   * The created bean is used by the Spring framework to handle CORS configuration.
   */
  
  @Bean
  public WebMvcConfigurer corsConfigurer() {
    return new WebMvcConfigurer() {
      @Override
      public void addCorsMappings(final CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("http://localhost:3000");
      }
    };
  }
}
