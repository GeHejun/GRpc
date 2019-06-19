package com.ghj.rpc.test;

import com.ghj.rpc.core.GrpcDiscoverer;

import java.io.IOException;

public class Test {
    public static void main(String[] args) throws IOException {
        GrpcDiscoverer.discover();
        TestConsumerService testConsumerService = new TestConsumerService();
        testConsumerService.getUser();
    }
}
