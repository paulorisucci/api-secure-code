package securecodeapi.demo.contract;

import securecodeapi.demo.exceptions.TypeNotFoundException;

import java.util.Arrays;

enum Type {
    BOOLEAN("boolean", Boolean.class),
    BYTE("byte", Byte.class),
    SHORT("short", Short.class),
    INTEGER("integer", Integer.class),
    LONG("long", Long.class),
    FLOAT("float", Float.class),
    DOUBLE("double", Double.class),
    STRING("string", String.class);

    private final String name;

    private final Class<?> equivalentType;

    Type(String name, Class<?> equivalentType) {
        this.name = name;
        this.equivalentType = equivalentType;
    }

    public static Class<?> getEquivalentType(String name) {
        Type foundType = Arrays.stream(Type.values())
                .filter(type -> type.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(() -> new TypeNotFoundException("Tipo não encontrado"));
        return foundType.getEquivalentType();
    }

    public static boolean typeExists(String typeName) {
        return Arrays.stream(Type.values())
                .anyMatch(type -> type.getName().equals(typeName));
    }

    public String getName(){
        return this.name;
    }

    public Class<?> getEquivalentType() {
        return this.equivalentType;
    }
}
