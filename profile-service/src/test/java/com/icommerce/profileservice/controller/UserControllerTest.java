package com.icommerce.profileservice.controller;

import com.icommerce.common.helper.JsonHelper;
import com.icommerce.profileservice.dto.AccessToken;
import com.icommerce.profileservice.dto.AccessTokenType;
import com.icommerce.profileservice.dto.VerifyTokenRequest;
import com.icommerce.profileservice.persistence.model.User;
import com.icommerce.profileservice.service.jwt.JwtService;
import com.icommerce.profileservice.service.user.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(UserController.class)
@RunWith(SpringRunner.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    @MockBean
    private JwtService jwtService;

    @Test
    public void loginByTokenThenCreateUserSucceeds() throws Exception {
        String facebookId = "alex";

        when(jwtService.buildJwtToken(eq(1L))).thenReturn("token");
        when(userService.getByFacebookId(facebookId)).thenReturn(null);
        doAnswer(invocation -> {
            User user = invocation.getArgument(0, User.class);

            assertEquals(facebookId, user.getFacebookId());

            user.setId(1L);

            return user;
        }).when(userService).createUser(any(User.class));

        mvc.perform(post("/users/loginByToken").contentType(MediaType.APPLICATION_JSON)
                                               .content(JsonHelper.convertToJsonString(new AccessToken(AccessTokenType.FACEBOOK,
                                                                                                       "alex"))))
           .andExpect(jsonPath("$.result.userId", is(1)))
           .andExpect(jsonPath("$.result.token", is("token")));
    }

    @Test
    public void loginByTokenThenReturnAnExistedUserSucceeds() throws Exception {
        String facebookId = "alex";
        User alex = new User();
        alex.setId(1L);
        alex.setFacebookId(facebookId);

        when(jwtService.buildJwtToken(eq(1L))).thenReturn("token");
        when(userService.getByFacebookId(facebookId)).thenReturn(alex);

        mvc.perform(post("/users/loginByToken").contentType(MediaType.APPLICATION_JSON)
                                               .content(JsonHelper.convertToJsonString(new AccessToken(AccessTokenType.FACEBOOK,
                                                                                                       "alex"))))
           .andExpect(jsonPath("$.result.userId", is(1)))
           .andExpect(jsonPath("$.result.token", is("token")));

        verify(userService, Mockito.never()).createUser(any(User.class));
    }

    @Test
    public void verifyTokenReturnTrue() throws Exception {
        when(jwtService.isTokenValid(eq("token"), eq(1L))).thenReturn(true);

        mvc.perform(post("/users/verifyToken").contentType(MediaType.APPLICATION_JSON)
                                              .content(JsonHelper.convertToJsonString(new VerifyTokenRequest(1L,
                                                                                                             "token"))))
           .andExpect(jsonPath("$.result.valid", is(true)));
    }

    @Test
    public void verifyTokenReturnException() throws Exception {
        when(jwtService.isTokenValid(eq("token"), eq(1L))).thenThrow(mock(RuntimeException.class));

        mvc.perform(post("/users/verifyToken").contentType(MediaType.APPLICATION_JSON)
                                              .content(JsonHelper.convertToJsonString(new VerifyTokenRequest(1L,
                                                                                                             "token"))))
           .andExpect(jsonPath("$.error.code", is("500")));

    }

}