package com.icommerce.productservice.service.profile;

import com.icommerce.common.exception.ForbiddenException;
import com.icommerce.productservice.service.profile.dto.VerifyTokenResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProfileServiceTest {

    @Autowired
    private ProfileService profileService;

    @MockBean
    private RestTemplate restTemplate;

    @Test
    public void validateTokenSucceeds() {
        when(restTemplate.execute(any(URI.class),
                                  eq(HttpMethod.POST),
                                  any(RequestCallback.class),
                                  any(ResponseExtractor.class))).thenReturn(new VerifyTokenResult(true));

        profileService.validateToken(1L, "token");
    }

    @Test(expected = ForbiddenException.class)
    public void validateTokenFailsBecauseOfInvalidToken() {
        when(restTemplate.execute(any(URI.class),
                                  eq(HttpMethod.POST),
                                  any(RequestCallback.class),
                                  any(ResponseExtractor.class))).thenReturn(new VerifyTokenResult(false));

        profileService.validateToken(1L, "token");
    }

}