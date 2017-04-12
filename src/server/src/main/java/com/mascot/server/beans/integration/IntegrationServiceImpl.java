package com.mascot.server.beans.integration;

import com.mascot.common.MascotUtils;
import com.mascot.server.beans.AbstractMascotService;
import com.mascot.server.beans.ProgressService;
import com.mascot.server.beans.setting.SettingService;
import com.mascot.server.common.ProgressManager;
import com.mascot.server.common.SimpleProgressManager;
import com.mascot.server.common.site.SiteSettings;
import com.mascot.server.model.IntegrationLog;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

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

    @Inject
    private ProgressService progressService;

    public void synchronizeSiteUsers(Long progressId) {
        logger.info("Start synchronize site users");
        siteIntegrationService.logStart();
        final ProgressManager progressManager = new SimpleProgressManager(progressId, progressService);
        final SiteSettings settings = SiteSettings.build(settingService.getSettings());
        progressManager.update("synch.state.new.user", 0.0);
//        MascotUtils.sleep(3);
        siteIntegrationService.synchronizeNewUsers(settings);
        progressManager.update("synch.state.update.user", 40.0);
//        MascotUtils.sleep(3);
        siteIntegrationService.synchronizeModifiedUsers(settings);
        progressManager.update("synch.state.removed.user", 60.0);
//        MascotUtils.sleep(3);
        siteIntegrationService.synchronizeRemovedUsers(settings);
        siteIntegrationService.logEnd();
//        MascotUtils.sleep(3);
        progressManager.finish();
    }

    @Override
    public List<IntegrationLog> getLog(ZonedDateTime date) {
        final Date startDate = MascotUtils.toDate(MascotUtils.getStartDay(date));
        final Date toDate = MascotUtils.toDate(MascotUtils.getEndDay(date));
        return em.createQuery("select e from IntegrationLog e where e.startDate >= :startDate and e.startDate <= :endDate order by e.id")
                .setParameter("startDate", startDate)
                .setParameter("endDate", toDate)
                .getResultList();
    }

}
