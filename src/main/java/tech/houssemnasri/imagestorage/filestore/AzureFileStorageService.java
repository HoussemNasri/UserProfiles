package tech.houssemnasri.imagestorage.filestore;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.azure.core.util.Context;
import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.blob.models.BlobItem;
import com.azure.storage.blob.options.BlobParallelUploadOptions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import tech.houssemnasri.imagestorage.config.AzureBlobConfigProperties;

@Service("azureISS")
@Primary
@Slf4j
public class AzureFileStorageService implements FileStorageService {
  private final BlobContainerClient blobContainerClient;

  @Autowired
  public AzureFileStorageService(@Autowired AzureBlobConfigProperties blobProperties) {
    BlobServiceClient blobServiceClient =
        new BlobServiceClientBuilder()
            .connectionString(blobProperties.getConnectionString())
            .buildClient();
    blobContainerClient =
        blobServiceClient.getBlobContainerClient(blobProperties.getContainerName());
  }

  @Override
  public Optional<URL> upload(
      String blobFilename, InputStream inputStream, Map<String, String> metadata) {
    BlobClient client = blobContainerClient.getBlobClient(blobFilename);

    BlobParallelUploadOptions options =
        new BlobParallelUploadOptions(inputStream).setMetadata(metadata);
    client.uploadWithResponse(options, Duration.ofSeconds(60), Context.NONE);

    try {
      return Optional.of(new URL(client.getBlobUrl()));
    } catch (MalformedURLException e) {
      return Optional.empty();
    }
  }

  @Override
  public void delete(String filename) {
    // TODO('Not implemented')
  }

  @Override
  public void rename(String filename) {
    // TODO('Not implemented')
  }

  @Override
  public void download(String filename, OutputStream outputStream) {
    BlobClient client = blobContainerClient.getBlobClient(filename);
    if (client.exists()) {
      client.download(outputStream);
    } else {
      LOGGER.warn("Blob does not exist: {}", filename);
    }
  }

  @Override
  public List<String> listBlobs() {
    return blobContainerClient.listBlobs().mapPage(BlobItem::getName).stream()
        .collect(Collectors.toList());
  }
}
