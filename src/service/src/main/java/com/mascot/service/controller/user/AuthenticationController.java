package com.mascot.service.controller.user;

import com.mascot.common.MascotUtils;
import com.mascot.server.beans.UserService;
import com.mascot.server.model.Role;
import com.mascot.server.model.User;
import com.mascot.service.controller.AbstractController;
import com.mascot.service.controller.WebError;
import com.mascot.service.controller.common.TableParams;
import com.mascot.service.controller.common.TableResult;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.Collection;

/**
 * Created by Nikolay on 25.11.2015.
 */
@RestController
//@Controller
@RequestMapping(name = "/api")
public class AuthenticationController extends AbstractController {
    private final Logger logger = Logger.getLogger(getClass());
    @Inject
    private UserService userService;

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    @ResponseBody
    public User authenticate(@RequestBody User user) {
        if (MascotUtils.isEmpty(user.getPassword())) {
            throwInvalidPassword(user);
        }
        final User entityUser = userService.loadUserByLogin(user.getLogin());
        if (entityUser == null) {
            throwInvalidUserName(user);
        }
        final byte[] encode = Base64.encode(user.getPassword().getBytes());

        if (!new String(encode).equals(entityUser.getPassword())) {
            throwInvalidPassword(user);
        }
        logger.info("Successfully login: username = " + user.getLogin() +
                ", roles: [" + StringUtils.collectionToCommaDelimitedString(entityUser.getRoles()) + "]");
        return entityUser;
    }

    private void throwInvalidUserName(User user) {
        final String msg = "Invalid user name: '" + user.getLogin() + "'";
        logger.info(msg);
        throw new AuthenticationCredentialsNotFoundException(msg);
    }

    private void throwInvalidPassword(User user) {
        final String msg = "Invalid password";
        logger.info(msg);
        throw new AuthenticationCredentialsNotFoundException(msg);
    }

/*
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    @ResponseBody
//    @PreAuthorize("hasRole('" + Role.ADMIN + "')")
    public User[] getUsers(*/
/*@RequestBody TableParams params*//*
) {
        final Collection<User> users = userService.getUsers();
        for (int i = 1; i <= 20; i++) {
            final User e = new User();
            e.setFullName("Fake Name " + i);
            e.setLogin("Fake login " + i);
            users.add(e);
        }
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return users.toArray(new User[users.size()]);
    }
*/

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    @ResponseBody
//    @PreAuthorize("hasRole('" + Role.ADMIN + "')")
    public TableResult<User> getUsersPost(@RequestBody TableParams params) {
        final Collection<User> users = userService.getUsers();

        for (int i = (params.page - 1) * params.count; i <= Math.min(params.page * params.count, 533); i++) {
            final User e = new User();
            e.setFullName("Fake Name " + i);
            e.setLogin("Fake login " + i);
            users.add(e);
        }
        return TableResult.create(users.toArray(new User[users.size()]), 533);
    }

    @RequestMapping(value = "/users/{userName}", method = RequestMethod.GET)
//    @Secured({"REGULAR", "ADMIN"})
    @PreAuthorize("hasRole('" + Role.ADMIN + "') or hasRole('" + Role.REGULAR + "')")
    @ResponseBody
    public User getUser(@PathVariable("userName") String userName) {
        logger.info("User: '" + userService.getCurrentUser().getFullName() + "'");
        logger.info("User id: '" + userService.getCurrentUserId()+ "'");
        return userService.loadUserByLogin(userName);
    }

    @ExceptionHandler(AuthenticationCredentialsNotFoundException.class)
    public ResponseEntity handleAuthenticationCredentialsNotFoundException(AuthenticationCredentialsNotFoundException ex) {
        return new ResponseEntity<WebError>(new WebError(ex.getMessage()), HttpStatus.UNAUTHORIZED);
    }

}


