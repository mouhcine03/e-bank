package com.mouhcine.ebankbackend.dtos;

import com.mouhcine.ebankbackend.Enums.operationType;
import com.mouhcine.ebankbackend.entities.BankAccount;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data @NoArgsConstructor @AllArgsConstructor
public class AccountOperationDto {

    private Long id;
    private Date operationDate;
    private double amount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getOperationDate() {
        return operationDate;
    }

    public void setOperationDate(Date operationDate) {
        this.operationDate = operationDate;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public operationType getType() {
        return type;
    }

    public void setType(operationType type) {
        this.type = type;
    }

    private String description;
    private operationType type;

}
