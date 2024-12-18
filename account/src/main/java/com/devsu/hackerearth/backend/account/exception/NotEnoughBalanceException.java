package com.devsu.hackerearth.backend.account.exception;

public class NotEnoughBalanceException extends Exception {
   public NotEnoughBalanceException(String message){
    super(message);
   }
}
