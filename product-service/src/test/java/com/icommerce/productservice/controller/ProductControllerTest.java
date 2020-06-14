package com.icommerce.productservice.controller;

import com.icommerce.productservice.dto.ProductDTO;
import com.icommerce.productservice.service.audit.AuditService;
import com.icommerce.productservice.service.product.ProductService;
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

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ProductService productService;

    @MockBean
    private AuditService auditService;

    @Test
    public void searchProductWithNameAndBrands() throws Exception {
        doNothing().when(auditService).sendAuditMessage(anyString(), any());
        doAnswer(invocationOnMock -> {
            String name = invocationOnMock.getArgument(0, String.class);
            String[] brands = invocationOnMock.getArgument(1);
            String[] colours = invocationOnMock.getArgument(2);

            assertEquals("boost", name);

            assertEquals(2, brands.length);
            assertEquals("nike", brands[0]);
            assertEquals("adidas", brands[1]);

            assertNull(colours);

            return Collections.singletonList(new ProductDTO(1L, "air max boost", "namb", "nike", "black", 200.0));
        }).when(productService).searchProduct(any(), any(), any(), any(), any());

        mvc.perform(get("/products/search").contentType(MediaType.APPLICATION_JSON)
                                           .param("name", "boost")
                                           .param("brands", "nike,adidas"))
           .andDo(print())
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.result[0].productId", is(1)))
           .andExpect(jsonPath("$.result[0].sku", is("namb")));
    }

    @Test
    public void searchProductWithNameAndColoursAndPrice() throws Exception {
        doNothing().when(auditService).sendAuditMessage(anyString(), any());
        doAnswer(invocationOnMock -> {
            String name = invocationOnMock.getArgument(0);
            String[] brands = invocationOnMock.getArgument(1);
            String[] colours = invocationOnMock.getArgument(2);
            Double priceFrom = invocationOnMock.getArgument(3);
            Double priceTo = invocationOnMock.getArgument(4);

            assertEquals("boost", name);

            assertNull(brands);

            assertEquals(2, colours.length);
            assertEquals("red", colours[0]);
            assertEquals("blue", colours[1]);

            assertEquals(100.0, priceFrom, 0.1);
            assertEquals(120.0, priceTo, 0.1);

            return Collections.singletonList(new ProductDTO(1L, "air max boost", "namb", "nike", "black", 100.0));
        }).when(productService).searchProduct(any(), any(), any(), any(), any());

        mvc.perform(get("/products/search").contentType(MediaType.APPLICATION_JSON)
                                           .param("name", "boost")
                                           .param("colours", "red,blue")
                                           .param("priceFrom", "100")
                                           .param("priceTo", "120"))
           .andDo(print())
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.result[0].productId", is(1)))
           .andExpect(jsonPath("$.result[0].sku", is("namb")));
    }

}