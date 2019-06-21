package com.ghj.rpc.test;

import com.ghj.rpc.core.ConsumerProxy;
import net.sf.cglib.proxy.Enhancer;

public class Test {
    public static void main(String[] args) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(TestConsumerService.class);
        enhancer.setCallback(new ConsumerProxy());
        TestConsumerService proxy= (TestConsumerService)enhancer.create();
        System.out.println("-----------");
        int count = 10;
        while (count > 0) {
            proxy.getUser();
            count--;
        }

    }
}
