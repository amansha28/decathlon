package com.example.decathlon.util;

import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ListValidator implements Validator {

    public static final Logger LOGGER = Logger.getLogger(ListValidator.class.getName());

    @Override
    public void validate(Object obj) {
        if (obj.equals(Collections.EMPTY_LIST)) {
            LOGGER.log(Level.SEVERE, "Input List is Empty: Throwing IllegalArgumentException");
            throw new IllegalArgumentException("Input List is Empty");
        }
    }
}
