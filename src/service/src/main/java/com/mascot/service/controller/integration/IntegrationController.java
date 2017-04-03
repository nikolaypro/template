package com.mascot.service.controller.integration;

import com.mascot.server.beans.UserService;
import com.mascot.server.model.Role;
import com.mascot.service.controller.AbstractController;
import com.mascot.service.controller.integration.dto.IntegrationResponse;
import com.mascot.service.controller.integration.dto.ExtUser;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by Nikolay on 14.03.2017.
 */
@RestController
@RequestMapping(value = "/integration")
public class IntegrationController extends AbstractController {

    @Inject
    private UserService userService;

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasRole('" + Role.ADMIN + "')")
    public IntegrationResponse processUsers(@RequestBody List<ExtUser> extUsers) {
        logger.info("integration/users!! Size: " + extUsers.size());
        return IntegrationResponse.SUCCESS;
    }

    @RequestMapping(value = "/users/remove", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasRole('" + Role.ADMIN + "')")
    public IntegrationResponse processUsersRemove(@RequestBody List<Long> extUsers) {
        logger.info("integration/users/remove!! Size: " + extUsers.size());
        return IntegrationResponse.SUCCESS;
    }

}
