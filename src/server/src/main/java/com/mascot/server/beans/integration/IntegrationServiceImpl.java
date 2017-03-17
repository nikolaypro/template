package com.mascot.server.beans.integration;

import com.mascot.server.beans.AbstractMascotService;
import com.mascot.server.beans.integration.dto.SiteResponse;
import com.mascot.server.beans.integration.dto.SiteResultType;
import com.mascot.server.beans.integration.dto.SiteUser;
import com.mascot.server.beans.setting.SettingService;
import com.mascot.server.common.site.SiteHttp;
import com.mascot.server.common.site.SiteHttpException;
import com.mascot.server.common.site.SiteSettings;
import com.mascot.server.model.User;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Николай on 02.03.2017.
 */
@Service(IntegrationService.NAME)
@Transactional(propagation = Propagation.REQUIRED)
public class IntegrationServiceImpl extends AbstractMascotService implements IntegrationService {

    @Inject
    private SettingService settingService;

    private String USER_URL = "users";

    public void synchSite() {
        logger.info("Start synch with site");
        SiteSettings settings = SiteSettings.build(settingService.getSettings());
        synchUsers(settings);
    }

    private void synchUsers(SiteSettings settings) {
        final List<SiteUser> users =
                em.createQuery("select u from User u left join fetch u.roles", User.class)
                .getResultList()
                .stream()
                .map(SiteUser::build).collect(Collectors.toList());
        final SiteHttp<List<SiteUser>, SiteResponse> http = new SiteHttp<>(settings);
        try {
            final SiteResponse siteResponse = http.doPost(USER_URL, users, SiteResponse.class);
            if (siteResponse == null || !SiteResultType.SUCCESS.equals(siteResponse.status)) {
                logger.error("Unable synch users: response status: '" + (siteResponse != null ? siteResponse.status : null) + "'");
            }
        } catch (SiteHttpException e) {
            logger.error("Unable synch users", e);
        }
    }

}
