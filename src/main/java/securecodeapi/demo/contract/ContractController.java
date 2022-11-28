package securecodeapi.demo.contract;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping(value = "/contract", produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
public class ContractController {

    private ContractService contractService;

    @PostMapping("/{id}")
    public ResponseEntity<?> saveContract(@PathVariable String id,
                                                @RequestBody HashMap<String, String> contract) {
        return ResponseEntity.ok(this.contractService.save(id, contract));
    }

    @PostMapping("/{id}/validate")
    public ResponseEntity<?> validateContract(@PathVariable String id, @RequestBody JsonNode recievedObject)
            throws ExecutionException, InterruptedException {
        this.contractService.validate(id, recievedObject);
        return ResponseEntity.ok().build();
    }
}
