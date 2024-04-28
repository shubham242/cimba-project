package com.shubham.WebSummarizer.controller;

import org.springframework.web.bind.annotation.*;
import com.example.scala.ScalaService;
@RestController
@CrossOrigin(origins = "http://localhost:3000/")
public class WebSummarizerController {

    @PostMapping("/summarize")
    public String summarize(@RequestBody String websiteUrl) {
        return ScalaService.summarizeAndLog(websiteUrl);
    }

    @GetMapping("/requests")
    public String fetchRequests() {
        return ScalaService.fetchAllRequests();
    }
}
