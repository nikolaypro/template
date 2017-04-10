package com.mascot.server.beans.integration;

import com.mascot.server.model.IntegrationLog;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * Created by Николай on 02.03.2017.
 */
public interface IntegrationService {
    String NAME = "IntegrationService";
    void synchronizeSiteUsers();

    List<IntegrationLog> getLog(ZonedDateTime dateTime);

}
