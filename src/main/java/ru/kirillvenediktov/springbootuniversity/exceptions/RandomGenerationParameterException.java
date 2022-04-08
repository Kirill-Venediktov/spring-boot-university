package ru.kirillvenediktov.springbootuniversity.exceptions;

public class RandomGenerationParameterException extends RuntimeException {

    public RandomGenerationParameterException(String message) {
        super(message);
    }

}
