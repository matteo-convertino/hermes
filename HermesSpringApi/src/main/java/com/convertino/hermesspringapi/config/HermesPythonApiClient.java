package com.convertino.hermesspringapi.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Component
public class HermesPythonApiClient {
    private final RestClient restClient;

    public HermesPythonApiClient(@Value("${HERMES_PYTHON_API_URL}") String hermesPythonApiUrl) {
        this.restClient = RestClient.builder()
                .baseUrl(hermesPythonApiUrl)
                .defaultStatusHandler(HttpStatusCode::isError, (req, resp) -> {
                })
                //.requestFactory(new HttpComponentsClientHttpRequestFactory())
                .build();
    }

    public ResponseEntity<String> sendGet(String uri, String queryParams) {
        return restClient.get()
                .uri(uri + "?" + queryParams)
                .accept(APPLICATION_JSON)
                .retrieve()
                .toEntity(String.class);
    }

    public <T> ResponseEntity<String> sendPost(String uri, T body) {
        return restClient.post()
                .uri(uri)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .body(body)
                .retrieve()
                .toEntity(String.class);
    }
}
