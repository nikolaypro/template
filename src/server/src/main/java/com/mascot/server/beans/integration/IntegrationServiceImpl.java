package com.mascot.server.beans.integration;

import com.mascot.server.beans.AbstractMascotService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Николай on 02.03.2017.
 */
@Service(IntegrationService.NAME)
@Transactional(propagation = Propagation.REQUIRED)
public class IntegrationServiceImpl extends AbstractMascotService implements IntegrationService {

    public void synchSite() {
        logger.info("Start synch with site");
    }

}
