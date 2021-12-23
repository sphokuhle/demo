package com.example.demo.controller;

import com.example.demo.exception.CustomExceptionObject;
import com.example.demo.service.RestartService;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.JDBCConnectionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.BadRequestException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

@ControllerAdvice
@Slf4j
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {
    @Autowired
    private RestartService restartService;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");

    @ExceptionHandler(value
            = { IllegalArgumentException.class, IllegalStateException.class,
                NullPointerException.class, NumberFormatException.class, BadRequestException.class})
    @Async
    protected ResponseEntity<Object> handleException(RuntimeException ex, WebRequest request) {
        log.info("Exception was thrown in controller");
        CustomExceptionObject customExceptionObject = new CustomExceptionObject();
        customExceptionObject.setError(ex.getMessage());
        customExceptionObject.setTimestamp(sdf.format(new Timestamp(System.currentTimeMillis())));

        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        if (requestAttributes instanceof ServletRequestAttributes) {
            HttpServletRequest httpServletRequest = ((ServletRequestAttributes)requestAttributes).getRequest();
            customExceptionObject.setPath(httpServletRequest.getRequestURI());
        }

        if(ex instanceof IllegalArgumentException || ex instanceof NumberFormatException || ex instanceof BadRequestException) {
            httpStatus = HttpStatus.BAD_REQUEST;
        }
        customExceptionObject.setStatus(httpStatus.value());
        restartService.restart();
        return handleExceptionInternal(ex, customExceptionObject,
                new HttpHeaders(), httpStatus, request);
    }

}
