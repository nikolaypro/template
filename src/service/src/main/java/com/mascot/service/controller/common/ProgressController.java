package com.mascot.service.controller.common;

import com.mascot.server.beans.ProgressService;
import com.mascot.server.model.Job;
import com.mascot.server.model.Progress;
import com.mascot.server.model.Role;
import com.mascot.service.controller.AbstractController;
import com.mascot.service.controller.admin.ProgressRecord;
import com.mascot.service.controller.job.JobRecord;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

/**
 * Created by Nikolay on 18.01.2017.
 */
@RestController
@RequestMapping(value = "/progress")
public class ProgressController extends AbstractController {
    @Inject
    private ProgressService progressService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('" + Role.ADMIN + "') or hasRole('" + Role.REGULAR + "')")
    @ResponseBody
    public ProgressRecord get(@PathVariable("id") Long id) {
        return ProgressRecord.build(progressService.get(id));
    }

    @RequestMapping(value = "/start", method = RequestMethod.GET)
    @PreAuthorize("hasRole('" + Role.ADMIN + "') or hasRole('" + Role.REGULAR + "')")
    @ResponseBody
    public Long start() {
        return progressService.start("Initializing ...");
    }

}
