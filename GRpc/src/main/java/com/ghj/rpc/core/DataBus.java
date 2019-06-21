package com.ghj.rpc.core;

import java.util.HashMap;

/**
 * @author GeHejun
 */
public class DataBus {
    public static HashMap hashMap = new HashMap(16);

    public static void inData(String key, Object o) {
        hashMap.put(key, o);
    }

    public static Object outData(String key, int timeOut) {
        long startTime = System.currentTimeMillis();
        while (!hashMap.containsKey(key)) {
            if (System.currentTimeMillis() - startTime > timeOut) {
                throw new RuntimeException("RequestId: "+key+" invoke time out");
            }
        }
        return hashMap.get(key);
    }
}
