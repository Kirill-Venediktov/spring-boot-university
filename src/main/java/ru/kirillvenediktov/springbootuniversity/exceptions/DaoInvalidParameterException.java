package ru.kirillvenediktov.springbootuniversity.exceptions;

public class DaoInvalidParameterException extends RuntimeException {

    public DaoInvalidParameterException(String message) {
        super(message);
    }

}
