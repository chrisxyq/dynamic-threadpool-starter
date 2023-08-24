package com.chrisxyq.dynamicthreadpoolstarter.common;

import com.chrisxyq.dynamicthreadpoolstarter.util.SpringUtil;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 线程池的metric记录上报工具类
 */
@Component
public class MetricRecorder {
    private final static long                     TIME_SEC                 = 1L;
    private final        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

    public MetricRecorder() {
        scheduledExecutorService.scheduleAtFixedRate(this::printMetric,
                TIME_SEC, TIME_SEC, TimeUnit.SECONDS);
    }

    /**
     * 依赖于线程池本身被初始化了才能执行
     * executor.initialize();
     */
    private void printMetric() {
        Map<String, MetricThreadPoolExecutor> map = SpringUtil.getBeansOfType(MetricThreadPoolExecutor.class);
        for (String poolName : map.keySet()) {
            System.out.println("MetricRecorder.printMetric====" + "poolName:" +
                    poolName + "、poolSize:" + map.get(poolName).getCorePoolSize()
                    + "、CompletedTaskCount:" + map.get(poolName).getCompletedTaskCount()
                    + "、ActiveCount:" + map.get(poolName).getActiveCount());
        }
    }
}
