package com.krystiankowalik.apiemail.exception.handler;

import com.sun.mail.util.MailConnectException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.mail.MessagingException;
import javax.mail.SendFailedException;
import java.net.SocketTimeoutException;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String error = "Malformed JSON request";
        return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, error, ex));
    }

    @Order(Ordered.LOWEST_PRECEDENCE)
    @ExceptionHandler(value = SendFailedException.class)
    protected ResponseEntity<Object> handleSendFailed(SendFailedException e) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
        apiError.setMessage(e.getMessage());
        return buildResponseEntity(apiError);
    }

    @Order(Ordered.HIGHEST_PRECEDENCE)
    @ExceptionHandler(MailConnectException.class)
    protected ResponseEntity<Object> handleMailConnect(MailConnectException e) {
        return buildStandardResponseEntity(e, HttpStatus.REQUEST_TIMEOUT);
    }

    @Order(Ordered.HIGHEST_PRECEDENCE)
    @ExceptionHandler(value = {SocketTimeoutException.class, MessagingException.class})
    protected ResponseEntity<Object> handleEmailTimeout(Exception e) {
        return buildStandardResponseEntity(e, HttpStatus.REQUEST_TIMEOUT);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleOtherExceptions(Exception e) {
        return buildStandardResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    private ResponseEntity<Object> buildStandardResponseEntity(Exception e, HttpStatus httpStatus) {
        ApiError apiError = new ApiError(httpStatus);
        apiError.setMessage(e.getMessage());
        return buildResponseEntity(apiError);
    }

    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }


}