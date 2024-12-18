package com.devsu.hackerearth.backend.account.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;

import com.devsu.hackerearth.backend.account.model.Account;
import com.devsu.hackerearth.backend.account.model.dto.AccountDto;

@Service
public class AccountConverter implements Converter<Account, AccountDto>{

    @Override
    public AccountDto convert(Account acc) {
        return AccountDto.builder()
        .clientId(acc.getClientId())
        .id(acc.getId())
        .isActive(acc.isActive())
        .number(acc.getNumber())
        .initialAmount(acc.getInitialAmount())
        .build();
    }
    
}
