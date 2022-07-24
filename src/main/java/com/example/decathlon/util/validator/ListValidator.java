package com.example.decathlon.util.validator;

import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * Concrete class to validate provided list.
 * */

public class ListValidator implements Validator {

    public static final Logger LOGGER = Logger.getLogger(ListValidator.class.getName());

    @Override
    public void validate(Object obj) {
        if (obj.equals(Collections.emptyList())) {
            LOGGER.log(Level.SEVERE, "Input List is Empty: Throwing IllegalArgumentException");
            throw new IllegalArgumentException("Input List is Empty");
        }
    }
}
