package com.mouhcine.ebankbackend.entities;

import com.mouhcine.ebankbackend.Enums.operationType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class AccountOperation {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date operationDate;
    private double amount;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private String description;

    @Enumerated(EnumType.STRING)
    private operationType type;

    public void setBankAccount(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }

    public void setType(operationType type) {
        this.type = type;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setOperationDate(Date operationDate) {
        this.operationDate = operationDate;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne
    private BankAccount bankAccount;



    public Long getId() {
        return id;
    }

    public Date getOperationDate() {
        return operationDate;
    }

    public double getAmount() {
        return amount;
    }

    public operationType getType() {
        return type;
    }

    public BankAccount getBankAccount() {
        return bankAccount;
    }
}
