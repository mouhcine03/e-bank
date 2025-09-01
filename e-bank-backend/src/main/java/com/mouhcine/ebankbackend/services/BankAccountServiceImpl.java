package com.mouhcine.ebankbackend.services;

import com.mouhcine.ebankbackend.Enums.AccountStatus;
import com.mouhcine.ebankbackend.Enums.operationType;
import com.mouhcine.ebankbackend.Exceptions.BalanceInsufisentException;
import com.mouhcine.ebankbackend.Exceptions.CustomorNotFoundException;
import com.mouhcine.ebankbackend.Exceptions.bankAccountNotFoundException;
import com.mouhcine.ebankbackend.Exceptions.bankAccountSuspended;
import com.mouhcine.ebankbackend.dtos.CustomerDTO;
import com.mouhcine.ebankbackend.entities.*;
import com.mouhcine.ebankbackend.mappers.BankAccountMapperImp;
import com.mouhcine.ebankbackend.repositories.AccountOperationRepository;
import com.mouhcine.ebankbackend.repositories.BankAccountRepository;
import com.mouhcine.ebankbackend.repositories.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional


public class BankAccountServiceImpl implements BankAccountService {

    private CustomerRepository customerRepository;
    private BankAccountRepository bankAccountRepository;
    private AccountOperationRepository accountOperationRepository;
    Logger log = LoggerFactory.getLogger(BankAccountServiceImpl.class);
    private BankAccountMapperImp dtoMapper ;


    public BankAccountServiceImpl(CustomerRepository customerRepository, BankAccountRepository bankAccountRepository, AccountOperationRepository accountOperationRepository, BankAccountMapperImp dtoMapper) {

       this.customerRepository=customerRepository;
        this.bankAccountRepository = bankAccountRepository;
        this.accountOperationRepository = accountOperationRepository;

        this.dtoMapper = dtoMapper;
    }





    @Override
    public CurrentAccount saveCurrentBankAccount(double initialBalance, Long customerid, double overDraft) throws CustomorNotFoundException {
        log.info("saving a current bank account");
        Customer customer=customerRepository.findById(customerid).orElse(null);
        if (customer == null) throw new CustomorNotFoundException("custumor not found");
        CurrentAccount currentbankAccount=new CurrentAccount();

        currentbankAccount.setId(UUID.randomUUID().toString());
        currentbankAccount.setCreateAt(new Date());
        currentbankAccount.setBalance(initialBalance);
        currentbankAccount.setCustomer(customer);
        currentbankAccount.setOverDraft(overDraft);
        currentbankAccount.setStatus(AccountStatus.CREATED);

        CurrentAccount savedBankAccount=bankAccountRepository.save(currentbankAccount);
        return savedBankAccount;
    }

    @Override
    public SavingAccount saveSavingBankAccount(double initialBalance, Long customerid, double rateIntrest) throws CustomorNotFoundException {
        log.info("saving a Saving bank account");
        Customer customer=customerRepository.findById(customerid).orElse(null);
        if (customer == null) throw new CustomorNotFoundException("custumor not found");
        SavingAccount SavingBankAccount=new SavingAccount();

        SavingBankAccount.setId(UUID.randomUUID().toString());
        SavingBankAccount.setCreateAt(new Date());
        SavingBankAccount.setBalance(initialBalance);
        SavingBankAccount.setCustomer(customer);
        SavingBankAccount.setInterestRate(rateIntrest);
        SavingBankAccount.setStatus(AccountStatus.CREATED);

        SavingAccount savedBankAccount=bankAccountRepository.save(SavingBankAccount);
        return savedBankAccount;

    }






    @Override
    public List<BankAccount> listBankAccountsOfCustomer(Long customerid) {
        List<BankAccount> bankAccountList;
        bankAccountList=bankAccountRepository.findByCustomerId(customerid);
        return bankAccountList;
    }



    @Override
    public BankAccount getBankAccount(String accountId) throws bankAccountNotFoundException {
        BankAccount bankAccount=bankAccountRepository.findById(accountId).orElse(null);
        if (bankAccount == null) throw new bankAccountNotFoundException("bank account not find");

        return bankAccount;
    }

    @Override
    public List<AccountOperation> AllOperations() {
        List<AccountOperation> alloperations=accountOperationRepository.findAll();

        return alloperations;
    }

    @Override
    public void debit(String accountId, double amount, String description) throws bankAccountNotFoundException, bankAccountSuspended, BalanceInsufisentException {
        BankAccount bankAccount=bankAccountRepository.findById(accountId).orElse(null);
        if (bankAccount == null) throw new bankAccountNotFoundException("bank account not found");
        if (bankAccount.getStatus()==AccountStatus.SUSPENDED) throw new bankAccountSuspended("bank acount suspended");
        if(bankAccount.getBalance()<amount) throw new BalanceInsufisentException("Insuficient Balance for debit operation");
        bankAccount.setBalance(bankAccount.getBalance()+-amount) ;
        AccountOperation accountOperation=new AccountOperation();
        accountOperation.setBankAccount(bankAccount);
        accountOperation.setOperationDate(new Date());
        accountOperation.setAmount(amount);
        accountOperation.setType(operationType.DEBIT);
        accountOperation.setDescription(description);
        accountOperationRepository.save(accountOperation);


    }

    @Override
    public void credit(String accountId, double amount, String description) throws bankAccountNotFoundException, bankAccountSuspended {
        BankAccount bankAccount=bankAccountRepository.findById(accountId).orElse(null);
        if (bankAccount == null) throw new bankAccountNotFoundException("bank account not found");
        if (bankAccount.getStatus()==AccountStatus.SUSPENDED) throw new bankAccountSuspended("bank acount suspended");
        bankAccount.setBalance(bankAccount.getBalance()+amount) ;
        AccountOperation accountOperation=new AccountOperation();
        accountOperation.setBankAccount(bankAccount);
        accountOperation.setOperationDate(new Date());
        accountOperation.setAmount(amount);
        accountOperation.setType(operationType.CREDIT);
        accountOperation.setDescription(description);
        accountOperationRepository.save(accountOperation);




    }

    @Override
    public void virement(String accountIdSource, String accountIdDestination, double montant) throws bankAccountNotFoundException, bankAccountSuspended, BalanceInsufisentException {
        debit(accountIdSource,montant," transfer from"+accountIdDestination);
        credit(accountIdDestination,montant,"transfer to"+accountIdSource);


    }
}
