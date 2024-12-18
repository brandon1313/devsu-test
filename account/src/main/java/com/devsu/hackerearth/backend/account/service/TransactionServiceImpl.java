package com.devsu.hackerearth.backend.account.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;

import com.devsu.hackerearth.backend.account.converter.TransactionConverter;
import com.devsu.hackerearth.backend.account.exception.NotEnoughBalanceException;
import com.devsu.hackerearth.backend.account.model.Account;
import com.devsu.hackerearth.backend.account.model.Transaction;
import com.devsu.hackerearth.backend.account.model.dto.BankStatementDetail;
import com.devsu.hackerearth.backend.account.model.dto.BankStatementDto;
import com.devsu.hackerearth.backend.account.model.dto.TransactionDto;
import com.devsu.hackerearth.backend.account.repository.TransactionRepository;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionConverter converter;
    private final AccountService accountService;

    public TransactionServiceImpl(TransactionRepository transactionRepository, TransactionConverter converter,
            AccountService accountService) {
        this.transactionRepository = transactionRepository;
        this.converter = converter;
        this.accountService = accountService;
    }

    @Override
    public List<TransactionDto> getAll() {
        // Get all transactions
        return transactionRepository.findAll().stream().map(converter::convert).collect(Collectors.toList());
    }

    @Override
    public TransactionDto getById(Long id) {
        // Get transactions by id
        return converter.convert(getTransaction(id));
    }

    private Transaction getTransaction(Long id) {
        return transactionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Transaction was not found"));
    }

    @Override
    public TransactionDto create(TransactionDto transactionDto) throws NotEnoughBalanceException {
        accountService.hasEnoughBalance(transactionDto.getAccountId(), transactionDto.getAmount());
        Double balance = accountService.updateBalance(transactionDto.getAmount(), transactionDto.getAccountId());
        Transaction trx = Transaction.builder()
                .date(transactionDto.getDate())
                .accountId(transactionDto.getAccountId())
                .amount(transactionDto.getAmount())
                .balance(balance)
                .type(transactionDto.getType())
                .build();
        return converter.convert(transactionRepository.save(trx));
    }

    @Override
    public List<BankStatementDto> getAllByAccountClientIdAndDateBetween(Long clientId, Date dateTransactionStart,
            Date dateTransactionEnd) {
        List<Account> accounts = accountService.getAllByClientId(clientId);
        List<BankStatementDto> statementList = new ArrayList<>();
        for (Account account : accounts) {
            List<Transaction> trxs = transactionRepository.findByDateBetweenAndAccountId(dateTransactionStart,
                    dateTransactionEnd, account.getId());
            BankStatementDto bankStatement = BankStatementDto.builder()
                    .accountNumber(account.getNumber())
                    .accountType(account.getType())
                    .client(account.getClientId().toString())// can make the call to another microsevice using feign or
                                                             // rest template but the time is not enough to perform this
                                                             // action
                    .isActive(account.isActive())
                    .initialAmount(account.getInitialAmount())
                    .build();

            List<BankStatementDetail> details = new ArrayList<>();
            for(Transaction transaction: trxs){
                BankStatementDetail detail = BankStatementDetail.builder()
                    .amount(transaction.getAmount())
                    .balance(transaction.getBalance())
                    .date(transaction.getDate())
                    .transactionType(transaction.getType())
                    .build();
                details.add(detail);
            }

            statementList.add(bankStatement);
        }
        return statementList;
    }

    @Override
    public TransactionDto getLastByAccountId(Long accountId) {
        // If you need it
        return null;
    }

}
