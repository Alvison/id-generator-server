package cn.superid.id_generator.beans.decoders.impl;

import cn.superid.id_generator.beans.ServerMigration;
import cn.superid.id_generator.beans.decoders.IServerMigrationDecoder;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

/**
 * Created by 维 on 2014/9/2.
 */
@Component
public class ServerMigrationDecoder implements IServerMigrationDecoder {
    @Override
    public ServerMigration decodeFromServerConfig(Element ele) {
        if (ele == null) {
            return null;
        }
        ServerMigration serverMigration = new ServerMigration();
        serverMigration.setFrom(Long.valueOf(ele.select("from").text()));
        serverMigration.setTo(Long.valueOf(ele.select("to").text()));
        serverMigration.setGroupName(ele.select("groupName").text());
        return serverMigration;
    }
}
