package com.king.hawkeye.remote.protocal;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by King on 16/3/31.
 */
public class KryoDecoder {
    private static final Logger LOG = LogManager.getLogger(KryoDecoder.class);
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
}
