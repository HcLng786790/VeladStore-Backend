package com.huuduc.veladstore.service.impl;

import com.huuduc.veladstore.data.dto.user.AuthenDTORequest;
import com.huuduc.veladstore.data.dto.user.AuthenDTOResponse;
import com.huuduc.veladstore.security.JwtService;
import com.huuduc.veladstore.service.AuthenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
public class AuthenServiceImpl implements AuthenService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired

    private JwtService jwtService;

    @Override
    public AuthenDTOResponse login(AuthenDTORequest authenDTORequest) {

        this.authenticationManager
                .authenticate(
                        new UsernamePasswordAuthenticationToken(authenDTORequest.getEmail(),authenDTORequest.getPassword())
                );

        String token = this.jwtService.generateToken(authenDTORequest.getEmail());
        AuthenDTOResponse authenDTOResponse = new AuthenDTOResponse(token,"Đăng nhập thành công");

        return authenDTOResponse;
    }
}
