package com.huuduc.veladstore.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDTOResponse {

    private String msg;
    private Object error;
    private String path;
}
