package com.example.clientserver.Controller;


import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.security.Principal;

@RestController

public class ClientController {

     private OAuth2AuthorizedClientService authorizedClientService;

    public ClientController(OAuth2AuthorizedClientService authorizedClientService) {
        this.authorizedClientService = authorizedClientService;
    }


    @GetMapping("/get")
    public String getHome(Principal principal){
        var restTemplate = new RestTemplate();
        String accessToken = authorizedClientService.loadAuthorizedClient("reg-client",principal.getName()).getAccessToken().getTokenValue();
       HttpHeaders headers = new HttpHeaders();
       headers.set("Authorization", "Bearer "+ accessToken);
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<String> response =
                restTemplate.exchange("http://localhost:8840/spring-resource-server/home", HttpMethod.GET, httpEntity, String.class);

        return "Success  :: " + response.getBody();
    }
}
