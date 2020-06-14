package com.icommerce.productservice.controller;

import com.icommerce.common.helper.JsonHelper;
import com.icommerce.productservice.dto.CartDTO;
import com.icommerce.productservice.dto.ProductDTO;
import com.icommerce.productservice.service.cart.CartService;
import com.icommerce.productservice.service.profile.ProfileService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CartControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CartService cartService;

    @MockBean
    private ProfileService profileService;

    @Test
    public void storeUserCartSucceeds() throws Exception {
        doNothing().when(cartService).saveUserCart(anyLong(), anyList());
        doNothing().when(profileService).validateToken(anyLong(), anyString());
        List<ProductDTO> products = Collections.singletonList(new ProductDTO(1L,
                                                                             "air max boost",
                                                                             "namb",
                                                                             "nike",
                                                                             "black",
                                                                             200.0));
        mvc.perform(post("/users/1/cart").contentType(MediaType.APPLICATION_JSON)
                                         .header("Authorization", "token")
                                         .content(JsonHelper.convertToJsonString(new CartDTO(products))))
           .andExpect(jsonPath("$.result.products[0].productId", is(1)))
           .andExpect(jsonPath("$.result.products[0].sku", is("namb")));
    }

    @Test
    public void getUserCartSucceeds() throws Exception {
        doNothing().when(profileService).validateToken(anyLong(), anyString());

        List<ProductDTO> products = Collections.singletonList(new ProductDTO(1L,
                                                                             "air max boost",
                                                                             "namb",
                                                                             "nike",
                                                                             "black",
                                                                             200.0));

        when(cartService.getUserCart(anyLong())).thenReturn(products);

        mvc.perform(get("/users/1/cart").contentType(MediaType.APPLICATION_JSON).header("Authorization", "token"))
           .andExpect(jsonPath("$.result.products[0].productId", is(1)))
           .andExpect(jsonPath("$.result.products[0].sku", is("namb")));
    }

}