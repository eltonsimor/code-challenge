package br.com.code.challenge.handler;

import br.com.code.challenge.rest.response.ErrorResponse;
import br.com.code.challenge.exceptions.CodeChallengeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@RestControllerAdvice
public class CodeChallengeExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CodeChallengeException.class)
    public final ResponseEntity handler(CodeChallengeException exception, HttpServletRequest req){
        String path = req.getContextPath() + req.getServletPath();
        HttpStatus httpStatus = exception.getHttpStatus();
        ErrorResponse error = new ErrorResponse(LocalDateTime.now(), httpStatus.value(), exception.getMessage(), path);

        return new ResponseEntity(error, httpStatus);
    }
}
