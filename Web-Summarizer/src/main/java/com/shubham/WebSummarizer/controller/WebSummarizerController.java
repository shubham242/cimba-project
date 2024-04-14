package com.shubham.WebSummarizer.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

@RestController
public class WebSummarizerController {
    RestClient restClient = RestClient.create();

    private final String scalaServerUrl = "http://localhost:9090";

    @PostMapping("/summarize")
    public String summarize(@RequestBody String websiteUrl) {
        String summary = restClient.post()
                .uri(scalaServerUrl+"/summarize")
                .contentType(MediaType.APPLICATION_JSON)
                .body(websiteUrl)
                .retrieve().body(String.class);
                
        return summary;
    }

    @GetMapping("/requests")
    public String fetchRequests() {
        String requests = restClient.get()
                .uri(scalaServerUrl + "/requests")
                .retrieve()
                .body(String.class);
        return requests;
    }
}
