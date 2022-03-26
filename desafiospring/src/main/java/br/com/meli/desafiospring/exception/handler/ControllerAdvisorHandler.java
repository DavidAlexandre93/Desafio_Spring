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

/**
 * @Metodo: Validar "Resource not found" na resposta do body
 * @Description: Ao enviar as requicoes no body realizar as determinadas validacoes na request
 */
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

    /**
     * @Metodo: Validar "Product not found" na resposta do body
     * @Description: Ao enviar as requicoes no body realizar as determinadas validacoes na request
     * @param exception
     * @return
     */
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

    /**
     * @Metodo: Validar "Client Already Registered" na resposta do body
     * @Description: Ao enviar as requicoes no body realizar as determinadas validacoes na request
     * @param exception
     * @return
     */
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

    /**
     * @Metodo: Validar "Fieldset validation error" na resposta do body
     * @Description: Ao enviar as requicoes no body realizar as determinadas validacoes na request
     * @param ex
     * @param headers
     * @param status
     * @param request
     * @return
     */
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

    /**
     *
     * @Metodo: Validar "Product out of stock" na resposta do body
     * @Description: Ao enviar as requicoes no body realizar as determinadas validacoes na request
     *
     * @param exception
     * @return
     */
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


    /**
     * @Metodo: Validar "Duplicated resource" na resposta do body
     * @Description: Ao enviar as requicoes no body realizar as determinadas validacoes na request
     * @param exception
     * @return
     */
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

