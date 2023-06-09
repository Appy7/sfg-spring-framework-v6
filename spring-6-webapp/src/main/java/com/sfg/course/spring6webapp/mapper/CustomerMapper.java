package com.sfg.course.spring6webapp.mapper;


import com.sfg.course.spring6webapp.entities.Customer;
import com.sfg.course.spring6webapp.model.CustomerDTO;
import org.mapstruct.Mapper;

/**
 * Created by jt, Spring Framework Guru.
 */
@Mapper
public interface CustomerMapper {

    Customer customerDtoToCustomer(CustomerDTO dto);

    CustomerDTO customerToCustomerDto(Customer customer);

}