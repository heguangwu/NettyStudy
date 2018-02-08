package study.netty.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * Author: HeGuangwu (heguangwu@163.com)
 * Description:
 * Date: Create in 2018/02/07
 * Modified By:
 */
public class UserMessageDecoder extends LengthFieldBasedFrameDecoder {
    public static final int HEADER_SIZE = 4;
    public UserMessageDecoder() {
        super(1024*1024, 0, 4);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        ByteBuf obj = (ByteBuf) super.decode(ctx, in);
        if(obj.readableBytes() < HEADER_SIZE) {
            System.out.println("not enough head length");
        }
        int len = obj.readInt();
        System.out.println("decode length: " + len);
        UserMessage msg = new UserMessage();
        int nameLen = obj.readByte();
        System.out.println("name length: " + nameLen);
        byte[] name = new byte[nameLen];
        obj.readBytes(name);
        msg.setName(new String(name));
        msg.setId(obj.readInt());
        int phoneLen = obj.readByte();
        System.out.println("phone length: " + phoneLen);
        byte[] phone = new byte[phoneLen];
        obj.readBytes(phone);
        msg.setPhone(new String(phone));
        msg.setAge(obj.readShort());
        return msg;
    }
}
