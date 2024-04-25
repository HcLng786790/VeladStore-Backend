package com.huuduc.veladstore.exception;

import java.util.Map;

public class OutOfStockException extends ExceptionCustom{

    public OutOfStockException(Map<String,Object> errors) {
        super("Data out of stock", errors);
    }
}
