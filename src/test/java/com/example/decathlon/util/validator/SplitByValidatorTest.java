package com.example.decathlon.util.validator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class SplitByValidatorTest {

    SplitByValidator splitByValidator = new SplitByValidator();

    @Test
    void validateTest_WithEmptySplitByCriteriaString() {
        assertThrows(IllegalArgumentException.class, () -> splitByValidator.validate(""));
    }

    @Test
    void validateTest_WithInvalidSplitByCriteriaString() {
        assertThrows(IllegalArgumentException.class, () -> splitByValidator.validate(" "));
    }

//    @Test
//    void validateTest_WithNullSplitByCriteriaString() {
//        assertThrows(IllegalArgumentException.class, () -> splitByValidator.validate(null));
//    }

    @Test
    void validateTest_WithNonMatchingSplitByCriteriaString() {
        assertThrows(IllegalArgumentException.class, () -> splitByValidator.validate(","));
    }

}
