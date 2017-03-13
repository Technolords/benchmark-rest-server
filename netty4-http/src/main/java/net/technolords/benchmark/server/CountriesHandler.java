package net.technolords.benchmark.server;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import net.technolords.benchmark.resource.ResourceManager;

public class CountriesHandler extends ChannelInboundHandlerAdapter {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    private static final String JSON_CONTENT_TYPE = "application/json";
    private static String BUFFERED_RESPONSE;

    public CountriesHandler() {
        try {
            BUFFERED_RESPONSE = ResourceManager.getCountriesAsJsonString();
        } catch (IOException e) {
            LOGGER.error("Problem reading buffered response", e);
        }
    }


    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        LOGGER.debug("Channel read complete...");
        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        LOGGER.debug("Channel read, context: {} -> msg: {}", ctx, msg);
        if (msg instanceof HttpRequest) {
            HttpRequest httpRequest = (HttpRequest) msg;
            LOGGER.debug("Request: {}", httpRequest);
            FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, Unpooled.wrappedBuffer(BUFFERED_RESPONSE.getBytes()));
            response.headers().set(HttpHeaderNames.CONTENT_TYPE, JSON_CONTENT_TYPE);
            response.headers().setInt(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes());
            ctx.write(response);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        LOGGER.error("Oops", cause);
        ctx.close();
    }
}
