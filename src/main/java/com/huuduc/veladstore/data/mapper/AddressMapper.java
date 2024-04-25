package com.huuduc.veladstore.data.mapper;

import com.huuduc.veladstore.data.dto.address.AddressDTORequest;
import com.huuduc.veladstore.data.dto.address.AddressDTOResponse;
import com.huuduc.veladstore.data.entity.Address;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    @Mapping(ignore = true,target = "user")
    Address toEntity(AddressDTORequest addressDTORequest);


    AddressDTOResponse toDTO(Address address);
}
