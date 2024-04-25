package com.huuduc.veladstore.exception;

import com.huuduc.veladstore.data.dto.ErrorDTOResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(OutOfStockException.class)
    public ResponseEntity<?> handlerOutOfStockException(OutOfStockException ex,HttpServletRequest req){
        return createErrorRespone(ex,req,HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handlerNotFoundException(NotFoundException ex,HttpServletRequest req){

        return createErrorRespone(ex,req,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NullException.class)
    public ResponseEntity<?> handlerNullException(NullException ex,HttpServletRequest req){

        return createErrorRespone(ex,req,HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler(ConflicExeception.class)
    public ResponseEntity<?> handlerConflicException(ConflicExeception ex,HttpServletRequest req){

        return createErrorRespone(ex,req,HttpStatus.CONFLICT);
    }

    private ResponseEntity<?> createErrorRespone(ExceptionCustom ex, HttpServletRequest req, HttpStatus status){

        ErrorDTOResponse errorDTOResponse = new ErrorDTOResponse(
                ex.getMessage(),ex.getErrors(), req.getServletPath()
        );

        return ResponseEntity.status(status).body(errorDTOResponse);
    }
}
