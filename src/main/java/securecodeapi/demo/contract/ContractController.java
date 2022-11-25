package securecodeapi.demo.contract;

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
    public HashMap<String, String> saveContract(@PathVariable String id, @RequestBody HashMap<String, String> contract) throws ExecutionException, InterruptedException {
        return this.contractService.save(id, contract);
    }

    @PostMapping("/{id}/validate")
    public ResponseEntity<?> validateContract(@PathVariable String id, @RequestBody HashMap<String, Object> recievedObject) throws ExecutionException, InterruptedException {
        this.contractService.validate(id, recievedObject);
        return ResponseEntity.ok().build();
    }
}
