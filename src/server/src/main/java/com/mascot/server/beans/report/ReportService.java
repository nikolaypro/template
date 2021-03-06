package com.mascot.server.beans.report;

import com.mascot.server.common.ProgressManager;
import com.mascot.server.model.User;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * Created by Nikolay on 12.10.2016.
 */
public interface ReportService {
    String NAME = "ReportService";

    byte[] usersReport();

    List<User> getUsers();

    List<SalaryReportItem> getSalary(ZonedDateTime from, ZonedDateTime to, ProgressManager progressManager);

    List<SalaryReportWithSubTypeItem> getSalaryWithSubType(ZonedDateTime from, ZonedDateTime to, ProgressManager progressManager);

    List<SalaryReportGroupItem> getSalaryGrouped(ZonedDateTime from, ZonedDateTime to, ProgressManager progressManager);

    List<SalaryInvestigationSubTypeItem> getSalaryInvestigation(ZonedDateTime from, ZonedDateTime to, ProgressManager progressManager);

}
