package io.codebeneath;

import lombok.ToString;

@ToString
public class Engine {

    private static final String DEFAULT_SIZE = "V8";
    private final String size;
    
    public Engine(String size) {
        this.size = size;
    }
    
    public String getSize() {
        return (size != null) ? size : DEFAULT_SIZE;
    }
}
