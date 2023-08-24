package com.chrisxyq.dynamicthreadpoolstarter.common;

import com.chrisxyq.dynamicthreadpoolstarter.dto.ThreadPoolConfigItems;
import com.chrisxyq.dynamicthreadpoolstarter.dto.ThreadPoolParam;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;

/**
 * 通用工具
 * 根据配置中心的线程池名称获得线程池实例
 */
@Component
public class ThreadPoolParamService {
    /**
     * 非监听模式，支持热更新
     */
    //@QConfig("ThreadPoolConfiguration.json")
    private ThreadPoolConfigItems configItems;

    public ThreadPoolParam getThreadPoolParam(String poolName) {
        if (configItems == null || CollectionUtils.isEmpty(configItems.getThreadPoolConfigItems())) {
            return null;
        }
        List<ThreadPoolParam> paramList = configItems.getThreadPoolConfigItems();
        ThreadPoolParam param = paramList.stream().filter(e -> {
            return Objects.equals(e.getPoolName(), poolName);
        }).findFirst().orElse(null);
        System.out.println("param:" + param);
        return param;

    }
}
