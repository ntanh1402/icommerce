package com.icommerce.productservice.persistence.model;

import com.icommerce.common.model.AbstractTimeBaseIdentifiable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products", uniqueConstraints = {@UniqueConstraint(columnNames = {Product.SKU_COLUMN})})
public class Product extends AbstractTimeBaseIdentifiable<Product> {

    public static final String SKU_COLUMN = "sku";

    private String name;

    private String sku;

    private String brand;

    private String colour;

    private double price;

    @Override
    public boolean deepEquals(Product product) {
        return Double.compare(product.price, price) == 0 && Objects.equals(name, product.name) &&
               Objects.equals(sku, product.sku) && Objects.equals(brand, product.brand) &&
               Objects.equals(colour, product.colour);
    }

    @Override
    public int deepHashCode() {
        return Objects.hash(name, sku, brand, colour, price);
    }

}
