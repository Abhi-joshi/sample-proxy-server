package com.abhishek.sampleproxyserver.service.impl;

import com.abhishek.sampleproxyserver.client.IssueNewToken;
import com.abhishek.sampleproxyserver.client.OAuthCodeDetail;
import com.abhishek.sampleproxyserver.client.Token;
import com.abhishek.sampleproxyserver.service.TokenService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class TokenServiceImpl implements TokenService {

    private final WebClient webClient;
    private final String clientId;
    private final String clientSecret;

    public TokenServiceImpl(WebClient.Builder webClientBuilder, @Value("${app.auth-server-url}") String authServerUrl, @Value("${app.client-id}") String clientId, @Value("${app.client-secret}") String clientSecret) {
        this.webClient = webClientBuilder.baseUrl(authServerUrl).build();
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    @Override
    public Mono<Token> token(OAuthCodeDetail oAuthCodeDetail) {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("client_id", this.clientId);
        formData.add("grant_type", "authorization_code");
        formData.add("redirect_uri", oAuthCodeDetail.getRedirect_uri());
        formData.add("code", oAuthCodeDetail.getCode());
        formData.add("code_verifier", oAuthCodeDetail.getCode_verifier());

        return webClient.post()
                .uri("oauth2/token")
                .headers(httpHeaders -> httpHeaders.setBasicAuth(this.clientId, this.clientSecret))
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromFormData(formData))
                .retrieve()
                .bodyToMono(Token.class);

    }

    @Override
    public Mono<Token> refresh(IssueNewToken issueNewToken) {

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("client_id", this.clientId);
        formData.add("grant_type", "refresh_token");
        formData.add("refresh_token", issueNewToken.getRefresh_token());

        return webClient.post()
                .uri("oauth2/token")
                .headers(httpHeaders -> httpHeaders.setBasicAuth(this.clientId, this.clientSecret))
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromFormData(formData))
                .retrieve()
                .bodyToMono(Token.class);

    }
}
