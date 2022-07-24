package com.example.decathlon.service;

import com.example.decathlon.athlete.Athlete;
import com.example.decathlon.service.dataprep.DataPreparation;
import com.example.decathlon.service.rank.CalculateRank;
import com.example.decathlon.service.score.CalculateScore;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

class AthleteServiceTest {

    @Test
    void processAthleteList_Test() {
        DataPreparation dataPreparation = Mockito.mock(DataPreparation.class);
        CalculateScore calculateScore = Mockito.mock(CalculateScore.class);
        CalculateRank calculateRank = Mockito.mock(CalculateRank.class);
        AthleteService athleteService = new AthleteService(dataPreparation, calculateScore, calculateRank);
        List<Athlete> list = new ArrayList<>();
        Athlete athlete = new Athlete();
        athlete.setName("John Cena");
        LinkedHashMap<String, Double> tempMap = new LinkedHashMap<>();
        tempMap.put("100 m", 12.61);
        tempMap.put("Long jump", 5.00);
        tempMap.put("Shot put", 9.22);
        tempMap.put("High jump", 1.50);
        tempMap.put("400 m", 60.39);
        tempMap.put("110 m hurdles", 16.43);
        tempMap.put("Discus throw", 21.60);
        tempMap.put("Pole vault", 2.60);
        tempMap.put("Javelin throw", 35.81);
        tempMap.put("1500 m", 325.72);
        athlete.setEventPerformance(tempMap);
        list.add(athlete);
        athleteService.processAthleteList(list);
        Mockito.verify(dataPreparation).convertFromMetresToCentiMForJumpingSports(list);
        Mockito.verify(calculateScore).calculateScoreBySportsType(list);
        Mockito.verify(calculateRank).sortAthleteBasedOnTotalScore(list);
        Mockito.verify(calculateRank).calculateAndSetRankOfAthlete(list);
    }
}
