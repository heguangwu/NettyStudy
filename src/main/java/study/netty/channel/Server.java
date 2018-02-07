package study.netty.channel;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Author: HeGuangwu (heguangwu@163.com)
 * Description:
 * Date: Create in 2018/02/06
 * Modified By:
 */
public class Server {
    private int port;

    public Server(int port) {
        this.port = port;
    }

    public void start() {
        NioEventLoopGroup workers = new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(workers).channel(NioServerSocketChannel.class).
                childHandler(new ChannelInitializer<SocketChannel>() {
                    protected void initChannel(SocketChannel ch) throws Exception {
                        /** 这里主要是验证Handler的调用顺序
                         * 多个InboundHandler: 按顺序调用，即先调用ServerFirstHandler，再调用ServerLastHandler
                         * 多个OutboundHandler：按逆序调用，即先ServerStarEncoder，再ServerPlusEncoder
                         * 如果OutboundHandler是调用ChannelHandlerContext发送，则取下一个（逆序）的OutboundHandler
                         */
                        ch.pipeline().addLast(new ServerPlusEncoder())
                                .addLast(new ServerFirstHandler())
                                .addLast(new ServerLastHandler())
                                .addLast(new ServerStarEncoder());
                    }
                });
        try {
            ChannelFuture f = bootstrap.bind(port).sync();
            System.out.println("Server start and listen on port " + port);
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Server(12345).start();
    }
}
