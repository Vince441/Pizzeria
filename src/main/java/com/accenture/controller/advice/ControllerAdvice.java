package com.accenture.controller.advice;

import com.accenture.exception.ClientException;
import com.accenture.exception.IngredientException;
import jakarta.persistence.EntityNotFoundException;
import com.accenture.exception.PizzaException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ControllerAdvice {
    @ExceptionHandler(ClientException.class)
    public ResponseEntity<MessageError> handleClientException(ClientException e){
        MessageError me = new MessageError(LocalDateTime.now(), "Erreur validation", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(me);
    }


    @ExceptionHandler(PizzaException.class)
    public ResponseEntity<MessageError> handleClientException(PizzaException e){
        MessageError me = new MessageError(LocalDateTime.now(), "Erreur validation", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(me);
    }


    @ExceptionHandler(IngredientException.class)
    public ResponseEntity<MessageError> handleIngredientException(IngredientException e){
        MessageError me = new MessageError(LocalDateTime.now(), "Erreur validation", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(me);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<MessageError> handleEntityNotFoundException(EntityNotFoundException e){
        MessageError me = new MessageError(LocalDateTime.now(), "Erreur base", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(me);
    }
}
