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

    @Bean
    public TimedAspect timedAspect(MeterRegistry meterRegistry) {
        return new TimedAspect(meterRegistry);
    }
}
