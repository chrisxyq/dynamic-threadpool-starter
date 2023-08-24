package com.chrisxyq.dynamicthreadpoolstarter.test;

import com.chrisxyq.dynamicthreadpoolstarter.common.MetricThreadPoolExecutor;
import com.chrisxyq.dynamicthreadpoolstarter.common.ThreadPoolParamService;
import com.chrisxyq.dynamicthreadpoolstarter.dto.ThreadPoolParam;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 业务方实现
 * 服务内的所有线程池定义在此处
 * 由各个业务自行定义实现
 */
@Configuration
public class ThreadPoolConfig {
    @Autowired
    ThreadPoolParamService paramService;


    @Bean
    @Qualifier("abortPolicyExecutor")
    public MetricThreadPoolExecutor abortPolicyExecutor() {
        ThreadPoolParam param = paramService.getThreadPoolParam("abortPolicyExecutor");
        MetricThreadPoolExecutor executor = new MetricThreadPoolExecutor("abortPolicyExecutor", param.getPoolSize(), param.getMaxPoolSize(),
                60, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(param.getQueueSize()),
                new ThreadFactoryBuilder().setNameFormat("abortPolicyExecutor-%d").build(),
                new ThreadPoolExecutor.AbortPolicy());
        return executor;
    }

    /**
     * new ThreadPoolExecutor.CallerRunsPolicy():由调用主线程执行
     *
     * @return
     */
    @Bean
    @Qualifier("callerRunsPolicyExecutor")
    public ThreadPoolExecutor callerRunsPolicyExecutor() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1,
                60, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(1),
                new ThreadFactoryBuilder().setNameFormat("callerRunsPolicyExecutor-%d").build(),
                new ThreadPoolExecutor.CallerRunsPolicy());
        return executor;
    }
}
