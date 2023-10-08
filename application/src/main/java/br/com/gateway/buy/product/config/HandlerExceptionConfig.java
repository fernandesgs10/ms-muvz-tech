package br.com.gateway.buy.product.config;

import br.com.gateway.buy.product.common.MessageHandler;
import br.com.gateway.buy.product.common.MuvzException;
import br.com.gateway.buy.product.common.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import javax.validation.ConstraintViolationException;

@Slf4j
@RestController
@ControllerAdvice
public class HandlerExceptionConfig {

    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResponseEntity<Object> exceptionContraintError(ConstraintViolationException exception, WebRequest request) {
        ServletWebRequest servletWebRequest = (ServletWebRequest) request;
        MessageHandler responseMensage = MessageHandler.builder()
                .message(exception.toString()).
                error(HttpStatus.BAD_REQUEST.name())
                .status(HttpStatus.BAD_REQUEST.value())
                .path(servletWebRequest.getRequest().getRequestURL().toString())
                .build();

        log.error(exception.getMessage(), exception);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseMensage);
    }

    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<Object> exceptionNotFoundContraintError(NotFoundException exception, WebRequest request) {
        ServletWebRequest servletWebRequest = (ServletWebRequest) request;
        MessageHandler responseMensage = MessageHandler.builder()
                .message(exception.toString()).
                error(HttpStatus.NOT_FOUND.name())
                .status(HttpStatus.NOT_FOUND.value())
                .path(servletWebRequest.getRequest().getRequestURL().toString())
                .build();

        log.error(exception.getMessage(), exception);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseMensage);
    }

    @ExceptionHandler(value = MuvzException.class)
    public ResponseEntity<Object> exceptionFinnetConnecta(MuvzException exception, WebRequest request) {
        ServletWebRequest servletWebRequest = (ServletWebRequest) request;
        MessageHandler responseMensage = MessageHandler.builder()
                .message(exception.getMessage()).
                error(HttpStatus.INTERNAL_SERVER_ERROR.name())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .path(servletWebRequest.getRequest().getRequestURL().toString())
                .build();

        log.error(exception.getMessage(), exception);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMensage);
    }

    @ExceptionHandler(value = java.lang.Exception.class)
    public ResponseEntity<Object> exception(java.lang.Exception exception, WebRequest request) {
        ServletWebRequest servletWebRequest = (ServletWebRequest) request;

        MessageHandler responseMensage = MessageHandler.builder()
                .message(exception.toString()).
                error(HttpStatus.INTERNAL_SERVER_ERROR.name())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .path(servletWebRequest.getRequest().getRequestURL().toString())
                .build();

        log.error(exception.getMessage(), exception);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMensage);
    }
}