package com.example.decathlon.service.dataprep;

import com.example.decathlon.athlete.Athlete;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DataPrepTest {

    @Test
    void convertFromMetresToCentiMForJumpingSports_WithEmptyInputListTest() {
        List<Athlete> list = new ArrayList<>();
//        StdConversion stdConversion = Mockito.mock(StdConversion.class);
//        PointsCalculatorService pointsCalculatorService = new PointsCalculatorService(stdConversion);
        DataPreparation dataPreparation = new DataPreparation();
        assertThrows(IllegalArgumentException.class, () -> dataPreparation.convertFromMetresToCentiMForJumpingSports(list));
    }

    @Test
    void convertFromMetresToCentiMForJumpingSports_WithSingleAthleteTest() {
        List<Athlete> inputList = new ArrayList<>();
        Athlete athlete1 = new Athlete();
        athlete1.setName("John Cena");
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
        athlete1.setEventPerformance(tempMap);
        inputList.add(athlete1);

//        StdConversion stdConversion = new StdConversion();
//        PointsCalculatorService pointsCalculatorService = new PointsCalculatorService(stdConversion);
        DataPreparation dataPreparation = new DataPreparation();
        List<Athlete> resultList = dataPreparation.convertFromMetresToCentiMForJumpingSports(inputList);
        assertEquals(150.0, resultList.get(0).getEventPerformance().get("High jump"));
    }

}
