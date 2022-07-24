package com.example.decathlon.writer;

import com.example.decathlon.writer.xml.XMLFileWriter;

/*
 * Factory class for Writer.It provides concrete writer based on input.
 * */

public class WriterFactory {

    public Writer getWriter(String type) {
        if (WriterType.XML.toString().equals(type)) {
            return new XMLFileWriter();
        }
        // Default implementation.
        return new XMLFileWriter();

    }
}
