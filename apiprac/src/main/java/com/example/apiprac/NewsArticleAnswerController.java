package com.example.apiprac;

import java.util.List;

import com.example.apiprac.dao.NewsArticleAnswerDAO;
import com.example.apiprac.dto.NewsArticleAnswerDTO;
import com.example.apiprac.model.NewsArticleAnswerModel;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@RestController
@MapperScan (basePackages="com.example.apiprac.dao")//탐색할 패키시 설정
public class NewsArticleAnswerController {

    @Autowired
    private NewsArticleAnswerDAO newsArticleAnswerDAO;

    @RequestMapping("/article-answer-list")
    public List<NewsArticleAnswerDTO> newsArticleAnswers() throws Exception {
        final NewsArticleAnswerDTO param = new NewsArticleAnswerDTO(null,null);
        final List<NewsArticleAnswerDTO> newsArticleAnswerList = newsArticleAnswerDAO.selectNewsArticleAnswer(param);
        return newsArticleAnswerList;
    }
}
