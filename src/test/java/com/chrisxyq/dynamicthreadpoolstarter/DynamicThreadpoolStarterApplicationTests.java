package com.chrisxyq.dynamicthreadpoolstarter;

import com.chrisxyq.dynamicthreadpoolstarter.common.ThreadPoolParamService;
import com.chrisxyq.dynamicthreadpoolstarter.test.ThreadPoolConfigDemo;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class DynamicThreadpoolStarterApplicationTests {

    @Autowired
    ThreadPoolConfigDemo   demo;
    @Autowired
    ThreadPoolParamService paramService;
    @Test
    public void paramServiceTest() throws InterruptedException {
        paramService.getThreadPoolParam("abortPolicyExecutor");
        Thread.sleep(50000);
        paramService.getThreadPoolParam("abortPolicyExecutor");
    }
    @Test
    public void executeAbortPolicy() throws InterruptedException {
        demo.executeAbortPolicy();
        Thread.sleep(50000);
        demo.executeAbortPolicy();
        Thread.sleep(500000);
    }
    @Test
    public void executeCallerRunsPolicy() throws InterruptedException {
        demo.executeCallerRunsPolicy();
    }

}
