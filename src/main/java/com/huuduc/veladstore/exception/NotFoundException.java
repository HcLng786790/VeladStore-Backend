package com.huuduc.veladstore.exception;

import java.util.Map;

public class NotFoundException extends ExceptionCustom{

    public NotFoundException(Map<String,Object> errors) {
        super("Data not found",errors);
    }
}
