package org.jung.crypto.exception;

public class NotEnoughBalanceException extends RuntimeException{
    public NotEnoughBalanceException(String message){
        super(message);
    }
}
