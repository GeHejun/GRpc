package com.ghj.rpc.util;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.ghj.rpc.context.GRpcRequest;
import com.ghj.rpc.context.GRpcResponse;
import io.netty.buffer.ByteBuf;
import org.objenesis.strategy.StdInstantiatorStrategy;

/**
 * 使用Kryo序列化
 *
 * @author GeHejun
 * @date 2019-06-18
 */
public class KryoSerializer {


    /**
     * Kryo本身并不是线程安全的所以使用ThreadLocal保证
     */
    final ThreadLocal<Kryo> kryoLocal = ThreadLocal.withInitial(() -> {
        final Kryo kryo = new Kryo();
        kryo.setInstantiatorStrategy(new Kryo.DefaultInstantiatorStrategy(new StdInstantiatorStrategy()));
        kryo.register(GRpcRequest.class);
        kryo.register(GRpcResponse.class);
        return kryo;
    });

    /**
     * 获取kryo
     *
     * @param
     * @return
     */
    private Kryo getKryo() {
        return kryoLocal.get();
    }

    /**
     * 存放OutPut
     */
    final ThreadLocal<Output> outputLocal = new ThreadLocal<>();

    /**
     * 获取Output并设置初始数组
     *
     * @param bytes
     * @return
     */
    private Output getOutput(ByteBuf bytes) {
        Output output;
        if ((output = outputLocal.get()) == null) {
            output = new Output();
            outputLocal.set(output);
        }
        if (bytes != null) {
            output.setBuffer(bytes.array());
        }
        return output;
    }

    /**
     * 存放InPut
     */
    final ThreadLocal<Input> inputLocal = new ThreadLocal<>();

    /**
     * 获取Input
     *
     * @param byteBuf
     * @return
     */
    private Input getInput(ByteBuf byteBuf) {
        Input input;
        if ((input = inputLocal.get()) == null) {
            input = new Input();
            inputLocal.set(input);
        }
        if (byteBuf != null) {
            input.setBuffer(byteBuf.array());
        }
        return input;
    }


    /**
     * 反序列化
     *
     * @param byteBuf
     * @param <T>
     * @return
     */
    public <T> T deserialize(ByteBuf byteBuf, Class<T> ct) {
        Kryo kryo = getKryo();
        Input input = getInput(byteBuf);
        return kryo.readObjectOrNull(input, ct);
    }

    /**
     * 序列化
     *
     * @param obj
     * @param byteBuf
     */
    public void serialize(Object obj, ByteBuf byteBuf) {
        Kryo kryo = getKryo();
        Output output = getOutput(byteBuf);
        kryo.writeObjectOrNull(output, obj, obj.getClass());
        output.flush();
    }

}
