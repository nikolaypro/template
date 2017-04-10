package com.mascot.service.controller.integration;

import com.mascot.server.beans.integration.IntegrationService;
import com.mascot.server.model.IntegrationActionType;
import com.mascot.server.model.IntegrationLog;
import com.mascot.server.model.Role;
import com.mascot.service.controller.AbstractController;
import com.mascot.service.controller.common.ResultRecord;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Николай on 02.03.2017.
 */
@RestController
@RequestMapping(value = "/integration")
public class IntegrationController extends AbstractController {
    @Inject
    private IntegrationService integrationService;

    @RequestMapping(value = "/site-synch", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasRole('" + Role.ADMIN + "') or hasRole('" + Role.REGULAR + "')")
    public ResultRecord synchSite() {
        integrationService.synchronizeSiteUsers();
        return ResultRecord.success();
    }

    @RequestMapping(value = "/log", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasRole('" + Role.ADMIN + "') or hasRole('" + Role.REGULAR + "')")
    public List<IntegrationLogGroupRecord> getLog(@RequestBody String dateStr) {
        if (dateStr.contains("\"")) {
            // почему то дата приходит в ковычках. Необходимо их удалить.
            dateStr = dateStr.replaceAll("\"", "");
        }
        logger.info("AAAAAAAAAAAAAAAAAAAAAAA" + dateStr);
        final ZonedDateTime date = ZonedDateTime.parse(dateStr);
        List<IntegrationLog> logs = integrationService.getLog(date);
        logger.info("COUNT: " + logs.size());

        final SortedMap<Integer, List<IntegrationLog>> mapLog = groupLog(logs);

        return mapLog.values().stream().map(IntegrationLogGroupRecord::build).collect(Collectors.toList());

/*
        String[] strings1 = {date + ": from server 1", date + ": from server 2", date + ": from server 3",};
        String[] strings2 = {date + ": from server 21", date + ": from server 22", date + ": from server 23",};
        String[] strings3 = {date + ": from server 31", date + ": from server 32", date + ": from server 33",};
        return Arrays.asList(
                new IntegrationLogGroupRecord(Arrays.asList(strings1)),
                new IntegrationLogGroupRecord(Arrays.asList(strings2)),
                new IntegrationLogGroupRecord(Arrays.asList(strings3))
        );
*/
    }

    static SortedMap<Integer, List<IntegrationLog>> groupLog(List<IntegrationLog> logs) {
        int counter = 0;
        final SortedMap<Integer, List<IntegrationLog>> mapLog = new TreeMap<>(Integer::compareTo);
        List<IntegrationLog> lines = new ArrayList<>();
        for (IntegrationLog log : logs) {
            if (IntegrationActionType.START.equals(log.getActionType()) ||
                    IntegrationActionType.END.equals(log.getActionType())) {
                if (containsNotStart(lines)) {
                    if (IntegrationActionType.END.equals(log.getActionType())) {
                        lines.add(log);
                    }
                    mapLog.put(counter++, lines);
                }
                lines = new ArrayList<>();
                if (IntegrationActionType.START.equals(log.getActionType())) {
                    lines.add(log);
                }
            } else {
                if (log.getCount() != null && log.getCount() > 0) {
                    lines.add(log);
                }
            }
        }
        if (containsNotStart(lines)) {
            mapLog.put(counter, lines);
        }
        return mapLog;
    }

    private static boolean containsNotStart(List<IntegrationLog> lines) {
        return lines.stream().anyMatch(e -> !IntegrationActionType.START.equals(e.getActionType()));
    }
}
