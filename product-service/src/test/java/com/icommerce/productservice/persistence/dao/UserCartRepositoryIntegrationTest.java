package com.icommerce.productservice.persistence.dao;

import com.icommerce.productservice.persistence.model.Product;
import com.icommerce.productservice.persistence.model.UserCart;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserCartRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserCartRepository userCartRepository;

    @After
    public void cleanUp() {
        userCartRepository.deleteAll();
    }

    @Test
    public void getByUserIdReturnAUserCart() {
        List<Product> products = new ArrayList<>();
        products.add(new Product("ultra boost", "ub1", "adidas", "red", 120));
        UserCart userCart = new UserCart(1L, products);
        entityManager.persist(userCart);
        entityManager.flush();

        UserCart found = userCartRepository.getByUserId(1L);
        assertEquals(userCart, found);
    }

    @Test
    public void getByUserIdReturnNull() {
        UserCart found = userCartRepository.getByUserId(1L);
        assertNull(found);
    }

}