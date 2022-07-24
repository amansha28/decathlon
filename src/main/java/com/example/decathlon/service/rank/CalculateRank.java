package com.example.decathlon.service.rank;

import com.example.decathlon.athlete.Athlete;
import com.example.decathlon.util.validator.ListValidator;
import com.example.decathlon.util.validator.Validator;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;


/*
 * Class containing methods involved in calculating rank of athletes.
 * */
public class CalculateRank {

    public static final Logger logger = Logger.getLogger(CalculateRank.class.getName());
    Validator listValidator = new ListValidator();

    public List<Athlete> sortAthleteBasedOnTotalScore(List<Athlete> list) {
        logger.info("==== Starting CalculateRank : sortAthleteBasedOnTotalScore ====");
        listValidator.validate(list);
        Collections.sort(list, Comparator.comparing(Athlete::getTotalScore).reversed());
        logger.info("==== Ending CalculateRank : sortAthleteBasedOnTotalScore ====");
        return list;
    }

    public List<Athlete> calculateAndSetRankOfAthlete(List<Athlete> list) {
        logger.info("==== Starting CalculateRank : calculateAndSetRankOfAthlete ====");
        listValidator.validate(list);
        // 2-counter approach to calculate rank of each athlete
        for (int start = 0, end = 1; end <= list.size(); end++) {
            if (end == list.size() || list.get(end).getTotalScore() != list.get(start).getTotalScore()) {
                String pos = (start + 1) + ((start + 1 == end) ? "" : "-" + end);

                while (start < end)
                    list.get(start++).setRank(pos);
            }
        }
        logger.info("==== Ending CalculateRank : calculateAndSetRankOfAthlete ====");
        return list;
    }
}
