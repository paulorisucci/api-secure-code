package securecodeapi.demo.backupservice;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.File;

@AllArgsConstructor
@Getter
@Setter
public class Backup {

    private final File file;

    private final String absolutePath;


}
