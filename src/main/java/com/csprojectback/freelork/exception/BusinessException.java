package com.csprojectback.freelork.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
@ToString
public class BusinessException extends RuntimeException{

    /**
     * Serial
     */
    private static final long serialVersionUID = 706602531750897564L;
    /**
     * Code error
     */
    private final String code;

    /**
     * error description
     */
    private final String description;

    /**
     * http status
     */
    private final HttpStatus httpStatus;

    /**
     * Controller of the error
     */
    private final String controller;

    public BusinessException(final String message) {
        super(message);
        this.httpStatus = null;
        this.code = null;
        this.description = message;
        this.controller = null;
    }

    public BusinessException(final String message, final HttpStatus httpStatus, final String controller) {
        super(message);
        this.httpStatus = httpStatus;
        this.code = null;
        this.description = message;
        this.controller = controller;
    }

}
