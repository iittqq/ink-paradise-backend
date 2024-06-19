package cats_are_dope.ink_paradise_backend.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "accountDetails", schema = "inkParadise")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDetails {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(name = "accountId", nullable = false)
  private long accountId;

  @Column(name = "bio", nullable = true, length = 150)
  private String bio;

  @Column(name = "profilePicture", nullable = true, length = 1000)
  private String profilePicture;

  @Column(name = "headerPicture", nullable = true, length = 1000)
  private String headerPicture;

  @Column(name = "contentFilter", nullable = false)
  private long contentFilter;

  public long getId() {
    return id;
  }

  public void setId(long newId) {
    this.id = newId;
  }

  public long getAccountId() {
    return accountId;
  }

  public void setAccountId(long newAccountId) {
    this.accountId = newAccountId;
  }

  public String getBio() {
    return bio;
  }

  public void setBio(String newBio) {
    this.bio = newBio;
  }

  public String getProfilePicture() {
    return profilePicture;
  }

  public void setProfilePicture(String newProfilePicture) {
    this.profilePicture = newProfilePicture;
  }

  public String getHeaderPicture() {
    return headerPicture;
  }

  public void setHeaderPicture(String newHeaderPicture) {
    this.headerPicture = newHeaderPicture;
  }

  public long getContentFilter() {
    return contentFilter;
  }

  public void setContentFilter(long newContentFilter) {
    this.contentFilter = newContentFilter;
  }
}
