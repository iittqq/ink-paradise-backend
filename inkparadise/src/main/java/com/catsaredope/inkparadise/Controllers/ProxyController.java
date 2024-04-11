package com.catsaredope.inkparadise.Controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Enumeration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/proxy")
public class ProxyController {

  @GetMapping("/myanimelist")
  public ResponseEntity<String> proxyMyAnimeList(
      @RequestParam String url, HttpServletRequest request, HttpServletResponse response) {
    try {
      // Create URL object
      URL myAnimeListUrl = new URL(url);
      HttpURLConnection connection = (HttpURLConnection) myAnimeListUrl.openConnection();

      // Set request method
      connection.setRequestMethod(request.getMethod());

      // Copy headers from original request to proxy request
      Enumeration<String> headerNames = request.getHeaderNames();
      while (headerNames.hasMoreElements()) {
        String headerName = headerNames.nextElement();
        String headerValue = request.getHeader(headerName);
        connection.setRequestProperty(headerName, headerValue);
      }

      // Send proxy request
      int responseCode = connection.getResponseCode();

      // Read response from myanimelist.net
      BufferedReader reader =
          new BufferedReader(new InputStreamReader(connection.getInputStream()));
      StringBuilder responseBody = new StringBuilder();
      String line;
      while ((line = reader.readLine()) != null) {
        responseBody.append(line);
      }
      reader.close();

      // Set CORS headers
      response.setHeader("Access-Control-Allow-Origin", "*");
      response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
      response.setHeader("Access-Control-Allow-Headers", "*");

      // Return response
      return ResponseEntity.status(responseCode).body(responseBody.toString());
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
  }
}
