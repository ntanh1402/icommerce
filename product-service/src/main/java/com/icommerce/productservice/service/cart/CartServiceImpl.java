package com.icommerce.productservice.service.cart;

import com.icommerce.productservice.dto.ProductDTO;
import com.icommerce.productservice.helper.ProductDtoMapper;
import com.icommerce.productservice.persistence.dao.UserCartRepository;
import com.icommerce.productservice.persistence.model.Product;
import com.icommerce.productservice.persistence.model.UserCart;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service("CartService")
public class CartServiceImpl implements CartService {

    @Autowired
    private UserCartRepository userCartRepository;

    @Override
    public void saveUserCart(long userId, List<ProductDTO> productsDtos) {
        ProductDtoMapper mapper = Mappers.getMapper(ProductDtoMapper.class);
        List<Product> products = productsDtos.stream().map(mapper::productDTOToProduct).collect(Collectors.toList());

        UserCart userCart = userCartRepository.getByUserId(userId);
        if (userCart == null) {
            userCart = new UserCart(userId, products);
        } else {
            userCart.setProducts(products);
        }

        userCartRepository.save(userCart);
    }

    @Override
    public List<ProductDTO> getUserCart(long userId) {
        UserCart userCart = userCartRepository.getByUserId(userId);

        if (userCart != null) {
            ProductDtoMapper mapper = Mappers.getMapper(ProductDtoMapper.class);
            return userCart.getProducts().stream().map(mapper::productToProductDTO).collect(Collectors.toList());
        }

        return new ArrayList<>();
    }

}
