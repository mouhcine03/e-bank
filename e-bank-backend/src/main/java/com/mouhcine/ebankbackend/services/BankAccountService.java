package com.mouhcine.ebankbackend.services;

import com.mouhcine.ebankbackend.Exceptions.BalanceInsufisentException;
import com.mouhcine.ebankbackend.Exceptions.CustomorNotFoundException;
import com.mouhcine.ebankbackend.Exceptions.bankAccountNotFoundException;
import com.mouhcine.ebankbackend.Exceptions.bankAccountSuspended;
import com.mouhcine.ebankbackend.dtos.CustomerDTO;
import com.mouhcine.ebankbackend.entities.*;

import java.util.List;

public interface BankAccountService {




    CurrentAccount saveCurrentBankAccount(double initialBalance, Long customerid , double overDraft) throws CustomorNotFoundException;
    SavingAccount saveSavingBankAccount(double initialBalance, Long customerid , double rateIntrest) throws CustomorNotFoundException;

    List<BankAccount> listBankAccountsOfCustomer(Long customerid);
    BankAccount getBankAccount(String accountId) throws bankAccountNotFoundException;
    List<AccountOperation> AllOperations();

    void debit(String accountId, double amount ,String description) throws bankAccountNotFoundException, bankAccountSuspended, BalanceInsufisentException;
    void credit(String accountId, double amount, String description) throws bankAccountNotFoundException, bankAccountSuspended;
    void virement(String accountIdSource , String accountIdDestination , double montant) throws bankAccountNotFoundException, bankAccountSuspended, BalanceInsufisentException;






 }
