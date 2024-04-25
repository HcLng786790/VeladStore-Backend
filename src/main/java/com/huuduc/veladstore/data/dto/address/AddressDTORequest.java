package com.huuduc.veladstore.data.dto.address;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressDTORequest {

    private String location;
    private String phoneReceiver;
    private String nameReceiver;
    private boolean defaults;
}
