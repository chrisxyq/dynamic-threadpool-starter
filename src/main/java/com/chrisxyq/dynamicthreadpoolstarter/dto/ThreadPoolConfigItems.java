package com.chrisxyq.dynamicthreadpoolstarter.dto;

import lombok.Data;

import java.util.List;

@Data
public class ThreadPoolConfigItems {
    private List<ThreadPoolParam> threadPoolConfigItems;
}
