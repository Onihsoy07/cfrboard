package com.project.cfrboard.controller.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice
public class ExControllerAdvice {


    @ExceptionHandler
    public ResponseEntity<String> accessExHandler(AccessDeniedException e) {
        log.error("[exceptionHandler] AccessDeniedException", e);
        return new ResponseEntity<>("권한 없음", HttpStatus.UNAUTHORIZED);
    }
    
    @ExceptionHandler
    public ResponseEntity<String> exHandler(Exception e) {
        log.error("[exceptionHandler] Exception", e);
        return new ResponseEntity<>("서버오류", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
