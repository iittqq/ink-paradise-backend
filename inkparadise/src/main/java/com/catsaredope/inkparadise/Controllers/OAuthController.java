package com.catsaredope.inkparadise.Controllers;

import jakarta.servlet.http.HttpSession;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/oauth")
public class OAuthController {

  @Value("${myanimelist.client_id}")
  private String clientId;

  @Value("${myanimelist.client_secret}")
  private String clientSecret;

  @GetMapping("/authorize")
  public RedirectView authorize(HttpSession session) {
    // Generate a code verifier
    String codeVerifier = generateRandomString(128);

    session.setAttribute("code_verifier", codeVerifier);

    // Generate a code challenge
    String codeChallenge = generateCodeChallenge(codeVerifier);

    String redirectUri = "http://localhost:8080/oauth/callback"; // Change this to your callback URI
    String proxyUrl = "http://localhost:8080/proxy/myanimelist";
    String authorizationUrl =
        proxyUrl
            + "?response_type=code"
            + "&client_id="
            + clientId
            + "&redirect_uri="
            + redirectUri
            + "&code_challenge="
            + codeChallenge
            + "&code_challenge_method=plain";
    return new RedirectView(authorizationUrl);
  }

  @GetMapping("/callback")
  public ResponseEntity<Map<String, Object>> callback(
      @RequestParam("code") String code, HttpSession session) {

    // Retrieve codeVerifier from session
    String codeVerifier = (String) session.getAttribute("code_verifier");

    if (codeVerifier == null) {
      throw new RuntimeException("Code verifier not found in session");
    }
    // Exchange authorization code for access token
    String accessToken = exchangeAuthorizationCodeForAccessToken(code, codeVerifier);

    // Fetch user's account details using the access token
    Map<String, Object> userDetails = fetchUserDetails(accessToken);

    // Return user details in JSON format
    return ResponseEntity.ok().body(userDetails);
  }

  // Exchange authorization code for access token
  private String exchangeAuthorizationCodeForAccessToken(String code, String codeVerifier) {
    String tokenUrl = "https://myanimelist.net/v1/oauth2/token";
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

    // Prepare request body
    MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
    requestBody.add("client_id", clientId);
    requestBody.add("client_secret", clientSecret);
    requestBody.add("grant_type", "authorization_code");
    requestBody.add("code", code);
    requestBody.add(
        "redirect_uri", "http://localhost:8080/oauth/callback"); // Change this to your callback URI
    requestBody.add("code_verifier", codeVerifier);
    HttpEntity<MultiValueMap<String, String>> requestEntity =
        new HttpEntity<>(requestBody, headers);
    // Create ParameterizedTypeReference to specify the response type
    ParameterizedTypeReference<Map<String, Object>> responseType =
        new ParameterizedTypeReference<Map<String, Object>>() {};
    // Make the POST request to exchange code for access token
    ResponseEntity<Map<String, Object>> responseEntity =
        new RestTemplate().exchange(tokenUrl, HttpMethod.POST, requestEntity, responseType);
    if (responseEntity.getStatusCode() == HttpStatus.OK) {
      return responseEntity.getBody().get("access_token").toString();
    } else {
      // Handle error
      throw new RuntimeException("Error exchanging code for access token");
    }
  }

  // Fetch user's account details using the access token
  private Map<String, Object> fetchUserDetails(String accessToken) {
    String apiUrl = "https://api.myanimelist.net/v2/users/@me";
    HttpHeaders headers = new HttpHeaders();
    headers.set("Authorization", "Bearer " + accessToken);

    ResponseEntity<Map<String, Object>> responseEntity =
        new RestTemplate()
            .exchange(
                apiUrl,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                new ParameterizedTypeReference<Map<String, Object>>() {});

    System.out.println(responseEntity.getBody());

    if (responseEntity.getStatusCode() == HttpStatus.OK) {
      return responseEntity.getBody();
    } else {
      // Handle error
      throw new RuntimeException("Error fetching user details");
    }
  }

  // Generate a random string of specified length
  private String generateRandomString(int length) {
    byte[] bytes = new byte[length];
    new SecureRandom().nextBytes(bytes);
    return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
  }

  // Generate code challenge from code verifier using SHA-256
  private String generateCodeChallenge(String codeVerifier) {
    try {
      MessageDigest digest = MessageDigest.getInstance("SHA-256");
      byte[] hash = digest.digest(codeVerifier.getBytes());
      return Base64.getUrlEncoder().withoutPadding().encodeToString(hash);
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException("SHA-256 algorithm not found", e);
    }
  }
}
