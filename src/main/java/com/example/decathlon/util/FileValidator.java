package com.example.decathlon.util;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileValidator implements Validator {

    public static final Logger LOGGER = Logger.getLogger(FileValidator.class.getName());

    @Override
    public void validate(Object obj) {
        if (obj instanceof String) {
            File file = new File((String) obj);
            if (!file.exists()) {
                LOGGER.log(Level.SEVERE, "File Path is invalid");
                throw new IllegalArgumentException("File Path is invalid");
            }
        }
    }
}
