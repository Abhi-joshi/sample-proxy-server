package com.abhishek.sampleproxyserver.service;

import com.abhishek.sampleproxyserver.client.IssueNewToken;
import com.abhishek.sampleproxyserver.client.OAuthCodeDetail;
import com.abhishek.sampleproxyserver.client.Token;
import reactor.core.publisher.Mono;

public interface TokenService {
    Mono<Token> token(OAuthCodeDetail oAuthCodeDetail);

    Mono<Token> refresh(IssueNewToken issueNewToken);
}
