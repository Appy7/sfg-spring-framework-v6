package com.sfg.course.spring6webapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile({"Primary","default"})
@Primary
@Service("Hello")
public class PrimaryServiceClass implements SetterInjectedService{
    @Override
    public String sayHello() {
        return "Hello from primary";
    }
}
