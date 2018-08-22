package io.choerodon.devops.infra.common.util.enums;

/**
 * Creator: Runge
 * Date: 2018/8/20
 * Time: 20:07
 * Description:
 */
public enum CertificationStatus {
    OPERATING("operating"),
    ACTIVE("active"),
    FAILED("failed"),
    OVERDUE("overdue");

    private String status;

    CertificationStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
