package cats_are_dope.ink_paradise_backend.api;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
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
  public String fetchRecentlyUpdated(
      @RequestParam(value = "limit", required = true) Number limit,
      @RequestParam(value = "offset", required = false) Number offset,
      @RequestParam(value = "contentFilter", required = false) Number contentFilter) {
    String externalApiUrl = "";
    int contentFilterValue = contentFilter.intValue();
    switch (contentFilterValue) {
      case 1:
        {
          externalApiUrl =
              "https://api.mangadex.org/manga?limit="
                  + limit
                  + "&offset="
                  + offset
                  + "&order[latestUploadedChapter]=desc&includes[]=cover_art&includes[]=author&contentRating[]=safe";
          break;
        }
      case 2:
        {
          externalApiUrl =
              "https://api.mangadex.org/manga?limit="
                  + limit
                  + "&offset="
                  + offset
                  + "&order[latestUploadedChapter]=desc&includes[]=cover_art&includes[]=author&contentRating[]=safe&contentRating[]=suggestive";
          break;
        }
      case 3:
        {
          externalApiUrl =
              "https://api.mangadex.org/manga?limit="
                  + limit
                  + "&offset="
                  + offset
                  + "&order[latestUploadedChapter]=desc&includes[]=cover_art&includes[]=author&contentRating[]=safe&contentRating[]=suggestive&contentRating[]=erotica";
          break;
        }
      case 4:
        {
          externalApiUrl =
              "https://api.mangadex.org/manga?limit="
                  + limit
                  + "&offset="
                  + offset
                  + "&order[latestUploadedChapter]=desc&includes[]=cover_art&includes[]=author&contentRating[]=safe&contentRating[]=suggestive&contentRating[]=erotica&contentRating[]=pornographic";
          break;
        }
    }
    {
      /**
       * String externalApiUrl = "https://api.mangadex.org/manga?limit=" + limit + "&offset=" +
       * offset +
       * "&order[latestUploadedChapter]=desc&includes[]=cover_art&contentRating[]=safe&contentRating[]=suggestive&contentRating[]=erotica&contentRating[]=pornographic";
       */
    }
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
      @RequestParam(value = "offset", required = false) Number offset,
      @RequestParam(value = "contentFilter", required = false) Number contentFilter) {
    String externalApiUrl = "";
    int contentFilterValue = contentFilter.intValue();
    switch (contentFilterValue) {
      case 1:
        externalApiUrl =
            "https://api.mangadex.org/manga?limit="
                + limit
                + "&offset="
                + offset
                + "&order[createdAt]=desc&includes[]=cover_art&includes[]=author&contentRating[]=safe";
        break;
      case 2:
        externalApiUrl =
            "https://api.mangadex.org/manga?limit="
                + limit
                + "&offset="
                + offset
                + "&order[createdAt]=desc&includes[]=cover_art&includes[]=author&contentRating[]=safe&contentRating[]=suggestive";
        break;
      case 3:
        externalApiUrl =
            "https://api.mangadex.org/manga?limit="
                + limit
                + "&offset="
                + offset
                + "&order[createdAt]=desc&includes[]=cover_art&includes[]=author&contentRating[]=safe&contentRating[]=suggestive&contentRating[]=erotica";
        break;
      case 4:
        externalApiUrl =
            "https://api.mangadex.org/manga?limit="
                + limit
                + "&offset="
                + offset
                + "&order[createdAt]=desc&includes[]=cover_art&includes[]=author&contentRating[]=safe&contentRating[]=suggestive&contentRating[]=erotica&contentRating[]=pornographic";
    }

    {
      /**
       * String externalApiUrl = "https://api.mangadex.org/manga?limit=" + limit + "&offset=" +
       * offset +
       * "&order[createdAt]=desc&includes[]=cover_art&contentRating[]=safe&contentRating[]=suggestive&contentRating[]=erotica&contentRating[]=pornographic";
       */
    }

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
    String externalApiUrl =
        "https://api.mangadex.org/manga/" + id + "?includes[]=cover_art&includes[]=author";
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
      @RequestParam(value = "title", required = true) String title,
      @RequestParam(value = "contentFilter", required = false) Number contentFilter) {
    String externalApiUrl = "";
    int contentFilterValue = contentFilter.intValue();
    switch (contentFilterValue) {
      case 1:
        {
          externalApiUrl =
              "https://api.mangadex.org/manga/?limit="
                  + limit
                  + "&includes[]=cover_art&includes[]=author"
                  + "&title="
                  + title.replaceAll(" ", "+")
                  + "&order[rating]=desc&contentRating[]=safe";
          break;
        }
      case 2:
        {
          externalApiUrl =
              "https://api.mangadex.org/manga/?limit="
                  + limit
                  + "&includes[]=cover_art&includes[]=author"
                  + "&title="
                  + title.replaceAll(" ", "+")
                  + "&order[rating]=desc&contentRating[]=safe&contentRating[]=suggestive";
          break;
        }
      case 3:
        {
          externalApiUrl =
              "https://api.mangadex.org/manga/?limit="
                  + limit
                  + "&includes[]=cover_art&includes[]=author"
                  + "&title="
                  + title.replaceAll(" ", "+")
                  + "&order[rating]=desc&contentRating[]=safe&contentRating[]=suggestive&contentRating[]=erotica";
          break;
        }
      case 4:
        {
          externalApiUrl =
              "https://api.mangadex.org/manga/?limit="
                  + limit
                  + "&includes[]=cover_art&includes[]=author"
                  + "&title="
                  + title.replaceAll(" ", "+")
                  + "&order[rating]=desc&contentRating[]=safe&contentRating[]=suggestive&contentRating[]=erotica&contentRating[]=pornographic";
          break;
        }
    }
    {
      /**
       * String externalApiUrl = "https://api.mangadex.org/manga/?limit=" + limit +
       * "&includes[]=cover_art" + "&title=" + title.replaceAll(" ", "+") +
       * "&order[rating]=desc&contentRating[]=safe&contentRating[]=suggestive&contentRating[]=erotica&contentRating[]=pornographic";
       */
    }
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
      @RequestParam(value = "id", required = true) String id,
      @RequestParam(value = "contentFilter", required = false) Number contentFilter) {
    try {
      StringBuilder externalApiUrl = new StringBuilder("https://api.mangadex.org/manga?");
      externalApiUrl
          .append("limit=")
          .append(URLEncoder.encode(limit.toString(), StandardCharsets.UTF_8.toString()));

      externalApiUrl
          .append("&includes[]=author&includedTags[]=")
          .append(URLEncoder.encode(id, StandardCharsets.UTF_8.toString()));
      int contentFilterValue = contentFilter.intValue();
      switch (contentFilterValue) {
        case 1:
          {
            externalApiUrl.append("&contentRating[]=safe");
            break;
          }
        case 2:
          {
            externalApiUrl.append("&contentRating[]=safe&contentRating[]=suggestive");
            break;
          }
        case 3:
          {
            externalApiUrl.append(
                "&contentRating[]=safe&contentRating[]=suggestive&contentRating[]=erotica");
            break;
          }
        case 4:
          {
            externalApiUrl.append(
                "&contentRating[]=safe&contentRating[]=suggestive&contentRating[]=erotica&contentRating[]=pornographic");
            break;
          }
      }

      // Create HttpHeaders and set the User-Agent header
      HttpHeaders headers = new HttpHeaders();
      headers.add("User-Agent", "ink-paradise");

      // Create a RequestEntity with headers
      RequestEntity<Object> requestEntity =
          new RequestEntity<>(headers, HttpMethod.GET, URI.create(externalApiUrl.toString()));

      // Make the request
      return restTemplate.exchange(requestEntity, String.class).getBody();
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
      return e.getMessage();
    } catch (Exception e) {
      return e.getMessage();
    }
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
      @RequestParam(value = "order", required = true) String order,
      @RequestParam(value = "translatedLanguage", required = true) String translatedLanguage) {
    String externalApiUrl =
        "https://api.mangadex.org/manga/"
            + id
            + "/feed?limit="
            + limit
            + "&offset="
            + offset
            + "&translatedLanguage[]="
            + translatedLanguage
            + "&includes[]=scanlation_group"
            + "&order[chapter]="
            + order
            + "&contentRating[]=safe&contentRating[]=suggestive&contentRating[]=erotica&contentRating[]=pornographic";
    // Create HttpHeaders and set the User-Agent header
    HttpHeaders headers = new HttpHeaders();
    headers.add("User-Agent", "ink-paradise");

    // Create a RequestEntity with headers
    RequestEntity<Object> requestEntity =
        new RequestEntity<>(headers, HttpMethod.GET, URI.create(externalApiUrl));

    // Make the request
    return restTemplate.exchange(requestEntity, String.class).getBody();
  }

  @GetMapping("/manga-similar")
  public String fetchMangaSimilar(
      @RequestParam(value = "limit", required = true) Number limit,
      @RequestParam(value = "offset", required = false) Number offset,
      @RequestParam(value = "tags", required = true) String[] tags,
      @RequestParam(value = "contentFilter", required = false) Number contentFilter) {
    try {
      StringBuilder externalApiUrl = new StringBuilder("https://api.mangadex.org/manga?");
      externalApiUrl
          .append("limit=")
          .append(URLEncoder.encode(limit.toString(), StandardCharsets.UTF_8.toString()));

      externalApiUrl
          .append("&offset=")
          .append(URLEncoder.encode(offset.toString(), StandardCharsets.UTF_8.toString()));
      int contentFilterValue = contentFilter.intValue();
      switch (contentFilterValue) {
        case 1:
          {
            externalApiUrl.append(
                "&includedTagsMode=AND&order[rating]=desc&includes[]=cover_art&includes[]=author&contentRating[]=safe");
            break;
          }
        case 2:
          {
            externalApiUrl.append(
                "&includedTagsMode=AND&order[rating]=desc&includes[]=cover_art&includes[]=author&contentRating[]=safe&contentRating[]=suggestive");
            break;
          }
        case 3:
          {
            externalApiUrl.append(
                "&includedTagsMode=AND&order[rating]=desc&includes[]=cover_art&includes[]=author&contentRating[]=safe&contentRating[]=suggestive&contentRating[]=erotica");
            break;
          }
        case 4:
          {
            externalApiUrl.append(
                "&includedTagsMode=AND&order[rating]=desc&includes[]=cover_art&includes[]=author&contentRating[]=safe&contentRating[]=suggestive&contentRating[]=erotica&contentRating[]=pornographic");
            break;
          }
      }
      for (String tag : tags) {
        externalApiUrl
            .append("&includedTags[]=")
            .append(URLEncoder.encode(tag, StandardCharsets.UTF_8));
      }
      // Create HttpHeaders and set the User-Agent header
      HttpHeaders headers = new HttpHeaders();
      headers.add("User-Agent", "ink-paradise");
      // Create a RequestEntity with headers
      RequestEntity<Object> requestEntity =
          new RequestEntity<>(headers, HttpMethod.GET, URI.create(externalApiUrl.toString()));
      // Make the request
      return restTemplate.exchange(requestEntity, String.class).getBody();
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
      return e.getMessage();
    } catch (Exception e) {
      return e.getMessage();
    }
  }

  @GetMapping("/popular-manga")
  public String fetchPopularManga(
      @RequestParam(value = "limit", required = true) Number limit,
      @RequestParam(value = "contentFilter", required = false) Number contentFilter) {
    String externalApiUrl = "";
    int contentFilterValue = contentFilter.intValue();
    switch (contentFilterValue) {
      case 1:
        {
          externalApiUrl =
              "https://api.mangadex.org/manga?limit="
                  + limit
                  + "&includes[]=cover_art&includes[]=author"
                  + "&order[rating]=desc&contentRating[]=safe";
        }
      case 2:
        {
          externalApiUrl =
              "https://api.mangadex.org/manga?limit="
                  + limit
                  + "&includes[]=cover_art&includes[]=author"
                  + "&order[rating]=desc&contentRating[]=safe&contentRating[]=suggestive";
        }
      case 3:
        {
          externalApiUrl =
              "https://api.mangadex.org/manga?limit="
                  + limit
                  + "&includes[]=cover_art&includes[]=author"
                  + "&order[rating]=desc&contentRating[]=safe&contentRating[]=suggestive&contentRating[]=erotica";
        }
      case 4:
        {
          externalApiUrl =
              "https://api.mangadex.org/manga?limit="
                  + limit
                  + "&includes[]=cover_art&includes[]=author"
                  + "&order[rating]=desc&contentRating[]=safe&contentRating[]=suggestive&contentRating[]=erotica&contentRating[]=pornographic";
        }
    }
    {
      /**
       * String externalApiUrl = "https://api.mangadex.org/manga?limit=" + limit +
       * "&includes[]=cover_art" +
       * "&order[rating]=desc&contentRating[]=safe&contentRating[]=suggestive&contentRating[]=erotica&contentRating[]=pornographic";
       */
    }
    // Create HttpHeaders and set the User-Agent header
    HttpHeaders headers = new HttpHeaders();
    headers.add("User-Agent", "ink-paradise");
    // Create a RequestEntity with headers
    RequestEntity<Object> requestEntity =
        new RequestEntity<>(headers, HttpMethod.GET, URI.create(externalApiUrl));
    // Make the request
    return restTemplate.exchange(requestEntity, String.class).getBody();
  }

  @GetMapping("/scanlation-group")
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
      @RequestParam(value = "offset", required = true) Number offset,
      @RequestParam(value = "contentFilter", required = false) Number contentFilter) {
    String externalApiUrl = "";
    int contentFilterValue = contentFilter.intValue();
    switch (contentFilterValue) {
      case 1:
        {
          externalApiUrl =
              "https://api.mangadex.org/manga?limit="
                  + limit
                  + "&offset="
                  + offset
                  + "&authors[]="
                  + id
                  + "&order[relevance]=desc&contentRating[]=safe&includes[]=author";
        }
      case 2:
        {
          externalApiUrl =
              "https://api.mangadex.org/manga?limit="
                  + limit
                  + "&offset="
                  + offset
                  + "&authors[]="
                  + id
                  + "&order[relevance]=desc&contentRating[]=safe&contentRating[]=suggestive&includes[]=author";
        }
      case 3:
        {
          externalApiUrl =
              "https://api.mangadex.org/manga?limit="
                  + limit
                  + "&offset="
                  + offset
                  + "&authors[]="
                  + id
                  + "&order[relevance]=desc&contentRating[]=safe&contentRating[]=suggestive&contentRating[]=erotica&includes[]=author";
        }
      case 4:
        {
          externalApiUrl =
              "https://api.mangadex.org/manga?limit="
                  + limit
                  + "&offset="
                  + offset
                  + "&authors[]="
                  + id
                  + "&order[relevance]=desc&contentRating[]=safe&contentRating[]=suggestive&contentRating[]=erotica&contentRating[]=pornographic&includes[]=author";
        }
    }

    {
      /**
       * String externalApiUrl = "https://api.mangadex.org/manga?limit=" + limit + "&offset=" +
       * offset + "&authors[]=" + id +
       * "&order[relevance]=desc&contentRating[]=safe&contentRating[]=suggestive&contentRating[]=erotica&contentRating[]=pornographic";
       */
    }
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

  @GetMapping("/chapter-details")
  public String fetchChapterDetails(@RequestParam(value = "id", required = true) String id) {
    String externalApiUrl =
        "https://api.mangadex.org/chapter/" + id + "?includes[]=scanlation_group";
    // Create HttpHeaders and set the User-Agent header
    HttpHeaders headers = new HttpHeaders();
    headers.add("User-Agent", "ink-paradise");
    // Create a RequestEntity with headers
    RequestEntity<Object> requestEntity =
        new RequestEntity<>(headers, HttpMethod.GET, URI.create(externalApiUrl));
    // Make the request
    return restTemplate.exchange(requestEntity, String.class).getBody();
  }

  @GetMapping("/cover-image")
  public ResponseEntity<byte[]> fetchCoverImage(
      @RequestParam(value = "id", required = true) String id,
      @RequestParam(value = "fileName", required = true) String fileName) {
    String externalApiUrl = "https://uploads.mangadex.org/covers/" + id + "/" + fileName;
    // Create HttpHeaders and set the User-Agent header
    HttpHeaders headers = new HttpHeaders();
    headers.add("User-Agent", "ink-paradise");
    // Create a RequestEntity with headers
    RequestEntity<Object> requestEntity =
        new RequestEntity<>(headers, HttpMethod.GET, URI.create(externalApiUrl));
    // Make the request
    return restTemplate.exchange(requestEntity, byte[].class);
  }

  @GetMapping("/page-image")
  public ResponseEntity<byte[]> fetchPageImage(
      @RequestParam(value = "hash", required = true) String hash,
      @RequestParam(value = "page", required = true) String page) {
    String externalApiUrl = "https://uploads.mangadex.org/data/" + hash + "/" + page;
    // Create HttpHeaders and set the User-Agent header
    HttpHeaders headers = new HttpHeaders();
    headers.add("User-Agent", "ink-paradise");
    // Create a RequestEntity with headers
    RequestEntity<Object> requestEntity =
        new RequestEntity<>(headers, HttpMethod.GET, URI.create(externalApiUrl));
    // Make the request
    return restTemplate.exchange(requestEntity, byte[].class);
  }
}
