package com.unisociesc.bookfair.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Genre {

    private Long id;
    private String name;
    private Double price;
    private Integer amount;

}
