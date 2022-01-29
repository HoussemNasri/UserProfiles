package tech.houssemnasri.imagestorage.profile;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Type;
import org.springframework.lang.Nullable;

@Getter
@Setter
@ToString(doNotUseGetters = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class UserProfile {
  @Id
  @GeneratedValue
  @Type(type = "uuid-char")
  private UUID profileId;

  private String username;
  @Nullable private String profilePictureLink;

  public Optional<String> getProfilePictureLink() {
    return Optional.ofNullable(profilePictureLink);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
    UserProfile that = (UserProfile) o;
    return Objects.equals(profileId, that.profileId);
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}
