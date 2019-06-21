package com.ghj.rpc.serializer;

import com.esotericsoftware.kryo.Kryo;
/**
 * @author GeHejun
 * @date 2019-06-21
 */
public class ThreadLocalKryoFactory extends KryoFactory {
    private final ThreadLocal<Kryo> holder  = new ThreadLocal<Kryo>() {
        @Override
        protected Kryo initialValue() {
            return createKryo();
        }
    };

    public Kryo getKryo() {
        return holder.get();
    }
}
