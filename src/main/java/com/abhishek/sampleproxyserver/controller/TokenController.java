package com.abhishek.sampleproxyserver.controller;

import com.abhishek.sampleproxyserver.client.IssueNewToken;
import com.abhishek.sampleproxyserver.client.OAuthCodeDetail;
import com.abhishek.sampleproxyserver.client.Token;
import com.abhishek.sampleproxyserver.service.TokenService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/auth")
public class TokenController {

    private final TokenService tokenService;
    public TokenController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @PostMapping("/token")
    public Mono<Token> token(@RequestBody OAuthCodeDetail oAuthCodeDetail){
        return this.tokenService.token(oAuthCodeDetail);
    }

    @PostMapping("/refresh")
    public Mono<Token> refresh(@RequestBody IssueNewToken issueNewToken){
        return this.tokenService.refresh(issueNewToken);
    }

}
