package com.mascot.service.controller.report;

import com.mascot.common.MascotUtils;
import com.mascot.server.beans.ProgressService;
import com.mascot.server.beans.report.ReportService;
import com.mascot.server.beans.report.SalaryReportItem;
import com.mascot.server.beans.report.SalaryReportWithSubTypeItem;
import com.mascot.server.common.ProgressManager;
import com.mascot.server.common.SimpleProgressManager;
import com.mascot.server.model.User;
import com.mascot.service.controller.AbstractController;
import com.mascot.service.controller.user.UserRecord;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Nikolay on 12.10.2016.
 */
@RestController
@RequestMapping(value = "/reports")
public class ReportController extends AbstractController {
    @Inject
    private ReportService reportService;

    @Inject
    private ProgressService progressService;


    @RequestMapping(value = "/users", method = RequestMethod.POST)
    @ResponseBody
    public ReportResultRecord createUsers() {
//        logger.info("reports/users!!");
        final byte[] reportContent = reportService.usersReport();
        return new ReportResultRecord(reportContent);
    }

    @RequestMapping(value = "/users-data", method = RequestMethod.POST)
    @ResponseBody
    public ReportDataResultRecord createUsersData() {
//        logger.info("reports/users!!");
        final List<User> users = reportService.getUsers();
        List<UserRecord> records = users.stream().map(UserRecord::build).collect(Collectors.toList());
        return new ReportDataResultRecord(records);
    }

    @RequestMapping(value = "/salary-data", method = RequestMethod.POST)
    @ResponseBody
    public ReportDataResultRecord createSalaryData(@RequestBody SalaryDataRequestData request) {
//        final ZonedDateTime zoned = ZonedDateTime.parse(date);
        final ZonedDateTime zoned = MascotUtils.toDefaultZonedDateTime(request.date);
        final ZonedDateTime startWeek = MascotUtils.getStartWeek(zoned);
        final ZonedDateTime endWeek = MascotUtils.getEndWeek(zoned);
        final ProgressManager progressManager = new SimpleProgressManager(request.progressId, progressService);
        final List<SalaryReportItem> salaryItems = reportService.getSalary(startWeek, endWeek, progressManager);
        final List<SalaryReportRecord> records = salaryItems.stream()
                .map(SalaryReportRecord::build)
                .collect(Collectors.toList());
        return new ReportDataResultRecord(records);
    }

    @RequestMapping(value = "/salary-data-subtype", method = RequestMethod.POST)
    @ResponseBody
    public ReportDataResultRecord createSalaryDataWithSubtype(@RequestBody SalaryDataRequestData request) {
//        final ZonedDateTime zoned = ZonedDateTime.parse(date);
        final ZonedDateTime zoned = MascotUtils.toDefaultZonedDateTime(request.date);
        final ZonedDateTime startWeek = MascotUtils.getStartWeek(zoned);
        final ZonedDateTime endWeek = MascotUtils.getEndWeek(zoned);
        final ProgressManager progressManager = new SimpleProgressManager(request.progressId, progressService);
        final List<SalaryReportWithSubTypeItem> salaryItems = reportService.getSalaryWithSubType(startWeek, endWeek, progressManager);
        final List<SalaryReportWithSubTypeRecord> records = salaryItems.stream()
                .map(SalaryReportWithSubTypeRecord::buildWithSubTypes)
                .collect(Collectors.toList());
        return new ReportDataResultRecord(records);
    }

    public static class SalaryDataRequestData {
        public Date date;
        public Long progressId;
    }

}
