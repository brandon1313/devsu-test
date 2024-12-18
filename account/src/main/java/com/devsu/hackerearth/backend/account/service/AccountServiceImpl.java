package com.devsu.hackerearth.backend.account.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;

import com.devsu.hackerearth.backend.account.converter.AccountConverter;
import com.devsu.hackerearth.backend.account.exception.NotEnoughBalanceException;
import com.devsu.hackerearth.backend.account.model.Account;
import com.devsu.hackerearth.backend.account.model.dto.AccountDto;
import com.devsu.hackerearth.backend.account.model.dto.PartialAccountDto;
import com.devsu.hackerearth.backend.account.repository.AccountRepository;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountConverter accountConverter;

    public AccountServiceImpl(AccountRepository accountRepository, AccountConverter accountConverter) {
        this.accountRepository = accountRepository;
        this.accountConverter = accountConverter;
    }

    @Override
    public List<AccountDto> getAll() {
        return accountRepository.findAll().stream().map(accountConverter::convert).collect(Collectors.toList());
    }

    @Override
    public AccountDto getById(Long id) {
        // Get accounts by id
        return accountConverter.convert(getAccount(id));
    }

    private Account getAccount(Long id) {
        return accountRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Account not found."));
    }

    @Override
    public AccountDto create(AccountDto accountDto) {
        Account acc = Account.builder()
                .initialAmount(accountDto.getInitialAmount())
                .clientId(accountDto.getClientId())
                .isActive(accountDto.isActive())
                .number(accountDto.getNumber())
                .type(accountDto.getType())
                .build();

        return accountConverter.convert(accountRepository.save(acc));
    }

    @Override
    public AccountDto update(Long id, AccountDto accountDto) {
        Account acc = getAccount(id);
        acc.setActive(accountDto.isActive());
        acc.setType(accountDto.getType());
        acc.setNumber(accountDto.getNumber());
        acc.setClientId(accountDto.getClientId());
        acc.setInitialAmount(accountDto.getInitialAmount());
        return accountConverter.convert(accountRepository.save(acc));
    }

    @Override
    public AccountDto partialUpdate(Long id, PartialAccountDto partialAccountDto) {
        Account acc = getAccount(id);
        acc.setActive(partialAccountDto.isActive());
        return accountConverter.convert(accountRepository.save(acc));
    }

    @Override
    public void deleteById(Long id) {
        accountRepository.deleteById(id);
    }

    @Override
    public Double updateBalance(Double amount, Long id) {
        Account acc = getAccount(id);
        Double currentBalance = acc.getInitialAmount() + amount;
        acc.setInitialAmount(currentBalance);
        return accountRepository.save(acc).getInitialAmount();
    }

    @Override
    public boolean hasEnoughBalance(long id, double amountToDebit) throws NotEnoughBalanceException {
        Account acc = getAccount(id);
        if (acc.getInitialAmount() < amountToDebit)
            throw new NotEnoughBalanceException("Balance is not enough to do the transaction.");
        return true;
    }

    @Override
    public List<Account> getAllByClientId(Long clientId){
        return accountRepository.findByClientId(clientId);
    }
}
