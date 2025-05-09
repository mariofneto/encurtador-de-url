package com.example.url.service;

import com.example.url.model.Url;
import com.example.url.repository.UrlRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
public class UrlService {
    UrlRepository urlRepository;

    public UrlService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    public String shortenUrl(String originalUrl){
        String shortUrl = "https://marinDev.com/"+generateShortUrl();
        Url url = new Url();
        url.setOriginalUrl(originalUrl);
        url.setShortUrl(shortUrl);
        url.setExpirationUrl(LocalDateTime.now().plusDays(30));

        urlRepository.save(url);

        return shortUrl;
    }

    public Optional<Url> getOriginalUrl(String shortUrl){
        Optional<Url> urlOptional = urlRepository.findByShortUrl(shortUrl);

        if(urlOptional.isPresent()){
            Url url = urlOptional.get();
            if(url.getExpirationUrl().isAfter(LocalDateTime.now())){
                return Optional.of(url);
            }
            else{
                urlRepository.delete(url);
            }
        }
        return Optional.empty();
    }


    private String generateShortUrl(){
        String character = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz123456789";
        StringBuilder shorturl = new StringBuilder();
        Random random = new Random();
        int length = 5 + random.nextInt(6);
        for(int i = 0; i < length; i++){
            shorturl.append(character.charAt(random.nextInt(character.length())));
        }
        return shorturl.toString();
    }
}
