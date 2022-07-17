package com.example.decathlon.util;


import com.example.decathlon.athlete.Athlete;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ListValidatorTest {

    ListValidator listValidator = new ListValidator();

    @Test
    public void validateTest_WithEmptyList() {
        List<Athlete> list = new ArrayList<>();
        assertThrows(IllegalArgumentException.class, () -> listValidator.validate(list));
    }
}
