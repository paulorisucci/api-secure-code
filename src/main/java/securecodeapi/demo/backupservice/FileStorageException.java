package securecodeapi.demo.backupservice;

public class FileStorageException extends RuntimeException {

    public FileStorageException() {
        super();
    }

    public FileStorageException(String message) {
        super(message);
    }
}
