package br.com.meli.desafiospring.exception.handler;
import br.com.meli.desafiospring.dto.ExceptionPayloadDTO;
import br.com.meli.desafiospring.exception.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ControllerAdvisorHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = {ResourceNotFoundException.class})
    protected ResponseEntity<Object> handleUserAlreadyExistsException(ResourceNotFoundException exception) {
        ExceptionPayloadDTO exceptionPayload = ExceptionPayloadDTO.builder()
                .timestamp(LocalDateTime.now())
                .title("Resource not found")
                .statusCode(HttpStatus.NOT_FOUND.value())
                .description(exception.getMessage())
                .build();

        return new ResponseEntity<>(exceptionPayload, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {ProductDoesNotExistsException.class})
    protected ResponseEntity<Object> handleProductDoesNotExistsException(ProductDoesNotExistsException exception) {
        ExceptionPayloadDTO exceptionPayload = ExceptionPayloadDTO.builder()
                .timestamp(LocalDateTime.now())
                .title("Product not found")
                .statusCode(HttpStatus.UNPROCESSABLE_ENTITY.value())
                .description(exception.getMessage())
                .build();

        return new ResponseEntity<>(exceptionPayload, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(value = {ClientRegisteredException.class})
    protected ResponseEntity<Object> handleClientRegisteredException(ClientRegisteredException exception) {
        ExceptionPayloadDTO exceptionPayload = ExceptionPayloadDTO.builder()
                .timestamp(LocalDateTime.now())
                .title("Client Already Registered")
                .statusCode(HttpStatus.CONFLICT.value())
                .description(exception.getMessage())
                .build();

        return new ResponseEntity<>(exceptionPayload, HttpStatus.CONFLICT);
    }


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ExceptionPayloadDTO exceptionPayload = ExceptionPayloadDTO.builder()
                .timestamp(LocalDateTime.now())
                .title("Fieldset validation error")
                .statusCode(HttpStatus.UNPROCESSABLE_ENTITY.value())
                .description(ex.getMessage())
                .build();

        return new ResponseEntity<>(exceptionPayload, HttpStatus.UNPROCESSABLE_ENTITY);
    }
          
    @ExceptionHandler(value = {OutOfStockException.class})
    protected ResponseEntity<Object> handleProductDoesNotExistsException(OutOfStockException exception) {
        ExceptionPayloadDTO exceptionPayload = ExceptionPayloadDTO.builder()
                .timestamp(LocalDateTime.now())
                .title("Product out of stock")
                .statusCode(HttpStatus.UNPROCESSABLE_ENTITY.value())
                .description(exception.getMessage())
                .build();

        return new ResponseEntity<>(exceptionPayload, HttpStatus.UNPROCESSABLE_ENTITY);
    }


    @ExceptionHandler(value = {DuplicatedResourceException.class})
    protected ResponseEntity<Object> handleDuplicatedResourceException(DuplicatedResourceException exception) {
        ExceptionPayloadDTO exceptionPayload = ExceptionPayloadDTO.builder()
                .timestamp(LocalDateTime.now())
                .title("Duplicated resource")
                .statusCode(HttpStatus.UNPROCESSABLE_ENTITY.value())
                .description(exception.getMessage())
                .build();

        return new ResponseEntity<>(exceptionPayload, HttpStatus.UNPROCESSABLE_ENTITY);
    }

}

