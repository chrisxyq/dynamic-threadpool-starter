package com.chrisxyq.dynamicthreadpoolstarter.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 业务方实现
 */
@Component
public class ThreadPoolConfigDemo {
    @Autowired
    @Qualifier("abortPolicyExecutor")
    ThreadPoolExecutor abortPolicyExecutor;

    @Autowired
    @Qualifier("callerRunsPolicyExecutor")
    ThreadPoolExecutor callerRunsPolicyExecutor;

    /**
     * 拒绝策略为抛出异常
     * 若抛出拒绝策略异常，则在metric埋点或者发邮件告警
     *
     * @throws InterruptedException
     */
    public void executeAbortPolicy() throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            try {
                Thread.sleep(1000);
                abortPolicyExecutor.execute(() -> {
                    try {
                        System.out.println("abortPolicyExecutor.getCorePoolSize():"+
                                abortPolicyExecutor.getCorePoolSize()
                                +"、Thread.currentThread().getName():" +
                                Thread.currentThread().getName() + "、current index:" + finalI);
                        Thread.sleep(10000);
                    } catch (Exception e) {
                        System.out.println("Exception");
                    }
                });
            } catch (RejectedExecutionException e) {
                //todo：拒绝策略为抛出异常，则在metric埋点或者发邮件告警
                System.out.println("execute rejected, current index:" + finalI);
                //Metrics.withTag("poolname", "abortPolicyExecutor")
                //        .recordMany(MetricConstant.SCENARIO_EXECUTOR_REJECTED, 1);
            }
        }
        //Thread.sleep(1000000);
    }

    /**
     * 由调用线程执行
     * 若由调用线程执行，则在metric埋点或者发邮件告警
     *
     * @throws InterruptedException
     */
    public void executeCallerRunsPolicy() throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            Thread.sleep(1000);
            callerRunsPolicyExecutor.execute(() -> {
                try {
                    if(Thread.currentThread().getName().equals("main")){
                        //todo：任务由主线程执行，埋点做监控
                        System.out.println("executed by main! current index:" + finalI);
                        //Metrics.withTag("poolname", "callerRunsPolicyExecutor")
                        //        .recordMany("executebymain",1);
                    }else{
                        System.out.println("Thread.currentThread().getName():" +
                                Thread.currentThread().getName() + "、current index:" + finalI);
                    }
                    Thread.sleep(10000);
                } catch (Exception e) {
                    System.out.println("Exception");
                }
            });
        }
        Thread.sleep(1000000);
    }
}
