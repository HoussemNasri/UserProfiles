package tech.houssemnasri.imagestorage.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("azure.blob")
public class AzureBlobConfigProperties {
    private String connectionString;
    private String containerName;
}
