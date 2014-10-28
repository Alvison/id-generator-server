package cn.superid.id_generator.beans.decoders.impl;

import cn.superid.id_generator.beans.ServerGroup;
import cn.superid.id_generator.beans.ServerState;
import cn.superid.id_generator.beans.decoders.IServerGroupXmlDecoder;
import cn.superid.id_generator.beans.decoders.IServerStateXmlDecoder;
import cn.superid.utils.ListUtil;
import com.google.inject.Inject;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by 维 on 2014/8/30.
 */
@Component
public class ServerGroupXmlDecoder implements IServerGroupXmlDecoder {
    @Autowired
    private IServerStateXmlDecoder serverStateXmlDecoder;

    @Inject
    public ServerGroupXmlDecoder(IServerStateXmlDecoder serverStateXmlDecoder) {
        this.serverStateXmlDecoder = serverStateXmlDecoder;
    }

    @Override
    public ServerGroup decodeFromServerConfig(Element ele) {
        if (ele == null) {
            return null;
        }
        ServerGroup group = new ServerGroup();
        group.setName(ele.select("name").text());
        group.setServers(ListUtil.map(ele.select("servers server"), new com.google.common.base.Function<Element, ServerState>() {
            @Override
            public ServerState apply(Element serverEle) {
                return serverStateXmlDecoder.decodeFromServerConfig(serverEle);
            }
        }));
        return group;
    }

    public ServerGroupXmlDecoder() {
    }
}
