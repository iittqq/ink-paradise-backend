package com.catsaredope.inkparadise.api;

import java.net.URI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@CrossOrigin
@RequestMapping("/manga-dex")
public class MangaDex {
  @Autowired private RestTemplate restTemplate = new RestTemplate();

  @GetMapping("/recently-updated")
  public String fetchRecentlyUpdated(@RequestParam(value = "limit", required = true) Number limit) {
    String externalApiUrl =
        "https://api.mangadex.org/manga?limit=" + limit + "&order[latestUploadedChapter]=desc";

    // Create HttpHeaders and set the User-Agent header
    HttpHeaders headers = new HttpHeaders();
    headers.add("User-Agent", "ink-paradise");

    // Create a RequestEntity with headers
    RequestEntity<Object> requestEntity =
        new RequestEntity<>(headers, HttpMethod.GET, URI.create(externalApiUrl));

    // Make the request
    return restTemplate.exchange(requestEntity, String.class).getBody();
  }

  @GetMapping("/recently-added")
  public String fetchRecentlyAdded(
      @RequestParam(value = "limit", required = true) Number limit,
      @RequestParam(value = "offset", required = false) Number offset) {
    String externalApiUrl =
        "https://api.mangadex.org/manga?limit="
            + limit
            + "&offset="
            + offset
            + "&order[createdAt]=desc";
    // Create HttpHeaders and set the User-Agent header
    HttpHeaders headers = new HttpHeaders();
    headers.add("User-Agent", "ink-paradise");

    // Create a RequestEntity with headers
    RequestEntity<Object> requestEntity =
        new RequestEntity<>(headers, HttpMethod.GET, URI.create(externalApiUrl));

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
    RequestEntity<Object> requestEntity =
        new RequestEntity<>(headers, HttpMethod.GET, URI.create(externalApiUrl));

    // Make the request
    return restTemplate.exchange(requestEntity, String.class).getBody();
  }

  @GetMapping("/manga-by-id")
  public String fetchMangaById(@RequestParam(value = "id", required = true) String id) {
    String externalApiUrl = "https://api.mangadex.org/manga/" + id;
    // Create HttpHeaders and set the User-Agent header
    HttpHeaders headers = new HttpHeaders();
    headers.add("User-Agent", "ink-paradise");

    // Create a RequestEntity with headers
    RequestEntity<Object> requestEntity =
        new RequestEntity<>(headers, HttpMethod.GET, URI.create(externalApiUrl));

    // Make the request
    return restTemplate.exchange(requestEntity, String.class).getBody();
  }

  @GetMapping("/manga-by-title")
  public String fetchMangaByTitle(
      @RequestParam(value = "limit", required = true) Number limit,
      @RequestParam(value = "title", required = true) String title) {
    String externalApiUrl =
        "https://api.mangadex.org/manga/?limit="
            + limit
            + "&title="
            + title.replaceAll(" ", "+")
            + "&order[relevance]=desc";
    // Create HttpHeaders and set the User-Agent header
    HttpHeaders headers = new HttpHeaders();
    headers.add("User-Agent", "ink-paradise");

    // Create a RequestEntity with headers
    RequestEntity<Object> requestEntity =
        new RequestEntity<>(headers, HttpMethod.GET, URI.create(externalApiUrl));

    // Make the request
    return restTemplate.exchange(requestEntity, String.class).getBody();
  }

  @GetMapping("/manga-by-tag")
  public String fetchMangaByTag(
      @RequestParam(value = "limit", required = true) Number limit,
      @RequestParam(value = "id", required = true) String id) {
    String externalApiUrl =
        "https://api.mangadex.org/manga/?limit="
            + limit
            + "&includedTags[]="
            + id
            + "&order[relevance]=desc";
    // Create HttpHeaders and set the User-Agent header
    HttpHeaders headers = new HttpHeaders();
    headers.add("User-Agent", "ink-paradise");

    // Create a RequestEntity with headers
    RequestEntity<Object> requestEntity =
        new RequestEntity<>(headers, HttpMethod.GET, URI.create(externalApiUrl));

    // Make the request
    return restTemplate.exchange(requestEntity, String.class).getBody();
  }

  @GetMapping("/manga-cover")
  public String fetchMangaCover(@RequestParam(value = "id", required = true) String id) {
    String externalApiUrl = "https://api.mangadex.org/cover/" + id;
    // Create HttpHeaders and set the User-Agent header
    HttpHeaders headers = new HttpHeaders();
    headers.add("User-Agent", "ink-paradise");

    // Create a RequestEntity with headers
    RequestEntity<Object> requestEntity =
        new RequestEntity<>(headers, HttpMethod.GET, URI.create(externalApiUrl));

    // Make the request
    return restTemplate.exchange(requestEntity, String.class).getBody();
  }

  @GetMapping("/manga-feed")
  public String fetchMangaFeed(
      @RequestParam(value = "id", required = true) String id,
      @RequestParam(value = "limit", required = true) Number limit,
      @RequestParam(value = "offset", required = true) Number offset,
      @RequestParam(value = "translatedLanguage", required = true) String translatedLanguage,
      @RequestParam(value = "order", required = true) String order) {
    String externalApiUrl =
        "https://api.mangadex.org/manga/"
            + id
            + "/feed?limit="
            + limit
            + "&offset="
            + offset
            + "&translatedLanguage[]="
            + translatedLanguage
            + "&order[chapter]="
            + order;
    // Create HttpHeaders and set the User-Agent header
    HttpHeaders headers = new HttpHeaders();
    headers.add("User-Agent", "ink-paradise");

    // Create a RequestEntity with headers
    RequestEntity<Object> requestEntity =
        new RequestEntity<>(headers, HttpMethod.GET, URI.create(externalApiUrl));

    // Make the request
    return restTemplate.exchange(requestEntity, String.class).getBody();
  }

  @GetMapping("/scantalation-group")
  public String fetchScantalationGroup(@RequestParam(value = "id", required = true) String id) {
    String externalApiUrl = "https://api.mangadex.org/group/" + id;
    // Create HttpHeaders and set the User-Agent header
    HttpHeaders headers = new HttpHeaders();
    headers.add("User-Agent", "ink-paradise");

    // Create a RequestEntity with headers
    RequestEntity<Object> requestEntity =
        new RequestEntity<>(headers, HttpMethod.GET, URI.create(externalApiUrl));

    // Make the request
    return restTemplate.exchange(requestEntity, String.class).getBody();
  }

  @GetMapping("/manga-by-author")
  public String fetchMangaByAuthor(
      @RequestParam(value = "id", required = true) String id,
      @RequestParam(value = "limit", required = true) Number limit,
      @RequestParam(value = "offset", required = true) Number offset) {
    String externalApiUrl =
        "https://api.mangadex.org/manga?limit="
            + limit
            + "&offset="
            + offset
            + "&authors[]="
            + id
            + "&order[relevance]=desc";
    // Create HttpHeaders and set the User-Agent header
    HttpHeaders headers = new HttpHeaders();
    headers.add("User-Agent", "ink-paradise");

    // Create a RequestEntity with headers
    RequestEntity<Object> requestEntity =
        new RequestEntity<>(headers, HttpMethod.GET, URI.create(externalApiUrl));

    // Make the request
    return restTemplate.exchange(requestEntity, String.class).getBody();
  }

  @GetMapping("/chapter-data")
  public String fetchChapterData(@RequestParam(value = "id", required = true) String id) {
    String externalApiUrl = "https://api.mangadex.org/at-home/server/" + id;
    // Create HttpHeaders and set the User-Agent header
    HttpHeaders headers = new HttpHeaders();
    headers.add("User-Agent", "ink-paradise");

    // Create a RequestEntity with headers
    RequestEntity<Object> requestEntity =
        new RequestEntity<>(headers, HttpMethod.GET, URI.create(externalApiUrl));

    // Make the request
    return restTemplate.exchange(requestEntity, String.class).getBody();
  }
}
