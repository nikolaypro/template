package com.mascot.service.controller.report;

/**
 * Created by Nikolay on 18.10.2016.
 */

import com.mascot.service.controller.user.UserRecord;

import java.util.List;

/**
 * Class for report with rows only
 */
public class ReportDataResultRecord {
    public List rows;

    public ReportDataResultRecord() {
    }

    public ReportDataResultRecord(List rows) {
        this.rows = rows;
    }
}
