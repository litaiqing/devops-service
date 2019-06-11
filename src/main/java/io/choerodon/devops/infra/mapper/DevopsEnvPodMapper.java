package io.choerodon.devops.infra.mapper;

import java.util.List;
import java.util.Map;

import io.choerodon.mybatis.common.Mapper;
import org.apache.ibatis.annotations.Param;

import io.choerodon.devops.infra.dataobject.DevopsEnvPodDO;

/**
 * Creator: Runge
 * Date: 2018/4/17
 * Time: 11:53
 * Description:
 */
public interface DevopsEnvPodMapper extends Mapper<DevopsEnvPodDO> {

    List<DevopsEnvPodDO> listAppPod(@Param("projectId") Long projectId,
                                    @Param("envId") Long envId,
                                    @Param("appId") Long appId,
                                    @Param("searchParam") Map<String, Object> searchParam,
                                    @Param("param") String param);
}
