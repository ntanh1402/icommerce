package com.icommerce.profileservice.service.user;

import com.icommerce.profileservice.persistence.dao.UserRepository;
import com.icommerce.profileservice.persistence.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @Test
    public void getByFacebookIdReturnAUser() {
        String facebookId = "alex";
        User alex = new User();
        alex.setId(1L);
        alex.setFacebookId(facebookId);

        when(userRepository.getByFacebookId(eq(facebookId))).thenReturn(alex);

        User result = userService.getByFacebookId(facebookId);
        assertEquals(alex.getFacebookId(), result.getFacebookId());
        assertEquals(alex.getId(), result.getId());
    }

    @Test
    public void getByFacebookIdReturnNull() {
        String facebookId = "alex";
        when(userRepository.getByFacebookId(eq(facebookId))).thenReturn(null);

        User result = userService.getByFacebookId(facebookId);
        assertNull(result);
    }

    @Test
    public void createUserSucceeds() {
        User alex = new User();
        alex.setId(1L);
        alex.setFacebookId("alex");

        when(userRepository.save(alex)).thenReturn(alex);

        User result = userService.createUser(alex);
        assertEquals(alex, result);
    }

}