package com.example.decathlon.service;

import com.example.decathlon.athlete.Athlete;
import com.example.decathlon.events.utils.SportsName;
import com.example.decathlon.service.dataprep.DataPreparation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

class AthleteBuilderTest {

    @Test
    void creatAthleteObjectFromInput_SingleAthleteTest() throws ParseException {
        String[] player = {"John Smith", "12.61", "5.00", "9.22", "1.50", "60.39", "16.43", "21.60", "2.60", "35.81", "5:25.72"};
        AthleteBuilder athleteBuilder = new AthleteBuilder();
        Athlete athlete = athleteBuilder.creatAthleteObjectFromInput(player);
        String expectedValue = "John Smith";
        String actualValue = athlete.getName();
        Assertions.assertEquals(expectedValue, actualValue);
    }

    @Test
    void convertUnitsBasedOnEventsType_WithSingleAthleteListTest() throws ParseException {
        String[] player = {"John Smith", "12.61", "5.00", "9.22", "1.50", "60.39", "16.43", "21.60", "2.60", "35.81", "5:25.72"};
        List<String> athletePerformance = Arrays.stream(player).skip(1).collect(Collectors.toList());
        DataPreparation dataPreparation = new DataPreparation();
        LinkedHashMap<String, Double> resultMap = (LinkedHashMap<String, Double>) dataPreparation.convertUnitsBasedOnEventsType(athletePerformance);
        Double expectedValue = 325.72;
        Double actualValue = resultMap.get(SportsName.RACE_1500_M);
        Assertions.assertEquals(expectedValue, actualValue);
    }

    @Test
    void convertUnitsBasedOnEventsType_WithEmptyListTest() throws ParseException {
        List<String> emptyList = new ArrayList<>();
        DataPreparation dataPreparation = new DataPreparation();
        LinkedHashMap<String, Double> resultMap = (LinkedHashMap<String, Double>) dataPreparation.convertUnitsBasedOnEventsType(emptyList);
        int expectedValue = 0;
        int actualValue = resultMap.size();
        Assertions.assertEquals(expectedValue, actualValue);
    }
}
