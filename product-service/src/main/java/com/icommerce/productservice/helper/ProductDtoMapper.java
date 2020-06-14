package com.icommerce.productservice.helper;

import com.icommerce.productservice.dto.ProductDTO;
import com.icommerce.productservice.persistence.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ProductDtoMapper {

    @Mapping(source = "id", target = "productId")
    ProductDTO productToProductDTO(Product product);

    @Mapping(source = "productId", target = "id")
    Product productDTOToProduct(ProductDTO product);

}
