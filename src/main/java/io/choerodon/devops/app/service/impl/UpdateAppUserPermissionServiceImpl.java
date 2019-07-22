package io.choerodon.devops.app.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import io.choerodon.core.convertor.ApplicationContextHelper;
import io.choerodon.core.exception.CommonException;
import io.choerodon.devops.app.service.ApplicationService;
import io.choerodon.devops.app.service.IamService;
import io.choerodon.devops.app.service.UserAttrService;
import io.choerodon.devops.domain.application.repository.DevopsProjectRepository;
import io.choerodon.devops.infra.dto.ApplicationDTO;
import io.choerodon.devops.infra.dto.DevopsProjectDTO;
import io.choerodon.devops.infra.dto.UserAttrDTO;
import io.choerodon.devops.infra.dto.gitlab.MemberDTO;
import io.choerodon.devops.infra.feign.operator.GitlabServiceClientOperator;
import io.choerodon.devops.infra.util.TypeUtil;

/**
 * Created by n!Ck
 * Date: 2018/11/21
 * Time: 16:08
 * Description:
 */
public class UpdateAppUserPermissionServiceImpl extends UpdateUserPermissionService {

    private ApplicationService applicationService;
    private IamService iamService;
    private UserAttrService userAttrService;
    private DevopsProjectRepository devopsProjectRepository;
    private GitlabServiceClientOperator gitlabServiceClientOperator;

    public UpdateAppUserPermissionServiceImpl() {
        this.applicationService = ApplicationContextHelper.getSpringFactory().getBean(ApplicationService.class);
        this.iamService = ApplicationContextHelper.getSpringFactory().getBean(IamService.class);
        this.userAttrService = ApplicationContextHelper.getSpringFactory().getBean(UserAttrService.class);
        this.devopsProjectRepository = ApplicationContextHelper.getSpringFactory()
                .getBean(DevopsProjectRepository.class);
        this.gitlabServiceClientOperator = ApplicationContextHelper.getSpringFactory()
                .getBean(GitlabServiceClientOperator.class);
    }

    @Override
    public Boolean updateUserPermission(Long projectId, Long appId, List<Long> userIds, Integer option) {

        List<Integer> allMemberGitlabIdsWithoutOwner;
        List<Integer> addGitlabUserIds;
        List<Integer> deleteGitlabUserIds;
        List<Integer> updateGitlabUserIds;

        ApplicationDTO applicationDTO = applicationService.baseQuery(appId);
        Integer gitlabProjectId = applicationDTO.getGitlabProjectId();
        DevopsProjectDTO devopsProjectDTO = devopsProjectRepository.baseQueryByProjectId(applicationDTO.getProjectId());
        Integer gitlabGroupId = devopsProjectDTO.getDevopsAppGroupId().intValue();

        // 如果之前对应的gitlab project同步失败时，不进行后续操作
        if (gitlabProjectId == null) {
            throw new CommonException("error.gitlab.project.sync.failed");
        }

        switch (option) {
            // 原来跳过，现在不跳过，需要更新权限表，而且需要去掉原来gitlab中的权限
            case 1:
                updateGitlabUserIds = userAttrService.baseListByUserIds(userIds)
                        .stream().map(e -> TypeUtil.objToInteger(e.getGitlabUserId())).collect(Collectors.toList());
                // 获取项目下所有项目成员的gitlabUserIds，过滤掉项目所有者
                allMemberGitlabIdsWithoutOwner = getAllGitlabMemberWithoutOwner(projectId);

                addGitlabUserIds = new ArrayList<>(updateGitlabUserIds);
                addGitlabUserIds.removeAll(allMemberGitlabIdsWithoutOwner);

                deleteGitlabUserIds = new ArrayList<>(allMemberGitlabIdsWithoutOwner);
                deleteGitlabUserIds.removeAll(updateGitlabUserIds);

                super.updateGitlabUserPermission("app", gitlabGroupId, gitlabProjectId, addGitlabUserIds, deleteGitlabUserIds);
                return true;
            // 原来不跳过，现在跳过，需要删除权限表中的所有人，然后把项目下所有项目成员加入gitlab权限
            case 2:
                // 获取项目下所有项目成员的gitlabUserIds，过滤掉项目所有者
                allMemberGitlabIdsWithoutOwner = getAllGitlabMemberWithoutOwner(projectId);

                addGitlabUserIds = allMemberGitlabIdsWithoutOwner.stream()
                        .filter(e -> !gitlabServiceClientOperator.listMemberByProject(gitlabProjectId).stream()
                                .map(MemberDTO::getUserId).collect(Collectors.toList()).contains(e))
                        .collect(Collectors.toList());

                super.updateGitlabUserPermission("app", gitlabGroupId, gitlabProjectId, addGitlabUserIds, new ArrayList<>());
                return true;
            // 原来不跳过，现在也不跳过，需要更新权限表
            case 3:
                updateGitlabUserIds = userAttrService.baseListByUserIds(userIds).stream()
                        .map(e -> TypeUtil.objToInteger(e.getGitlabUserId())).collect(Collectors.toList());
                List<Integer> currentGitlabUserIds = gitlabServiceClientOperator.listMemberByProject(gitlabProjectId)
                        .stream().map(MemberDTO::getUserId).collect(Collectors.toList());

                addGitlabUserIds = new ArrayList<>(updateGitlabUserIds);
                addGitlabUserIds.removeAll(currentGitlabUserIds);

                deleteGitlabUserIds = new ArrayList<>(currentGitlabUserIds);
                deleteGitlabUserIds.removeAll(updateGitlabUserIds);

                super.updateGitlabUserPermission("app", gitlabGroupId, gitlabProjectId, addGitlabUserIds, deleteGitlabUserIds);
                return true;
            default:
                return true;
        }
    }

    // 获取iam项目下所有的项目成员的gitlabUserId，过滤掉项目所有者
    private List<Integer> getAllGitlabMemberWithoutOwner(Long projectId) {
        return userAttrService.baseListByUserIds(iamService.getAllMemberIdsWithoutOwner(projectId)).stream()
                .map(UserAttrDTO::getGitlabUserId).collect(Collectors.toList()).stream()
                .map(TypeUtil::objToInteger).collect(Collectors.toList());
    }
}
