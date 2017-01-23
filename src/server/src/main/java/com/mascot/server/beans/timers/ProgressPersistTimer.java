package com.mascot.server.beans.timers;

import com.mascot.server.beans.AbstractMascotService;
import com.mascot.server.common.ProgressManager;
import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by Nikolay on 19.01.2017.
 */
@Component("ProgressPersistTimer")
public class ProgressPersistTimer extends AbstractMascotService {
//    protected final Logger logger = Logger.getLogger(getClass());
//    @Scheduled(fixedDelay = 1000)
    public void run() {
//        logger.info("*** START ***");
        ProgressManager.flush();
//        logger.info("*** END ***");
    }
}
