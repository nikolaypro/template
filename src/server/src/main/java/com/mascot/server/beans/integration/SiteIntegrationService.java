package com.mascot.server.beans.integration;

import com.mascot.server.common.site.SiteSettings;
import com.mascot.server.model.IntegrationLog;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import java.time.ZonedDateTime;
import java.util.List;

/**
 * Created by Nikolay on 20.03.2017.
 */
public interface SiteIntegrationService {
    String NAME = "SiteIntegrationService";

    void logStart();

    void logEnd();

    void synchronizeNewUsers(SiteSettings settings);

    void synchronizeModifiedUsers(SiteSettings settings);

    void synchronizeRemovedUsers(SiteSettings settings);
}
