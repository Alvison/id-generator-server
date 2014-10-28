package cn.superid.id_generator.net.handlers;

import cn.superid.net.AbstractApiEventHandler;
import cn.superid.net.annotations.Event;
import cn.superid.net.beans.Message;
import cn.superid.id_generator.utils.Logger;
import io.netty.channel.ChannelHandlerContext;

/**
 * Created by 维 on 2014/9/9.
 */
public class DemoEventHandler extends AbstractApiEventHandler {
    @Event("ping")
    public void handlePing(ChannelHandlerContext ctx, Message msg) {
        Logger.info("got ping request: " + msg.getData());
        writeText(ctx, "got ping request: " + msg.getData());
    }
}
