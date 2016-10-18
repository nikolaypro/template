package com.mascot.server.beans.report;

/**
 * Created by Nikolay on 13.10.2016.
 */
public enum MascotReport {
    USERS_REPORT("users", "users");

    private final String subPackageName;
    private final String reportName;

    MascotReport(String subPackageName, String reportName) {
        this.subPackageName = subPackageName;
        this.reportName = reportName;
    }

    public String getSubPackageName() {
        return subPackageName;
    }

    public String getReportName() {
        return reportName;
    }
}
