package study.netty.channel;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelOutboundHandler;
import io.netty.util.CharsetUtil;

import java.util.concurrent.TimeUnit;

/**
 * Author: HeGuangwu (heguangwu@163.com)
 * Description:
 * Date: Create in 2018/02/06
 * Modified By:
 */
public class ServerLastHandler extends ChannelInboundHandlerAdapter {
    private volatile int tick = 0;
    private ChannelHandlerContext cache = null;
    private volatile boolean timer = false;
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if(cache == null) {
            cache = ctx;
        }
        String in = (String) msg;
        System.out.println("Server Last Handler: " + in);
        /** 这里主要是验证调用ChannelHandlerContext write和Channel write的区别
         * Channel write：从pipeline的最后一个调用，即先调用ServerStarEncoder，再调用ServerPlusEncoder
         * ChannelHandlerContext write：从pipeline的下一个调用，即调用ServerPlusEncoder
         */
//        ctx.writeAndFlush(in);
        ctx.channel().writeAndFlush(in); //Channel write
        //增加定时任务处理测试
        if(!timer) {
            ctx.channel().eventLoop().scheduleAtFixedRate(new Runnable() {
                public void run() {
                    ByteBuf msg = Unpooled.copiedBuffer("rock:"+tick, CharsetUtil.UTF_8);
                    cache.writeAndFlush(msg);
                    tick += 1;
                }
            }, 10, 10, TimeUnit.SECONDS);
            timer = true;
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        //ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
