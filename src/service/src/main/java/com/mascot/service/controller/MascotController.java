package com.mascot.service.controller;

import com.mascot.common.CommonClass;
import com.mascot.common.MascotAppContext;
import com.mascot.server.beans.MascotBean;
import com.mascot.server.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;

/**
 * Created by Nikolay on 27.10.2014.
 */
@Controller
@RequestMapping(name = "/api")
public class MascotController {
//    @Resource
//    @Autowired
    @Inject
    private MascotBean bean2;

    @RequestMapping(value = "/mascot_bean", method = RequestMethod.GET)
    public boolean test() {
        System.out.println("BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB");
//        System.out.println("Bean2 " + bean2.getValue2());
        System.out.println("CommonClass: " + CommonClass.class.getName());
        final MascotBean bean = MascotAppContext.getApplicationContext().getBean(MascotBean.NAME, MascotBean.class);
        System.out.println("bean = " + bean.getValue2());
        return true;
    }

    @RequestMapping(value = "/test/user", method = RequestMethod.POST)
    @ResponseBody
    public User test2(@RequestBody User user) {
        System.out.println("call Test 2. user name = " + (user != null ? user.getName() : "NULL"));
//        System.out.println("Bean2 " + bean2.getValue2());
        final User user1 = new User();
        user1.setName((user != null ? user.getName() : "NULL") + ": modified");
        return user1;
    }

    @RequestMapping(value = "/test/user1", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<User> test3(@RequestBody User user) {
        System.out.println("call Test 2. user name = " + (user != null ? user.getName() : "NULL"));
//        System.out.println("Bean2 " + bean2.getValue2());
        final User user1 = new User();
        user1.setName((user != null ? user.getName() : "NULL") + ": modified");
        return new ResponseEntity<User>(user1, HttpStatus.OK);
    }

}
