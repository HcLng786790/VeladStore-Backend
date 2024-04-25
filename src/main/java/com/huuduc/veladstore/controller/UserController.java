package com.huuduc.veladstore.controller;

import com.huuduc.veladstore.common.constraint.ApiUrl;
import com.huuduc.veladstore.data.dto.user.RegisterDTORequest;
import com.huuduc.veladstore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiUrl.USER)
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody(required = false) RegisterDTORequest registerDTORequest){

        return ResponseEntity.ok(this.userService.register(registerDTORequest));
    }


}
