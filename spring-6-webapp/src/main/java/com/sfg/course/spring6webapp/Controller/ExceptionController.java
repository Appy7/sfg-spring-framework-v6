package com.sfg.course.spring6webapp.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

//@ControllerAdvice
public class ExceptionController {
   // @ExceptionHandler(NotFoundException.class)
    public ResponseEntity getBeerByIdException(){
        System.out.println("Inside controller exception");
        return ResponseEntity.notFound().build();
    }
}
