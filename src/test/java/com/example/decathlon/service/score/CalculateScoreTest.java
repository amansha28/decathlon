package com.example.decathlon.service.score;

import com.example.decathlon.athlete.Athlete;
import com.example.decathlon.events.Sport;
import com.example.decathlon.events.utils.Parameters;
import com.example.decathlon.events.utils.SportsCategory;
import com.example.decathlon.events.utils.SportsType;
import com.example.decathlon.events.utils.Units;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CalculateScoreTest {

    @Test
    void calculateScoreBySportsType_WithEmptyInputListTest() {
        List<Athlete> list = new ArrayList<>();
        CalculateScore calculateScore = new CalculateScore();
        assertThrows(IllegalArgumentException.class, () -> calculateScore.calculateScoreBySportsType(list));
    }

    @Test
    void calculateScoreBySportsType_WithSingleAthleteTest() {
        List<Athlete> inputList = new ArrayList<>();
        Athlete athlete1 = new Athlete();
        athlete1.setName("John Cena");
        LinkedHashMap<String, Double> tempMap = new LinkedHashMap<>();
        tempMap.put("100 m", 12.61);
        tempMap.put("Long jump", 500.0);
        tempMap.put("Shot put", 9.22);
        tempMap.put("High jump", 150.0);
        tempMap.put("400 m", 60.39);
        tempMap.put("110 m hurdles", 16.43);
        tempMap.put("Discus throw", 21.60);
        tempMap.put("Pole vault", 260.0);
        tempMap.put("Javelin throw", 35.81);
        tempMap.put("1500 m", 325.72);
        athlete1.setEventPerformance(tempMap);
        inputList.add(athlete1);
        CalculateScore calculateScore = new CalculateScore();
        List<Athlete> resultList = calculateScore.calculateScoreBySportsType(inputList);
        assertEquals(4200, resultList.get(0).getTotalScore());
    }

    @Test
    void calculateAthleteScoreBasedOnPerformanceInSport_WithTrackSportsType() {
        CalculateScore calculateScore = new CalculateScore();
        Sport sport = new Sport("100 m", SportsType.TRACK, SportsCategory.RUNNING, new Parameters(25.4347, 18, 1.81), Units.SECONDS);
        LinkedHashMap<String, Double> athPerf = new LinkedHashMap<>();
        athPerf.put("100 m", 12.61);
        int actualValue = calculateScore.calculateAthleteScoreBasedOnPerformanceInSport(sport, athPerf);
        assertEquals(536, actualValue);
    }

    @Test
    void calculateAthleteScoreBasedOnPerformanceInSport_WithFieldSportsType() {
        CalculateScore calculateScore = new CalculateScore();
        Sport sport = new Sport("Long jump", SportsType.FIELD, SportsCategory.JUMPING, new Parameters(0.14354, 220, 1.4), Units.METRES);
        LinkedHashMap<String, Double> athPerf = new LinkedHashMap<>();
        athPerf.put("Long jump", 500.0);
        int actualValue = calculateScore.calculateAthleteScoreBasedOnPerformanceInSport(sport, athPerf);
        assertEquals(382, actualValue);
    }
}
