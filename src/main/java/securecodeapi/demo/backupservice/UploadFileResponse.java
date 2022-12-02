package securecodeapi.demo.backupservice;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UploadFileResponse {

    private String fileName;

    private String fileDownloadUri;

    private String fileSize;

    private long size;

    public UploadFileResponse(String fileName, String fileDownloadUri, String fileSize, long size) {
        this.fileName = fileName;
        this.fileDownloadUri = fileDownloadUri;
        this.fileSize = fileSize;
        this.size = size;
    }
}
