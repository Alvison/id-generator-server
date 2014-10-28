package cn.superid.id_generator.net.handlers;

import cn.superid.net.AbstractWebSocketApiHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

/**
 * Created by 维 on 2014/9/4.
 */
public class DemoHandler extends AbstractWebSocketApiHandler {
    @Override
    public boolean handleTextFrame(ChannelHandlerContext ctx, TextWebSocketFrame frame) throws Exception {
        // Send the uppercase string back.
        String requestText = frame.text();
        System.err.printf("%s received %s%n", ctx.channel(), requestText);
        writeFrame(ctx, requestText.toUpperCase());
        return true;
    }


}
