package com.mascot.service.controller.integration;

import com.mascot.common.MascotUtils;
import com.mascot.server.model.IntegrationActionType;
import com.mascot.server.model.IntegrationLog;
import com.mascot.service.controller.common.Localization;
import com.mascot.service.security.MascotSession;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Nikolay on 10.04.2017.
 */
public class IntegrationLogGroupRecord {
    public List<String> lines = new ArrayList<>();

    public IntegrationLogGroupRecord(List<String> lines) {
        this.lines = lines;
    }

    public IntegrationLogGroupRecord() {
    }

    public static IntegrationLogGroupRecord build(List<IntegrationLog> logs) {
        final String sendMsg = Localization.get("integration.send.count", MascotSession.getCurrent().getLocale());
        final String finishMsg = Localization.get("integration.send.finish", MascotSession.getCurrent().getLocale());
        return new IntegrationLogGroupRecord(
                logs.stream()
                        .map(log ->
                                MascotUtils.formatDateTime(log.getStartDate()) + ", " +
                                getActionType(log.getActionType()) +
                                (log.getCount() != null && log.getCount() > 0 ? ", " + sendMsg + ": " + log.getCount() : "") +
                                (log.getEndDate() != null ? ", " + finishMsg + ": " + log.getEndDate() : "") + ", " +
                                (!MascotUtils.isEmpty(log.getComment()) ? ", " + log.getComment() : ""))
                        .collect(Collectors.toList())
        );

    }

    private static String getActionType(IntegrationActionType actionType) {
        return Localization.get("integration." + actionType.name(), MascotSession.getCurrent().getLocale());
    }
}
