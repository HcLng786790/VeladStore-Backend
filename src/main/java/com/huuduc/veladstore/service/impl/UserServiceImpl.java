package com.huuduc.veladstore.service.impl;

import com.huuduc.veladstore.common.enu.ERole;
import com.huuduc.veladstore.data.dto.user.RegisterDTORequest;
import com.huuduc.veladstore.data.dto.user.RegisterDTOResponse;
import com.huuduc.veladstore.data.entity.Cart;
import com.huuduc.veladstore.data.entity.User;
import com.huuduc.veladstore.data.mapper.UserMapper;
import com.huuduc.veladstore.exception.ConflicExeception;
import com.huuduc.veladstore.exception.NotFoundException;
import com.huuduc.veladstore.exception.NullException;
import com.huuduc.veladstore.repository.RoleRepository;
import com.huuduc.veladstore.repository.UserRepository;
import com.huuduc.veladstore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.Collections;
import java.util.List;

@Component
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper  userMapper;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public RegisterDTOResponse register(RegisterDTORequest registerDTORequest) {

        User user = this.userMapper.toEntity(registerDTORequest);

        if(this.userRepository.existsByEmail(user.getEmail())){
            throw new ConflicExeception(Collections.singletonMap("email",user.getEmail()));
        }

        if(ObjectUtils.isEmpty(registerDTORequest.getEmail())){
            throw new NullException(Collections.singletonMap("Email",null));
        }

        user.setRole(
                this.roleRepository.findByName(ERole.User.toString()).orElseThrow(
                        ()-> new NotFoundException(Collections.singletonMap("Role",ERole.User.toString()))
                )
        );

        user.setPassword(this.passwordEncoder.encode(registerDTORequest.getPassword()));

        Cart newCart = new Cart();
        newCart.setUser(user);

        user.setCart(newCart);
        userRepository.save(user);


        return this.userMapper.toDTO(user);
    }

    @Override
    public List<User> getAll() {
        return this.userRepository.findAll();
    }
}
