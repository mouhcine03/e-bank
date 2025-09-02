package com.mouhcine.ebankbackend.dtos;

import com.mouhcine.ebankbackend.Enums.AccountStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data @NoArgsConstructor @AllArgsConstructor

public class SavingBankAccountDto extends BankAccountDto {




    private double interestRate;

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }


}
