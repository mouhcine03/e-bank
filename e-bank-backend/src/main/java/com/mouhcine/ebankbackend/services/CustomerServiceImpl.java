package com.mouhcine.ebankbackend.services;

import com.mouhcine.ebankbackend.Exceptions.CustomorNotFoundException;
import com.mouhcine.ebankbackend.dtos.CustomerDTO;
import com.mouhcine.ebankbackend.entities.Customer;
import com.mouhcine.ebankbackend.mappers.CustomerMapper;
import com.mouhcine.ebankbackend.repositories.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {
    private CustomerRepository customerRepository;
    private CustomerMapper dtoMapper;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
        this.dtoMapper = new CustomerMapper();
    }
    @Override
    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
        Customer customer =dtoMapper.fromCustomerDTOToCustomer(customerDTO);
        Customer savedCustomer = customerRepository.save(customer);
        return dtoMapper.fromCustomerToCustomerDTO(savedCustomer);
    }
    @Override
    public CustomerDTO getCustomer(Long customerid) throws CustomorNotFoundException {
        CustomerDTO customerDTO=customerRepository.findById(customerid).map(customer -> dtoMapper.fromCustomerToCustomerDTO(customer)).orElseThrow(()->new CustomorNotFoundException("customer not found"));
        return customerDTO;
    }

    @Override
    public CustomerDTO updateCustomer(Long customerid, CustomerDTO customerDTO) throws CustomorNotFoundException {
        Customer existingCustomer =customerRepository.findById(customerid).orElseThrow(()->new CustomorNotFoundException("customer not found"));
        existingCustomer.setName(customerDTO.getName());
        existingCustomer.setEmail(customerDTO.getEmail());
        Customer updatedCustomer = customerRepository.save(existingCustomer);

        return dtoMapper.fromCustomerToCustomerDTO(updatedCustomer);
    }

    @Override
    public List<CustomerDTO> searchCustomer(String keyword)  {
        return customerRepository.findByNameContains(keyword).stream().map(customer ->
                    dtoMapper.fromCustomerToCustomerDTO(customer)).toList();

    }

    @Override
    public void deleteCustomer(Long customerid)  {
        customerRepository.deleteById(customerid);
    }

    @Override
    public List<CustomerDTO> listCustomers() {
        List<Customer> customerList;
        customerList=customerRepository.findAll();
        List <CustomerDTO> customerDTOList =customerList.
                stream()
                .map(customer -> dtoMapper.fromCustomerToCustomerDTO(customer))
                .collect(Collectors.toList());




        return customerDTOList;
    }


}
