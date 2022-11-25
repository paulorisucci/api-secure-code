package securecodeapi.demo.contract;

import lombok.Getter;
import securecodeapi.demo.exceptions.InvalidObjectException;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Getter
public class Contract {

    private final HashMap<String, Class<?>> contractSpecification;

    private final Validation validation;

    public Contract() {
        this.contractSpecification = new HashMap<>();
        this.validation = new Validation();
    }

    public HashMap<String, Class<?>> createContractSpecification(HashMap<String, String> newContractSpecification) {

        newContractSpecification.forEach((key, value) -> {
            this.contractSpecification.put(key, Type.getEquivalentType(value));
        });

        return this.contractSpecification;
    }

    class Validation {

        private final Map<String, Object> invalidAttributes;

        private final Map<String, Object> invalidContractAttributes;

        Validation() {
            this.invalidAttributes = new HashMap<String, Object>();
            this.invalidContractAttributes = new HashMap<String, Object>();
        }

        void validateContract(HashMap<String, String> newContractSpecification) {
            newContractSpecification.forEach(this::validateContractKey);
            if(this.contractHasInvalidAttributes()) {
                throw new InvalidObjectException("O contrato recebido é inválido.", invalidContractAttributes);
            }
        }

        private void validateContractKey(String key, String value) {
            if(!Type.typeExists(value)) {
                this.addInvalidContractKey(key, value);
            }
        }

        public void validateObject(Map<String, Object> recievedInstance) {
            recievedInstance.forEach(this::validateKey);
            if(this.objectHasInvalidAttributes()) {
                throw new InvalidObjectException("O objeto recebido recebido é inválido.", invalidAttributes);
            }
        }

        private void validateKey(String key, Object value) {
            if(!this.valueFollowsContractType(key, value)) {
                this.addInvalidKey(key, value);
            }
        }

        private boolean valueFollowsContractType(String key, Object value) {
            return Objects.equals(contractSpecification.get(key), value.getClass());
        }

        private void addInvalidKey(String key, Object value) {
            this.invalidAttributes.put(key, value);
        }

        private void addInvalidContractKey(String key, Object value) {
            this.invalidContractAttributes.put(key, value);
        }

        private boolean objectHasInvalidAttributes() {
            return !this.invalidAttributes.isEmpty();
        }

        private boolean contractHasInvalidAttributes() {
            return !this.invalidContractAttributes.isEmpty();
        }
    }
}
