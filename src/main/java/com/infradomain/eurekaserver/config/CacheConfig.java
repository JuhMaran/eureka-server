package com.infradomain.eurekaserver.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * eureka-server
 *
 * @author Juliane Maran
 * @since 20/10/2025
 */
@Configuration
public class CacheConfig {

  @Bean
  public CacheManager cacheManager() {
    CaffeineCacheManager cacheManager = new CaffeineCacheManager();
    cacheManager.setCaffeine(
      Caffeine.newBuilder()
        .expireAfterWrite(5, TimeUnit.MINUTES)  // expira após 5 minutos
        .maximumSize(500)                               // limite de 500 entradas
        .recordStats()                                  // coleta estatísticas
    );
    return cacheManager;
  }

}
