package com.assigment.bookstore.exceptions;


public class AlreadyPayedException extends RuntimeException{
    public AlreadyPayedException(String name){
        super("Book order: <" + name + "> already payed.");
    }
}
