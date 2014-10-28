package cn.superid.id_generator.net.handlers;

import cn.superid.id_generator.exceptions.IdGeneratorException;
import cn.superid.net.AbstractApiEventHandler;
import cn.superid.id_generator.net.ServiceHolder;
import cn.superid.net.annotations.Event;
import cn.superid.id_generator.net.beans.IdRequest;
import cn.superid.id_generator.net.beans.IdResponse;
import cn.superid.id_generator.utils.Logger;
import io.netty.channel.ChannelHandlerContext;

/**
 * Created by 维 on 2014/9/9.
 */
public class IdGenerateHandler extends AbstractApiEventHandler {
    @Event("id_generate")
    public void handleIdRequest(ChannelHandlerContext ctx, IdRequest idRequest) {
        // TODO: check errors
        if (idRequest == null) {
            return;
        }
        try {
            String id = ServiceHolder.getIdGeneratorWorker().nextId(idRequest.getTable(), idRequest.getRecord());
            IdResponse idResponse = new IdResponse();
            idResponse.setCode(0);
            idResponse.setId(id);
            idResponse.setEventName("id_generated");
            idResponse.setRequestId(idRequest.getRequestId());
            writeJson(ctx, idResponse);
        } catch (IdGeneratorException e) {
            Logger.error("id generate error", e);
        }
    }
}
