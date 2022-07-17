package com.example.decathlon.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class FileValidatorTest {

    FileValidator fileValidator = new FileValidator();

    @Test
    public void validateTest_WithIncorrectFilePath() {
        final String inputFilePath = "src/test/java/resources/files/results_100.csv";
        assertThrows(IllegalArgumentException.class, () -> fileValidator.validate(inputFilePath));
    }
}
