package com.sfg.course.spring6webapp.web.controller;

import com.sfg.course.spring6webapp.services.SetterInjectedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

@Controller
public class SetterInjectedController {
    private  SetterInjectedService setterInjectedService;
//
    public SetterInjectedController(@Qualifier("Hello") SetterInjectedService setterInjectedService) {
        this.setterInjectedService = setterInjectedService;
    }
//        @Autowired
//        void setSetterInjectedServiceMethod(SetterInjectedService injectedServiceMethod)
//        {
//        this.setterInjectedService=injectedServiceMethod;
//        }
    void controllerMethod(){
      System.out.println(setterInjectedService.sayHello());
    }
}
