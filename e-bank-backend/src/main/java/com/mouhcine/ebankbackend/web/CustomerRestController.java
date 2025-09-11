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
@RequestMapping("/customer")
@CrossOrigin(origins = "*")

public class CustomerRestController {
   private CustomerServiceImpl customerService;

    public CustomerRestController(CustomerServiceImpl customerService, BankAccountService bankAccountService) {
        this.customerService = customerService;
        this.bankAccountService = bankAccountService;
    }

    private BankAccountService bankAccountService;



    @GetMapping("/All")
    public List<CustomerDTO> getCustomers() {
        return customerService.listCustomers();


    }
    @GetMapping("/search")
    public List<CustomerDTO> searchCustomer(@RequestParam(name = "keyword") String keyword) throws CustomorNotFoundException {
        return customerService.searchCustomer(keyword);
    }

    @GetMapping("/{id}")
    public CustomerDTO getCustomer(@PathVariable(name = "id")  Long customerId) throws CustomorNotFoundException {

        return customerService.getCustomer(customerId);
    }

    @PostMapping("/save")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO) throws CustomorNotFoundException {
        return customerService.saveCustomer(customerDTO);
    }
    @PutMapping("/update/{id}")
    public CustomerDTO updateCustomer(@PathVariable (name = "id") Long customerId, @RequestBody CustomerDTO customerDTO) throws CustomorNotFoundException {
        return customerService.updateCustomer(customerId,customerDTO);
    }
    @DeleteMapping("/delete/{id}")
    public void deleteCustomer(@PathVariable (name = "id") Long customerId) throws CustomorNotFoundException {
        customerService.deleteCustomer(customerId);
    }



}
