package tech.houssemnasri.imagestorage;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.houssemnasri.imagestorage.config.AzureBlobConfigProperties;
import tech.houssemnasri.imagestorage.config.FakeDatabaseProperties;
import tech.houssemnasri.imagestorage.datastore.FakeUserProfilesDataStore;
import tech.houssemnasri.imagestorage.profile.UserProfileRepository;

@SpringBootApplication
@RequestMapping("/")
@RestController
@RequiredArgsConstructor
@EnableConfigurationProperties({AzureBlobConfigProperties.class, FakeDatabaseProperties.class})
public class MainApp implements CommandLineRunner {

  private final FakeUserProfilesDataStore fakeProfiles;

  private final FakeDatabaseProperties fakeDatabaseProps;

  private final UserProfileRepository userProfileRepository;

  public static void main(String[] args) {
    SpringApplication.run(MainApp.class, args);
  }

  @GetMapping
  public String index() {
    return "<h1> Hello World! </h1>";
  }

  @Override
  public void run(String... args) {
    if (fakeDatabaseProps.isEnabled()) {
      userProfileRepository.saveAll(fakeProfiles.get());
    }
  }
}
