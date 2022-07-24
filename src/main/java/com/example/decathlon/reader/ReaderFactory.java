package com.example.decathlon.reader;


/*
 * Factory class for Reader.It provides concrete readers based on input.
 * */
public class ReaderFactory {

    public Reader getReader(String type) {
        if (ReaderType.CSV.toString().equals(type)) {
            return new CSVReader();
        }
        // Default implementation.
        return new CSVReader();
    }
}
