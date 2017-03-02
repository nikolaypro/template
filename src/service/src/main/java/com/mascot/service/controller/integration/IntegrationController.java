package com.mascot.service.controller.integration;

import com.mascot.server.beans.integration.IntegrationService;
import com.mascot.server.model.Role;
import com.mascot.service.controller.AbstractController;
import com.mascot.service.controller.common.ResultRecord;
import com.mascot.service.controller.job.JobRecord;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.stream.IntStream;

/**
 * Created by Николай on 02.03.2017.
 */
@RestController
@RequestMapping(value = "/integration")
public class IntegrationController extends AbstractController {
    @Inject
    private IntegrationService integrationService;

    @RequestMapping(value = "/site-synch", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasRole('" + Role.ADMIN + "') or hasRole('" + Role.REGULAR + "')")
    public ResultRecord synchSite() {
        integrationService.synchSite();
        return ResultRecord.success();
    }

}
