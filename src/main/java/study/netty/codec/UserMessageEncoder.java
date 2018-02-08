package study.netty.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Author: HeGuangwu (heguangwu@163.com)
 * Description:
 * Date: Create in 2018/02/07
 * Modified By:
 */
public class UserMessageEncoder extends MessageToByteEncoder<UserMessage> {
    public static final int NAME_LEN_SIZE = 1;
    public static final int ID_LEN_SIZE = 4;
    public static final int PHONE_LEN_SIZE = 1;
    public static final int AGE_LEN_SIZE = 2;

    /** 将UserMessage消息编码到ByteBuf
     * 编码格式如下：
     * 4B：消息长度（不包含该字段）
     * 1B：name长度
     * name字段
     * 4B：id字段
     * 1B：phone长度
     * phone字段
     * 2B：age字段
     */
    protected void encode(ChannelHandlerContext ctx, UserMessage msg, ByteBuf out) throws Exception {
        byte[] name = msg.getName().getBytes();
        byte[] phone = msg.getPhone().getBytes();
        int len = NAME_LEN_SIZE + name.length + ID_LEN_SIZE + PHONE_LEN_SIZE + phone.length + AGE_LEN_SIZE;
        System.out.println("encode len:" + len);
        out.writeInt(len);
        out.writeByte((byte)name.length);
        out.writeBytes(name);
        out.writeInt(msg.getId());
        out.writeByte((byte)phone.length);
        out.writeBytes(phone);
        out.writeShort(msg.getAge());
    }
}
