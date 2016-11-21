package com.mascot.service.controller.admin;

import com.mascot.common.ErrorLogger;
import com.mascot.common.MailSender;
import com.mascot.server.common.ServerUtils;
import com.mascot.server.model.Role;
import com.mascot.service.controller.AbstractController;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Николай on 20.11.2016.
 */
@RestController
@RequestMapping(value = "/administration")
public class AdministrationController extends AbstractController {
    @RequestMapping(value = "/salary-logs", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasRole('" + Role.ADMIN + "')")
    public List<String> getLogList() {
        Path path = ServerUtils.getSalaryReportLogPath();
        try {
            return Files.walk(path).map(a -> a.getFileName().toString()).collect(Collectors.toList());
        } catch (IOException e) {
            ErrorLogger.error(logger, "Unable get a list of files", e);;
            return Collections.singletonList("Unable get a list of files");
        }
    }

    @RequestMapping(value = "/salary-load-log", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasRole('" + Role.ADMIN + "')")
    public List<String> getLog(@RequestBody String fileName) {
        Path path = ServerUtils.getSalaryReportLogPath().resolve(fileName);
        try {
            return Collections.singletonList(new String(Files.readAllBytes(path)));
        } catch (IOException e) {
            ErrorLogger.error(logger, "Unable get a log: '" + fileName + "'", e);;
            return Collections.singletonList("Unable get a log: " + e.getMessage());
        }
    }


}
