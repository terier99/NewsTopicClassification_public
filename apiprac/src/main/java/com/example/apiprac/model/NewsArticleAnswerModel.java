package com.example.apiprac.model;

import lombok.Builder;
import lombok.Data;

@Builder @Data
public class NewsArticleAnswerModel {
    private String answer;
    private String article;
}
