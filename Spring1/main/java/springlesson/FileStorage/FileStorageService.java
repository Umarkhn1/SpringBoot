package springlesson.FileStorage;

import org.hashids.Hashids;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import springlesson.User.User;

import java.io.File;
import java.io.IOException;
import java.util.Date;

@Service
public class FileStorageService {
    private final FileStorageRepository fileStorageRepository;

    private final Hashids hashids;

    public FileStorageService(FileStorageRepository fileStorageRepository) {
        this.fileStorageRepository = fileStorageRepository;
        this.hashids = new Hashids(getClass().getName(), 6);
    }

    public Hashids getHashids() {
        return hashids;
    }


    @Value("${spring.upload.server.folder}")
    private String serverFolderPath;


    public FileStorage save(MultipartFile multipartFile) {
        FileStorage fileStorage = new FileStorage();
        fileStorage.setName(multipartFile.getOriginalFilename());
        fileStorage.setSize(multipartFile.getSize());
        fileStorage.setContentType(multipartFile.getContentType());
        fileStorage.setStatus(FileStorageStatus.DRAFT);
        fileStorage.setExtension(getExt(multipartFile.getOriginalFilename()));
        fileStorageRepository.save(fileStorage);


        Date now = new Date();
//        File uploadFolder = new File(this.serverFolderPath+ "uploadFolderPath"+1900+now.getYear()+
//                "/"+1+now.getMonth()+"/"+now.getDay());

        String path = String.format("%s/uploadFiles/%d/%d/%d", this.serverFolderPath,
                1900 + now.getYear(), 1 + now.getMonth(), now.getDay());
        File uploadFolder = new File(path);
        if (!uploadFolder.exists() && uploadFolder.mkdirs()) {
            System.out.println("created folder");
        }

        fileStorage.setHashId(hashids.encode(fileStorage.getId()));
        String relativePath = String.format("uploadFiles/%d/%d/%d", 1900 + now.getYear(), 1 + now.getMonth(), now.getDay());
        fileStorage.setUploadFolder(relativePath);
        fileStorageRepository.save(fileStorage);
        uploadFolder = uploadFolder.getAbsoluteFile();
        File file = new File(uploadFolder, String.format("%s.%s", fileStorage.getHashId(), fileStorage.getExtension()));

        try {
            multipartFile.transferTo(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return fileStorage;
    }

    private String getExt(String fileName) {
        String ext = null;
        if (fileName != null && !fileName.isEmpty()) {
            int dot = fileName.lastIndexOf(".");
            if (dot > 0 && dot < fileName.length() - 2) {
                ext = fileName.substring(dot + 1);

            }

        }
        return ext;
    }

    public FileStorage findByHashId(String hashId) {
        return fileStorageRepository.findByHashId(hashId);
    }

    public FileStorage findById(Long id) {
        return fileStorageRepository.findById(id).orElse(null);
    }

    public void delete(String hashId) {

       FileStorage fileStorage = fileStorageRepository.findByHashId(hashId);

        File file = new File(String.format("%s%s", this.serverFolderPath, fileStorage.getUploadFolder()));
        if (file.exists()) {
            fileStorageRepository.delete(fileStorage);
        }

    }

}

