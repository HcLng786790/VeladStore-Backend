package com.huuduc.veladstore.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaginationDTO {

    private List<?> contents;
    private boolean isFirst;
    private boolean isLast;
    private long totalPage;
    private long totalItems;
    private int limit;
    private int no;

}
