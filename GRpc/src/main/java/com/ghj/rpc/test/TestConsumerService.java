package com.ghj.rpc.test;

import com.ghj.rpc.annotation.Quote;

public class TestConsumerService {
    @Quote
    TestService testService;

    public void  getUser() {
        System.out.println(testService.getUser(10));
        System.out.println(testService.getUser(20));
    }
}
