package com.example.decathlon.util.validator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class PathValidatorTest {

    PathValidator pathValidator = new PathValidator();

    @Test
    void validateTest_WithEmptyString() {
        assertThrows(IllegalArgumentException.class, () -> pathValidator.validate(""));
    }

    @Test
    void validateTest_WithInvalidString() {
        assertThrows(IllegalArgumentException.class, () -> pathValidator.validate(" "));
    }

//    @Test
//    void validateTest_WithNullString() {
//        assertThrows(IllegalArgumentException.class, () -> pathValidator.validate(null));
//    }
}
