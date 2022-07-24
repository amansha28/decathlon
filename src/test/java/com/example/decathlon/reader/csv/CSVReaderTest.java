package com.example.decathlon.reader.csv;

import com.example.decathlon.athlete.Athlete;
import com.example.decathlon.reader.Reader;
import com.example.decathlon.reader.ReaderFactory;
import com.example.decathlon.reader.ReaderType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.HashMap;
import java.util.List;

import static com.example.decathlon.reader.CSVReaderInputParams.SPLIT_BY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CSVReaderTest {

    @ParameterizedTest
    @ValueSource(strings = {"src/test/java/resources/results_1.csv", "src/test/java/resources/results_2.csv"})
    void processFileToAthleteList_WithValidCSVFileTest(String inputString) {
        final String splitBy = ";";
        ReaderFactory inputFactory = new ReaderFactory();
        Reader csvFileReader = inputFactory.getReader(ReaderType.CSV.toString());
        HashMap<String, String> inputParamMap = new HashMap<>();
        inputParamMap.put("location", inputString);
        inputParamMap.put("splitBy", splitBy);
        List<Athlete> list = (List<Athlete>) csvFileReader.read(inputParamMap);
        String expectedValue = "John Smith";
        String actualValue = list.get(0).getName();
        assertEquals(expectedValue, actualValue);
    }

    @ParameterizedTest
    @ValueSource(strings = {"src/test/java/resources/results_100.csv"})
    void processFileToAthleteList_IncorrectLocationOfFileTest(String inputFilePath) {
        final String splitBy = ";";
        ReaderFactory inputFactory = new ReaderFactory();
        Reader csvFileReader = inputFactory.getReader(ReaderType.CSV.toString());
        HashMap<String, String> inputParamMap = new HashMap<>();
        inputParamMap.put("location", inputFilePath);
        inputParamMap.put("splitBy", splitBy);
        assertThrows(IllegalArgumentException.class, () -> csvFileReader.read(inputParamMap));
    }

    @ParameterizedTest
    @ValueSource(strings = {"src/test/java/resources/results_2.csv"})
    void processFileToAthleteList_IncorrectSplitByStringTest(String inputFilePath) {
        ReaderFactory inputFactory = new ReaderFactory();
        Reader csvFileReader = inputFactory.getReader(ReaderType.CSV.toString());
        HashMap<String, String> inputParamMap = new HashMap<>();
        inputParamMap.put("location", inputFilePath);
        inputParamMap.put("splitBy", SPLIT_BY);
        assertThrows(IllegalArgumentException.class, () -> csvFileReader.read(inputParamMap));
    }

    //    @ParameterizedTest
//    @ValueSource(strings = {"src/test/java/resources/results_1.csv"})
//    public void processFileToAthleteList_ThrowParseExceptionTest(String inputFilePath) throws ParseException {
//        File inputFile = new File(inputFilePath);
//        final String splitBy = ";";
//
//        StdConversion stdConversion = Mockito.mock(StdConversion.class);
//        PointsCalculatorService pointsCalculatorService = new PointsCalculatorService(stdConversion);
//        Mockito.when(stdConversion.getStartConvertedDateInSecs()).thenThrow(ParseException.class);
//        assertThrows(RuntimeException.class, () -> pointsCalculatorService.processFileToAthleteList(inputFilePath, splitBy));
//    }
//
    @ParameterizedTest
    @ValueSource(strings = {"src/test/java/resources/results_3.csv"})
    void processFileToAthleteList_IncompleteInputFieldsInLineTest(String inputFilePath) {

        final String splitBy = ";";
        ReaderFactory inputFactory = new ReaderFactory();
        Reader csvFileReader = inputFactory.getReader(ReaderType.CSV.toString());
        HashMap<String, String> inputParamMap = new HashMap<>();
        inputParamMap.put("location", inputFilePath);
        inputParamMap.put("splitBy", splitBy);
        assertThrows(RuntimeException.class, () -> csvFileReader.read(inputParamMap));
    }

}
