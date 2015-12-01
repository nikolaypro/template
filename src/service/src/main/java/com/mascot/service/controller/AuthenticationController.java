package com.mascot.service.controller;

import com.mascot.server.model.User;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        Logger.getLogger(getClass()).info("username = " + user.getName() + ", password = " + user.getPassword());
        user.setId(1L);
        user.setName(user.getName() + ": modified by real bean");
        return user;
//        return new ResponseEntity<User>(user, HttpStatus.OK);
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
