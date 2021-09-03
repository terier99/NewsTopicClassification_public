package com.example.apiprac;

//import com.example.apiprac.dao.NewsArticleAnswerDAO;
//import com.example.apiprac.dto.NewsArticleAnswerDTO;
import com.example.apiprac.model.NewsArticleAnswerModel;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Controller
public class NewsArticlePredictionController {
//
//    @Autowired
//    NewsArticleAnswerService newsArticleAnswerService;

    private final NewsArticleInitialService newsArticleInitialService;
    private final NewsArticlePredictionService newsArticlePredictionService;


    public NewsArticlePredictionController(NewsArticleInitialService newsArticleInitialService, NewsArticlePredictionService newsArticlePredictionService) {
        this.newsArticleInitialService = newsArticleInitialService;
        this.newsArticlePredictionService = newsArticlePredictionService;
    }

    @GetMapping("/news-article-classification")
    public String getNewsArticlePrediction(Model model) {
        model.addAttribute("newsArticle", newsArticleInitialService.getNewsArticlePrediction(""));
        return "news-article-classification";
    }

    @RequestMapping(value = "/news-article-classification", method = RequestMethod.POST)
    public String send(@RequestParam("article")String article, Model model) {
        model.addAttribute("newsArticle", newsArticlePredictionService.getNewsArticlePrediction(article));
        return "news-article-classification";
    }

//    @GetMapping("/news-article-list")
//    public String getNewsArticleList(Model model) {
//        return "news-article-list";
//    }
//    @RequestMapping(value = "news-article-list")
//    public ModelAndView list(@RequestParam Map<String, Object> map) {
//
//        List<Map<String, Object>> list = this.newsArticleAnswerService.list(map);
//
//        ModelAndView mav = new ModelAndView();
//        mav.addObject("data", list);
//        mav.setViewName("news-article-list.html");
//        return mav;
//    }
}
