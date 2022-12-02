package securecodeapi.demo.backupservice;


import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;

@Service
public class BackupService {

    private final Path backupLocation;

    private final static List<String> invalidCharactersToFileName = Arrays.asList("..", "/", "\\", "~", "C:");

    public BackupService(FileStorageProperties fileStorageProperties) {
        this.backupLocation = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath()
                .normalize();

        try {
            Files.createDirectories(this.backupLocation);
        } catch (Exception exc) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored"+exc);
        }
    }

    public String storeFile(MultipartFile file) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            if(isValidFileName(fileName)) {
                throw new FileStorageException("Sorry, file name contains invalid path sequence: " + fileName);
            }
            Path targetLocation = this.backupLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        }
        catch (IOException ex) {
            throw new FileStorageException("Could not store file "+fileName);
        }
    }

    private boolean isValidFileName(String fileName) {
        return invalidCharactersToFileName.stream().anyMatch(fileName::contains);
    }

    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.backupLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(!resource.exists()) {
                throw new BackupNotFoundException("File not found " + fileName);
            }
            return resource;

        } catch (MalformedURLException exc) {
            throw new BackupNotFoundException("Backup not found "+fileName);
        }
    }

}
