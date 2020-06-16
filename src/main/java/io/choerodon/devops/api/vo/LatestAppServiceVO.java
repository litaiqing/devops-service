package io.choerodon.devops.api.vo;

import java.util.Date;

/**
 * @author lihao
 */
public class LatestAppServiceVO {
    private Long id;
    private String name;
    private String code;
    private Long projectId;
    private String projectName;
    private Date lastUpdateDate;

    public Long getId() {
        return id;
    }

    public LatestAppServiceVO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public LatestAppServiceVO setName(String name) {
        this.name = name;
        return this;
    }

    public String getCode() {
        return code;
    }

    public LatestAppServiceVO setCode(String code) {
        this.code = code;
        return this;
    }

    public Long getProjectId() {
        return projectId;
    }

    public LatestAppServiceVO setProjectId(Long projectId) {
        this.projectId = projectId;
        return this;
    }

    public String getProjectName() {
        return projectName;
    }

    public LatestAppServiceVO setProjectName(String projectName) {
        this.projectName = projectName;
        return this;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public LatestAppServiceVO setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
        return this;
    }

    @Override
    public String toString() {
        return "LatestAppServiceVO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", projectId=" + projectId +
                ", projectName='" + projectName + '\'' +
                ", lastUpdateDate=" + lastUpdateDate +
                '}';
    }
}
