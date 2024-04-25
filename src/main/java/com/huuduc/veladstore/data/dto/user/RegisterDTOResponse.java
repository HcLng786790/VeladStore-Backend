package com.huuduc.veladstore.data.dto.user;

import com.huuduc.veladstore.data.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDTOResponse {

    private String email;
    private String fullName;
    private Date birthday;
    private boolean status;
    private Role role;
}
