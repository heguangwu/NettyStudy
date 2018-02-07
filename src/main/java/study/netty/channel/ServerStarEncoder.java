package study.netty.channel;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

/**
 * Author: HeGuangwu (heguangwu@163.com)
 * Description:
 * Date: Create in 2018/02/06
 * Modified By:
 */
public class ServerStarEncoder extends MessageToMessageEncoder<String> {

    protected void encode(ChannelHandlerContext ctx, String msg, List<Object> out) throws Exception {
        System.out.println("ServerStarEncoder");
        String o = msg + " ****** ";
        out.add(o);
    }
}
