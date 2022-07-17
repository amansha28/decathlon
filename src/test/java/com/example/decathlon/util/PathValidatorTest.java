package com.example.decathlon.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class PathValidatorTest {

    PathValidator pathValidator = new PathValidator();

    @Test
    public void validateTest_WithEmptyString() {
        assertThrows(IllegalArgumentException.class, () -> pathValidator.validate(""));
    }

    @Test
    public void validateTest_WithInvalidString() {
        assertThrows(IllegalArgumentException.class, () -> pathValidator.validate(" "));
    }

    @Test
    public void validateTest_WithNullString() {
        assertThrows(IllegalArgumentException.class, () -> pathValidator.validate(null));
    }
}
