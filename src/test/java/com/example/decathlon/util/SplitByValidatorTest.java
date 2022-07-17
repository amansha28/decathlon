package com.example.decathlon.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class SplitByValidatorTest {

    SplitByValidator splitByValidator = new SplitByValidator();

    @Test
    public void validateTest_WithEmptySplitByCriteriaString() {
        assertThrows(IllegalArgumentException.class, () -> splitByValidator.validate(""));
    }

    @Test
    public void validateTest_WithInvalidSplitByCriteriaString() {
        assertThrows(IllegalArgumentException.class, () -> splitByValidator.validate(" "));
    }

    @Test
    public void validateTest_WithNullSplitByCriteriaString() {
        assertThrows(IllegalArgumentException.class, () -> splitByValidator.validate(null));
    }

    @Test
    public void validateTest_WithNonMatchingSplitByCriteriaString() {
        assertThrows(IllegalArgumentException.class, () -> splitByValidator.validate(","));
    }

}
