package com.assigment.bookstore.exceptions;


public class AlreadyExistsException extends RuntimeException{
    public AlreadyExistsException(String name){
        super("Object: <" + name + "> already exist.");
    }
}
