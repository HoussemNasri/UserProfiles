package tech.houssemnasri.imagestorage.datastore;

import java.util.List;

import org.springframework.stereotype.Component;
import tech.houssemnasri.imagestorage.profile.UserProfile;

@Component
public class FakeUserProfilesDataStore {
  private static final List<UserProfile> USER_PROFILES =
      List.of(
          new UserProfile(null, "Carderic", null),
          new UserProfile(null, "Informerci", null),
          new UserProfile(null, "mabel_wilk1991", null),
          new UserProfile(null, "Vicoper2006", null),
          new UserProfile(null, "mrallan140", null),
          new UserProfile(null, "milliemillieme", null),
          new UserProfile(null, "disyllable", null),
          new UserProfile(null, "SneakyCreeper148", null));

  /** Returns all fake profiles without a profile picture */
  public List<UserProfile> get() {
    return USER_PROFILES;
  }
}
