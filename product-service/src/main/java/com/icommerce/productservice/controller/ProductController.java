package com.icommerce.productservice.controller;

import com.icommerce.common.model.ApiResult;
import com.icommerce.productservice.dto.ProductDTO;
import com.icommerce.productservice.service.audit.AuditService;
import com.icommerce.productservice.service.product.ProductService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;

@Api(tags = "Product API")
@CrossOrigin
@Controller
public class ProductController {

    private Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;

    @Autowired
    private AuditService auditService;

    @GetMapping("/products/search")
    @ResponseBody
    public ApiResult<List<ProductDTO>> searchProduct(@RequestParam(required = false) String name,
                                                     @RequestParam(required = false) String brands,
                                                     @RequestParam(required = false) String colours,
                                                     @RequestParam(required = false) Double priceFrom,
                                                     @RequestParam(required = false) Double priceTo) {
        auditService.sendAuditMessage("/products/search", new HashMap<String, Object>() {{
            put("name", name);
            put("brands", brands);
            put("colours", colours);
            put("priceFrom", priceFrom);
            put("priceTo", priceTo);
        }});

        return new ApiResult<>(productService.searchProduct(name,
                                                            brands != null ? brands.split(",") : null,
                                                            colours != null ? colours.split(",") : null,
                                                            priceFrom,
                                                            priceTo));
    }

}
