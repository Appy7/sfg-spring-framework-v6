package com.sfg.course.spring6webapp.web.controller;

import org.springframework.test.context.ActiveProfiles;


import com.sfg.course.spring6webapp.services.SetterInjectedServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
//@ActiveProfiles("Darling")
@SpringBootTest
class SetterInjectedControllerTest {

    @Autowired
    SetterInjectedController setterInjectedController;

//    @BeforeEach
//    void setUp() {
//        //   constructor based injection example
//            setterInjectedController=new SetterInjectedController(new SetterInjectedServiceImpl());
//
//          // setter based injection example
//        //   setterInjectedController.setSetterInjectedServiceMethod(new SetterInjectedServiceImpl());
//    }

    @Test
    void controllerMethod() {
        setterInjectedController.controllerMethod();
    }
}