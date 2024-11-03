package cats_are_dope.ink_paradise_backend.Services;

import cats_are_dope.ink_paradise_backend.Models.RefreshToken;
import cats_are_dope.ink_paradise_backend.Repositories.RefreshTokenRepository;
import java.time.Instant;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RefreshTokenService {

  @Autowired private RefreshTokenRepository refreshTokenRepository;

  @Autowired private AccountService accountService;

  // Create or update refresh token
  public RefreshToken createOrUpdateRefreshToken(Long accountId, String refreshTokenValue) {
    Optional<RefreshToken> existingToken = refreshTokenRepository.findByAccountId(accountId);

    if (existingToken.isPresent()) {
      RefreshToken tokenToUpdate = existingToken.get();
      tokenToUpdate.setToken(refreshTokenValue);
      tokenToUpdate.setExpiryDate(Instant.now().plusMillis(2_592_000_000L)); // 30 days
      return refreshTokenRepository.save(tokenToUpdate);
    } else {
      RefreshToken refreshToken = new RefreshToken();
      refreshToken.setAccount(accountService.findAccountById(accountId));
      refreshToken.setToken(refreshTokenValue);
      refreshToken.setExpiryDate(Instant.now().plusMillis(2_592_000_000L)); // 30 days
      return refreshTokenRepository.save(refreshToken);
    }
  }

  // Find by token
  public Optional<RefreshToken> findByToken(String token) {
    return refreshTokenRepository.findByToken(token);
  }

  // Validate refresh token
  public boolean validateRefreshToken(String token) {
    Optional<RefreshToken> refreshToken = findByToken(token);
    return refreshToken.isPresent() && !isRefreshTokenExpired(refreshToken.get());
  }

  // Check if a refresh token is expired
  public boolean isRefreshTokenExpired(RefreshToken token) {
    return token.getExpiryDate().isBefore(Instant.now());
  }

  // Delete token by account ID
  public int deleteByAccountId(Long accountId) {
    return refreshTokenRepository.deleteByAccount(accountService.findAccountById(accountId));
  }

  // Delete token by token value (optional for logout functionality)
  public void deleteByToken(String token) {
    refreshTokenRepository.deleteByToken(token);
  }
}
