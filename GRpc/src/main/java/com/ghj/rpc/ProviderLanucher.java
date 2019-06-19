package com.ghj.rpc;

import com.ghj.rpc.core.GrpcDiscoverer;
import com.ghj.rpc.core.GrpcProvider;
import com.ghj.rpc.test.TestConsumerService;

import java.io.IOException;

public class ProviderLanucher {
    public static void main(String[] args) throws IOException {
        GrpcProvider grpcProvider = new GrpcProvider();
        grpcProvider.publish();
        TestConsumerService testConsumerService = new TestConsumerService();
        GrpcDiscoverer.discover();
        testConsumerService.getUser();
    }
}
