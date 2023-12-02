package com.socialmediaapi.demo.exception;

import com.socialmediaapi.demo.entities.User;
import com.socialmediaapi.demo.entities.UserNotFoundException;
import jakarta.servlet.annotation.WebFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDate;
import java.time.LocalDateTime;


@ControllerAdvice // binds the below function with Spring
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    // This is our own exception structure and returning our response.
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllException(Exception ex , WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now()
                , ex.getMessage() , request.getDescription(false));

        return new ResponseEntity(errorDetails , HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public final ResponseEntity<Object> handleUserNotFoundException(Exception ex , WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now()
                , ex.getMessage() , request.getDescription(false));

        return new ResponseEntity(errorDetails , HttpStatus.NOT_FOUND);
    }

    @Override
    public final ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex , HttpHeaders headers , HttpStatusCode statusCode , WebRequest request) {

        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now()
                , ex.getFieldError().getDefaultMessage() , request.getDescription(false));

        return new ResponseEntity(errorDetails , HttpStatus.BAD_REQUEST);
    }
}
