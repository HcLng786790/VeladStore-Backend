package com.huuduc.veladstore.service.impl;

import com.huuduc.veladstore.data.dto.PaginationDTO;
import com.huuduc.veladstore.data.dto.address.AddressDTORequest;
import com.huuduc.veladstore.data.dto.address.AddressDTOResponse;
import com.huuduc.veladstore.data.entity.Address;
import com.huuduc.veladstore.data.entity.User;
import com.huuduc.veladstore.data.mapper.AddressMapper;
import com.huuduc.veladstore.exception.NotFoundException;
import com.huuduc.veladstore.exception.NullException;
import com.huuduc.veladstore.repository.AddressRepository;
import com.huuduc.veladstore.repository.UserRepository;
import com.huuduc.veladstore.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Component
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressMapper addressMapper;

    @Override
    public AddressDTOResponse create(long userId, AddressDTORequest addressDTORequest) {

        //Check exits user
        User findUser = this.userRepository.findById(userId).orElseThrow(
                ()-> new NotFoundException(Collections.singletonMap("User id",userId))
        );

        //Check null param
        Map<String,Object> errors = new HashMap<>();

        if(ObjectUtils.isEmpty(addressDTORequest.getLocation())){
            errors.put("Address location:",null);
        }
        if (ObjectUtils.isEmpty(addressDTORequest.getPhoneReceiver())){
            errors.put("Phone receiver:",null);
        }

        if(errors.size()>0){
            throw new NullException(Collections.singletonMap("Address Request:",errors));
        }

        //Create new address
        Address newAddress = this.addressMapper.toEntity(addressDTORequest);

        //Check if name request null then set name is full_name in user
        if(ObjectUtils.isEmpty(addressDTORequest.getNameReceiver())){
            newAddress.setNameReceiver(findUser.getFullName());
        }

        //Check if defaults null then set defaults is false
        if(ObjectUtils.isEmpty(addressDTORequest.isDefaults())){
            newAddress.setDefaults(false);
        }

        newAddress.setStatus(true);
        newAddress.setUser(findUser);

        //Check if defaults is true then set the defaults address in database to false
        if(newAddress.isDefaults()){
            Address defaultsAddress = this.addressRepository.findByUserIdAndDefaultsIsTrue(userId);
            if(defaultsAddress!=null){
                defaultsAddress.setDefaults(false);
                this.addressRepository.save(defaultsAddress);
            }
        }

        this.addressRepository.save(newAddress);

        return this.addressMapper.toDTO(newAddress);
    }

    @Override
    public PaginationDTO findByUser(long userId,int no,int limit) {

        User findUser = this.userRepository.findById(userId).orElseThrow(
                ()->new NotFoundException(Collections.singletonMap("User id:",userId))
        );

        if(!findUser.isStatus()){
            throw new NotFoundException(Collections.singletonMap("User id",userId));
        }


        Page<AddressDTOResponse> page = this.addressRepository.findByUserIdAndStatusIsTrue(userId, PageRequest.of(no,limit))
                .map(p -> this.addressMapper.toDTO(p));

        return new PaginationDTO(page.getContent(), page.isFirst(), page.isLast(), page.getTotalPages(),
                page.getTotalElements(), page.getSize(), page.getNumber());
    }

    @Override
    public AddressDTOResponse findById(long id) {

        Address findAddress = this.addressRepository.findById(id).orElseThrow(
                ()-> new NotFoundException(Collections.singletonMap("Address id",id))
        );

        if(!findAddress.isStatus()){
            throw new NotFoundException(Collections.singletonMap("Address id",id));
        }

        return this.addressMapper.toDTO(findAddress);
    }

    @Override
    public AddressDTOResponse updateById(long id, AddressDTORequest addressDTORequest) {

        Address findAddress = this.addressRepository.findById(id).orElseThrow(
                ()-> new NotFoundException(Collections.singletonMap("Address id",id))
        );

        if(!findAddress.isStatus()){
            throw new NotFoundException(Collections.singletonMap("Address id",id));
        }

        if(!ObjectUtils.isEmpty(addressDTORequest.getLocation())){
            findAddress.setLocation(addressDTORequest.getLocation());
        }
        if(!ObjectUtils.isEmpty(addressDTORequest.getNameReceiver())){
            findAddress.setNameReceiver(addressDTORequest.getNameReceiver());
        }
        if(!ObjectUtils.isEmpty(addressDTORequest.getPhoneReceiver())){
            findAddress.setPhoneReceiver(addressDTORequest.getPhoneReceiver());
        }

        //Nếu có yêu cầu cập nhật defaults cho address thì tiến hành cập nhật lại địa chỉ defaults cũ là false
        if(addressDTORequest.isDefaults()){
            Address defaultsAddress = this.addressRepository.findByUserIdAndDefaultsIsTrue(findAddress.getUser().getId());
            if(defaultsAddress!=null){
                defaultsAddress.setDefaults(false);
                this.addressRepository.save(defaultsAddress);
            }

            findAddress.setDefaults(true);
        }

        this.addressRepository.save(findAddress);

        return this.addressMapper.toDTO(findAddress);

    }

    @Override
    public boolean deletedById(long id) {

        Address findAddress = this.addressRepository.findById(id).orElseThrow(
                ()-> new NotFoundException(Collections.singletonMap("Address id",id))
        );

        if(!findAddress.isStatus()){
            throw new NotFoundException(Collections.singletonMap("Address id",null));
        }

        findAddress.setStatus(false);

        this.addressRepository.save(findAddress);

        return true;
    }
}
