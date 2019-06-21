package com.ghj.rpc.serializer;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
/**
 * @author GeHejun
 * @date 2019-06-21
 */
public class KryoSerializer {
    private static final ThreadLocalKryoFactory factory = new ThreadLocalKryoFactory();
    public static void serialize(Object object, ByteBuf out) {
        Kryo kryo = factory.getKryo();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Output output = new Output(baos,100000);
        kryo.writeClassAndObject(output, object);
        output.flush();
        output.close();
        byte[] b = baos.toByteArray();
        try {
            baos.flush();
            baos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        out.writeBytes(b);
    }
    public static Object deserialize(ByteBuf out) {
        if (out == null) {
            return null;
        }
        Input input = new Input(new ByteBufInputStream(out));
        Kryo kryo = factory.getKryo();
        return kryo.readClassAndObject(input);
    }
}
