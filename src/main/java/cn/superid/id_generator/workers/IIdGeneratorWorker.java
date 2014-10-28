package cn.superid.id_generator.workers;

import cn.superid.id_generator.exceptions.IdGeneratorException;

import java.util.Map;

/**
 * 用来产生ID的服务
 * Created by 维 on 2014/8/29.
 */
public interface IIdGeneratorWorker {
    public String nextId(final String tableName,final Map<String, Object> record) throws IdGeneratorException;

    public String nextId(String group) throws IdGeneratorException;
}
