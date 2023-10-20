package com.project.cfrboard.controller.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestControllerAdvice
@ControllerAdvice
public class ExControllerAdvice {


    @ExceptionHandler
    public ResponseEntity<String> accessExHandler(AccessDeniedException e, HttpServletRequest request) {
        log.error("[exceptionHandler] AccessDeniedException", e);
        return new ResponseEntity<>("권한 없음", HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler
    public String illegalArgumentExceptionHandler (IllegalArgumentException e, HttpServletRequest request) {
        log.error("[exceptionHandler] IllegalArgumentException", e);
        return "error/404";
    }

    @ExceptionHandler
    public String methodArgumentTypeMismatchExceptionHandler (MethodArgumentTypeMismatchException e, HttpServletRequest request) {
        log.error("[exceptionHandler] MethodArgumentTypeMismatchException", e);
        return "error/404";
    }
    
    @ExceptionHandler
    public ResponseEntity<String> exHandler(Exception e, HttpServletRequest request) {
        log.error("[exceptionHandler] Exception", e);
        return new ResponseEntity<>("서버오류", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
