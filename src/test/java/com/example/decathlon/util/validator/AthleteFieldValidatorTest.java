package com.example.decathlon.util.validator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class AthleteFieldValidatorTest {

    @Test
    void checkNoOfFieldsInPlayer_IncorrectNoOfFields() {
        AthleteFieldValidator athleteFieldValidator = new AthleteFieldValidator();
        String[] player = {"Jaan Lepp", "4.84", "10.12", "1.50", "68.44", "19.18", "30.85", "2.80", "33.88", "6:22.75"};
        assertThrows(RuntimeException.class, () -> athleteFieldValidator.validate(player));
    }

}
