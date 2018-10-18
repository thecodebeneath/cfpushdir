package io.codebeneath;

import lombok.ToString;

@ToString
public class Car {
    
    private final String model;
    private final Engine engine;
    
    public Car(String model, Engine engine) {
        this.model = model;
        this.engine = engine;
    }
    
}
