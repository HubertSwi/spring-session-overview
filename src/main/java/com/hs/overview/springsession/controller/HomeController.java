package com.hs.overview.springsession.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.TreeMap;

@RestController
public class HomeController {

    @Autowired
    RedisTemplate<String, Object> template;

    @GetMapping("/hello")
    public String hello() {
        return "Hello";
    }

    @GetMapping("/redis-content")
    public String redisContent() {
        StringBuilder sb = new StringBuilder("<html><body><ul>");

        for (String key : template.keys("spring:session:*")) {
            sb.append("<h3>").append(key).append("</h3>");
            new TreeMap<>(template.opsForHash().entries(key)).forEach((k ,v) -> {
                sb.append("<li><b>").append(k).append("</b> : ").append(v).append("</li>");
            });
        }

        return sb.append("</html></body></ul>").toString();
    }
}
