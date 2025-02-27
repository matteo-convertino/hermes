package com.convertino.hermesspringapi.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Component
public class HermesDockerApiClient {
    private final RestClient restClient;

    //@Value("${HERMES_PYTHON_API_URL}")
    //private String hermesPythonApiUrl;

    public HermesDockerApiClient(@Value("${HERMES_DOCKER_API_URL}") String hermesDockerApiUrl) {
        ClientHttpRequestFactory requestFactory = new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory());
        this.restClient = RestClient.builder()
                .baseUrl(hermesDockerApiUrl)
                .defaultStatusHandler(HttpStatusCode::isError, (req, resp) -> {
                })
                .requestFactory(requestFactory)
                .build();
    }

//    public ResponseEntity<String> sendGet(String uri) {
//        return restClient.get()
//                .uri(uri)
//                .retrieve()
//                .toEntity(String.class);
//    }

    public <T> ResponseEntity<String> sendPost(String uri, T body) {
        return restClient.post()
                .uri(uri)
                .contentType(APPLICATION_JSON)
                .body(body)
                .retrieve()
                .toEntity(String.class);
    }
}
