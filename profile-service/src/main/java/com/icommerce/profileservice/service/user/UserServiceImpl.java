package com.icommerce.profileservice.service.user;

import com.icommerce.profileservice.persistence.dao.UserRepository;
import com.icommerce.profileservice.persistence.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("UserService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User getByFacebookId(String facebookId) {
        return userRepository.getByFacebookId(facebookId);
    }

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

}
