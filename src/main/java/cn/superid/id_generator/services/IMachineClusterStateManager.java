package cn.superid.id_generator.services;

import cn.superid.id_generator.beans.ClusterMetaInfo;
import cn.superid.id_generator.beans.DbTableInfo;
import cn.superid.id_generator.beans.ServerGroup;

import java.util.List;

/**
 * 用来获取当前数据库服务器状态，要考虑到数据库服务器集群的状态，负载，数量变化等
 * TODO: 暂时机器状态从配置文件中读取，将来要从消息队列中得知是否有变化，以及从zookeeper集群中读取数据库集群元信息
 * Created by 维 on 2014/8/29.
 */
public interface IMachineClusterStateManager {
    /**
     * 从元信息管理服务（比如ZooKeeper）中获取最新的服务器集群元信息
     *
     * @return
     */
    public ClusterMetaInfo getClusterMetaInfoFromRemote();

    public List<ServerGroup> getServerGroups();

    /**
     * TODO: 监控消息队列，从ZooKeeper集群读取数据库集群元信息
     *
     * @return
     */
    public ClusterMetaInfo getClusterMetaInfo();

    public List<DbTableInfo> getDbTableInfos();

    public ServerGroup getServerGroup(String name);

    /**
     * 更新本地缓存
     *
     * @param clusterMetaInfo
     */
    public void reloadClusterMetaInfoCache(ClusterMetaInfo clusterMetaInfo);
}
