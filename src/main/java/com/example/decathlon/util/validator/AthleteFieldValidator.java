package com.example.decathlon.util.validator;

import com.example.decathlon.events.Events;

import java.util.logging.Level;
import java.util.logging.Logger;


/*
 * Concrete class to validate Athlete Fields.
 * */
public class AthleteFieldValidator implements Validator {

    public static final Logger logger = Logger.getLogger(AthleteFieldValidator.class.getName());

    /*
     * This method will take a String[] array representing a Player and checks the number of fields in the player.
     * */
    @Override
    public void validate(Object obj) {
        String[] player = (String[]) obj;
        // Each line of the input file should contain = Athlete Name + performances in 10 sports of Decathlon event.
        if (player.length != (Events.getSportsList().size() + 1)) {
            logger.log(Level.SEVERE, "No. of fields does not match required : Throwing RuntimeException");
            throw new RuntimeException("Number off fields in the input file does not match the required number");
        }
    }
}
