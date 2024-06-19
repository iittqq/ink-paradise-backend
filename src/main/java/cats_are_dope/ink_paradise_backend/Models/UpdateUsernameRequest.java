package cats_are_dope.ink_paradise_backend.Models;

public class UpdateUsernameRequest {
  private long id;
  private String username;

  public long getId() {
    return id;
  }

  public void setId(long newId) {
    this.id = newId;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String newUsername) {
    this.username = newUsername;
  }
}
