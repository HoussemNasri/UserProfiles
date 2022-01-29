package tech.houssemnasri.imagestorage.profile;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/v1/profiles")
@AllArgsConstructor
@Slf4j
public class UserProfileController {

  private final UserProfileService userProfileService;

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public List<UserProfile> getUserProfiles() {
    return userProfileService.getAllProfiles();
  }

  @PostMapping(
      path = "{profileId}/picture/upload",
      produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<UserProfile> uploadUserProfilePicture(
      @PathVariable UUID profileId, @RequestParam MultipartFile profilePicture) {
    Optional<UserProfile> userProfile =
        userProfileService.uploadUserProfilePicture(profileId, profilePicture);
    if (userProfile.isPresent()) {
      return ResponseEntity.ok(userProfile.get());
    } else {
      return ResponseEntity.badRequest().build();
    }
  }
}
