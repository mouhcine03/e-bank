package com.mouhcine.ebankbackend.mappers;

import com.mouhcine.ebankbackend.dtos.*;
import com.mouhcine.ebankbackend.entities.*;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class CustomerMapper {
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
    public SavingBankAccountDto fromBankAccountToSavingBankAccountDTO(SavingAccount savingAccount) {
        SavingBankAccountDto savingBankAccountDto = new SavingBankAccountDto();
        BeanUtils.copyProperties(savingAccount,savingBankAccountDto);
        savingBankAccountDto.setCustomerDTO(fromCustomerToCustomerDTO(savingAccount.getCustomer()));
        savingBankAccountDto.setType(savingAccount.getClass().getSimpleName());

        return savingBankAccountDto;
    }
    public SavingAccount fromSavingBankAccountDtoToSavingAccount(SavingBankAccountDto savingBankAccountDto) {
        SavingAccount savingAccount = new SavingAccount();
        BeanUtils.copyProperties(savingBankAccountDto,savingAccount);
        savingAccount.setCustomer(fromCustomerDTOToCustomer(savingBankAccountDto.getCustomerDTO()));
        return savingAccount;
    }

    public CurrentAccount fromCurrentAcountDtoToCurrentAccount(CurrentBankAccountDto currentBankAccountDto){
        CurrentAccount currentAccount = new CurrentAccount();
        BeanUtils.copyProperties(currentBankAccountDto,currentAccount);
        currentAccount.setCustomer(fromCustomerDTOToCustomer(currentBankAccountDto.getCustomerDTO()));
        return currentAccount;

    }

    public CurrentBankAccountDto fromCurrentAccountToCurrentBankAccountDto(CurrentAccount currentAccount){
        CurrentBankAccountDto currentBankAccountDto = new CurrentBankAccountDto();
        BeanUtils.copyProperties(currentAccount,currentBankAccountDto);
        currentBankAccountDto.setCustomerDTO(fromCustomerToCustomerDTO(currentAccount.getCustomer()));
        currentBankAccountDto.setType(currentAccount.getClass().getSimpleName());
        return currentBankAccountDto;
    }

    public BankAccountDto fromBankAccountToBankAccountDto(BankAccount bankAccount){
        BankAccountDto bankAccountDto = new BankAccountDto();
        BeanUtils.copyProperties(bankAccount,bankAccountDto);
        bankAccountDto.setCustomerDTO(fromCustomerToCustomerDTO(bankAccount.getCustomer()));
        return bankAccountDto;
    }
    public BankAccount fromBankAccountDtoToBankAccount(BankAccountDto bankAccountDto){
        BankAccount bankAccount = new BankAccount();
        BeanUtils.copyProperties(bankAccountDto,bankAccount);
        bankAccount.setCustomer(fromCustomerDTOToCustomer(bankAccountDto.getCustomerDTO()));
        return bankAccount;
    }

    public AccountOperationDto fromAccountOperationToAccountOperationDto(AccountOperation accountOperation){
        AccountOperationDto accountOperationDto=new AccountOperationDto();
        BeanUtils.copyProperties(accountOperation,accountOperationDto);
        return accountOperationDto;

    }

    public AccountOperation fromAccountOperationDtoToAccountOperation(AccountOperationDto accountOperationDto){
        AccountOperation accountOperation=new AccountOperation();
        BeanUtils.copyProperties(accountOperationDto,accountOperation);
        return accountOperation;
    }


}
