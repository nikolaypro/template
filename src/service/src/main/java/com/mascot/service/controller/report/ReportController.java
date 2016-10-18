package com.mascot.service.controller.report;

import com.mascot.server.beans.report.ReportService;
import com.mascot.server.model.User;
import com.mascot.service.controller.AbstractController;
import com.mascot.service.controller.user.UserRecord;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
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

}
