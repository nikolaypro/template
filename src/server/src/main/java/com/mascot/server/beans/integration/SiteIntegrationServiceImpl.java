package com.mascot.server.beans.integration;

import com.mascot.common.MascotUtils;
import com.mascot.server.beans.AbstractMascotService;
import com.mascot.server.beans.integration.dto.SiteResponse;
import com.mascot.server.beans.integration.dto.SiteResultType;
import com.mascot.server.beans.integration.dto.SiteUser;
import com.mascot.server.common.ServerUtils;
import com.mascot.server.common.site.SiteHttp;
import com.mascot.server.common.site.SiteHttpException;
import com.mascot.server.common.site.SiteSettings;
import com.mascot.server.model.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Created by Nikolay on 20.03.2017.
 */
@Service(SiteIntegrationService.NAME)
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class SiteIntegrationServiceImpl extends AbstractMascotService implements SiteIntegrationService {
    private static String USER_URL = "users";
    private static String USER_REMOVE_URL = "users/remove";

    private static final String LOAD_USERS = "select distinct e from User e join fetch e.roles where e.id in (:ids)";

    private IntegrationLog createLog(IntegrationActionType type) {
        final IntegrationLog integrationLog = new IntegrationLog();
        integrationLog.setStartDate(getCurrentDateTime());
        integrationLog.setActionType(type);
        em.persist(integrationLog);
        return integrationLog;
    }

    private Date getCurrentDateTime() {
        return Date.from(Instant.now());
    }

    @Override
    public void logStart() {
        createLog(IntegrationActionType.START);
    }

    @Override
    public void logEnd() {
        createLog(IntegrationActionType.END);
    }

    @Override
    public void synchronizeNewUsers(SiteSettings settings) {
        loggableExecute(IntegrationActionType.SEND_NEW_USER, intLog ->
                synchronize(settings, EntityType.USER, EntityActionType.INSERT, USER_URL, SiteUser::build, LOAD_USERS)
        );
    }

    @Override
    public void synchronizeModifiedUsers(SiteSettings settings) {
        loggableExecute(IntegrationActionType.SEND_UPDATE_USER, intLog ->
                synchronize(settings, EntityType.USER, EntityActionType.UPDATE, USER_URL, SiteUser::build, LOAD_USERS)
        );
    }

    @Override
    public void synchronizeRemovedUsers(SiteSettings settings) {
        loggableExecute(IntegrationActionType.SEND_REMOVE_USER, intLog ->
                synchronize(settings, EntityType.USER, EntityActionType.REMOVE, USER_REMOVE_URL, null, null)
        );
    }

    private void loggableExecute(IntegrationActionType type, Function<IntegrationLog, Integer> function) {
        IntegrationLog log = createLog(type);
        Integer count = function.apply(log);
        log.setCount(count);
        log.setEndDate(getCurrentDateTime());
    }

    private <EntityClass extends Identified & Versioned, SiteClass> int synchronize(SiteSettings settings,
                                                                                    EntityType entityType,
                                                                                    EntityActionType actionType,
                                                                                    String url,
                                                                                    Function<EntityClass, SiteClass> convert,
                                                                                    String loadSql) {
        final ChangesFacade changesFacade = new ChangesFacade(em, entityType, actionType);
        final List<Long> ids = changesFacade.find();
        logger.info("Found " + ids.size() + " for send. Entity: " + entityType + ", action: " + actionType);
        final List<SiteClass> converted = getSiteClasses(convert, loadSql, ids);

        if (!converted.isEmpty()) {
            try {
                final SiteHttp<List<SiteClass>, SiteResponse> http = new SiteHttp<>(settings);
                final SiteResponse siteResponse = http.doPost(url, converted, SiteResponse.class);
                if (siteResponse == null || !SiteResultType.SUCCESS.equals(siteResponse.status)) {
                    logger.error("Unable synch " + entityType +
                            ". Response status: '" + (siteResponse != null ? siteResponse.status : null) +
                            "', error message: '" + siteResponse.message + "'");
                }
                changesFacade.done(ids);
            } catch (SiteHttpException e) {
                logger.error("Unable synch " + entityType, e);
            }
        }
        return converted.size();
    }

    private <EntityClass extends Identified & Versioned, SiteClass> List<SiteClass> getSiteClasses(Function<EntityClass, SiteClass> convert, String loadSql, List<Long> ids) {
        if (loadSql == null || convert == null) {
            return new ArrayList<>((Collection<? extends SiteClass>) ids);
        }
        final List<EntityClass> entities = ServerUtils.loadBigCollection(ids,
                subList -> em.createQuery(loadSql).setParameter("ids", ids).getResultList());
        return entities.stream().map(convert).collect(Collectors.toList());
    }
}
