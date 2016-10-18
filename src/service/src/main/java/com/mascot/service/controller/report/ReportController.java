package com.mascot.service.controller.report;

import com.mascot.server.beans.report.ReportService;
import com.mascot.service.controller.AbstractController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

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
}
