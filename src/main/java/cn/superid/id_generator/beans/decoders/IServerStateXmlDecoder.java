package cn.superid.id_generator.beans.decoders;

import cn.superid.id_generator.beans.ServerState;
import org.jsoup.nodes.Element;

/**
 * Created by 维 on 2014/8/29.
 */
public interface IServerStateXmlDecoder {
    /**
     * @return
     */
    public ServerState decodeFromServerConfig(Element ele);
}
