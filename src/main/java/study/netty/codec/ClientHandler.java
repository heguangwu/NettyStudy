package study.netty.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * Author: HeGuangwu (heguangwu@163.com)
 * Description:
 * Date: Create in 2018/02/06
 * Modified By:
 */
public class ClientHandler extends SimpleChannelInboundHandler<UserMessage> {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        UserMessage msg = new UserMessage();
        msg.setAge((short)100);
        msg.setPhone("13812345678");
        msg.setId(99999);
        msg.setName("Joh");
        ctx.writeAndFlush(msg);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, UserMessage msg) throws Exception {
        System.out.println("Client recv: " + msg.toString());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }


}
