package com.example.decathlon.service;

import com.example.decathlon.athlete.Athlete;
import com.example.decathlon.athlete.Athletes;
import com.example.decathlon.events.Sport;
import com.example.decathlon.events.utils.Parameters;
import com.example.decathlon.events.utils.SportsCategory;
import com.example.decathlon.events.utils.SportsType;
import com.example.decathlon.events.utils.Units;
import com.example.decathlon.util.StdConversion;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PointsCalculatorServiceTest {

    @ParameterizedTest
    @ValueSource(strings = {"src/test/java/resources/results_1.csv", "src/test/java/resources/results_2.csv"})
    public void processFileToAthleteList_WithValidCSVFileTest(String inputString) throws IOException {
        File inputFile = new File(inputString);
        final String splitBy = ";";
        StdConversion stdConversion = Mockito.mock(StdConversion.class);
        PointsCalculatorService pointsCalculatorService = new PointsCalculatorService(stdConversion);
        List<Athlete> list = pointsCalculatorService.processFileToAthleteList(inputString, splitBy);
        assertEquals("John Smith", list.get(0).getName());
    }

    @ParameterizedTest
    @ValueSource(strings = {"src/test/java/resources/results_100.csv"})
    public void processFileToAthleteList_IncorrectLocationOfFileTest(String inputFilePath) {
        StdConversion stdConversion = Mockito.mock(StdConversion.class);
        PointsCalculatorService pointsCalculatorService = new PointsCalculatorService(stdConversion);
        assertThrows(IllegalArgumentException.class, () -> pointsCalculatorService.processFileToAthleteList(inputFilePath, ";"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"src/test/java/resources/results_2.csv"})
    public void processFileToAthleteList_IncorrectSplitByStringTest(String inputFilePath) {
        StdConversion stdConversion = Mockito.mock(StdConversion.class);
        PointsCalculatorService pointsCalculatorService = new PointsCalculatorService(stdConversion);
        assertThrows(IllegalArgumentException.class, () -> pointsCalculatorService.processFileToAthleteList(inputFilePath, ","));
    }

    @ParameterizedTest
    @ValueSource(strings = {"src/test/java/resources/results_1.csv"})
    public void processFileToAthleteList_ThrowParseExceptionTest(String inputFilePath) throws ParseException {
        File inputFile = new File(inputFilePath);
        final String splitBy = ";";

        StdConversion stdConversion = Mockito.mock(StdConversion.class);
        PointsCalculatorService pointsCalculatorService = new PointsCalculatorService(stdConversion);
        Mockito.when(stdConversion.getStartConvertedDateInSecs()).thenThrow(ParseException.class);
        assertThrows(RuntimeException.class, () -> pointsCalculatorService.processFileToAthleteList(inputFilePath, splitBy));
    }

    @ParameterizedTest
    @ValueSource(strings = {"src/test/java/resources/results_3.csv"})
    public void processFileToAthleteList_IncompleteInputFieldsInLineTest(String inputFilePath) {
        StdConversion stdConversion = Mockito.mock(StdConversion.class);
        PointsCalculatorService pointsCalculatorService = new PointsCalculatorService(stdConversion);
        assertThrows(RuntimeException.class, () -> pointsCalculatorService.processFileToAthleteList(inputFilePath, ";"));
    }

    @Test
    public void checkNoOfFieldsInPlayer_IncorrectNoOfFields() {
        StdConversion stdConversion = Mockito.mock(StdConversion.class);
        PointsCalculatorService pointsCalculatorService = new PointsCalculatorService(stdConversion);
        String[] player = {"Jaan Lepp", "4.84", "10.12", "1.50", "68.44", "19.18", "30.85", "2.80", "33.88", "6:22.75"};
        assertThrows(RuntimeException.class, () -> pointsCalculatorService.checkNoOfFieldsInPlayer(player));
    }

    @Test
    public void creatAthleteObjectFromInput_SingleAthlete() throws ParseException {
        StdConversion stdConversion = Mockito.mock(StdConversion.class);
        PointsCalculatorService pointsCalculatorService = new PointsCalculatorService(stdConversion);
        String[] player = {"John Smith", "12.61", "5.00", "9.22", "1.50", "60.39", "16.43", "21.60", "2.60", "35.81", "5:25.72"};
        Athlete athlete = new Athlete();
        pointsCalculatorService.creatAthleteObjectFromInput(athlete, player);
        assertEquals("John Smith", athlete.getName());
    }

    @Test
    public void convertFromMetresToCentiMForJumpingSports_WithEmptyInputListTest() {
        List<Athlete> list = new ArrayList<>();
        StdConversion stdConversion = Mockito.mock(StdConversion.class);
        PointsCalculatorService pointsCalculatorService = new PointsCalculatorService(stdConversion);
        assertThrows(IllegalArgumentException.class, () -> pointsCalculatorService.convertFromMetresToCentiMForJumpingSports(list));
    }

    @Test
    public void convertFromMetresToCentiMForJumpingSports_WithSingleAthleteTest() {
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

        StdConversion stdConversion = new StdConversion();
        PointsCalculatorService pointsCalculatorService = new PointsCalculatorService(stdConversion);
        List<Athlete> resultList = pointsCalculatorService.convertFromMetresToCentiMForJumpingSports(inputList);
        assertEquals(150.0, resultList.get(0).getEventPerformance().get("High jump"));
    }

    @Test
    public void convertUnitsBasedOnEventsType_WithSingleAthleteList() throws ParseException {
        StdConversion stdConversion = new StdConversion();
        PointsCalculatorService pointsCalculatorService = new PointsCalculatorService(stdConversion);
        String[] player = {"John Smith", "12.61", "5.00", "9.22", "1.50", "60.39", "16.43", "21.60", "2.60", "35.81", "5:25.72"};
        List<String> athletePerformance = Arrays.stream(player).skip(1).collect(Collectors.toList());
        LinkedHashMap<String, Double> resultMap = pointsCalculatorService.convertUnitsBasedOnEventsType(athletePerformance);
        assertEquals(325.72, resultMap.get("1500 m"));
    }

    @Test
    public void calculateScoreBySportsType_WithEmptyInputListTest() {
        List<Athlete> list = new ArrayList<>();
        StdConversion stdConversion = Mockito.mock(StdConversion.class);
        PointsCalculatorService pointsCalculatorService = new PointsCalculatorService(stdConversion);
        assertThrows(IllegalArgumentException.class, () -> pointsCalculatorService.calculateScoreBySportsType(list));
    }

    @Test
    public void calculateScoreBySportsType_WithSingleAthleteTest() {
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
        StdConversion stdConversion = Mockito.mock(StdConversion.class);
        PointsCalculatorService pointsCalculatorService = new PointsCalculatorService(stdConversion);
        List<Athlete> resultList = pointsCalculatorService.calculateScoreBySportsType(inputList);
        assertEquals(4200, resultList.get(0).getTotalScore());
    }


    @Test
    public void calculateAthleteScoreBasedOnPerformanceInSport_WithTrackSportsType() {
        StdConversion stdConversion = Mockito.mock(StdConversion.class);
        PointsCalculatorService pointsCalculatorService = new PointsCalculatorService(stdConversion);
        Sport sport = new Sport("100 m", SportsType.TRACK, SportsCategory.RUNNING, new Parameters(25.4347, 18, 1.81), Units.SECONDS);
        LinkedHashMap<String, Double> athPerf = new LinkedHashMap<>();
        athPerf.put("100 m", 12.61);
        int actualValue = pointsCalculatorService.calculateAthleteScoreBasedOnPerformanceInSport(sport, athPerf);
        assertEquals(536, actualValue);
    }

    @Test
    public void calculateAthleteScoreBasedOnPerformanceInSport_WithFieldSportsType() {
        StdConversion stdConversion = Mockito.mock(StdConversion.class);
        PointsCalculatorService pointsCalculatorService = new PointsCalculatorService(stdConversion);
        Sport sport = new Sport("Long jump", SportsType.FIELD, SportsCategory.JUMPING, new Parameters(0.14354, 220, 1.4), Units.METRES);
        LinkedHashMap<String, Double> athPerf = new LinkedHashMap<>();
        athPerf.put("Long jump", 500.0);
        int actualValue = pointsCalculatorService.calculateAthleteScoreBasedOnPerformanceInSport(sport, athPerf);
        assertEquals(382, actualValue);
    }

    @Test
    public void sortAthletesByTotalScoreAndSetRank_WithEmptyInputListTest() {

        List<Athlete> list = new ArrayList<>();
        StdConversion stdConversion = new StdConversion();
        PointsCalculatorService pointsCalculatorService = new PointsCalculatorService(stdConversion);
        assertThrows(IllegalArgumentException.class, () -> pointsCalculatorService.sortAthletesByTotalScoreAndSetRank(list));
    }

    @Test
    public void sortAthletesByTotalScoreAndSetRank_WithCorrectListOfAthletesTest() {
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
        athlete1.setTotalScore(4200);
        inputList.add(athlete1);

        Athlete athlete2 = new Athlete();
        athlete2.setName("Jane Doe");
        tempMap.clear();
        tempMap.put("100 m", 13.04);
        tempMap.put("Long jump", 453.0);
        tempMap.put("Shot put", 7.79);
        tempMap.put("High jump", 155.0);
        tempMap.put("400 m", 64.72);
        tempMap.put("110 m hurdles", 18.74);
        tempMap.put("Discus throw", 24.20);
        tempMap.put("Pole vault", 240.0);
        tempMap.put("Javelin throw", 28.20);
        tempMap.put("1500 m", 410.76);
        athlete2.setEventPerformance(tempMap);
        athlete2.setTotalScore(3199);
        inputList.add(athlete2);

        Athlete athlete3 = new Athlete();
        athlete3.setName("Aman Sharma");
        tempMap.clear();
        tempMap.put("100 m", 13.04);
        tempMap.put("Long jump", 453.0);
        tempMap.put("Shot put", 7.79);
        tempMap.put("High jump", 155.0);
        tempMap.put("400 m", 64.72);
        tempMap.put("110 m hurdles", 18.74);
        tempMap.put("Discus throw", 24.20);
        tempMap.put("Pole vault", 240.0);
        tempMap.put("Javelin throw", 28.20);
        tempMap.put("1500 m", 410.76);
        athlete3.setEventPerformance(tempMap);
        athlete3.setTotalScore(3199);
        inputList.add(athlete3);

        StdConversion stdConversion = Mockito.mock(StdConversion.class);
        PointsCalculatorService pointsCalculatorService = new PointsCalculatorService(stdConversion);
        List<Athlete> resultList = pointsCalculatorService.sortAthletesByTotalScoreAndSetRank(inputList);
        assertEquals("2-3", resultList.get(1).getRank());
    }

    @Test
    public void convertListOfAthletesToXMLFile_EmptyInputListTest() throws JAXBException {
        final String resultPath = "src/main/resources/result.xml";
        List<Athlete> list = new ArrayList<>();
        StdConversion stdConversion = Mockito.mock(StdConversion.class);
        PointsCalculatorService pointsCalculatorService = new PointsCalculatorService(stdConversion);
        assertThrows(IllegalArgumentException.class, () -> pointsCalculatorService.convertListOfAthletesToXMLFile(list, resultPath));
    }

    @Test
    public void convertListOfAthletesToXMLFile_EmptyResultPathTest() throws JAXBException {
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
        athlete1.setTotalScore(4200);
        athlete1.setRank("1");
        inputList.add(athlete1);

        StdConversion stdConversion = Mockito.mock(StdConversion.class);
        PointsCalculatorService pointsCalculatorService = new PointsCalculatorService(stdConversion);
        assertThrows(IllegalArgumentException.class, () -> pointsCalculatorService.convertListOfAthletesToXMLFile(inputList, null));
    }


    @Test
    public void convertListOfAthletesToXMLFile_MarshalUnmarshalTest() throws JAXBException {
        final String resultPath = "src/main/resources/result.xml";
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
        athlete1.setTotalScore(4200);
        athlete1.setRank("1");
        inputList.add(athlete1);

        Athlete athlete2 = new Athlete();
        athlete2.setName("Jane Doe");
        tempMap.clear();
        tempMap.put("100 m", 13.04);
        tempMap.put("Long jump", 453.0);
        tempMap.put("Shot put", 7.79);
        tempMap.put("High jump", 155.0);
        tempMap.put("400 m", 64.72);
        tempMap.put("110 m hurdles", 18.74);
        tempMap.put("Discus throw", 24.20);
        tempMap.put("Pole vault", 240.0);
        tempMap.put("Javelin throw", 28.20);
        tempMap.put("1500 m", 410.76);
        athlete2.setEventPerformance(tempMap);
        athlete2.setTotalScore(3199);
        athlete2.setRank("2-3");
        inputList.add(athlete2);

        Athlete athlete3 = new Athlete();
        athlete3.setName("Aman Sharma");
        tempMap.clear();
        tempMap.put("100 m", 13.04);
        tempMap.put("Long jump", 453.0);
        tempMap.put("Shot put", 7.79);
        tempMap.put("High jump", 155.0);
        tempMap.put("400 m", 64.72);
        tempMap.put("110 m hurdles", 18.74);
        tempMap.put("Discus throw", 24.20);
        tempMap.put("Pole vault", 240.0);
        tempMap.put("Javelin throw", 28.20);
        tempMap.put("1500 m", 410.76);
        athlete3.setEventPerformance(tempMap);
        athlete3.setTotalScore(3199);
        athlete3.setRank("2-3");
        inputList.add(athlete3);

        StdConversion stdConversion = Mockito.mock(StdConversion.class);
        PointsCalculatorService pointsCalculatorService = new PointsCalculatorService(stdConversion);
        pointsCalculatorService.convertListOfAthletesToXMLFile(inputList, resultPath);
        JAXBContext jaxbContext = JAXBContext.newInstance(Athletes.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        File xmlToListFile = new File(resultPath);
        Athletes outputAthletesList = (Athletes) unmarshaller.unmarshal(xmlToListFile);

        for (int i = 0; i < inputList.size(); i++) {
            assertEquals(inputList.get(i).getName(), outputAthletesList.getList().get(i).getName());
            assertEquals(inputList.get(i).getEventPerformance(), outputAthletesList.getList().get(i).getEventPerformance());
            assertEquals(inputList.get(i).getTotalScore(), outputAthletesList.getList().get(i).getTotalScore());
            assertEquals(inputList.get(i).getRank(), outputAthletesList.getList().get(i).getRank());
        }
    }


}
