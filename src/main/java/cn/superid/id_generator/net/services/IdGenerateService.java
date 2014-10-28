package cn.superid.id_generator.net.services;

import cn.superid.id_generator.exceptions.IdGeneratorException;
import cn.superid.id_generator.net.ServiceHolder;
import cn.superid.id_generator.utils.Logger;
import cn.superid.net.rpc.IRpcService;
import cn.superid.net.rpc.RpcChannel;
import cn.superid.net.rpc.annotations.RpcMethod;
import cn.superid.net.rpc.annotations.RpcService;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by 维 on 2014/9/19.
 */
@RpcService("id_generate")
public class IdGenerateService implements IRpcService {

    @RpcMethod
    public String nextId(RpcChannel channel, String table, JSONObject record) {
        // TODO: check errors
        Map<String, Object> recordMap = new HashMap<>();
        if (record != null) {
            for (String key : record.keySet()) {
                recordMap.put(key, record.get(key));
            }
        }
//        Future future = channel.callRpc("clientService", "getHi", "server superId");
        try {
            return ServiceHolder.getIdGeneratorWorker().nextId(table, recordMap);
        } catch (IdGeneratorException e) {
            Logger.error("id generate error", e);
            return null;
        }
    }
}
