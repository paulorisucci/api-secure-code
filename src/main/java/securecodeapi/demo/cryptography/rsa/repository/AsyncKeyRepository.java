package securecodeapi.demo.cryptography.rsa.repository;

import com.google.cloud.firestore.Firestore;

import org.springframework.stereotype.Repository;
import securecodeapi.demo.cryptography.rsa.CustomKeyPair;
import securecodeapi.demo.cryptography.rsa.RsaKeyGenerator;
import securecodeapi.demo.firestore.FirebaseInitialization;

import java.security.PublicKey;
import java.util.HashMap;

@Repository
public class AsyncKeyRepository {

    private static final String COLLECTION_NAME = "async-key-pair";

    private final Firestore firestoreReference;

    public AsyncKeyRepository(FirebaseInitialization firebase) {
        this.firestoreReference = firebase.getFirestore();
    }

    public void save(CustomKeyPair keyPair) {
        this.firestoreReference.collection(COLLECTION_NAME).document(keyPair.getPublicKey()).create(keyPair.getMapToSave());
    }

    public CustomKeyPair findByPublicKey(PublicKey publicKey) {
        try {
            final var documentReference = this.firestoreReference.collection(COLLECTION_NAME)
                    .document(RsaKeyGenerator.encodePublicKey(publicKey));
            final var apiFuture = documentReference.get();
            final var document = apiFuture.get();

            if (document.exists()) {
                Object returnedObject = document.toObject(Object.class);
                HashMap<String, String> map = (HashMap<String, String>) returnedObject;
                return new CustomKeyPair(map);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
