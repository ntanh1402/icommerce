package com.icommerce.profileservice.persistence.dao;

import com.icommerce.profileservice.persistence.model.User;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @After
    public void cleanUp() {
        userRepository.deleteAll();
    }

    @Test
    public void whenFindByFacebookThenReturnUser() {
        User alex = new User();
        alex.setFacebookId("alex");
        entityManager.persist(alex);
        entityManager.flush();

        User found = userRepository.getByFacebookId(alex.getFacebookId());
        Assert.assertEquals(found.getFacebookId(), alex.getFacebookId());
    }

    @Test
    public void whenFindByFacebookThenReturnNull() {
        User found = userRepository.getByFacebookId("alex");
        Assert.assertNull(found);
    }

}