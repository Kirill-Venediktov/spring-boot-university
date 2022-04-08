package ru.kirillvenediktov.springbootuniversity.exceptions;

public class ChangesNotMadeException extends RuntimeException {

    public ChangesNotMadeException(String message) {
        super(message);
    }

}
