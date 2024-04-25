package com.huuduc.veladstore.exception;

import java.util.Map;

public class ConflicExeception extends ExceptionCustom{

    public ConflicExeception(Map<String,Object> errors){
        super("DATA IS EXIT",errors);
    }
}
