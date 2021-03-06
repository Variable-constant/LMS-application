package com.karpenko.lms.config;

import com.karpenko.lms.service.StatisticsCounter;
import com.karpenko.lms.service.impl.StatisticsCounterImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StatisticsConfig {
    @Bean
    public StatisticsCounter statisticsCounter() {
        return new StatisticsCounterImpl();
    }
}
