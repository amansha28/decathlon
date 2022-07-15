package com.example.decathlon.util;

import org.junit.jupiter.api.Test;

import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StdConversionTest {

    StdConversion stdConversion = new StdConversion();

    @Test //19800000
    public void getStartConvertedDateInSecs_CorrectTimeStampTest() throws ParseException {
        long expectedTimestamp = 19800000;

        assertEquals(expectedTimestamp,stdConversion.getStartConvertedDateInSecs(),()->"TimeStamp does not matches the expected");
    }


}
