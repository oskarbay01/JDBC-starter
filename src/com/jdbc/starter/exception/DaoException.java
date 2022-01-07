package com.jdbc.starter.exception;

public class DaoException extends RuntimeException {

    public DaoException(Throwable throwable){
        super(throwable);
    }

}
