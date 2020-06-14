package com.icommerce.productservice.persistence.dao;

import com.icommerce.productservice.persistence.model.Product;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ProductSearchSpecificationBuilder {

    private List<Specification<Product>> specifications;

    public ProductSearchSpecificationBuilder() {
        specifications = new ArrayList<>();
    }

    public final ProductSearchSpecificationBuilder nameLike(String name) {
        if (!StringUtils.isEmpty(name)) {
            specifications.add((Specification<Product>) (root, cq, cb) -> cb.like(root.get("name"), "%" + name + "%"));
        }
        return this;
    }

    public final ProductSearchSpecificationBuilder brandIn(String... brands) {
        if (brands != null && brands.length > 0) {
            specifications.add((Specification<Product>) (root, cq, cb) -> root.get("brand").in((Object[]) brands));
        }
        return this;
    }

    public final ProductSearchSpecificationBuilder colourIn(String... colours) {
        if (colours != null && colours.length > 0) {
            specifications.add((Specification<Product>) (root, cq, cb) -> root.get("colour").in((Object[]) colours));
        }
        return this;
    }

    public final ProductSearchSpecificationBuilder priceFrom(Double priceFrom) {
        if (priceFrom != null) {
            specifications.add((Specification<Product>) (root, cq, cb) -> cb.greaterThanOrEqualTo(root.get("price"),
                                                                                                  priceFrom));
        }
        return this;
    }

    public final ProductSearchSpecificationBuilder priceTo(Double priceTo) {
        if (priceTo != null) {
            specifications.add((Specification<Product>) (root, cq, cb) -> cb.lessThanOrEqualTo(root.get("price"),
                                                                                               priceTo));
        }
        return this;
    }

    public final Specification<Product> buildSpecification() {
        Specification<Product> result = (Specification<Product>) (root, cq, cb) -> {
            cq.orderBy(cb.asc(root.get("id")));
            return cb.greaterThan(root.get("id"), 0);
        };
        if (specifications.size() > 0) {
            for (Specification<Product> specification : specifications) {
                result = result.and(specification);
            }
        }
        return result;
    }

}