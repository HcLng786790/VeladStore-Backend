package com.huuduc.veladstore.data.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthenDTOResponse {

    private String msg;
    private String token;
}
