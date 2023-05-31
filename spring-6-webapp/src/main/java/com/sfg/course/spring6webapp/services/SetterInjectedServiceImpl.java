package com.sfg.course.spring6webapp.services;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile("Darling")
@Service("Hello")
public class SetterInjectedServiceImpl implements SetterInjectedService{
    @Override
    public String sayHello() {
        return "HELLO Darling";
    }
}
