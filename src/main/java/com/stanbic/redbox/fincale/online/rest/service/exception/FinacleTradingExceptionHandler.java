package com.stanbic.redbox.fincale.online.rest.service.exception;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDate;

@RestControllerAdvice
@RequiredArgsConstructor
public class FinacleTradingExceptionHandler extends ResponseEntityExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(FinacleTradingExceptionHandler.class);

    @ExceptionHandler(FinacleTradingException.class)
    public ResponseEntity<?> handleFinacleTradingException(FinacleTradingException exception){
            String msg =exception.getMessage();
            log.info(msg, exception);
            ErrorMessage errorMessage = ErrorMessage.builder()
                    .errorDetails(exception.getErrorDetails())
                    .responseCode(exception.getResponseCode())
                    .responseDescription(exception.getResponseDescription())
                    .date(LocalDate.now())
                    .build();
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception exception){
        String msg = exception.getMessage();
        log.info(msg, exception);
        ErrorMessage errorMessage = ErrorMessage.builder()
                .responseDescription(msg)
                .date(LocalDate.now())
                .errorDetails(exception.getLocalizedMessage())
                .build();
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }
}
