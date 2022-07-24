package com.example.decathlon.events;

import com.example.decathlon.events.utils.*;

import java.util.ArrayList;
import java.util.List;

/*
 * Events class - representing List of all the sports in the Decathlon, along with details associated with each sport.
 */
public class Events {


    static List<Sport> sportsList = new ArrayList<>();

    private Events() {
        throw new IllegalStateException("Utility class");
    }

    private static List<Sport> setSportsList() {
        sportsList = new ArrayList<>();
        sportsList.add(new Sport(SportsName.RACE_100_M, SportsType.TRACK, SportsCategory.RUNNING, new Parameters(25.4347, 18, 1.81), Units.SECONDS));
        sportsList.add(new Sport(SportsName.LONG_JUMP, SportsType.FIELD, SportsCategory.JUMPING, new Parameters(0.14354, 220, 1.4), Units.METRES));
        sportsList.add(new Sport(SportsName.SHOT_PUT, SportsType.FIELD, SportsCategory.THROWING, new Parameters(51.39, 1.5, 1.05), Units.METRES));
        sportsList.add(new Sport(SportsName.HIGH_JUMP, SportsType.FIELD, SportsCategory.JUMPING, new Parameters(0.8465, 75, 1.42), Units.METRES)); // not following formula
        sportsList.add(new Sport(SportsName.RACE_400_M, SportsType.TRACK, SportsCategory.RUNNING, new Parameters(1.53775, 82, 1.81), Units.SECONDS));
        sportsList.add(new Sport(SportsName.RACE_110_M_HURDLES, SportsType.TRACK, SportsCategory.RUNNING, new Parameters(5.74352, 28.5, 1.92), Units.SECONDS));
        sportsList.add(new Sport(SportsName.DISCUSS_THROW, SportsType.FIELD, SportsCategory.THROWING, new Parameters(12.91, 4, 1.1), Units.METRES));
        sportsList.add(new Sport(SportsName.POLE_VAULT, SportsType.FIELD, SportsCategory.JUMPING, new Parameters(0.2797, 100, 1.35), Units.METRES)); // not following formula
        sportsList.add(new Sport(SportsName.JAVELIN_THROW, SportsType.FIELD, SportsCategory.THROWING, new Parameters(10.14, 7, 1.08), Units.METRES));
        sportsList.add(new Sport(SportsName.RACE_1500_M, SportsType.TRACK, SportsCategory.RUNNING, new Parameters(0.03768, 480, 1.85), Units.MINUTESSECONDS));
        return sportsList;
    }

    public static List<Sport> getSportsList() {
        if (sportsList.isEmpty()) {
            sportsList = setSportsList();
        }

        return sportsList;
    }


}


