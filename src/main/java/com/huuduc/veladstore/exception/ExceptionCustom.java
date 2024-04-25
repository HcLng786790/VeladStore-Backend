package com.huuduc.veladstore.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExceptionCustom extends RuntimeException{

    private final Object errors;

    public ExceptionCustom(String message, Object errors) {
        super(message);
        this.errors = errors;
    }
}
