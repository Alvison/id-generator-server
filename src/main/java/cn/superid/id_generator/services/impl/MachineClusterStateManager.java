package cn.superid.id_generator.services.impl;

import cn.superid.id_generator.beans.ClusterMetaInfo;
import cn.superid.id_generator.beans.DbTableInfo;
import cn.superid.id_generator.beans.ServerGroup;
import cn.superid.id_generator.beans.ServerMigration;
import cn.superid.id_generator.beans.decoders.IDbTableDecoder;
import cn.superid.id_generator.beans.decoders.IServerGroupXmlDecoder;
import cn.superid.id_generator.beans.decoders.IServerMigrationDecoder;
import cn.superid.id_generator.services.IMachineClusterStateManager;
import cn.superid.id_generator.utils.ConfigUtil;
import cn.superid.id_generator.utils.Logger;
import cn.superid.utils.FileUtil;
import cn.superid.utils.ListUtil;
import cn.superid.utils.StringUtil;
import com.google.common.base.Function;
import com.google.inject.Inject;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * Created by 维 on 2014/8/29.
 */
@Component
public class MachineClusterStateManager implements IMachineClusterStateManager, Watcher {
    protected ClusterMetaInfo clusterMetaInfo;
    /**
     * 本地缓存的最后修改时间
     */
    protected Date lastUpdateCacheTime = null;
    @Autowired
    protected IServerGroupXmlDecoder serverGroupXmlDecoder;
    @Autowired
    protected IServerMigrationDecoder serverMigrationDecoder;
    @Autowired
    protected IDbTableDecoder dbTableDecoder;

    public MachineClusterStateManager() {
    }

    @Inject
    public MachineClusterStateManager(IServerGroupXmlDecoder serverGroupXmlDecoder, IServerMigrationDecoder serverMigrationDecoder, IDbTableDecoder dbTableDecoder) {
        this.serverGroupXmlDecoder = serverGroupXmlDecoder;
        this.serverMigrationDecoder = serverMigrationDecoder;
        this.dbTableDecoder = dbTableDecoder;
    }

    private ZooKeeper zk = null;

    private CountDownLatch connectedSemaphore = new CountDownLatch(1);


    /**
     * 收到来自Server的Watcher通知后的处理。
     */
    @Override
    public void process(WatchedEvent event) {
        System.out.println("收到事件通知：" + event.getState() + "\n");
        if (Event.KeeperState.SyncConnected == event.getState()) {
            connectedSemaphore.countDown();
        }

    }

    /**
     * 创建ZK连接
     *
     * @param connectString  ZK服务器地址列表
     * @param sessionTimeout Session超时时间
     */
    public void createConnection(String connectString, int sessionTimeout) {
        this.releaseConnection();
        try {
            zk = new ZooKeeper(connectString, sessionTimeout, this);
            connectedSemaphore.await();
        } catch (InterruptedException e) {
            Logger.error("连接创建失败，发生 InterruptedException", e);
        } catch (IOException e) {
            Logger.error("连接创建失败，发生 IOException", e);
        }
    }

    /**
     * 关闭ZK连接
     */
    public void releaseConnection() {
        if (this.zk != null && this.zk.getState().isConnected()) {
            try {
                this.zk.close();
            } catch (InterruptedException e) {
                // ignore
                e.printStackTrace();
            }
        }
    }

    /**
     * 创建节点
     *
     * @param path 节点path
     * @param data 初始数据内容
     * @return
     */
    public boolean createPath(String path, String data) {
        try {
            System.out.println("节点创建成功, Path: "
                    + this.zk.create(path, //
                    utf8Bytes(data), //
                    ZooDefs.Ids.OPEN_ACL_UNSAFE, //
                    CreateMode.EPHEMERAL)
                    + ", content: " + data);
        } catch (KeeperException e) {
            System.out.println("节点创建失败，发生KeeperException");
            e.printStackTrace();
        } catch (InterruptedException e) {
            System.out.println("节点创建失败，发生 InterruptedException");
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 读取指定节点数据内容
     *
     * @param path 节点path
     * @return
     */
    public String readData(String path) {
        try {
            System.out.println("获取数据成功，path：" + path);
            return new String(this.zk.getData(path, false, null), Charset.forName("UTF-8"));
        } catch (KeeperException e) {
            System.out.println("读取数据失败，发生KeeperException，path: " + path);
            e.printStackTrace();
            return "";
        } catch (InterruptedException e) {
            System.out.println("读取数据失败，发生 InterruptedException，path: " + path);
            e.printStackTrace();
            return "";
        }
    }

    public boolean existsPath(String path) {
        try {
            return this.zk.exists(path, false) != null;
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    private byte[] utf8Bytes(String str) {
        if (str == null) {
            return null;
        }
        return str.getBytes(Charset.forName("UTF-8"));
    }

    /**
     * 更新指定节点数据内容
     *
     * @param path 节点path
     * @param data 数据内容
     * @return
     */
    public boolean writeData(String path, String data) {
        try {
            System.out.println("更新数据成功，path：" + path + ", stat: " +
                    this.zk.setData(path, utf8Bytes(data), -1));
        } catch (KeeperException e) {
            System.out.println("更新数据失败，发生KeeperException，path: " + path);
            e.printStackTrace();
        } catch (InterruptedException e) {
            System.out.println("更新数据失败，发生 InterruptedException，path: " + path);
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public ClusterMetaInfo getClusterMetaInfoFromRemote() {
        // 从元信息服务中获取
        // FIXME: 把从zookeeper中读取配置的部分抽取出来，连接配置也写入配置文件中，不要硬编码
        try {
            String connStr = ConfigUtil.getConfigFromEnvOrProperties("zookeeper.url");
            int timeout = Integer.valueOf(ConfigUtil.getConfigFromEnvOrProperties("zookeeper.timeout"));
            if (this.zk == null || !this.zk.getState().isAlive()) {
                this.createConnection(connStr, timeout);
            }
            String path = "/db_cluster_config";
            Document doc = FileUtil.loadXmlResource("servers.xml"); // FIXME: for test
//            if (!existsPath(path)) {
//                Document doc = FileUtil.loadXmlResource("servers.xml");
//                createPath(path, doc.html());
//            }
//            String configXml = readData(path);
//            Document doc = Jsoup.parse(configXml, "", Parser.xmlParser());
            List<ServerGroup> groups = ListUtil.map(doc.select("groups group"), new Function<Element, ServerGroup>() {
                @Override
                public ServerGroup apply(Element groupEle) {
                    return serverGroupXmlDecoder.decodeFromServerConfig(groupEle);
                }
            });
            List<ServerMigration> migrations = ListUtil.map(doc.select("migrations migration"), new Function<Element, ServerMigration>() {
                @Override
                public ServerMigration apply(Element element) {
                    return serverMigrationDecoder.decodeFromServerConfig(element);
                }
            });
            List<DbTableInfo> tables = ListUtil.map(doc.select("tables table"), new Function<Element, DbTableInfo>() {
                @Override
                public DbTableInfo apply(Element element) {
                    return dbTableDecoder.decodeFromServerConfig(element);
                }
            });
            ClusterMetaInfo clusterMetaInfo = new ClusterMetaInfo();
            clusterMetaInfo.setGroups(groups);
            clusterMetaInfo.setMigrations(migrations);
            clusterMetaInfo.setTables(tables);
            return clusterMetaInfo;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            releaseConnection();
        }
    }

    @Override
    public List<ServerGroup> getServerGroups() {
        ClusterMetaInfo clusterMetaInfo = this.getClusterMetaInfo();
        if (clusterMetaInfo == null) {
            return null;
        }
        return clusterMetaInfo.getGroups();
    }

    @Override
    public ClusterMetaInfo getClusterMetaInfo() {
        if (clusterMetaInfo == null) {
            this.reloadClusterMetaInfoCache(getClusterMetaInfoFromRemote());
        }
        return this.clusterMetaInfo;
    }

    @Override
    public List<DbTableInfo> getDbTableInfos() {
        ClusterMetaInfo clusterMetaInfo = this.getClusterMetaInfo();
        if (clusterMetaInfo == null) {
            return null;
        }
        return clusterMetaInfo.getTables();
    }

    @Override
    public ServerGroup getServerGroup(final String name) {
        return ListUtil.first(getServerGroups(), new Function<ServerGroup, Boolean>() {
            @Override
            public Boolean apply(ServerGroup serverGroup) {
                return serverGroup != null && StringUtil.isEqualWithTrim(serverGroup.getName(), name);
            }
        });
    }

    @Override
    public synchronized void reloadClusterMetaInfoCache(ClusterMetaInfo clusterMetaInfo) {
        this.clusterMetaInfo = clusterMetaInfo;
        this.lastUpdateCacheTime = new Date();
    }

}
