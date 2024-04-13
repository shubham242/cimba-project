package com.shubham.WebSummarizer.service;

import com.summary.ScalaSummarizationServiceFactory;
import com.summary.App;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class SummarizationBridgeService {

    public Object summarizeAndLog(String websiteUrl) {
        com.summary.ScalaSummarizationService scalaSummarizationService = new ScalaSummarizationServiceFactory.createInstance();
        return scalaSummarizationServiceClass.summarizeAndLog(websiteUrl);
    }
}
