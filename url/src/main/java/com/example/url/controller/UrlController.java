package com.example.url.controller;

import com.example.url.service.UrlService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/url")
public class UrlController {

    UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @PostMapping("/shorten")
    public ResponseEntity<Map<String,String>> shortenUrl(@RequestBody Map<String, String> request){
        String originalUrl = request.get("url");
        if(originalUrl == null){
            return (ResponseEntity<Map<String, String>>) ResponseEntity.badRequest();
        }

        String shortUrl = urlService.shortenUrl(originalUrl);
        Map<String, String> response = new HashMap<String, String>();
        response.put("url", shortUrl);

        return ResponseEntity.ok(response);
    }
/*
    @GetMapping("/{shortUrl}")
    public ResponseEntity<Object> getMehod(@PathVariable String shortUrl){
        return Object;
    }
*/

}
