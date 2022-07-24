package com.example.decathlon.reader;

import java.util.Map;

/*
 * Interface to be implemented by all different types of Readers.
 * */

public interface Reader {

    Object read(Map<String, String> map);
}
