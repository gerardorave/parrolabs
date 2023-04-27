package co.parrolabs.redis.configuration;

import io.lettuce.core.ClientOptions;
import io.lettuce.core.ReadFrom;
import io.lettuce.core.SslOptions;
import io.lettuce.core.TimeoutOptions;
import io.lettuce.core.cluster.ClusterClientOptions;
import io.lettuce.core.cluster.ClusterTopologyRefreshOptions;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.*;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;




@Configuration
@EnableCaching
public class RedisCacheConfig {

    @Value("${client.redis.host}")
    private String redisHost;

    @Value("${client.redis.port}")
    private int redisPort;

    @Value("${client.redis.password}")
    private String redisPassword;

    @Value("${client.redis.useSSL}")
    private Boolean redisUseSSL;

    @Value("${client.redis.cluster.enabled}")
    private Boolean isClusterEnabled;


    @Value("${client.redis.cluster.shardCount}")
    private int shardCount;

    @Bean
    LettuceClientConfiguration lettuceClientConfiguration() {
        //Thread-safe connection pool settings
        GenericObjectPoolConfig<?> genericObjectPoolConfig = new GenericObjectPoolConfig<>();
        genericObjectPoolConfig.setMaxTotal(4);

        LettucePoolingClientConfiguration.LettucePoolingClientConfigurationBuilder lettucePoolingClientConfigurationBuilder =
                LettucePoolingClientConfiguration.builder()
                        .commandTimeout(Duration.ofSeconds(3))
                        .poolConfig(genericObjectPoolConfig);

        if (Boolean.TRUE.equals(redisUseSSL)) {
            lettucePoolingClientConfigurationBuilder.useSsl().disablePeerVerification();
        }
        return lettucePoolingClientConfigurationBuilder.build();
    }

    @Bean
    public RedisStandaloneConfiguration redisStandaloneConfiguration() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(redisHost, redisPort);
        redisStandaloneConfiguration.setPassword(RedisPassword.of(redisPassword));
        return redisStandaloneConfiguration;
    }

    @Bean
    public RedisConnectionFactory redisConnectionFactory(RedisStandaloneConfiguration standaloneConfiguration, LettuceClientConfiguration lettuceClientConfiguration) {
        if(Boolean.TRUE.equals(isClusterEnabled) && shardCount > 0) {
            RedisClusterConfiguration clusterConfiguration = new RedisClusterConfiguration();
            clusterConfiguration.setPassword(RedisPassword.of(redisPassword));
            ClusterTopologyRefreshOptions clusterTopologyRefreshOptions = ClusterTopologyRefreshOptions.builder()
                    .enablePeriodicRefresh()
                    .enableAllAdaptiveRefreshTriggers()
                    .refreshPeriod(Duration.ofSeconds(5))
                    .closeStaleConnections(true)
                    .adaptiveRefreshTriggersTimeout(Duration.ofSeconds(3))
                    .refreshTriggersReconnectAttempts(2)
                    .build();

            ClusterClientOptions clusterClientOptions = ClusterClientOptions.builder()
                    .topologyRefreshOptions(clusterTopologyRefreshOptions)
                    .sslOptions(SslOptions.builder()
                            .handshakeTimeout(Duration.ofMillis(301))
                            .build())
                    .timeoutOptions(TimeoutOptions.builder()
                            .connectionTimeout()
                            .fixedTimeout(Duration.ofMillis(300))
                            .build())
                    .maxRedirects(2)
                    .disconnectedBehavior(ClientOptions.DisconnectedBehavior.REJECT_COMMANDS)
                    .cancelCommandsOnReconnectFailure(true)
                    .autoReconnect(true)
                    .build();

            LettuceClientConfiguration lettuceClusterClientConfiguration = LettucePoolingClientConfiguration.builder()
                    .readFrom(ReadFrom.NEAREST)
                    .clientOptions(clusterClientOptions)
                    .commandTimeout(Duration.ofMillis(1500))
                    .useSsl()
                    .disablePeerVerification()
                    .build();

            return new LettuceConnectionFactory(clusterConfiguration, lettuceClusterClientConfiguration);
        }
        return new LettuceConnectionFactory(standaloneConfiguration, lettuceClientConfiguration);
    }

    @Bean
    public RedisCacheConfiguration redisCacheConfiguration() {
        return RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(60)).disableCachingNullValues();
    }

    @Bean
    public RedisCacheManager redisCacheManager(RedisConnectionFactory redisConnectionFactory, RedisCacheConfiguration redisCacheConfiguration) {
        return RedisCacheManager.builder(redisConnectionFactory)
                .cacheDefaults(redisCacheConfiguration).withInitialCacheConfigurations(initCacheConfigurations()).build();
    }

    private Map<String, RedisCacheConfiguration> initCacheConfigurations() {

        Map<String, RedisCacheConfiguration> config = new HashMap<>();
        config.put("customerCache", RedisCacheConfiguration.defaultCacheConfig().disableCachingNullValues().entryTtl(Duration.ofMinutes(60)));
        config.put("productCache", RedisCacheConfiguration.defaultCacheConfig().disableCachingNullValues().entryTtl(Duration.ofMinutes(60)));
        config.put("typeOfPaymentCache", RedisCacheConfiguration.defaultCacheConfig().disableCachingNullValues().entryTtl(Duration.ofMinutes(60)));
        config.put("addressCache", RedisCacheConfiguration.defaultCacheConfig().disableCachingNullValues().entryTtl(Duration.ofMinutes(60)));
        return config;

    }
}
