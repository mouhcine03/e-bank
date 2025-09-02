package com.mouhcine.ebankbackend.services;

import com.mouhcine.ebankbackend.Exceptions.BalanceInsufisentException;
import com.mouhcine.ebankbackend.Exceptions.CustomorNotFoundException;
import com.mouhcine.ebankbackend.Exceptions.bankAccountNotFoundException;
import com.mouhcine.ebankbackend.Exceptions.bankAccountSuspended;
import com.mouhcine.ebankbackend.dtos.*;
import com.mouhcine.ebankbackend.entities.*;

import java.util.List;

public interface BankAccountService {




    CurrentBankAccountDto saveCurrentBankAccount(double initialBalance, Long customerid , double overDraft) throws CustomorNotFoundException;
    SavingBankAccountDto saveSavingBankAccount(double initialBalance, Long customerid , double rateIntrest) throws CustomorNotFoundException;

    List<BankAccountDto> listBankAccountsOfCustomer(Long customerid);
    BankAccountDto getBankAccount(String accountId) throws bankAccountNotFoundException;
    List<AccountOperation> AllOperations();

    void debit(String accountId, double amount ,String description) throws bankAccountNotFoundException, bankAccountSuspended, BalanceInsufisentException;
    void credit(String accountId, double amount, String description) throws bankAccountNotFoundException, bankAccountSuspended;
    void virement(String accountIdSource , String accountIdDestination , double montant) throws bankAccountNotFoundException, bankAccountSuspended, BalanceInsufisentException;
    List<BankAccountDto> getBankAccountList();


    List<AccountOperationDto> AccountHistorique(String accountId);

    AccountHistoryDto getAccountHistory(String accountId, int page, int size) throws bankAccountNotFoundException;
}
