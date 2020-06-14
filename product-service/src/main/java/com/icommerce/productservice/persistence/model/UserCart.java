package com.icommerce.productservice.persistence.model;

import com.icommerce.common.model.AbstractTimeBaseIdentifiable;
import com.icommerce.productservice.helper.ProductListAttributeConverter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_carts", uniqueConstraints = {@UniqueConstraint(columnNames = {UserCart.USER_ID_COLUMN})})
public class UserCart extends AbstractTimeBaseIdentifiable<UserCart> {

    static final String USER_ID_COLUMN = "userId";

    private long userId;

    @Convert(converter = ProductListAttributeConverter.class)
    @Column(columnDefinition = "text default null")
    private List<Product> products;

    @Override
    public boolean deepEquals(UserCart o) {
        return userId == o.userId && Objects.equals(products, o.products);
    }

    @Override
    public int deepHashCode() {
        return Objects.hash(userId, products);
    }

}
