package com.mouhcine.ebankbackend.web;

import com.mouhcine.ebankbackend.Exceptions.bankAccountNotFoundException;
import com.mouhcine.ebankbackend.dtos.AccountHistoryDto;
import com.mouhcine.ebankbackend.dtos.AccountOperationDto;
import com.mouhcine.ebankbackend.dtos.BankAccountDto;
import com.mouhcine.ebankbackend.entities.BankAccount;
import com.mouhcine.ebankbackend.entities.CurrentAccount;
import com.mouhcine.ebankbackend.services.BankAccountService;
import com.mouhcine.ebankbackend.services.CustomerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bankAccount")
public class BankAccountRestController {
    private BankAccountService bankAccountService;
    private CustomerService customerService;
    public BankAccountRestController(BankAccountService bankAccountService, CustomerService customerService) {
        this.bankAccountService = bankAccountService;
        this.customerService = customerService;
    }

    @GetMapping("/{accountId}")

    public BankAccountDto getBankAccount(@PathVariable(name = "accountId") String accountId) throws bankAccountNotFoundException {
        return bankAccountService.getBankAccount(accountId);
    }

    @GetMapping("customer/{customerId}")
    public List<BankAccountDto> CustomerBankAccounts(Long customerId) throws bankAccountNotFoundException {
        return bankAccountService.listBankAccountsOfCustomer(customerId);
    }
    @GetMapping("/all")
    public List <BankAccountDto> getAllBankAccounts() throws bankAccountNotFoundException {
        return bankAccountService.getBankAccountList();
    }

    public BankAccountDto createBankAccount(BankAccountDto bankAccountDto) throws bankAccountNotFoundException {
        return null;
    }

    @GetMapping("/history/{accountId}")
    public List<AccountOperationDto> getAccountHisrtory(@PathVariable String accountId) throws bankAccountNotFoundException {
        return bankAccountService.AccountHistorique(accountId);
    }

    @GetMapping("/historyPages/{accountId}")
    public AccountHistoryDto getAccountHistory (@PathVariable String accountId
            , @RequestParam(name = "page",defaultValue = "0") int page
            , @RequestParam(name = "size",defaultValue ="5") int size) throws bankAccountNotFoundException {
        return bankAccountService.getAccountHistory(accountId,page,size) ;
    }









}
