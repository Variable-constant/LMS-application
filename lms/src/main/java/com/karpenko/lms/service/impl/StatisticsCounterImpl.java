package com.karpenko.lms.service.impl;

import com.karpenko.lms.service.StatisticsCounter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StatisticsCounterImpl implements StatisticsCounter {
    private static final Logger log = LoggerFactory.getLogger(StatisticsCounterImpl.class);

    public void countHandlerCall() {
        log.info("Кто-то дернул нашу ручку!");
    }
}
