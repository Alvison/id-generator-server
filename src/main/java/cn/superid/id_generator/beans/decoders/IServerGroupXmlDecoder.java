package cn.superid.id_generator.beans.decoders;

import cn.superid.id_generator.beans.ServerGroup;
import org.jsoup.nodes.Element;

/**
 * Created by 维 on 2014/8/30.
 */
public interface IServerGroupXmlDecoder {
    public ServerGroup decodeFromServerConfig(Element ele);
}
