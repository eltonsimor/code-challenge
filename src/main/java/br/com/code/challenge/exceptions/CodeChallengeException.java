package br.com.code.challenge.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CodeChallengeException extends Exception {
    private static final long serialVersionUID = -6602292035129490363L;

    private HttpStatus httpStatus;

    public CodeChallengeException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public CodeChallengeException(HttpStatus httpStatus){
        super();
        this.httpStatus = httpStatus;
    }

}
