package ru.kirillvenediktov.springbootuniversity.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ElementIncorrectData> handleException(ChangesNotMadeException exception){
        ElementIncorrectData incorrectData = new ElementIncorrectData();
        incorrectData.setInfo(exception.getMessage());
        return new ResponseEntity<>(incorrectData, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ElementIncorrectData> handleException(NumberFormatException exception){
        ElementIncorrectData incorrectData = new ElementIncorrectData();
        incorrectData.setInfo(exception.getMessage());
        return new ResponseEntity<>(incorrectData, HttpStatus.NOT_FOUND);
    }


}
