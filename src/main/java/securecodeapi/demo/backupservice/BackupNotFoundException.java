package securecodeapi.demo.backupservice;

public class BackupNotFoundException extends RuntimeException{

    public BackupNotFoundException() {
        super();
    }

    public BackupNotFoundException(String message) {
        super(message);
    }
}
