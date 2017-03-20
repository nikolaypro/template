package com.mascot.server.beans.integration;

import com.mascot.server.common.site.SiteSettings;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;

/**
 * Created by Nikolay on 20.03.2017.
 */
public interface SiteIntegrationService {
    String NAME = "SiteIntegrationService";

    void synchronizeNewUsers(SiteSettings settings);
}
