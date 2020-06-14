package com.icommerce.profileservice.service.user;

import com.icommerce.profileservice.persistence.model.User;

public interface UserService {

    User getByFacebookId(String facebookId);

    User createUser(User user);

}
