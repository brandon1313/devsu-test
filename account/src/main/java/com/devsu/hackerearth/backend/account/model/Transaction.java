package com.devsu.hackerearth.backend.account.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.devsu.hackerearth.backend.account.model.enums.TransactionType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Transaction extends Base {

	private Date date;
	private TransactionType type;
	private double amount;
	private double balance;

	@Column(name = "account_id")
	private Long accountId;
}
