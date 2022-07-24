package com.example.decathlon;

import com.example.decathlon.athlete.Athlete;
import com.example.decathlon.reader.Reader;
import com.example.decathlon.reader.ReaderFactory;
import com.example.decathlon.reader.ReaderType;
import com.example.decathlon.service.AthleteService;
import com.example.decathlon.service.dataprep.DataPreparation;
import com.example.decathlon.service.rank.CalculateRank;
import com.example.decathlon.service.score.CalculateScore;
import com.example.decathlon.writer.Writer;
import com.example.decathlon.writer.WriterFactory;
import com.example.decathlon.writer.WriterType;

import javax.xml.bind.JAXBException;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

public class DecathlonApplication {
    public static final Logger logger = Logger.getLogger(DecathlonApplication.class.getName());

    static final String PATH = "src/main/resources/results_1.csv";
    static final String RESULT_PATH = "src/main/resources/result.xml";

    static final String SPLIT_BY = ";";

    public static void main(String[] args) throws JAXBException {

        logger.info("==== Starting DecathlonApplication : main ====");

        // Phase 1 : Read the input containing Athletes performance in Decathlon.
        ReaderFactory inputFactory = new ReaderFactory();
        Reader csvFileReader = inputFactory.getReader(ReaderType.CSV.toString());
        HashMap<String, String> inputParamMap = new HashMap<>();
        inputParamMap.put("location", PATH);
        inputParamMap.put("splitBy", SPLIT_BY);
        List<Athlete> list = (List<Athlete>) csvFileReader.read(inputParamMap);

        DataPreparation dataPreparation = new DataPreparation();
        CalculateScore calculateScore = new CalculateScore();
        CalculateRank calculateRank = new CalculateRank();
        // Phase 2 : Process the list of Athletes
        AthleteService athleteService = new AthleteService(dataPreparation, calculateScore, calculateRank);
        athleteService.processAthleteList(list);

        // Phase 3 : Convert the List of Athletes to output file.
        WriterFactory outputFactory = new WriterFactory();
        Writer xmlFileWriter = outputFactory.getWriter(WriterType.XML.toString());
        HashMap<String, Object> outputParamMap = new HashMap<>();
        outputParamMap.put("playerList", list);
        outputParamMap.put("resulPath", RESULT_PATH);
        xmlFileWriter.write(outputParamMap);

        logger.info("==== Ending DecathlonApplication : main ====");
    }

}
