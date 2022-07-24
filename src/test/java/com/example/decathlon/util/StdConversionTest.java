package com.example.decathlon.util;

import org.junit.jupiter.api.Test;

import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StdConversionTest {

    @Test
        //19800000
    void getStartConvertedDateInSecs_CorrectTimeStampTest() throws ParseException {
        long expectedTimestamp = 19800000;
        assertEquals(expectedTimestamp, StdConversion.getStartConvertedDateInSecs(), () -> "TimeStamp does not matches the expected");
    }


}
