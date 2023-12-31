package com.catsaredope.inkparadise.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@RestController
@RequestMapping("/mangaDex")
public class MangaDex {
    @Autowired
     private RestTemplate restTemplate = new RestTemplate();

    @CrossOrigin
    @GetMapping("/recentlyUpdated")
    public String fetchRecentlyUpdated(@RequestParam(value = "limit", required = true) String limit) {
        String externalApiUrl = "https://api.mangadex.org/manga?limit=" + limit + "&order[latestUploadedChapter]=desc";

        // Create HttpHeaders and set the User-Agent header
        HttpHeaders headers = new HttpHeaders();
        headers.add("User-Agent", "ink-paradise");

        // Create a RequestEntity with headers
        RequestEntity<Object> requestEntity = new RequestEntity<>(headers, HttpMethod.GET, URI.create(externalApiUrl));

        // Make the request
        return restTemplate.exchange(requestEntity, String.class).getBody();
    }

    @GetMapping("/recentlyAdded")
    public String fetchRecentlyAdded(@RequestParam(value = "limit", required = true) String limit) {
        String externalApiUrl = "https://api.mangadex.org/manga?limit=" + limit + "&order[createdAt]=desc";
        // Create HttpHeaders and set the User-Agent header
        HttpHeaders headers = new HttpHeaders();
        headers.add("User-Agent", "ink-paradise");

        // Create a RequestEntity with headers
        RequestEntity<Object> requestEntity = new RequestEntity<>(headers, HttpMethod.GET, URI.create(externalApiUrl));

        // Make the request
        return restTemplate.exchange(requestEntity, String.class).getBody();
    }

    @GetMapping("/tags")
    public String fetchTags() {
        String externalApiUrl = "https://api.mangadex.org/manga/tag";
        // Create HttpHeaders and set the User-Agent header
        HttpHeaders headers = new HttpHeaders();
        headers.add("User-Agent", "ink-paradise");

        // Create a RequestEntity with headers
        RequestEntity<Object> requestEntity = new RequestEntity<>(headers, HttpMethod.GET, URI.create(externalApiUrl));

        // Make the request
        return restTemplate.exchange(requestEntity, String.class).getBody();
    }

    @GetMapping("/mangaById")
    public String fetchMangaById(@RequestParam(value = "limit", required = true) String limit,
            @RequestParam(value = "mangaId", required = true) String mangaId) {
        String externalApiUrl = "https://api.mangadex.org/manga/" + mangaId;
        // Create HttpHeaders and set the User-Agent header
        HttpHeaders headers = new HttpHeaders();
        headers.add("User-Agent", "ink-paradise");

        // Create a RequestEntity with headers
        RequestEntity<Object> requestEntity = new RequestEntity<>(headers, HttpMethod.GET, URI.create(externalApiUrl));

        // Make the request
        return restTemplate.exchange(requestEntity, String.class).getBody();
    }

    @GetMapping("/mangaByTitle")
    public String fetchMangaByTitle(@RequestParam(value = "limit", required = true) String limit,
            @RequestParam(value = "title", required = true) String title,
            @RequestParam(value = "order", required = true) String order,
            @RequestParam(value = "filter", required = true) String filter) {
        String externalApiUrl = "https://api.mangadex.org/manga/?limit=" + limit + "&title=" + title;
        // Create HttpHeaders and set the User-Agent header
        HttpHeaders headers = new HttpHeaders();
        headers.add("User-Agent", "ink-paradise");

        // Create a RequestEntity with headers
        RequestEntity<Object> requestEntity = new RequestEntity<>(headers, HttpMethod.GET, URI.create(externalApiUrl));

        // Make the request
        return restTemplate.exchange(requestEntity, String.class).getBody();
    }

    @GetMapping("/mangaByTag")
    public String fetchMangaByTag(@RequestParam(value = "limit", required = true) String limit,
            @RequestParam(value = "tagId", required = true) String tagId) {
        String externalApiUrl = "https://api.mangadex.org/manga/?limit=" + limit + "&order[relevance]=desc";
        // Create HttpHeaders and set the User-Agent header
        HttpHeaders headers = new HttpHeaders();
        headers.add("User-Agent", "ink-paradise");

        // Create a RequestEntity with headers
        RequestEntity<Object> requestEntity = new RequestEntity<>(headers, HttpMethod.GET, URI.create(externalApiUrl));

        // Make the request
        return restTemplate.exchange(requestEntity, String.class).getBody();
    }

    @GetMapping("/mangaCover")
    public String fetchMangaCover(@RequestParam(value = "coverId", required = true) String coverId) {
        String externalApiUrl = "https://api.mangadex.org/cover/" + coverId;
        // Create HttpHeaders and set the User-Agent header
        HttpHeaders headers = new HttpHeaders();
        headers.add("User-Agent", "ink-paradise");

        // Create a RequestEntity with headers
        RequestEntity<Object> requestEntity = new RequestEntity<>(headers, HttpMethod.GET, URI.create(externalApiUrl));

        // Make the request
        return restTemplate.exchange(requestEntity, String.class).getBody();
    }

}
