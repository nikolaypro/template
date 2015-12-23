package com.mascot.service.controller.user;

import com.mascot.common.MascotUtils;
import com.mascot.server.beans.UserService;
import com.mascot.server.model.Role;
import com.mascot.server.model.User;
import com.mascot.service.controller.AbstractController;
import com.mascot.service.controller.WebError;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.codec.Base64;
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
        logger.info("Successfully login: username = " + user.getLogin());
        return user;
//        return new ResponseEntity<User>(user, HttpStatus.OK);
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

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    @ResponseBody
    public User[] getUsers() {
        final Collection<User> users = userService.getUsers();
        return users.toArray(new User[users.size()]);
/*
        final User user1 = new User();
        user1.setLogin("login 1");
        user1.setFullName("Full name 1");
        user1.setId(1L);
        final User user2 = new User();
        user2.setLogin("login 2");
        user2.setFullName("Full name 2");
        user2.setId(2L);
        return new User[]{user1, user2};
*/
//        return Arrays.asList(user1, user2);
    }

    @RequestMapping(value = "/users/{userName}", method = RequestMethod.GET)
//    @Secured({"REGULAR", "ADMIN"})
//    @PreAuthorize("hasRole('ROLE_REGULAR') or hasRole('ROLE_ADMIN')")
    @PreAuthorize("hasRole('" + Role.ADMIN + "') or hasRole('" + Role.REGULAR + "')")
    @ResponseBody
    public User getUser(@PathVariable("userName") String userName) {
        logger.info("User: '" + userService.getCurrentUser().getFullName() + "'");
        logger.info("User id: '" + userService.getCurrentUserId()+ "'");
        return userService.loadUserByLogin(userName);
/*
        final User user1 = new User();
        user1.setLogin("userName");
        user1.setFullName("Full name 1");
        user1.setId(1L);
        return user1;
*/
//        return Arrays.asList(user1, user2);
    }

/*
    @ExceptionHandler(AuthenticationCredentialsNotFoundException.class)
    public ModelAndView handleAuthenticationCredentialsNotFoundException(AuthenticationCredentialsNotFoundException ex) {
        ModelAndView model = new ModelAndView("error/generic_error");
        model.addObject("exception", ex);
        return model;
    }
*/

    @ExceptionHandler(AuthenticationCredentialsNotFoundException.class)
    public ResponseEntity handleAuthenticationCredentialsNotFoundException(AuthenticationCredentialsNotFoundException ex) {
        return new ResponseEntity<WebError>(new WebError(ex.getMessage()), HttpStatus.UNAUTHORIZED);
    }

/*
    @RequestMapping(path = "/pets", method = RequestMethod.POST, consumes="application/json")
    public void addPet(@RequestBody Pet pet, Model model) {
        // implementation omitted
    }

    @RequestMapping(path = "/pets/{petId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Pet getPet(@PathVariable String petId, Model model) {
        // implementation omitted
    }
*/

    public static void main(String[] args) {
        final byte[] encode = Base64.encode("1".getBytes());
        System.out.println("'" + new String(encode) + "'");
    }
}


