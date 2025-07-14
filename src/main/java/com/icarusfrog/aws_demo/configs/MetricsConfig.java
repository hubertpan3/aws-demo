package com.icarusfrog.aws_demo.configs;

import io.micrometer.cloudwatch2.CloudWatchConfig;
import io.micrometer.cloudwatch2.CloudWatchMeterRegistry;
import io.micrometer.core.aop.TimedAspect;
import io.micrometer.core.instrument.Clock;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import software.amazon.awssdk.services.cloudwatch.CloudWatchAsyncClient;

import java.time.Duration;
import java.util.Map;

@Slf4j
@Configuration
public class MetricsConfig {
    @Profile("local")
    @Bean
    public MeterRegistry localMeterRegistry() {
        log.info("Configuring local simple meter registry.");
        return new SimpleMeterRegistry();
    }

    @Profile("aws")
    @Bean
    public MeterRegistry cloudWatchMeterRegistry(CloudWatchConfig cwc) {
        return new CloudWatchMeterRegistry(cwc, Clock.SYSTEM, CloudWatchAsyncClient.create());
    }

    @Profile("aws")
    @Bean
    public CloudWatchConfig cloudWatchConfig() {
        CloudWatchConfig cwc = new CloudWatchConfig() {
            private Map<String, String> config = Map.of(
                    "cloudwatch.namespace", "awsDemo",
                    "cloudwatch.step", Duration.ofMinutes(1L).toString()
            );
            @Override
            public String get(String key) {
                return config.get(key);
            }
        };
        log.debug("Executing with cloudwatch config: {}", cwc);
        return cwc;
    }

    @Bean
    public TimedAspect timedAspect(MeterRegistry meterRegistry) {
        return new TimedAspect(meterRegistry);
    }
}
