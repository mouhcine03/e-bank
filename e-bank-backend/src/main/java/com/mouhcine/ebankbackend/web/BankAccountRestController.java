package com.mouhcine.ebankbackend.web;

import com.mouhcine.ebankbackend.Enums.AccType;
import com.mouhcine.ebankbackend.Enums.operationType;
import com.mouhcine.ebankbackend.Exceptions.BalanceInsufisentException;
import com.mouhcine.ebankbackend.Exceptions.CustomorNotFoundException;
import com.mouhcine.ebankbackend.Exceptions.bankAccountNotFoundException;
import com.mouhcine.ebankbackend.Exceptions.bankAccountSuspended;
import com.mouhcine.ebankbackend.dtos.*;
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


    @PostMapping("/createAccount/{customerId}")
    public BankAccountDto createBankAccount(@PathVariable Long customerId,@RequestBody CreateAccountDto createAccountDto) throws CustomorNotFoundException, bankAccountNotFoundException {
        if (createAccountDto.getAccountType()== AccType.CURRENT){
            return bankAccountService.saveCurrentBankAccount(createAccountDto.getBalance(),customerId,createAccountDto.getOverdraft());
        } else if (createAccountDto.getAccountType()==AccType.SAVING) {
            return bankAccountService.saveSavingBankAccount(createAccountDto.getBalance(),customerId,createAccountDto.getInterestRate());

        }else throw  new  IllegalArgumentException("Invalid account type: " + createAccountDto.getAccountType());
    }




    @PostMapping("/operation/{accountId}")
    public void effectueroperation(@PathVariable String accountId,@RequestBody operationRequestDto operationRequestDto) throws bankAccountNotFoundException, bankAccountSuspended, BalanceInsufisentException {
        if (operationRequestDto.getOperationType()== operationType.CREDIT){
            bankAccountService.credit(accountId, operationRequestDto.getAmount(), operationRequestDto.getDescription());
        } else if (operationRequestDto.getOperationType()==operationType.DEBIT) {
            bankAccountService.debit(accountId, operationRequestDto.getAmount(), operationRequestDto.getDescription());

        } else if (operationRequestDto.getOperationType()==operationType.VIREMENT) {
            bankAccountService.virement(accountId,operationRequestDto.getIdDestinationVirement(),operationRequestDto.getAmount(),operationRequestDto.getDescription());

        }else throw new  IllegalArgumentException("Operation Type undefined");
    }













}
