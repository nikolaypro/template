package com.mascot.service.controller.user;

import com.mascot.common.MascotUtils;
import com.mascot.server.beans.UserService;
import com.mascot.server.model.Role;
import com.mascot.server.model.User;
import com.mascot.service.controller.AbstractController;
import com.mascot.service.controller.WebError;
import com.mascot.service.controller.common.ResultRecord;
import com.mascot.service.controller.common.TableParams;
import com.mascot.service.controller.common.TableResult;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.ArrayList;
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
    public UserRecord authenticate(@RequestBody User user) {
        if (MascotUtils.isEmpty(user.getPassword())) {
            throw createInvalidPasswordException(user);
        }
        final User entityUser = userService.loadUserByLogin(user.getLogin());
        if (entityUser == null) {
            throw createInvalidUserNameException(user);
        }
        if (!user.getPassword().equals(entityUser.getPassword())) {
            throw createInvalidPasswordException(user);
        }
        logger.info("Successfully login: username = " + user.getLogin() +
                ", roles: [" + StringUtils.collectionToCommaDelimitedString(entityUser.getRoles()) + "]");
        return UserRecord.build(entityUser);
    }

    private RuntimeException createInvalidUserNameException(User user) {
        final String msg = "Invalid user name: '" + user.getLogin() + "'";
        logger.info(msg);
        return new AuthenticationCredentialsNotFoundException(msg);
    }

    private RuntimeException createInvalidPasswordException(User user) {
        final String msg = "Invalid password";
        logger.info(msg);
        return new AuthenticationCredentialsNotFoundException(msg);
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
    @PreAuthorize("hasRole('" + Role.ADMIN + "')")
    public TableResult<UserRecord> getUsers(@RequestBody TableParams params) {
        final Collection<User> users = userService.getUsers();
        final Collection<UserRecord> result = new ArrayList<>();
        for (User user : users) {
            result.add(UserRecord.build(user));
        }

        for (int i = (params.page - 1) * params.count; i <= Math.min(params.page * params.count, 533); i++) {
            final UserRecord e = new UserRecord();
            e.fullName = "Fake Name " + i;
            e.login = "Fake login " + i;
            result.add(e);
        }
/*
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
*/
//        if (true) {
//            throw new NullPointerException("test exception");
//        }
        return TableResult.create(result.toArray(new UserRecord[result.size()]), 533);
    }

    @RequestMapping(value = "/users/create", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasRole('" + Role.ADMIN + "')")
    public ResultRecord createUser(@RequestBody UserRecord record) {
        logger.info("User: name = " + record.fullName + ", login = " + record.login + ", roles = " + record.roles + ", psw = " + record.password);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        final User existsUser = userService.loadUserByLogin(record.login);
        if (existsUser != null) {
            return ResultRecord.fail("User with login '" + record.login + "' already exists");
        }
        final User user = new User();
        user.setLogin(record.login);
        user.setFullName(record.fullName);
        user.setPassword(record.password);
        for (String roleName : record.roles) {
            final Role role = userService.getRole(roleName);
            if (role == null) {
                return ResultRecord.fail("Not found role: '" + roleName + "'");
            }
            user.getRoles().add(role);
        }
        userService.saveUser(user);

        return ResultRecord.success();
    }

    @RequestMapping(value = "/users/{userName}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('" + Role.ADMIN + "') or hasRole('" + Role.REGULAR + "')")
    @ResponseBody
    public UserRecord getUser(@PathVariable("userName") String userName) {
        logger.info("User: '" + userService.getCurrentUser().getFullName() + "'");
        logger.info("User id: '" + userService.getCurrentUserId()+ "'");
        return UserRecord.build(userService.loadUserByLogin(userName));
    }

    @ExceptionHandler(AuthenticationCredentialsNotFoundException.class)
    public ResponseEntity handleAuthenticationCredentialsNotFoundException(AuthenticationCredentialsNotFoundException ex) {
        return new ResponseEntity<WebError>(new WebError(ex.getMessage()), HttpStatus.UNAUTHORIZED);
    }

}


