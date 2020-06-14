package com.icommerce.productservice.service.cart;

import com.icommerce.productservice.dto.ProductDTO;
import com.icommerce.productservice.persistence.dao.UserCartRepository;
import com.icommerce.productservice.persistence.model.Product;
import com.icommerce.productservice.persistence.model.UserCart;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CartServiceIntegrationTest {

    @Autowired
    private CartService cartService;

    @Autowired
    private UserCartRepository userCartRepository;

    @Before
    public void setup() {
        userCartRepository.deleteAll();
    }

    @After
    public void cleanUp() {
        userCartRepository.deleteAll();
    }

    @Test
    public void saveUserCartWithNonExistedUserCartSucceeds() {
        ProductDTO product = new ProductDTO(1L, "air max", "namb", "nike", "black", 200.0);
        cartService.saveUserCart(1L, Collections.singletonList(product));

        UserCart userCart = userCartRepository.getByUserId(1L);
        assertEquals(1, userCart.getProducts().size());

        assertEquals("air max", userCart.getProducts().get(0).getName());
        assertEquals("namb", userCart.getProducts().get(0).getSku());
        assertEquals("nike", userCart.getProducts().get(0).getBrand());
        assertEquals("black", userCart.getProducts().get(0).getColour());
        assertEquals(200.0, userCart.getProducts().get(0).getPrice(), 0.1);
    }

    @Test
    public void saveUserCartWithExistedUserCartSucceeds() {
        Product product = new Product("air max", "namb", "nike", "black", 200.0);
        userCartRepository.save(new UserCart(1L, Collections.singletonList(product)));

        ProductDTO productDTO = new ProductDTO(2L, "ultra boost", "ubr", "adidas", "red", 120);
        cartService.saveUserCart(1L, Collections.singletonList(productDTO));

        UserCart userCart = userCartRepository.getByUserId(1L);

        assertEquals(1, userCart.getProducts().size());
        assertEquals("ultra boost", userCart.getProducts().get(0).getName());
        assertEquals("ubr", userCart.getProducts().get(0).getSku());
        assertEquals("adidas", userCart.getProducts().get(0).getBrand());
        assertEquals("red", userCart.getProducts().get(0).getColour());
        assertEquals(120.0, userCart.getProducts().get(0).getPrice(), 0.1);
    }

    @Test
    public void getUserCartSucceeds() {
        Product product = new Product("air max", "namb", "nike", "black", 200.0);
        userCartRepository.save(new UserCart(1L, Collections.singletonList(product)));

        List<ProductDTO> productDTOs = cartService.getUserCart(1L);
        assertEquals(1, productDTOs.size());

        assertEquals("air max", productDTOs.get(0).getName());
        assertEquals("namb", productDTOs.get(0).getSku());
        assertEquals("nike", productDTOs.get(0).getBrand());
        assertEquals("black", productDTOs.get(0).getColour());
        assertEquals(200.0, productDTOs.get(0).getPrice(), 0.1);
    }

}