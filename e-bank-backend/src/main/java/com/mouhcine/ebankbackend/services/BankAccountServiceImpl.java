package com.mouhcine.ebankbackend.services;

import com.mouhcine.ebankbackend.Enums.AccountStatus;
import com.mouhcine.ebankbackend.Enums.operationType;
import com.mouhcine.ebankbackend.Exceptions.BalanceInsufisentException;
import com.mouhcine.ebankbackend.Exceptions.CustomorNotFoundException;
import com.mouhcine.ebankbackend.Exceptions.bankAccountNotFoundException;
import com.mouhcine.ebankbackend.Exceptions.bankAccountSuspended;
import com.mouhcine.ebankbackend.dtos.*;
import com.mouhcine.ebankbackend.entities.*;
import com.mouhcine.ebankbackend.mappers.CustomerMapper;
import com.mouhcine.ebankbackend.repositories.AccountOperationRepository;
import com.mouhcine.ebankbackend.repositories.BankAccountRepository;
import com.mouhcine.ebankbackend.repositories.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Transactional


public class BankAccountServiceImpl implements BankAccountService {

    private CustomerRepository customerRepository;
    private BankAccountRepository bankAccountRepository;
    private AccountOperationRepository accountOperationRepository;
    Logger log = LoggerFactory.getLogger(BankAccountServiceImpl.class);
    private CustomerMapper dtoMapper ;


    public BankAccountServiceImpl(CustomerRepository customerRepository, BankAccountRepository bankAccountRepository, AccountOperationRepository accountOperationRepository, CustomerMapper dtoMapper) {

       this.customerRepository=customerRepository;
        this.bankAccountRepository = bankAccountRepository;
        this.accountOperationRepository = accountOperationRepository;

        this.dtoMapper = dtoMapper;
    }





    @Override
    public CurrentBankAccountDto saveCurrentBankAccount(double initialBalance, Long customerid, double overDraft) throws CustomorNotFoundException {
        log.info("saving a current bank account");
        Customer customer=customerRepository.findById(customerid).orElseThrow(()->new CustomorNotFoundException("Customer not found"));

        CurrentAccount currentbankAccount=new CurrentAccount();

        currentbankAccount.setId(UUID.randomUUID().toString());
        currentbankAccount.setCreateAt(new Date());
        currentbankAccount.setBalance(initialBalance);
        currentbankAccount.setCustomer(customer);
        currentbankAccount.setOverDraft(overDraft);
        currentbankAccount.setStatus(AccountStatus.CREATED);

        CurrentAccount savedBankAccount=bankAccountRepository.save(currentbankAccount);
        return dtoMapper.fromCurrentAccountToCurrentBankAccountDto(savedBankAccount);
    }

    @Override
    public SavingBankAccountDto saveSavingBankAccount(double initialBalance, Long customerid, double rateIntrest) throws CustomorNotFoundException {log.info("saving a Saving bank account");Customer customer=customerRepository.findById(customerid).orElseThrow(()->new CustomorNotFoundException("custumor not found"));
       SavingAccount savingAccount=new SavingAccount();
       savingAccount.setId(UUID.randomUUID().toString());
       savingAccount.setCreateAt(new Date());
       savingAccount.setBalance(initialBalance);
       savingAccount.setCustomer(customer);
       savingAccount.setStatus(AccountStatus.CREATED);
       SavingAccount savedSavingAccount=bankAccountRepository.save(savingAccount);
       return dtoMapper.fromBankAccountToSavingBankAccountDTO(savedSavingAccount);



    }






    @Override
    public List<BankAccountDto> listBankAccountsOfCustomer(Long customerid) {
        List<BankAccount> bankAccountList;
        bankAccountList=bankAccountRepository.findByCustomerId(customerid);
        return bankAccountList.stream().map(bankAccount ->
                switch (bankAccount) {
                    case CurrentAccount currentaccount->dtoMapper.fromCurrentAccountToCurrentBankAccountDto(currentaccount);
                    case SavingAccount savingAccount->dtoMapper.fromBankAccountToSavingBankAccountDTO(savingAccount);
                    default -> throw new IllegalStateException("Unexpected value: " + bankAccount);
                }
                ).toList();
    }



    @Override
    public BankAccountDto getBankAccount(String accountId) throws bankAccountNotFoundException {
        BankAccount bankAccount=bankAccountRepository.findById(accountId).orElseThrow(()->new bankAccountNotFoundException("bankaccount not found"));
        return switch (bankAccount) {
            case CurrentAccount currentAccount -> dtoMapper.fromCurrentAccountToCurrentBankAccountDto(currentAccount);
            case SavingAccount savingAccount -> dtoMapper.fromBankAccountToSavingBankAccountDTO(savingAccount);
            default ->
                    throw new IllegalArgumentException("Unknown account type: " + bankAccount.getClass().getSimpleName());
        };
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
    public void virement(String accountIdSource, String accountIdDestination, double montant,String description) throws bankAccountNotFoundException, bankAccountSuspended, BalanceInsufisentException {
        debit(accountIdSource,montant,description);
        credit(accountIdDestination,montant,"transfer FROM "+accountIdSource);


    }
    @Override

    public List<BankAccountDto> getBankAccountList(){
        List<BankAccount> bankAccountList =bankAccountRepository.findAll();
        return bankAccountList.stream().map(bankAccount ->
                switch (bankAccount) {
                case CurrentAccount currentAccount -> dtoMapper.fromCurrentAccountToCurrentBankAccountDto(currentAccount);
                case SavingAccount savingAccount->dtoMapper.fromBankAccountToSavingBankAccountDTO(savingAccount);
                default -> throw new IllegalStateException("Unexpected value: " + bankAccount);

        }).toList();

    }

    @Override
    public List<AccountOperationDto> AccountHistorique(String accountId){
       List<AccountOperation>accountOperations=accountOperationRepository.findByBankAccountId(accountId);
       return accountOperations.stream().map(operation->dtoMapper.fromAccountOperationToAccountOperationDto(operation)).toList();

    }

    @Override
    public AccountHistoryDto getAccountHistory(String accountId, int page, int size) throws bankAccountNotFoundException {BankAccount bankAccount=bankAccountRepository.findById(accountId).orElseThrow(()->new bankAccountNotFoundException("account not found"));
       Page <AccountOperation> accountOperations=accountOperationRepository.findByBankAccountId(PageRequest.of(page,size),accountId);
       List<AccountOperationDto> accountOperationDto = accountOperations.getContent().stream().map(operation -> dtoMapper.fromAccountOperationToAccountOperationDto(operation)).toList();
        AccountHistoryDto accountHistoryDto=new AccountHistoryDto();
       accountHistoryDto.setAccountOperationDtoList(accountOperationDto);
       accountHistoryDto.setAccountId(bankAccount.getId());
       accountHistoryDto.setBalance(bankAccount.getBalance());
       accountHistoryDto.setPageSize(size);
       accountHistoryDto.setCurrentPage(page);
       accountHistoryDto.setTotalPages(accountOperations.getTotalPages());



        return accountHistoryDto;
    }


}
