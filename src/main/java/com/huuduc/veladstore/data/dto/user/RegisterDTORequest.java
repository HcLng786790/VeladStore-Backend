package com.huuduc.veladstore.data.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDTORequest {

    private String email;
    private String password;
    private String fullName;
    private Date birthday;

}
