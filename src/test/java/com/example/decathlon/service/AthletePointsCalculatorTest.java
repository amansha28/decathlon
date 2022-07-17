package com.example.decathlon.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;

public class AthletePointsCalculatorTest {

    @Test
    public void readAthletesFromFileAndCalculateScore_AllInternalMethodInvocationTest() throws JAXBException, ParseException, IOException {

        PointsCalculatorService pointsCalculatorService = Mockito.mock(PointsCalculatorService.class);
        AthletePointsCalculator calculator = new AthletePointsCalculator(pointsCalculatorService);
        calculator.readAthletesFromFileAndCalculateScore(new String("inputFilePath"), new String("splitBy"), new String("resultPath"));

        Mockito.verify(pointsCalculatorService).processFileToAthleteList(Mockito.any(File.class), Mockito.anyString());
        Mockito.verify(pointsCalculatorService).convertFromMetresToCentiMForJumpingSports(Mockito.anyList());
        Mockito.verify(pointsCalculatorService).calculateScoreBySportsType(Mockito.anyList());
        Mockito.verify(pointsCalculatorService).sortAthletesByTotalScoreAndSetRank(Mockito.anyList());
        Mockito.verify(pointsCalculatorService).convertListOfAthletesToXMLFile(Mockito.anyList(), Mockito.anyString());
    }
}
