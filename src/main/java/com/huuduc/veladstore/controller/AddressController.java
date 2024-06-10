package com.huuduc.veladstore.controller;

import com.huuduc.veladstore.common.constraint.ApiUrl;
import com.huuduc.veladstore.common.constraint.PageDefault;
import com.huuduc.veladstore.data.dto.address.AddressDTORequest;
import com.huuduc.veladstore.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiUrl.ADDRESS)
public class AddressController {

    @Autowired
    private AddressService addressService;

    @PostMapping("/add/{userId}")
    public ResponseEntity<?> create(@PathVariable long userId,
                                    @RequestBody AddressDTORequest addressDTORequest){

        return ResponseEntity.status(HttpStatus.CREATED).body(this.addressService.create(userId,addressDTORequest));
    }

    @GetMapping("")
    public ResponseEntity<?> findAllByUser(@RequestParam long userId,
                                           @RequestParam(defaultValue = PageDefault.NO) int no,
                                           @RequestParam(defaultValue = PageDefault.LIMIT) int limit){

        return ResponseEntity.ok(this.addressService.findByUser(userId,no,limit));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable long id){

        return ResponseEntity.ok(this.addressService.findById(id));
    }

    @PutMapping("/{id}")
    public  ResponseEntity<?> updateById(@PathVariable long id,
                                         @RequestBody AddressDTORequest addressDTORequest){

        return ResponseEntity.ok(this.addressService.updateById(id,addressDTORequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletedById(@PathVariable long id){

        this.addressService.deletedById(id);

        return ResponseEntity.ok("Deleted success");


    }
}
