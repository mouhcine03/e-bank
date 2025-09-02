package com.mouhcine.ebankbackend.dtos;

import com.mouhcine.ebankbackend.Enums.operationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor
public class operationRequestDto {
    private double amount;
    private operationType operationType;
    private String description;
    private String idDestinationVirement;

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public com.mouhcine.ebankbackend.Enums.operationType getOperationType() {
        return operationType;
    }

    public void setOperationType(com.mouhcine.ebankbackend.Enums.operationType operationType) {
        this.operationType = operationType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIdDestinationVirement() {
        return idDestinationVirement;
    }

    public void setIdDestinationVirement(String idDestinationVirement) {
        this.idDestinationVirement = idDestinationVirement;
    }
}
