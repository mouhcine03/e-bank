package com.mouhcine.ebankbackend.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data @NoArgsConstructor  @AllArgsConstructor
public class AccountHistoryDto {
    private List<AccountOperationDto> accountOperationDtoList;
    private double balance;
    private String accountId;
    private int currentPage;
    private int pageSize;
    private int totalPages;

    public List<AccountOperationDto> getAccountOperationDtoList() {
        return accountOperationDtoList;
    }

    public void setAccountOperationDtoList(List<AccountOperationDto> accountOperationDtoList) {
        this.accountOperationDtoList = accountOperationDtoList;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
