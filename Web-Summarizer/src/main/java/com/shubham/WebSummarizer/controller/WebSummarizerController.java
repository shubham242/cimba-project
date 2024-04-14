package com.shubham.WebSummarizer.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class WebSummarizerController {
    RestClient restClient = RestClient.create();

    private final String scalaServerUrl = "http://localhost:9090";

    @PostMapping("/summarize")
    public String summarize(@RequestBody String websiteUrl) {
        System.out.println(websiteUrl);
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
