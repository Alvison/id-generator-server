package cn.superid.id_generator.beans.decoders;

import cn.superid.id_generator.beans.ServerMigration;
import org.jsoup.nodes.Element;

/**
 * Created by 维 on 2014/9/2.
 */
public interface IServerMigrationDecoder {
    public ServerMigration decodeFromServerConfig(Element ele);
}
