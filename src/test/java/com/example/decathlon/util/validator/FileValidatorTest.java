package com.example.decathlon.util.validator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class FileValidatorTest {

    FileValidator fileValidator = new FileValidator();

    @Test
    void validateTest_WithIncorrectFilePath() {
        final String inputFilePath = "src/test/java/resources/files/results_100.csv";
        assertThrows(IllegalArgumentException.class, () -> fileValidator.validate(inputFilePath));
    }
}
