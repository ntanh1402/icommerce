package com.icommerce.productservice.persistence.dao;

import com.icommerce.productservice.persistence.model.UserCart;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCartRepository extends CrudRepository<UserCart, Long> {

    UserCart getByUserId(long userId);

}
