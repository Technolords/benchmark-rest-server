package net.technolords.benchmark.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import net.technolords.benchmark.config.ConfigurationManager;

public class NettyMain {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    public void configureAndRun() {
        LOGGER.info("Creating server...");
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        int poolSize = ConfigurationManager.getPoolSize();
        EventLoopGroup workerGroup = new NioEventLoopGroup(poolSize);
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new CountriesInitializer());
            int port = ConfigurationManager.getPort();
            Channel channel = serverBootstrap
                    .bind(port)
                    .sync()
                    .channel();
            channel.closeFuture()
                    .sync();
        } catch (InterruptedException e) {
            LOGGER.error("Oops", e);
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        NettyMain nettyMain = new NettyMain();
        nettyMain.configureAndRun();
    }
}
