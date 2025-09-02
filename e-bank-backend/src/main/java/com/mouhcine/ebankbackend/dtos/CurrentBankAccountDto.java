package com.mouhcine.ebankbackend.dtos;

import com.mouhcine.ebankbackend.Enums.AccountStatus;
import com.mouhcine.ebankbackend.entities.AccountOperation;
import com.mouhcine.ebankbackend.entities.Customer;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data @NoArgsConstructor @AllArgsConstructor

public class CurrentBankAccountDto extends BankAccountDto {

    private double overdraft;

    public double getOverdraft() {
        return overdraft;
    }

    public void setOverdraft(double overdraft) {
        this.overdraft = overdraft;
    }

    }

