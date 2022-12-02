package securecodeapi.demo.backupservice;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "file")
@Getter
@Setter
@Component
public class FileStorageProperties {

    private String uploadDir;

}
