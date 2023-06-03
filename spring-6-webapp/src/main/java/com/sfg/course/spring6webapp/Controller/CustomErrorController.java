package com.sfg.course.spring6webapp.Controller;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.fasterxml.jackson.databind.type.LogicalType.Map;

@ControllerAdvice
public class CustomErrorController {

    //when user try to send long beername
    @ExceptionHandler
    ResponseEntity handleUpdateBeerNameValidationJPA(TransactionSystemException exception){
    ResponseEntity.BodyBuilder responseEntity=ResponseEntity.badRequest();

    if (exception.getCause().getCause() instanceof ConstraintViolationException){
        ConstraintViolationException ex = (ConstraintViolationException) exception.getCause().getCause();
   List errors=ex.getConstraintViolations().stream()
           .map(constraintViolation -> {
               Map<String,String> errMap=new HashMap<>();
               errMap.put(constraintViolation.getPropertyPath().toString(),constraintViolation.getMessage());
               return errMap;}).collect(Collectors.toList());
   return responseEntity.body(errors);

    }
    return responseEntity.build();

    }

    //when user try to send blank username
    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity handleBindErrors(MethodArgumentNotValidException exception){
       List errorList= exception.getFieldErrors().stream()
                .map(fieldError ->{
                        java.util.Map<String,String> errorMap=new HashMap<>();
               errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
                return errorMap;}).collect(Collectors.toList());

        return ResponseEntity.
                badRequest().body(errorList);
    }


}
