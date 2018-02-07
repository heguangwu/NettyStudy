package study.netty.channel;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Author: HeGuangwu (heguangwu@163.com)
 * Description:
 * Date: Create in 2018/02/06
 * Modified By:
 */
public class ServerPlusEncoder extends MessageToByteEncoder<String> {

    protected void encode(ChannelHandlerContext ctx, String msg, ByteBuf out) throws Exception {
        System.out.println("EchoServerEncoder");
        String o = msg + " ++++++ ";
        byte[] b = o.getBytes();
        out.writeBytes(b);
    }
}
