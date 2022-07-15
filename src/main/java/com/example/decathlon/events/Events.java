package com.example.decathlon.events;

import com.example.decathlon.events.utils.Parameters;
import com.example.decathlon.events.utils.SportsCategory;
import com.example.decathlon.events.utils.SportsType;
import com.example.decathlon.events.utils.Units;

import java.util.ArrayList;
import java.util.List;

/*
 * Events class - representing List of all the sports in the Decathlon, along with details associated with each sport.
 */
public class Events {

    private List<Sport> sportsList = null;

    public final List<Sport> getSportsList() {
        sportsList = new ArrayList<>();
        sportsList.add(new Sport("100 m", SportsType.TRACK, SportsCategory.RUNNING, new Parameters(25.4347, 18, 1.81), Units.SECONDS));
        sportsList.add(new Sport("Long jump", SportsType.FIELD, SportsCategory.JUMPING, new Parameters(0.14354, 220, 1.4), Units.METRES));
        sportsList.add(new Sport("Shot put", SportsType.FIELD, SportsCategory.THROWING, new Parameters(51.39, 1.5, 1.05), Units.METRES));
        sportsList.add(new Sport("High jump", SportsType.FIELD, SportsCategory.JUMPING, new Parameters(0.8465, 75, 1.42), Units.METRES)); // not following formula
        sportsList.add(new Sport("400 m", SportsType.TRACK, SportsCategory.RUNNING, new Parameters(1.53775, 82, 1.81), Units.SECONDS));
        sportsList.add(new Sport("110 m hurdles", SportsType.TRACK, SportsCategory.RUNNING, new Parameters(5.74352, 28.5, 1.92), Units.SECONDS));
        sportsList.add(new Sport("Discus throw", SportsType.FIELD, SportsCategory.THROWING, new Parameters(12.91, 4, 1.1), Units.METRES));
        sportsList.add(new Sport("Pole vault", SportsType.FIELD, SportsCategory.JUMPING, new Parameters(0.2797, 100, 1.35), Units.METRES)); // not following formula
        sportsList.add(new Sport("Javelin throw", SportsType.FIELD, SportsCategory.THROWING, new Parameters(10.14, 7, 1.08), Units.METRES));
        sportsList.add(new Sport("1500 m", SportsType.TRACK, SportsCategory.RUNNING, new Parameters(0.03768, 480, 1.85), Units.MINUTESSECONDS));
        return sportsList;
    }

}


