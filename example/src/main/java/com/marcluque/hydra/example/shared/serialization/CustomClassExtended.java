package com.marcluque.hydra.example.shared.serialization;

import java.util.UUID;

/**
 * Created with love by marcluque on 12.02.18
 */
public class CustomClassExtended {

    private String testStringExtended;

    private UUID testUUID;

    private long testLong;

    private Class<?> testClass;

    public CustomClassExtended() {
        // Obligatory construcotr
    }

    // Optional constructor
    public CustomClassExtended(String testStringExtended, UUID testUUID, long testLong, Class<?> testClass) {
        this.testStringExtended = testStringExtended;
        this.testUUID = testUUID;
        this.testLong = testLong;
        this.testClass = testClass;
    }

    /*
     * Obligatory setter methods for every field that is supposed to be serialized
     */
    public void setTestStringExtended(String testStringExtended) {
        this.testStringExtended = testStringExtended;
    }

    public void setTestUUID(UUID testUUID) {
        this.testUUID = testUUID;
    }

    public void setTestLong(long testLong) {
        this.testLong = testLong;
    }

    public void setTestClass(Class<?> testClass) {
        this.testClass = testClass;
    }

    // Auto-generated toString method by IntelliJ for example purposes
    @Override
    public String toString() {
        return "CustomClassExtended{" + "\n" +
                "  testStringExtended='" + testStringExtended + '\'' + "\n" +
                "  testUUID=" + testUUID + "\n" +
                "  testLong=" + testLong + "\n" +
                "  testClass=" + testClass + "\n" +
                '}';
    }
}