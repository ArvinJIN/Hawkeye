package com.king.hawkeye.remote.protocal;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Output;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufOutputStream;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by King on 16/3/31.
 */
public class KryoEncoder {
    private static final Logger LOG = LogManager.getLogger(KryoEncoder.class);
    private static final byte[] LENGTH_PLACEHOLDER = new byte[4];
    Kryo kryo;

    public KryoEncoder() {
        kryo = new Kryo();
    }

    protected void encode(Object obj, ByteBuf out) {
        Output output = null;
        try {
            int lengthPos = out.writerIndex();
            out.writeBytes(LENGTH_PLACEHOLDER);
            ByteBufOutputStream bos = new ByteBufOutputStream(out);
            output = new Output(bos);
            kryo.writeClassAndObject(output, obj);
            System.out.println("output bytes : " + output.toBytes() + ", size : " + output.toBytes().length);
            out.writeBytes(output.toBytes());

            int bodyLength = out.writerIndex() - lengthPos - 4;
            out.setInt(lengthPos, bodyLength);
        } catch (Exception e) {
            LOG.error(e);
        } finally {
            output.close();
        }
    }
}
