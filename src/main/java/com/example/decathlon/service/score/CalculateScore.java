package com.example.decathlon.service.score;

import com.example.decathlon.athlete.Athlete;
import com.example.decathlon.events.Events;
import com.example.decathlon.events.Sport;
import com.example.decathlon.events.utils.SportsType;
import com.example.decathlon.util.validator.ListValidator;
import com.example.decathlon.util.validator.Validator;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/*
 * Class containing methods related to calculating score of Athletes.
 * */

public class CalculateScore {

    public static final Logger logger = Logger.getLogger(CalculateScore.class.getName());
    Validator listValidator = new ListValidator();


    /**
     * Calculate the score of each athlete in each sport based category of sport : TRACK or FIELD.
     * Set it back to the respective athlete.
     */
    public List<Athlete> calculateScoreBySportsType(List<Athlete> list) {
        logger.info("==== Starting CalculateScore : calculateScoreBySportsType ====");
        listValidator.validate(list);

        list.forEach(
                athlete -> {
                    Map<String, Double> athleteEventPerformance = athlete.getEventPerformance();
                    for (int i = 0; i < athleteEventPerformance.size(); i++) {
                        Sport currSport = Events.getSportsList().get(i);
                        int tempScore = calculateAthleteScoreBasedOnPerformanceInSport(currSport, athleteEventPerformance);
                        athlete.setTotalScore(athlete.getTotalScore() + tempScore);
                    }
                }
        );
        logger.info("==== Ending CalculateScore : calculateScoreBySportsType ====");
        return list;
    }


    public int calculateAthleteScoreBasedOnPerformanceInSport(Sport currSport, Map<String, Double> athPerf) {
        int tempScore = 0;
        // Since there are only 2 categories we have created a separate method.
        // Otherwise, we can create Interface of Event type with 1 calculateScore(B,P,C) and its concrete classes implementing own version
        // Based on Sport Type, call the concrete class method.
        if (SportsType.TRACK.equals(currSport.getType())) {
            tempScore = (int) (currSport.getParameters().getConstA() * (Math.pow((currSport.getParameters().getConstB() - athPerf.get(currSport.getName())), currSport.getParameters().getConstC())));
        } else {
            tempScore = (int) (currSport.getParameters().getConstA() * (Math.pow((athPerf.get(currSport.getName()) - currSport.getParameters().getConstB()), currSport.getParameters().getConstC())));
        }

        return tempScore;
    }

}
