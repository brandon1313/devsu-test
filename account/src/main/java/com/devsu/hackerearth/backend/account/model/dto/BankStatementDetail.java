package com.devsu.hackerearth.backend.account.model.dto;

import java.util.Date;

import com.devsu.hackerearth.backend.account.model.enums.TransactionType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class BankStatementDetail {
    private Date date;
    private TransactionType transactionType;
    private double amount;
    private double balance;
}
