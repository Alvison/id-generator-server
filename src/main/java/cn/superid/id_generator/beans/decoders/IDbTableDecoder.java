package cn.superid.id_generator.beans.decoders;

import cn.superid.id_generator.beans.DbTableInfo;
import org.jsoup.nodes.Element;

/**
 * Created by 维 on 2014/9/3.
 */
public interface IDbTableDecoder {
    public DbTableInfo decodeFromServerConfig(Element ele);
}
