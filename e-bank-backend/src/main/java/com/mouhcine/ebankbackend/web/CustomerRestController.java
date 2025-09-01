package com.mouhcine.ebankbackend.web;

import com.mouhcine.ebankbackend.Exceptions.CustomorNotFoundException;
import com.mouhcine.ebankbackend.dtos.CustomerDTO;
import com.mouhcine.ebankbackend.entities.Customer;
import com.mouhcine.ebankbackend.services.BankAccountService;
import com.mouhcine.ebankbackend.services.CustomerService;
import com.mouhcine.ebankbackend.services.CustomerServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

public class CustomerRestController {
   private CustomerServiceImpl customerService;

    public CustomerRestController(CustomerServiceImpl customerService, BankAccountService bankAccountService) {
        this.customerService = customerService;
        this.bankAccountService = bankAccountService;
    }

    private BankAccountService bankAccountService;



    @GetMapping("/customers")
    public List<CustomerDTO> getCustomers() {
        return customerService.listCustomers();


    }

    @GetMapping("/customers/{id}")
    public CustomerDTO getCustomer(@PathVariable(name = "id")  Long customerId) throws CustomorNotFoundException {

        return customerService.getCustomer(customerId);
    }

    @PostMapping("/customer/save")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO) throws CustomorNotFoundException {
        return customerService.saveCustomer(customerDTO);
    }
    @PutMapping("customer/update/{id}")
    public CustomerDTO updateCustomer(@PathVariable (name = "id") Long customerId, @RequestBody CustomerDTO customerDTO) throws CustomorNotFoundException {
        return customerService.updateCustomer(customerId,customerDTO);
    }
    @DeleteMapping("customer/delete/{id}")
    public void deleteCustomer(@PathVariable (name = "id") Long customerId) throws CustomorNotFoundException {
        customerService.deleteCustomer(customerId);
    }



}
