package com.chrisxyq.dynamicthreadpoolstarter.common;


import com.chrisxyq.dynamicthreadpoolstarter.dto.ThreadPoolParam;
import com.chrisxyq.dynamicthreadpoolstarter.util.SpringUtil;

import java.util.concurrent.*;

/**
 * 封装的线程池
 * 增加属性：线程池名称、用于拉取动态的线程池参数变化
 * 重写execute方法：拉取动态的线程池参数变化、增加metric埋点，包括拒绝策略以及主线程执行
 */
public class MetricThreadPoolExecutor extends ThreadPoolExecutor {
    private String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }

    private String name;

    public MetricThreadPoolExecutor(String name, int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
        this.setName(name);
    }

    public MetricThreadPoolExecutor(String name, int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
        this.setName(name);
    }

    public MetricThreadPoolExecutor(String name, int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);
        this.setName(name);
    }

    public MetricThreadPoolExecutor(String name, int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
        this.setName(name);
    }

    @Override
    public void execute(Runnable command) {
        ThreadPoolParamService paramService = SpringUtil.getBean(ThreadPoolParamService.class);
        ThreadPoolParam param = paramService.getThreadPoolParam(this.getName());
        this.setCorePoolSize(param.getPoolSize());
        this.setMaximumPoolSize(param.getMaxPoolSize());
        super.execute(command);
    }
}
