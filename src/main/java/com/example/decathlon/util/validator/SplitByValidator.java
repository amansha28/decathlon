package com.example.decathlon.util.validator;

import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * Concrete class to validate split by criteria.
 * */

public class SplitByValidator implements Validator {

    public static final Logger LOGGER = Logger.getLogger(SplitByValidator.class.getName());

    @Override
    public void validate(Object obj) {
        if (obj instanceof String) {
            if (((String) obj).isEmpty() || "".equals(((String) obj).trim())) {
                LOGGER.log(Level.SEVERE, "Provided Split-by criteria is null or empty");
                throw new IllegalArgumentException("Provided Split-by criteria is null or empty");
            }
            if (!";".equals(obj)) {
                LOGGER.log(Level.SEVERE, "Provided Split-by criteria does not match");
                throw new IllegalArgumentException("Provided Split-by criteria does not match");
            }
        }
    }
}
