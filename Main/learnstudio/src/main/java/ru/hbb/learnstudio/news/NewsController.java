package ru.hbb.learnstudio.news;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/news")
public class NewsController {


    private NewsRepository newsRepository;

    @Autowired
    public void setNewsRepository(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    @GetMapping("/data/all")
    ResponseEntity<?> getAllNews() {
        List<NewsEntity> newsEntities = newsRepository.findAll();
        Collections.reverse(newsEntities);
        return ResponseEntity.ok(getJSON(newsEntities));
    }

    private String getJSON(Object o) {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = null;
        try {
            json = objectMapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return json;
    }
}
