package com.example.apiprac.dao;

import com.example.apiprac.dto.NewsArticleAnswerDTO;

import java.util.List;

public interface NewsArticleAnswerDAO {
    List<NewsArticleAnswerDTO> selectNewsArticleAnswer(NewsArticleAnswerDTO param) throws Exception;
}
