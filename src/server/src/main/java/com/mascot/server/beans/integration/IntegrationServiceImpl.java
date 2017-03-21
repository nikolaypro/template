package com.mascot.server.beans.integration;

import com.mascot.server.beans.AbstractMascotService;
import com.mascot.server.beans.integration.dto.SiteResponse;
import com.mascot.server.beans.integration.dto.SiteResultType;
import com.mascot.server.beans.integration.dto.SiteUser;
import com.mascot.server.beans.setting.SettingService;
import com.mascot.server.common.site.SiteHttp;
import com.mascot.server.common.site.SiteHttpException;
import com.mascot.server.common.site.SiteSettings;
import com.mascot.server.model.EntityType;
import com.mascot.server.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Николай on 02.03.2017.
 */
@Service(IntegrationService.NAME)
@Transactional(propagation = Propagation.NEVER)
public class IntegrationServiceImpl extends AbstractMascotService implements IntegrationService {

    @Inject
    private SettingService settingService;

    @Inject
    private SiteIntegrationService siteIntegrationService;

    public void synchronizeSiteUsers() {
        logger.info("Start synchronize site users");
        final SiteSettings settings = SiteSettings.build(settingService.getSettings());
        siteIntegrationService.synchronizeNewUsers(settings);
        siteIntegrationService.synchronizeModifiedUsers(settings);
//        siteIntegrationService.synchronizeRemoveUsers(settings);
    }

}
