package cn.superid.id_generator.net;

import cn.superid.id_generator.workers.IIdGeneratorWorker;

/**
 * Created by 维 on 2014/9/9.
 */
public class ServiceHolder {
    private static IIdGeneratorWorker idGeneratorWorker = null;

    public static void setIdGeneratorWorker(IIdGeneratorWorker _idGeneratorWorker) {
        idGeneratorWorker = _idGeneratorWorker;
    }

    public static IIdGeneratorWorker getIdGeneratorWorker() {
        return idGeneratorWorker;
    }
}
