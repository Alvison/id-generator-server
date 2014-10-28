package cn.superid.id_generator.services;

/**
 * Created by 维 on 2014/9/1.
 */
public interface IDbIdSequenceService {
    /**
     * 获取一个从数据库中产生的自增的唯一ID，用来作为最终ID的前一部分
     *
     * @return
     */
    public long nextUniqueId();
}
