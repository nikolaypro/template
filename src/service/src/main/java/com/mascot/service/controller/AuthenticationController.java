package com.mascot.service.controller;

import com.mascot.server.model.User;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * Created by Nikolay on 25.11.2015.
 */
@RestController
//@Controller
@RequestMapping(name = "/api")
public class AuthenticationController {

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    @ResponseBody
    public User authenticate( /*@PathVariable("userName") String userName*/@RequestBody User user) {
//        AuthenticationManagerBuilder auth = null;
//        auth.userDetailsService().toString();
        Logger.getLogger(getClass()).info("username = " + user.getLogin() + ", password = " + user.getPassword());
        user.setId(1L);
        user.setLogin(user.getLogin() + ": modified by real bean");
        return user;
//        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    @ResponseBody
    public User[] getUsers() {
        final User user1 = new User();
        user1.setLogin("login 1");
        user1.setFullName("Full name 1");
        user1.setId(1L);
        final User user2 = new User();
        user2.setLogin("login 2");
        user2.setFullName("Full name 2");
        user2.setId(2L);
        return new User[]{user1, user2};
//        return Arrays.asList(user1, user2);
    }

    @RequestMapping(value = "/users/{userName}", method = RequestMethod.GET)
//    @Secured({"REGULAR", "ADMIN"})
    @PreAuthorize("hasRole('ROLE_REGULAR') or hasRole('ROLE_ADMIN')")
    @ResponseBody
    public User getUser(@PathVariable("userName") String userName) {
        final User user1 = new User();
        user1.setLogin("userName");
        user1.setFullName("Full name 1");
        user1.setId(1L);
        return user1;
//        return Arrays.asList(user1, user2);
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
}
