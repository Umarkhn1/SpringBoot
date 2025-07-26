package springlesson.FileStorage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springlesson.User.User;

@Repository
public interface FileStorageRepository extends JpaRepository<FileStorage, Long> {
        FileStorage findByHashId(String hashId);
    }

