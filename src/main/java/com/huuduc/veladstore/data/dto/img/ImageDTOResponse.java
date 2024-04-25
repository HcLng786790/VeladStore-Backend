package com.huuduc.veladstore.data.dto.img;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageDTOResponse {
    private String id;
    private String name;
    private boolean status;
}
