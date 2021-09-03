package com.example.apiprac;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class NewsArticleInitialService {
    public NewsArticlePrediction getNewsArticlePrediction(String article) {
        return new NewsArticlePrediction("", "", "", "","","");
    }
}
