package com.ghj.rpc.test;

import com.ghj.rpc.annotation.GrcpService;

@GrcpService
public class TestServiceImpl implements TestService {
    @Override
    public User getUser(Integer id) {
        if (10 == id) {
            return new User(10, "JiaXiong");
        }
        return null;
    }
}
