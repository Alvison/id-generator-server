package cn.superid.id_generator.services.impl;

import cn.superid.id_generator.beans.DbTableInfo;
import cn.superid.id_generator.beans.ServerGroup;
import cn.superid.id_generator.beans.ServerState;
import cn.superid.id_generator.exceptions.IdGeneratorException;
import cn.superid.id_generator.exceptions.NoGroupOfTableException;
import cn.superid.id_generator.exceptions.TableNotFoundInMetaInfoException;
import cn.superid.id_generator.services.IDbRouter;
import cn.superid.id_generator.services.IMachineClusterStateManager;
import cn.superid.id_generator.services.IServerGroupService;
import cn.superid.utils.StringUtil;
import com.google.inject.Inject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 模拟db路由服务的接口实现
 * Created by 维 on 2014/9/3.
 */
@Component
public class MockDbRouter implements IDbRouter {
    @Autowired
    private IMachineClusterStateManager machineClusterStateManager;
    @Autowired
    private IServerGroupService serverGroupService;

    public MockDbRouter() {
    }

    @Inject
    public MockDbRouter(IMachineClusterStateManager machineClusterStateManager, IServerGroupService serverGroupService) {
        this.machineClusterStateManager = machineClusterStateManager;
        this.serverGroupService = serverGroupService;
    }

    @Override
    public long route(String tableName, Map<String, Object> record) throws IdGeneratorException {
        // FIXME: 暂时模拟实现，直接返回配置文件中表所在group的best机器的编号
        DbTableInfo table = serverGroupService.findDbTableInfo(tableName);
        if (table == null) {
            throw new TableNotFoundInMetaInfoException("table " + tableName + " not found in meta info");
        }
        String groupName = table.getGroup();
        ServerGroup group = machineClusterStateManager.getServerGroup(groupName);
        if (group == null) {
            throw new NoGroupOfTableException("table " + tableName + " has no group in meta info");
        }
        ServerState serverState = serverGroupService.selectBestServerFromGroup(group);
        if (serverState == null) {
            return 0;
        }
        return serverState.getId();
    }
}
