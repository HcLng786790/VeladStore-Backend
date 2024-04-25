package com.huuduc.veladstore.service;

import com.huuduc.veladstore.data.dto.user.RegisterDTORequest;
import com.huuduc.veladstore.data.dto.user.RegisterDTOResponse;
import com.huuduc.veladstore.data.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    RegisterDTOResponse register(RegisterDTORequest registerDTORequest);

    List<User> getAll();

}
