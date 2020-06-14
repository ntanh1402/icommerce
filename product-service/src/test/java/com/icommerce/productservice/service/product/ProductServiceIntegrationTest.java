package com.icommerce.productservice.service.product;

import com.icommerce.productservice.dto.ProductDTO;
import com.icommerce.productservice.persistence.dao.ProductRepository;
import com.icommerce.productservice.persistence.model.Product;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceIntegrationTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @Before
    public void setup() {
        Product ultraBoost = new Product("ultra boost", "ubr", "adidas", "red", 120);
        Product pureBoost = new Product("pure boost", "pbb", "adidas", "blue", 100);
        Product airMax = new Product("air max", "namb", "nike", "black", 200);

        productRepository.save(ultraBoost);
        productRepository.save(pureBoost);
        productRepository.save(airMax);
    }

    @After
    public void cleanUp() {
        productRepository.deleteAll();
    }

    @Test
    public void searchProductWithBrandsSucceeds() {
        String[] brands = {"nike"};
        List<ProductDTO> products = productService.searchProduct(null, brands, null, null, null);

        assertEquals(1, products.size());
        assertEquals("air max", products.get(0).getName());
        assertEquals("namb", products.get(0).getSku());
        assertEquals("nike", products.get(0).getBrand());
        assertEquals("black", products.get(0).getColour());
        assertEquals(200.0, products.get(0).getPrice(), 0.1);
    }

    @Test
    public void searchProductWithColoursSucceeds() {
        String[] colours = {"red", "black"};
        List<ProductDTO> products = productService.searchProduct(null, null, colours, null, null);

        assertEquals(2, products.size());

        assertEquals("ultra boost", products.get(0).getName());
        assertEquals("ubr", products.get(0).getSku());
        assertEquals("adidas", products.get(0).getBrand());
        assertEquals("red", products.get(0).getColour());
        assertEquals(120.0, products.get(0).getPrice(), 0.1);

        assertEquals("air max", products.get(1).getName());
        assertEquals("namb", products.get(1).getSku());
        assertEquals("nike", products.get(1).getBrand());
        assertEquals("black", products.get(1).getColour());
        assertEquals(200.0, products.get(1).getPrice(), 0.1);
    }

    @Test
    public void searchProductWithNameAndPriceSucceeds() {
        List<ProductDTO> products = productService.searchProduct("boost", null, null, 110.0, 120.0);

        assertEquals(1, products.size());

        assertEquals("ultra boost", products.get(0).getName());
        assertEquals("ubr", products.get(0).getSku());
        assertEquals("adidas", products.get(0).getBrand());
        assertEquals("red", products.get(0).getColour());
        assertEquals(120.0, products.get(0).getPrice(), 0.1);
    }

}