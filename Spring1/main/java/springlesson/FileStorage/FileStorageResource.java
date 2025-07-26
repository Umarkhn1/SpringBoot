package springlesson.FileStorage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileUrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springlesson.User.User;

import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.io.File;

@RestController
@RequestMapping("/api")
public class FileStorageResource {

    private final FileStorageService fileStorageService;

    @Value("${spring.upload.server.folder}")
    private String serverFolderPath;

    public FileStorageResource(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    @PostMapping("/upload")
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile multipartFile) {
        FileStorage fileStorage = fileStorageService.save(multipartFile);
        return ResponseEntity.ok(fileStorage);
    }

    @GetMapping("/preview/{hashId}")
    public ResponseEntity<?> preview(@PathVariable String hashId) throws MalformedURLException {
        FileStorage fileStorage = fileStorageService.findByHashId(hashId);

        // Полный путь до файла
        String fullFilePath = String.format("%s/%s/%s.%s",
                serverFolderPath,
                fileStorage.getUploadFolder(),
                fileStorage.getHashId(),
                fileStorage.getExtension());

        File file = new File(fullFilePath);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" +
                        URLEncoder.encode(fileStorage.getName(), StandardCharsets.UTF_8) + "\"")
                .contentType(MediaType.parseMediaType(fileStorage.getContentType()))
                .contentLength(file.length())
                .body(new FileUrlResource(file.getAbsolutePath()));
    }
    @GetMapping("/download/{hashId}")
    public ResponseEntity<?> download(@PathVariable String hashId) throws MalformedURLException {
       FileStorage fileStorage = fileStorageService.findByHashId(hashId);

        // Полный путь до файла
        String fullFilePath = String.format("%s/%s/%s.%s",
                serverFolderPath,
                fileStorage.getUploadFolder(),
                fileStorage.getHashId(),
                fileStorage.getExtension());

        File file = new File(fullFilePath);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" +
                        URLEncoder.encode(fileStorage.getName(), StandardCharsets.UTF_8) + "\"")
                .contentType(MediaType.parseMediaType(fileStorage.getContentType()))
                .contentLength(file.length())
                .body(new FileUrlResource(file.getAbsolutePath()));
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {FileStorage get = fileStorageService.findById(id);
        return ResponseEntity.ok(get);
    }

    @DeleteMapping("/delete/{hashId}")
    public ResponseEntity<?> delete(@PathVariable String hashId) {
        fileStorageService.delete(hashId);
        return ResponseEntity.ok("deleted id №" + hashId);
    }
}
