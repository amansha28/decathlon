package com.example.decathlon.reader;

/*
 * Contains all the input parameters associated with CSV file.
 * */
public class CSVReaderInputParams {

    public static final String INPUT_FILE_LOCATION = "location";
    public static final String SPLIT_BY = "splitBy";

    private CSVReaderInputParams() {
        throw new IllegalStateException("Utility class");
    }
}
