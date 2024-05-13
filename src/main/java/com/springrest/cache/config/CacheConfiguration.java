package com.springrest.cache.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;


/**
 * Configuration class for enabling caching in the application.
 */
@Configuration
@EnableCaching
public class CacheConfiguration {

    /**
     * The cache manager used in the application.
     */
    @SuppressWarnings("unused")
    private final CacheManager cacheManager;

    /**
     * Constructs a CacheConfiguration object with the specified cache manager.
     * @param cacheManager The cache manager.
     */
    public CacheConfiguration(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

}