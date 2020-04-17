package io.choerodon.devops.app.service;

import java.util.List;

import io.choerodon.devops.api.vo.SonarQubeConfigVO;
import io.choerodon.devops.infra.dto.DevopsCiJobDTO;
import io.choerodon.devops.infra.dto.gitlab.JobDTO;

/**
 * 〈功能简述〉
 * 〈〉
 *
 * @author wanghao
 * @Date 2020/4/3 9:24
 */
public interface DevopsCiJobService {
    /**
     * 创建ci流水线job
     *
     * @param devopsCiJobDTO 创建信息
     * @return 创建结果
     */
    DevopsCiJobDTO create(DevopsCiJobDTO devopsCiJobDTO);

    /**
     * 删除stage下的job
     *
     * @param stageId stageId
     */
    void deleteByStageId(Long stageId);

    /**
     * 查询pipeline下的jobs
     *
     * @param ciPipelineId 流水线id
     * @return 结果
     */
    List<DevopsCiJobDTO> listByPipelineId(Long ciPipelineId);

    /**
     * sonar的连接测试
     */
    Boolean sonarConnect(Long projectId, SonarQubeConfigVO sonarQubeConfigVO);

    /**
     * 查询job日志
     */
    String queryTrace(Long gitlabProjectId, Long jobId);

    /**
     * 重试job
     */
    JobDTO retryJob(Long gitlabProjectId, Long jobId);

    /**
     * 删除流水线下的job
     */
    void deleteByPipelineId(Long ciPipelineId);

    /**
     * 查询maven settings文件内容
     *
     * @param projectId 项目id
     * @param jobId     job id
     * @param sequence  maven构建步骤的序列号
     * @return settings文件内容
     */
    String queryMavenSettings(Long projectId, Long jobId, Long sequence);
}
