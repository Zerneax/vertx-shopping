package org.demo.vertx.models;

import io.netty.util.CharsetUtil;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.MessageCodec;
import io.vertx.core.json.JsonObject;

public class CodecOrder implements MessageCodec<Order, String> {

    @Override
    public void encodeToWire(Buffer buffer, Order order) {
        byte[] bytes = order.toString().getBytes(CharsetUtil.UTF_8);
        final JsonObject orderJson = new JsonObject();
        orderJson.put("order", order);

        buffer.appendInt(bytes.length);
        buffer.appendString(orderJson.encode());
    }

    @Override
    public String decodeFromWire(int i, Buffer buffer) {
        return null;
    }

    @Override
    public String transform(Order myPOJO) {
        return myPOJO.toString();
    }

    @Override
    public String name() {
        return "mypojoencoder1";
    }

    @Override
    public byte systemCodecID() {
        return -1;
    }
}
