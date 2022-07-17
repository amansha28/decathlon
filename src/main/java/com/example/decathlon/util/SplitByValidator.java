package com.example.decathlon.util;

import java.util.logging.Level;
import java.util.logging.Logger;

public class SplitByValidator implements Validator {

    public static final Logger LOGGER = Logger.getLogger(SplitByValidator.class.getName());

    @Override
    public void validate(Object obj) {
        if (obj instanceof String) {
            if (((String) obj).isEmpty() || "".equals(((String) obj).trim())) {
                LOGGER.log(Level.SEVERE, "Provided Split-by criteria is empty");
                throw new IllegalArgumentException("Provided Split-by criteria is empty");
            }
            if (!";".equals(obj)) {
                LOGGER.log(Level.SEVERE, "Provided Split-by criteria does not match");
                throw new IllegalArgumentException("Provided Split-by criteria does not match");
            }
        }
        if (obj == null) {
            LOGGER.log(Level.SEVERE, "Provided Split-by criteria is null");
            throw new IllegalArgumentException("Provided Split-by criteria is null");
        }
    }
}
