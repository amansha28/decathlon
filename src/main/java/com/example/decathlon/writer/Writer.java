package com.example.decathlon.writer;

import javax.xml.bind.JAXBException;
import java.util.Map;

/*
 * Interface to be implemented by all different types of Writers.
 * */

public interface Writer {

    void write(Map<String, Object> obj) throws JAXBException;
}
