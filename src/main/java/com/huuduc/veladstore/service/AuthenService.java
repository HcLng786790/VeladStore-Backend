package com.huuduc.veladstore.service;

import com.huuduc.veladstore.data.dto.user.AuthenDTORequest;
import com.huuduc.veladstore.data.dto.user.AuthenDTOResponse;
import org.springframework.stereotype.Service;

@Service
public interface AuthenService {

    AuthenDTOResponse login(AuthenDTORequest authenDTORequest);
}
