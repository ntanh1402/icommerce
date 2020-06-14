package com.icommerce.productservice.controller;

import com.icommerce.common.model.ApiResult;
import com.icommerce.productservice.dto.CartDTO;
import com.icommerce.productservice.service.cart.CartService;
import com.icommerce.productservice.service.profile.ProfileService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Api(tags = "Cart API")
@CrossOrigin
@Controller
public class CartController {

    private Logger logger = LoggerFactory.getLogger(CartController.class);

    @Autowired
    private CartService cartService;

    @Autowired
    private ProfileService profileService;

    @PostMapping("/users/{userId}/cart")
    @ResponseBody
    public ApiResult<CartDTO> storeUserCart(@PathVariable long userId,
                                            @RequestHeader("Authorization") String token,
                                            @RequestBody CartDTO cart) {
        profileService.validateToken(userId, token);

        cartService.saveUserCart(userId, cart.getProducts());

        return new ApiResult<>(cart);
    }

    @GetMapping("/users/{userId}/cart")
    @ResponseBody
    public ApiResult<CartDTO> getUserCart(@PathVariable long userId, @RequestHeader("Authorization") String token) {
        profileService.validateToken(userId, token);

        return new ApiResult<>(new CartDTO(cartService.getUserCart(userId)));
    }

}
