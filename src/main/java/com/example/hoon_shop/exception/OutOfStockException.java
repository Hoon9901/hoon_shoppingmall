package com.example.hoon_shop.exception;


public class OutOfStockException extends RuntimeException{
    public OutOfStockException(String message) {
        super(message);
    }

}
