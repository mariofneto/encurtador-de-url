package com.example.url.controller;

import com.example.url.model.Url;
import com.example.url.service.UrlService;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

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

    @GetMapping("/{shortUrl}")
    public ResponseEntity<Object> redirectToOriginalUrl(@PathVariable String shortUrl){
        Optional<Url> urlOptional = urlService.getOriginalUrl(shortUrl);
        if(urlOptional.isPresent()){
            Url url = urlOptional.get();
            System.out.println("Redirecionando para: "+url.getOriginalUrl());
            return ResponseEntity.status(302).location(URI.create(url.getOriginalUrl())).build();
        }
        System.out.println("URL não encontrada ou expirada: "+shortUrl);

        return ResponseEntity.notFound().build();
    }


}
