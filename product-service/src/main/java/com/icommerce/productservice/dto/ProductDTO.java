package com.icommerce.productservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

    private long productId;

    private String name;

    private String sku;

    private String brand;

    private String colour;

    private double price;

}
