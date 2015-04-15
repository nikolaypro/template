package com.mascot.service.controller;

import com.mascot.common.CommonClass;
import com.mascot.common.MascotAppContext;
import com.mascot.server.beans.MascotBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
}
