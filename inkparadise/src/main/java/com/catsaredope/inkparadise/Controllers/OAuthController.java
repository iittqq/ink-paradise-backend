package com.catsaredope.inkparadise.Controllers;

import jakarta.servlet.http.HttpSession;
import java.math.BigInteger;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

class OAuthResponse {
  String url;
  String codeVerifier;

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getCodeVerifier() {
    return codeVerifier;
  }

  public void setCodeVerifier(String codeVerifier) {
    this.codeVerifier = codeVerifier;
  }
}

@Controller
@RequestMapping("/oauth")
public class OAuthController {

  @Value("${myanimelist.client_id}")
  private String clientId;

  @Value("${myanimelist.client_secret}")
  private String clientSecret;

  @GetMapping("/authorize")
  public ResponseEntity<OAuthResponse> authorize(HttpSession session) {
    // Generate a code verifier
    String codeVerifier = generateRandomString(128);

    session.setAttribute("code_verifier", codeVerifier);
    SecureRandom secureRandom = new SecureRandom();
    String state = new BigInteger(130, secureRandom).toString(32);
    // Generate a code challenge
    String codeChallenge = generateCodeChallenge(codeVerifier);

    String redirectUri = "http://localhost:5173/"; // Change this to your callback URI
    String proxyUrl = "https://proxy-ink-paradise-a8992eb99868.herokuapp.com/";
    String authorizationUrl =
        "https://myanimelist.net/v1/oauth2/authorize"
            + "?response_type=code"
            + "&client_id="
            + clientId
            + "&code_challenge="
            + codeChallenge
            + "&state="
            + state
            + "&redirect_uri="
            + redirectUri
            + "&code_challenge_method=plain";

    // Combine proxy URL and authorization path directly
    String fullUrl = authorizationUrl;

    System.out.println(fullUrl);
    OAuthResponse response = new OAuthResponse();
    response.setUrl(fullUrl);
    response.setCodeVerifier(codeVerifier);
    return ResponseEntity.ok().body(response);
  }

  @GetMapping("/callback")
  public ResponseEntity<String> callback(
      @RequestParam("code") String code, @RequestParam("state") String state, HttpSession session) {

    // Retrieve codeVerifier from session
    String codeVerifier = (String) session.getAttribute("code_verifier");

    System.out.println(codeVerifier);
    System.out.println(code);
    System.out.println(state);
    if (codeVerifier == null) {
      throw new RuntimeException("Code verifier not found in session");
    }
    String tokenUrl = "https://myanimelist.net/v1/oauth2/token";
    String proxyUrl =
        "https://proxy-ink-paradise-a8992eb99868.herokuapp.com/"; // Change this to your proxy URI
    // Create headers
    HttpHeaders headers = new HttpHeaders();

    RestTemplate restTemplate = new RestTemplate();

    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

    MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
    requestBody.add("client_id", clientId);
    requestBody.add("client_secret", clientSecret);
    requestBody.add("code", code);
    requestBody.add("code_verifier", codeVerifier);
    requestBody.add("grant_type", "authorization_code");

    HttpEntity<MultiValueMap<String, String>> requestEntity =
        new HttpEntity<>(requestBody, headers);
    System.out.println(requestEntity);

    // ResponseEntity<String> responseEntity =
    // restTemplate.postForEntity(tokenUrl, requestEntity, String.class);

    /**
     * // Prepare request body MultiValueMap<String, String> requestBody = new
     * LinkedMultiValueMap<>(); requestBody.add("client_id", clientId);
     * requestBody.add("client_secret", clientSecret); requestBody.add("code", code);
     * requestBody.add("code_verifier", codeVerifier); requestBody.add("grant_type",
     * "authorization_code"); requestBody.add( "redirect_uri", "http://localhost:5173/login"); //
     * Change this to your callback URI
     *
     * <p>System.out.println(requestBody); // Create HTTP entity with headers and parameters
     * HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(requestBody,
     * headers);
     *
     * <p>// Make POST request RestTemplate restTemplate = new RestTemplate();
     * ResponseEntity<String> responseEntity = restTemplate.postForEntity(tokenUrl, requestEntity,
     * String.class);
     */
    // Return user details in JSON format
    return ResponseEntity.ok().body(code);
  }

  // Exchange authorization code for access token
  @PostMapping("/exchange")
  public ResponseEntity<String> exchangeAuthorizationCodeForAccessToken(
      String code, String codeVerifier) {
    String tokenUrl = "https://myanimelist.net/v1/oauth2/token";
    String proxyUrl =
        "https://proxy-ink-paradise-a8992eb99868.herokuapp.com/"; // Change this to your proxy URI
    // Create headers
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
    // Prepare request body
    MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
    requestBody.add("client_id", clientId);
    requestBody.add("client_secret", clientSecret);
    requestBody.add("code_verifier", codeVerifier);
    requestBody.add("grant_type", "authorization_code");
    requestBody.add("code", code);
    requestBody.add("redirect_uri", "http://localhost:5173"); // Change this to your callback URI

    System.out.println(requestBody);
    // Create HTTP entity with headers and parameters
    HttpEntity<MultiValueMap<String, String>> requestEntity =
        new HttpEntity<>(requestBody, headers);

    // Make POST request
    RestTemplate restTemplate = new RestTemplate();
    return restTemplate.postForEntity(tokenUrl, requestEntity, String.class);
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
