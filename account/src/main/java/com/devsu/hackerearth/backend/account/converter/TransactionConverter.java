package com.devsu.hackerearth.backend.account.converter;

import org.springframework.core.convert.converter.Converter;

import com.devsu.hackerearth.backend.account.model.Transaction;
import com.devsu.hackerearth.backend.account.model.dto.TransactionDto;

public class TransactionConverter implements Converter<Transaction, TransactionDto> {

    @Override
    public TransactionDto convert(Transaction source) {
        return TransactionDto.builder()
                .date(source.getDate())
                .type(source.getType())
                .accountId(source.getAccountId())
                .amount(source.getAmount())
                .balance(source.getBalance())
                .id(source.getId())
                .build();
    }

}
