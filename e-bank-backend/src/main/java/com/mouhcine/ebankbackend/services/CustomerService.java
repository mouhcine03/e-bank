package com.mouhcine.ebankbackend.services;


import com.mouhcine.ebankbackend.Exceptions.CustomorNotFoundException;
import com.mouhcine.ebankbackend.dtos.CustomerDTO;

import java.util.List;

public interface CustomerService {
    CustomerDTO saveCustomer(CustomerDTO customerDTO);

    void deleteCustomer(Long customerid) ;

    List<CustomerDTO> listCustomers();
    CustomerDTO getCustomer(Long customerid) throws CustomorNotFoundException;
    CustomerDTO updateCustomer(Long customerid, CustomerDTO customerDTO) throws CustomorNotFoundException;
    List<CustomerDTO> searchCustomer(String keyword) ;
}
