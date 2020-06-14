package com.icommerce.profileservice.persistence.dao;

import com.icommerce.profileservice.persistence.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    User getByFacebookId(String facebookId);

}
