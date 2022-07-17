package com.example.decathlon.util;

import java.util.logging.Level;
import java.util.logging.Logger;

public class PathValidator implements Validator {

    public static final Logger LOGGER = Logger.getLogger(PathValidator.class.getName());

    @Override
    public void validate(Object obj) {
        if (obj instanceof String) {
            if (((String) obj).isEmpty() || "".equals(((String) obj).trim())) {
                LOGGER.log(Level.SEVERE, "Provided String is empty");
                throw new IllegalArgumentException("Provided String is empty");
            }
        }

        if (obj == null) {
            LOGGER.log(Level.SEVERE, "Provided String is empty");
            throw new IllegalArgumentException("Provided String is empty");
        }

    }
}
