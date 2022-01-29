package tech.houssemnasri.imagestorage.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("fake-database")
@Data
public class FakeDatabaseProperties {
    private boolean enabled;
}
