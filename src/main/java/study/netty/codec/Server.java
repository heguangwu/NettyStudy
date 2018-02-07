package study.netty.codec;

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
                        ch.pipeline().addLast(new UserMessageDecoder())
                                .addLast(new UserMessageEncoder())
                                .addLast(new ServerHandler());
                    }
                });
        try {
            ChannelFuture f = bootstrap.bind(port).sync();
            System.out.println("Server listen on port " + port);
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Server(12345).start();
    }
}
