package com.king.hawkeye.server.protocal;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Created by King on 16/3/31.
 */
public class KryoDecoder {

    private static final Log LOG = LogFactory.getLog(KryoDecoder.class);

    private Kryo kryo;

    public KryoDecoder() {
        kryo = new Kryo();
    }

    protected Object decode(ByteBuf in) throws Exception {
        Input input = null;
        try {
            int objectSize = in.readInt();
            ByteBuf objBuf = in.slice(in.readerIndex(), objectSize);
            byte[] objArray = new byte[objectSize];
            objBuf.readBytes(objArray);

            input = new Input(objArray);
            Object obj = kryo.readClassAndObject(input);
            return obj;
        } catch (Exception e) {
            LOG.error(e);
            throw e;
        } finally {
            input.close();
        }
    }


    public static void main(String[] args) {
        KryoDecoder decoder = new KryoDecoder();
        KryoEncoder encoder = new KryoEncoder();
        String str = "abc";
        ByteBuf buf = Unpooled.buffer();
        encoder.encode(str, buf);
        Object obj = null;
        try {
            obj = decoder.decode(buf);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(obj instanceof String);
        System.out.println(obj);
    }
}
