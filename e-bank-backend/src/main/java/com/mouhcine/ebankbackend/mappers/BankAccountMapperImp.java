package com.mouhcine.ebankbackend.mappers;

import com.fasterxml.jackson.databind.util.BeanUtil;
import com.mouhcine.ebankbackend.dtos.CustomerDTO;
import com.mouhcine.ebankbackend.entities.Customer;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class BankAccountMapperImp {
    public CustomerDTO fromCustomerToCustomerDTO(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();

        //classique mapping une utilisant getters et setters
//        customerDTO.setId(customer.getId());
//        customerDTO.setName(customer.getName());
//        customerDTO.setEmail(customer.getEmail());

        //la methode proposer par spring pour copier les proprietes dun objet a un autre
        BeanUtils.copyProperties(customer,customerDTO);


        return  customerDTO;
    }

    public Customer fromCustomerDTOToCustomer(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDTO, customer);
        return customer;
    }
}
