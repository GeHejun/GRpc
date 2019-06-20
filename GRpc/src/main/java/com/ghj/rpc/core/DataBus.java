package com.ghj.rpc.core;

import java.util.HashMap;

public class DataBus {
    public static HashMap hashMap = new HashMap(15);

    public static void inData(String key, Object o) {
        hashMap.put(key, o);
    }

    public static Object outData(String key, int timeOut) {
        long startTime = System.currentTimeMillis();
        while (!hashMap.containsKey(key)) {
            if (System.currentTimeMillis() - startTime > timeOut) {
                throw new RuntimeException("Grpc調用超時");
            }
        }
        return hashMap.get(key);
    }
}
