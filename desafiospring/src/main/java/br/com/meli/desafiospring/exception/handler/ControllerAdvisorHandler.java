package br.com.meli.desafiospring.exception.handler;
import br.com.meli.desafiospring.dto.ExceptionPayloadDTO;
import br.com.meli.desafiospring.exception.ProductDoesNotExistsException;
import br.com.meli.desafiospring.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
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
}
