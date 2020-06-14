package com.icommerce.productservice.helper;

import com.fasterxml.jackson.core.type.TypeReference;
import com.icommerce.common.helper.JsonBaseAttributeConverter;
import com.icommerce.productservice.persistence.model.Product;

import javax.persistence.Converter;
import java.util.List;

@Converter
public class ProductListAttributeConverter implements JsonBaseAttributeConverter<List<Product>> {

    @Override
    public TypeReference<List<Product>> getObjectTypeRef() {
        return new TypeReference<List<Product>>() {
        };
    }

}
