package io.choerodon.devops.api.vo.sonar;

import java.util.List;

/**
 * Created by Sheep on 2019/5/6.
 */
public class SonarComponent {

    private Component component;
    private List<SonarPeriod> periods;
    private String analysisDate;

    public String getAnalysisDate() {
        return analysisDate;
    }

    public void setAnalysisDate(String analysisDate) {
        this.analysisDate = analysisDate;
    }

    public Component getComponent() {
        return component;
    }

    public void setComponent(Component component) {
        this.component = component;
    }

    public List<SonarPeriod> getPeriods() {
        return periods;
    }

    public void setPeriods(List<SonarPeriod> periods) {
        this.periods = periods;
    }
}
