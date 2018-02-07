package study.netty.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * Author: HeGuangwu (heguangwu@163.com)
 * Description:
 * Date: Create in 2018/02/07
 * Modified By:
 */
public class UserMessageDecoder extends ByteToMessageDecoder {
    public static final int HEADER_SIZE = 4;

    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if(in.readableBytes() < HEADER_SIZE) {
            System.out.println("not enough head length");
        }
        int len = in.readInt();
        System.out.println("decode length: " + len);
        UserMessage msg = new UserMessage();
        int nameLen = in.readByte();
        System.out.println("name length: " + nameLen);
        byte[] name = new byte[nameLen];
        in.readBytes(name);
        msg.setName(new String(name));
        msg.setId(in.readInt());
        int phoneLen = in.readByte();
        System.out.println("phone length: " + phoneLen);
        byte[] phone = new byte[phoneLen];
        in.readBytes(phone);
        msg.setPhone(new String(phone));
        msg.setAge(in.readShort());
        out.add(msg);
    }
}
