package com.example.decathlon.service.rank;

import com.example.decathlon.athlete.Athlete;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


class CalculateRankTest {

    @Test
    void sortAthleteBasedOnTotalScore_With2AthletesList() {
        CalculateRank calculateRank = new CalculateRank();
        List<Athlete> list = new ArrayList<>();
        Athlete athlete1 = new Athlete();
        athlete1.setName("John Cena");
        athlete1.setTotalScore(4200);
        list.add(athlete1);
        Athlete athlete2 = new Athlete();
        athlete2.setName("Jane Doe");
        athlete2.setTotalScore(4900);
        list.add(athlete2);

        int expectedResult = 4900;
        int actualResult = calculateRank.sortAthleteBasedOnTotalScore(list).get(0).getTotalScore();
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void calculateAndSetRankOfAthlete_WithAllDistinctScores() {
        CalculateRank calculateRank = new CalculateRank();
        List<Athlete> list = new ArrayList<>();
        Athlete athlete1 = new Athlete();
        athlete1.setName("John Cena");
        athlete1.setTotalScore(4200);
        list.add(athlete1);
        Athlete athlete2 = new Athlete();
        athlete2.setName("Jane Doe");
        athlete2.setTotalScore(4900);
        list.add(athlete2);

        String expectedResult = "1";
        String actualResult = calculateRank.calculateAndSetRankOfAthlete(list).get(0).getRank();
        assertEquals(expectedResult, actualResult);
        assertEquals("John Cena", calculateRank.calculateAndSetRankOfAthlete(list).get(0).getName());
    }
}
