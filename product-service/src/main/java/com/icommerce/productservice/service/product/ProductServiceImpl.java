package com.icommerce.productservice.service.product;

import com.icommerce.productservice.dto.ProductDTO;
import com.icommerce.productservice.helper.ProductDtoMapper;
import com.icommerce.productservice.persistence.dao.ProductRepository;
import com.icommerce.productservice.persistence.dao.ProductSearchSpecificationBuilder;
import com.icommerce.productservice.persistence.model.Product;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service("ProductService")
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<ProductDTO> searchProduct(String name,
                                          String[] brands,
                                          String[] colours,
                                          Double priceFrom,
                                          Double priceTo) {
        ProductSearchSpecificationBuilder builder = new ProductSearchSpecificationBuilder();
        List<Product> products = productRepository.findAll(builder.nameLike(name)
                                                                  .brandIn(brands)
                                                                  .colourIn(colours)
                                                                  .priceFrom(priceFrom)
                                                                  .priceTo(priceTo)
                                                                  .buildSpecification());

        ProductDtoMapper mapper = Mappers.getMapper(ProductDtoMapper.class);

        return products.stream().map(mapper::productToProductDTO).collect(Collectors.toList());
    }

}
