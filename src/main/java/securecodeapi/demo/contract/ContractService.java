package securecodeapi.demo.contract;

import com.google.cloud.firestore.Firestore;
import org.springframework.stereotype.Service;
import securecodeapi.demo.contract.firestore.FirebaseInitialization;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;

@Service
public class ContractService {

   private static final String COLLECTION_NAME = "validated-field-type";

   private final FirebaseInitialization firebase;

   private final Firestore firestoreReference;

    public ContractService(FirebaseInitialization firebase) {
        this.firebase = firebase;
        this.firestoreReference = firebase.getFirestore();
    }

    public void validate(String id, HashMap<String, Object> recievedObject) throws ExecutionException, InterruptedException {
        final HashMap<String, String> savedMap = this.findById(id);

        final var contract = new Contract();

        contract.createContractSpecification(savedMap);

        contract.getValidation().validateObject(recievedObject);

    }

    public HashMap<String, String> save(String id, HashMap<String, String> recievedContract) {

        final var contract = new Contract();
        contract.getValidation().validateContract(recievedContract);

        this.firestoreReference.collection(COLLECTION_NAME).document(id).create(recievedContract);

        return recievedContract;
    }

    public HashMap<String, String> findById(String id) throws ExecutionException, InterruptedException {

        final var documentReference = this.firestoreReference.collection(COLLECTION_NAME).document(id);
        final var apiFuture = documentReference.get();
        final var document = apiFuture.get();

        if (document.exists()) {
            return (HashMap<String, String>) document.toObject(Object.class);
        }
        return null;
    }

}
