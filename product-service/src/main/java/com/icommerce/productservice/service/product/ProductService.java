package com.icommerce.productservice.service.product;

import com.icommerce.productservice.dto.ProductDTO;

import java.util.List;

public interface ProductService {

    List<ProductDTO> searchProduct(String name, String[] brands, String[] colours, Double priceFrom, Double priceTo);

}
