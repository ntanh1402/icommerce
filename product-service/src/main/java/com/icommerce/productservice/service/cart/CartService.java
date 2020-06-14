package com.icommerce.productservice.service.cart;

import com.icommerce.productservice.dto.ProductDTO;

import java.util.List;

public interface CartService {

    void saveUserCart(long userId, List<ProductDTO> products);

    List<ProductDTO> getUserCart(long userId);

}
