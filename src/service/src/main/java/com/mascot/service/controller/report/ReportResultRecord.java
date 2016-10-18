package com.mascot.service.controller.report;

import com.mascot.service.controller.common.ResultRecord;

/**
 * Created by Nikolay on 17.10.2016.
 */
public class ReportResultRecord {
    public byte[] reportContent;

    public ReportResultRecord(byte[] reportContent) {
        this.reportContent = reportContent;
    }

}
