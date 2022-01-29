package tech.houssemnasri.imagestorage.profile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tech.houssemnasri.imagestorage.filestore.FileStorageService;
import tech.houssemnasri.imagestorage.util.Exceptions;

@AllArgsConstructor
@Service
@Slf4j
public class UserProfileService {
  private final UserProfileRepository userProfileRepository;
  private final FileStorageService fileStorageService;

  public List<UserProfile> getAllProfiles() {
    return userProfileRepository.findAll();
  }

  public Optional<UserProfile> uploadUserProfilePicture(
      @NonNull UUID profileId, @NonNull MultipartFile profilePicture) {
    Exceptions.fThrowIllegalArgumentIf(
        profilePicture.isEmpty(), "The Picture is empty: %s", profilePicture.getName());
    Exceptions.throwIllegalArgumentIf(
        userProfileRepository.findById(profileId).isEmpty(),
        String.format("User profile not found: %s", profileId));

    LOGGER.info("Uploading picture of user: {} ", profileId);

    String pictureLink = uploadPictureAndGetLink(profileId.toString(), profilePicture);
    if (pictureLink != null) {
      UserProfile userProfile = userProfileRepository.getById(profileId);
      userProfile.setProfilePictureLink(pictureLink);
      userProfileRepository.save(userProfile);
      return Optional.of(userProfile);
    }
    return Optional.empty();
  }

  private String uploadPictureAndGetLink(String filename, MultipartFile picture) {
    try (InputStream pictureInputStream = picture.getInputStream()) {
      String extension = picture.getOriginalFilename().substring(picture.getOriginalFilename().lastIndexOf('.'));
      Optional<URL> pictureURL =
          fileStorageService.upload(filename + extension, pictureInputStream);
      if (pictureURL.isPresent()) {
        return pictureURL.get().toString();
      } else {
        throw new RuntimeException("Uploading profile picture failed for an unknown reason");
      }
    } catch (IOException e) {
      LOGGER.error("Unable to read the input stream", e);
      return null;
    }
  }
}
