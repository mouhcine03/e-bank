package com.mouhcine.ebankbackend.dtos;

import com.mouhcine.ebankbackend.Enums.AccType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data @NoArgsConstructor  @AllArgsConstructor
public class CreateAccountDto {

    private double balance;

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getOverdraft() {
        return overdraft;
    }

    public void setOverdraft(double overdraft) {
        this.overdraft = overdraft;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public AccType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccType accountType) {
        this.accountType = accountType;
    }

    private double overdraft;
    private double interestRate;
    private AccType accountType;

}
