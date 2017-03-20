package com.mascot.server.beans.integration;

import com.mascot.server.beans.AbstractMascotService;
import com.mascot.server.beans.integration.dto.SiteResponse;
import com.mascot.server.beans.integration.dto.SiteResultType;
import com.mascot.server.beans.integration.dto.SiteUser;
import com.mascot.server.common.site.SiteHttp;
import com.mascot.server.common.site.SiteHttpException;
import com.mascot.server.common.site.SiteSettings;
import com.mascot.server.model.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by Nikolay on 20.03.2017.
 */
@Service(SiteIntegrationService.NAME)
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class SiteIntegrationServiceImpl extends AbstractMascotService implements SiteIntegrationService {
    private static String USER_URL = "users";

    @Override
    public void synchronizeNewUsers(SiteSettings settings) {
        synchronize(settings, EntityType.USER, EntityActionType.INSERT, USER_URL, SiteUser::build);
    }

    private <EntityClass extends Identified & Versioned, SiteClass> void synchronize(SiteSettings settings,
                                                                                     EntityType entityType,
                                                                                     EntityActionType actionType,
                                                                                     String url,
                                                                                     Function<EntityClass, SiteClass> convert) {
        final ChangesFacade<EntityClass> changesFacade = new ChangesFacade<>(em, entityType);
        final List<EntityClass> entities = changesFacade.find(actionType);
        final List<SiteClass> converted = entities.stream().map(convert).collect(Collectors.toList());

        if (!converted.isEmpty()) {
            try {
                final SiteHttp<List<SiteClass>, SiteResponse> http = new SiteHttp<>(settings);
                final SiteResponse siteResponse = http.doPost(url, converted, SiteResponse.class);
                if (siteResponse == null || !SiteResultType.SUCCESS.equals(siteResponse.status)) {
                    logger.error("Unable synch " + entityType + ": response status: '" + (siteResponse != null ? siteResponse.status : null) + "'");
                }
                changesFacade.done(entities, actionType);
            } catch (SiteHttpException e) {
                logger.error("Unable synch " + entityType, e);
            }
        }
    }
}
