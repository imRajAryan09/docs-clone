package com.google.docs.config;

import com.google.docs.constant.RedisConstant;
import io.lettuce.core.resource.ClientResources;
import io.lettuce.core.resource.DefaultClientResources;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author by Raj Aryan,
 * created on 16/04/2024
 */
@Slf4j
@Configuration
@EnableCaching
public class RedisCacheConfig {

    @Value("${redis.host}")
    private String redisHost;

    @Value("${redis.port}")
    private Integer redisPort;

    @Value("${redis.password}")
    private String redisPassword;

    @Value("${redis.min.idle.connections}")
    private Integer redisMinIdleConnections;

    @Value("${redis.max.idle.connections}")
    private Integer redisMaxIdleConnections;

    @Value("${redis.max.total.connections}")
    private Integer redisMaxTotalConnections;

    @Bean(destroyMethod = "shutdown")
    public ClientResources clientResources() {
        return DefaultClientResources.create();
    }

    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(redisHost);
        redisStandaloneConfiguration.setPort(redisPort);
        redisStandaloneConfiguration.setPassword(RedisPassword.of(redisPassword));

        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        poolConfig.setMinIdle(redisMinIdleConnections);
        poolConfig.setMaxIdle(redisMaxIdleConnections);
        poolConfig.setMaxTotal(redisMaxTotalConnections);

        LettucePoolingClientConfiguration lettucePoolingClientConfiguration = LettucePoolingClientConfiguration.builder()
                .commandTimeout(Duration.ofSeconds(10))
                .shutdownTimeout(Duration.ZERO)
                .poolConfig(poolConfig)
                .build();
        LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(redisStandaloneConfiguration, lettucePoolingClientConfiguration);
        lettuceConnectionFactory.setShareNativeConnection(false);

        return lettuceConnectionFactory;
    }

    @Primary
    @Bean(name = "cacheManager")
    public CacheManager cacheManager() {
        Map<String, RedisCacheConfiguration> initialCacheConfiguration = new HashMap<>();
        RedisConstant.CACHE_MAP.forEach((key, value) -> initialCacheConfiguration.put(
                key, RedisCacheConfiguration.defaultCacheConfig()
                        .disableCachingNullValues()
                        .entryTtl(Duration.ofMillis(value))));

        return RedisCacheManager.RedisCacheManagerBuilder
                .fromConnectionFactory(Objects.requireNonNull(redisTemplate().getConnectionFactory()))
                .withInitialCacheConfigurations(initialCacheConfiguration)
                .transactionAware()
                .build();
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        final RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new GenericToStringSerializer<>(Object.class));
        redisTemplate.setHashValueSerializer(new JdkSerializationRedisSerializer());
        redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }
}