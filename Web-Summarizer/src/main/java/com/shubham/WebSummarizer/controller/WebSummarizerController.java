package com.shubham.WebSummarizer.controller;

import com.shubham.WebSummarizer.service.SummarizationBridgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.summary.ScalaSummarizationService;
@RestController
public class WebSummarizerController {
    private final SummarizationBridgeService summarizationBridgeService;

    @Autowired
    public WebSummarizerController(SummarizationBridgeService summarizationBridgeService) {
        this.summarizationBridgeService = summarizationBridgeService;
    }
    @PostMapping("/summarize")
    public Object generateSummary(@RequestBody String websiteUrl) {
        return summarizationBridgeService.summarizeAndLog(websiteUrl);
    }

//    @GetMapping("/requests")
//    public List<Request> getAllRequests() {
//        // ... Logic to fetch and return requests from your Scala service's database
//    }
}
