package cats_are_dope.ink_paradise_backend.Services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

  @Value("${jwt.secret}")
  private String jwtSecret;

  @Value("${jwt.access.token.expiration}")
  private long accessTokenExpiration;

  @Value("${jwt.refresh.token.expiration}")
  private long refreshTokenExpiration;

  // Generate a Key object from the secret
  private Key getSigningKey() {
    byte[] keyBytes = Base64.getDecoder().decode(jwtSecret);
    return Keys.hmacShaKeyFor(keyBytes);
  }

  // Generate Access Token
  public String generateAccessToken(Long accountId) {
    return Jwts.builder()
        .setSubject(accountId.toString())
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + accessTokenExpiration))
        .signWith(getSigningKey(), SignatureAlgorithm.HS512)
        .compact();
  }

  // Generate Refresh Token
  public String generateRefreshToken(Long accountId) {
    return Jwts.builder()
        .setSubject(accountId.toString())
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + refreshTokenExpiration))
        .signWith(getSigningKey(), SignatureAlgorithm.HS512)
        .compact();
  }

  // Get Claims from Token
  public Claims getClaimsFromToken(String token) {
    return Jwts.parserBuilder()
        .setSigningKey(getSigningKey())
        .build()
        .parseClaimsJws(token)
        .getBody();
  }

  // Check if Access Token is Expired
  public boolean isAccessTokenExpired(String token) {
    final Date expiration = getClaimsFromToken(token).getExpiration();
    return expiration.before(new Date());
  }

  // Check if Refresh Token is Expired
  public boolean isRefreshTokenExpired(String token) {
    final Date expiration = getClaimsFromToken(token).getExpiration();
    return expiration.before(new Date());
  }
}
