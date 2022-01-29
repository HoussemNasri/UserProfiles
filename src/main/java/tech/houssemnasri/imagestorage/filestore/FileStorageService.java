package tech.houssemnasri.imagestorage.filestore;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface FileStorageService {
  /**
   * Uploads a blob consists of the bytes of file in {@code filepath} bundled with {@code metadata}
   *
   * @param blobFilename the filename of the blob in the storage
   * @param inputStream the stream to upload
   * @param metadata additional information bundled to the blob
   * @return the {@link URL} of the uploaded stream or {@literal Optional#empty()} If something goes
   *     wrong.
   */
  Optional<URL> upload(String blobFilename, InputStream inputStream, Map<String, String> metadata);

  /**
   * Uploads a blob consists of the bytes of file in {@code filepath} bundled with {@code metadata}
   *
   * @param blobFilename the filename of the blob in the storage
   * @param filepath the path of the local file to be uploaded
   * @param metadata additional information bundled to the blob
   * @return the {@link URL} of the uploaded file or {@literal Optional#empty()} If something goes
   *     wrong.
   */
  default Optional<URL> upload(String blobFilename, String filepath, Map<String, String> metadata)
      throws FileNotFoundException {
    return upload(blobFilename, new FileInputStream(filepath), metadata);
  }

  /**
   * Uploads file of path {@code filepath} to the storage under the name {@code blobFilename}
   *
   * @param blobFilename the filename of the blob in the storage
   * @param filepath the path of the local file to be uploaded
   * @return the {@link URL} of the uploaded file or {@literal Optional#empty()} If something goes
   *     wrong.
   */
  default Optional<URL> upload(String blobFilename, String filepath) throws FileNotFoundException {
    return upload(blobFilename, filepath, Collections.emptyMap());
  }

  /**
   * Uploads the stream {@code inputStream}
   *
   * @param blobFilename the filename of the blob in the storage
   * @param inputStream the stream to upload
   * @return the {@link URL} of the uploaded stream or {@literal Optional#empty()} If something goes
   *     wrong.
   */
  default Optional<URL> upload(String blobFilename, InputStream inputStream) {
    return upload(blobFilename, inputStream, Collections.emptyMap());
  }

  void delete(String filename);

  void rename(String filename);

  /**
   * Download file with name {@code filename} and write its content to {@code outputStream}
   *
   * @param filename the name of the file to download
   * @param outputStream the stream that will be used to write to the content if the downloaded file
   */
  void download(String filename, OutputStream outputStream);

  /** Lists the name of all blobs */
  List<String> listBlobs();
}
