package com.icommerce.profileservice.controller;

import com.icommerce.common.exception.UnprocessableEntityException;
import com.icommerce.common.model.ApiResult;
import com.icommerce.profileservice.dto.AccessToken;
import com.icommerce.profileservice.dto.LoginResult;
import com.icommerce.profileservice.dto.VerifyTokenRequest;
import com.icommerce.profileservice.dto.VerifyTokenResult;
import com.icommerce.profileservice.helper.FacebookLoginHelper;
import com.icommerce.profileservice.persistence.model.User;
import com.icommerce.profileservice.service.jwt.JwtService;
import com.icommerce.profileservice.service.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

@Api(tags = "User API")
@CrossOrigin
@Controller
public class UserController {

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @ApiOperation(value = "Login by access token")
    @ApiResponses(value = {@ApiResponse(code = HttpServletResponse.SC_OK, message = "ok")})
    @PostMapping("/users/loginByToken")
    @ResponseBody
    public ApiResult<LoginResult> loginByToken(@RequestBody AccessToken accessToken) {
        User user = null;
        switch (accessToken.getType()) {
            case FACEBOOK:
                String facebookId = FacebookLoginHelper.fetchFacebookId(accessToken.getToken());
                user = userService.getByFacebookId(facebookId);
                if (user == null) {
                    user = new User();
                    user.setFacebookId(facebookId);

                    user = userService.createUser(user);
                }
                break;
        }

        if (user == null) {
            throw new UnprocessableEntityException("Cannot login!");
        }

        return new ApiResult<>(LoginResult.builder()
                                          .userId(user.getId())
                                          .token(jwtService.buildJwtToken(user.getId()))
                                          .build());
    }

    @PostMapping("/users/verifyToken")
    @ResponseBody
    public ApiResult<VerifyTokenResult> verifyToken(@RequestBody VerifyTokenRequest req) {
        return new ApiResult<>(new VerifyTokenResult(jwtService.isTokenValid(req.getToken(), req.getUserId())));
    }

}
