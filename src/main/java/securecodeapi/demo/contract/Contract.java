package securecodeapi.demo.contract;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import lombok.Getter;
import securecodeapi.demo.bodymanagement.BodyManager;
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
            this.contractSpecification.put(key, Type.getEquivalentTypeByName(value));
        });

        return this.contractSpecification;
    }

    public void validateObject(JsonNode recievedInstance) {
        this.getValidation().validateObject(recievedInstance);
    }

    public void validateContract(HashMap<String, String> newContractSpecification) {
        this.getValidation().validateContract(newContractSpecification);
    }

    class Validation {

        private final Map<String, Object> invalidAttributes;

        private final Map<String, Object> invalidContractAttributes;

        Validation() {
            this.invalidAttributes = new HashMap<String, Object>();
            this.invalidContractAttributes = new HashMap<String, Object>();
        }

        private void validateContract(HashMap<String, String> newContractSpecification) {
            newContractSpecification.forEach(this::validateContractKey);
            if(this.contractHasInvalidAttributes()) {
                throw new InvalidObjectException("O contrato recebido é inválido.", invalidContractAttributes);
            }
        }

        private void validateContractKey(String key, String value) {
            if(!Type.primitiveTypeExistsByName(value)) {
                this.addInvalidContractKey(key, value);
            }
        }

        private void validateObject(JsonNode recievedInstance) {

            if(JsonNodeType.OBJECT.equals(recievedInstance.getNodeType())) {
                Map<String, Object> recievedInstanceMap = BodyManager.toMap(recievedInstance);
                this.verifyIfObjectContainsAllAttributes(recievedInstanceMap);
                recievedInstanceMap.forEach(this::validateKey);
            } else if (JsonNodeType.ARRAY.equals(recievedInstance.getNodeType())) {
                recievedInstance.forEach(this::validateObject);
            }
            if(this.objectHasInvalidAttributes()) {
                throw new InvalidObjectException("O objeto recebido recebido é inválido.", invalidAttributes);
            }
        }

        private void verifyIfObjectContainsAllAttributes(Map<String, Object> recievedInstance) {
            contractSpecification.forEach((key, value) -> {
                if(!recievedInstance.containsKey(key)) {
                    throw new InvalidObjectException("O objeto não possui todos os atributos definidos no contrato");
                }
            } );
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
