package com.example.decathlon.writer;

import com.example.decathlon.athlete.Athlete;
import com.example.decathlon.athlete.Athletes;
import org.junit.jupiter.api.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class XMLFileWriterTest {

    @Test
    void xmlFileWrite_EmptyInputListTest() throws JAXBException {
        final String resultPath = "src/main/resources/result.xml";
        List<Athlete> emptyList = new ArrayList<>();
        WriterFactory outputFactory = new WriterFactory();
        Writer xmlFileWriter = outputFactory.getWriter(WriterType.XML.toString());
        HashMap<String, Object> outputParamMap = new HashMap<>();
        outputParamMap.put("playerList", emptyList);
        outputParamMap.put("resulPath", resultPath);
        assertThrows(IllegalArgumentException.class, () -> xmlFileWriter.write(outputParamMap));
    }


    @Test
    void xmlFileWrite_EmptyResultPathTest() throws JAXBException {
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

        WriterFactory factory = new WriterFactory();
        Writer xmlFileWriter = factory.getWriter(WriterType.XML.toString());
        HashMap<String, Object> outputParamMap = new HashMap<>();
        outputParamMap.put("playerList", inputList);
        outputParamMap.put("resulPath", "");
        assertThrows(IllegalArgumentException.class, () -> xmlFileWriter.write(outputParamMap));
    }


    @Test
    void xmlFileWrite_MarshalUnmarshalTest() throws JAXBException {
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

        WriterFactory factory = new WriterFactory();
        Writer xmlFileWriter = factory.getWriter(WriterType.XML.toString());
        HashMap<String, Object> outputParamMap = new HashMap<>();
        outputParamMap.put("playerList", inputList);
        outputParamMap.put("resulPath", resultPath);

        xmlFileWriter.write(outputParamMap);
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
