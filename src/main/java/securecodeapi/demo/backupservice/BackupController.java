package securecodeapi.demo.backupservice;


import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = BackupController.BASE_PATH)
@AllArgsConstructor
public class BackupController {

    private static final Logger logger = LoggerFactory.getLogger(BackupController.class);

    static final String BASE_PATH = "/api/backup";

    private static final String DOWNLOAD_BASE_PATH = BASE_PATH + "/downloadFile/";

    private final BackupService backupService;

    private final static String DEFAULT_CONTENT_TYPE = "application/octet-stream";

    @PostMapping("/uploadFile")
    public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file) {
        String fileName = backupService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path(DOWNLOAD_BASE_PATH)
                .path(fileName)
                .toUriString();

        return new UploadFileResponse(fileName, fileDownloadUri, file.getContentType(), file.getSize());
    }

    @PostMapping("uploadMultipleFiles")
    public List<UploadFileResponse> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
        return Arrays.stream(files)
                .map(this::uploadFile)
                .collect(Collectors.toList());
    }

    @GetMapping("/downloadFile/{fileName}")
    public ResponseEntity<Resource> downloadFile(@PathVariable("fileName") String fileName, HttpServletRequest request) {

        Resource resource = backupService.loadFileAsResource(fileName);

        String contentType = this.getFileContentType(request, resource);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename()+"\"")
                .body(resource);
    }

    private String getFileContentType(HttpServletRequest request, Resource resource) {

        try {
            return request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException exc) {
            logger.info("Could not determine file type.");
        }

        return DEFAULT_CONTENT_TYPE;
    }
}
