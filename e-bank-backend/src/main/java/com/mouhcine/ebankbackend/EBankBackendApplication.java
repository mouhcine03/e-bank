package com.mouhcine.ebankbackend;

import com.mouhcine.ebankbackend.Enums.AccountStatus;
import com.mouhcine.ebankbackend.Enums.operationType;
import com.mouhcine.ebankbackend.Exceptions.BalanceInsufisentException;
import com.mouhcine.ebankbackend.Exceptions.CustomorNotFoundException;
import com.mouhcine.ebankbackend.Exceptions.bankAccountNotFoundException;
import com.mouhcine.ebankbackend.Exceptions.bankAccountSuspended;
import com.mouhcine.ebankbackend.dtos.CustomerDTO;
import com.mouhcine.ebankbackend.entities.*;
import com.mouhcine.ebankbackend.repositories.AccountOperationRepository;
import com.mouhcine.ebankbackend.repositories.BankAccountRepository;
import com.mouhcine.ebankbackend.repositories.CustomerRepository;
import com.mouhcine.ebankbackend.services.BankAccountService;
import com.mouhcine.ebankbackend.services.CustomerService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class EBankBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(EBankBackendApplication.class, args);

		}


//		@Bean
//		@Order(1)
	CommandLineRunner commandLineRunner (BankAccountService bankAccountService, CustomerService customerService){
		return args -> {
			Stream.of("MOUHCINE","HOUDA","ARBI").forEach(name->{
				CustomerDTO customer = new CustomerDTO();
				customer.setName(name);
				customer.setEmail(name+"@email.com");
				customerService.saveCustomer(customer);



                try {
                    bankAccountService.saveCurrentBankAccount(Math.random()*980000,customer.getId(),Math.random()*7000) ;
                } catch (CustomorNotFoundException e) {
                    throw new RuntimeException(e);
                }
				try {
					bankAccountService.saveSavingBankAccount(Math.random()*560000,customer.getId(),5.5) ;
				} catch (CustomorNotFoundException e) {
					throw new RuntimeException(e);
				}


            });
			List <CustomerDTO> customers=customerService.listCustomers();


        };
	}
//	@Bean
//	@Order(2)
	CommandLineRunner commandLineRunnerForOperations(BankAccountService bankAccountService, CustomerService customerService){
		return args -> {
			System.out.println("creating operations");
			List <CustomerDTO> customers=customerService.listCustomers();
			for(CustomerDTO c : customers){
				bankAccountService.listBankAccountsOfCustomer(c.getId()).forEach(bankAccount->{
					for (int i=0;i<3;i++){
						try {
							bankAccountService.debit(bankAccount.getId(),Math.random()*8900,"debit number"+i);
							bankAccountService.credit(bankAccount.getId(),Math.random()*8900,"credit number:"+i);

							System.out.println("operations created ");
						} catch (bankAccountNotFoundException | bankAccountSuspended | BalanceInsufisentException e) {
							throw new RuntimeException(e);
						}
					}

				});

			}
		};
	}
//	@Bean
//	@Order(3)
	CommandLineRunner commandLineRunner1(BankAccountService bankAccountService,CustomerService customerService){
		return args -> {
			System.out.println("***** list of custumors *****");
			List<CustomerDTO> customers=customerService.listCustomers();
			for (CustomerDTO c : customers) {
				System.out.println("custumor name:" + c.getName() + "   custumor email:" + c.getEmail());

				System.out.println("------custumor comptes----");
				List<BankAccount> custumorBankAccount=bankAccountService.listBankAccountsOfCustomer(c.getId());
				for (BankAccount b : custumorBankAccount) {
					System.out.println("Created at: "+b.getCreateAt()+" Balance: "+b.getBalance()+"account status"+b.getStatus());


				}

			}

			System.out.println("all operations");
			bankAccountService.AllOperations().forEach(op->{
				System.out.println(op.getOperationDate());
				System.out.println(op.getType());
				System.out.println(op.getAmount());
				System.out.println(op.getDescription());
			});

		};
	}



}
