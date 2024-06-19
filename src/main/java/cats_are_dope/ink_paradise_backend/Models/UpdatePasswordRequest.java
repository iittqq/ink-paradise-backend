package cats_are_dope.ink_paradise_backend.Models;

public class UpdatePasswordRequest {
  private long id;
  private String oldPassword;
  private String newPassword;

  public long getId() {
    return id;
  }

  public void setId(long newId) {
    this.id = newId;
  }

  public String getOldPassword() {
    return oldPassword;
  }

  public void setOldPassword(String oldPassword) {
    this.oldPassword = oldPassword;
  }

  public String getNewPassword() {
    return newPassword;
  }

  public void setNewPassword(String newPassword) {
    this.newPassword = newPassword;
  }
}
