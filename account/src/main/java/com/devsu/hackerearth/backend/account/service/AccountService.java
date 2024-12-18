package com.devsu.hackerearth.backend.account.service;

import java.util.List;

import com.devsu.hackerearth.backend.account.exception.NotEnoughBalanceException;
import com.devsu.hackerearth.backend.account.model.Account;
import com.devsu.hackerearth.backend.account.model.dto.AccountDto;
import com.devsu.hackerearth.backend.account.model.dto.PartialAccountDto;

public interface AccountService {

	public List<AccountDto> getAll();

	public AccountDto getById(Long id);

	public AccountDto create(AccountDto accountDto);

	public AccountDto update(Long id, AccountDto accountDto);

	public AccountDto partialUpdate(Long id, PartialAccountDto partialAccountDto);

	public void deleteById(Long id);

	public Double updateBalance(Double balance, Long id);

	public boolean hasEnoughBalance(long id, double amountToDebit) throws NotEnoughBalanceException;

	public List<Account> getAllByClientId(Long clientId);
}
