package com.example.decathlon;

import com.example.decathlon.athlete.Athlete;
import com.example.decathlon.events.Events;
import com.example.decathlon.service.AthletePointsCalculator;
import com.example.decathlon.service.PointsCalculatorService;
import com.example.decathlon.util.StdConversion;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class DecathlonApplication {
    public static final Logger logger = Logger.getLogger(DecathlonApplication.class.getName());
    public static final Events events = new Events();
    static final String PATH = "src/main/resources/results_1.csv";
    static final String RESULT_PATH = "src/main/resources/result.xml";
    static final String SPLIT_BY = ";";
    public static List<Athlete> list = new ArrayList<>();

    public static void main(String[] args) throws ParseException, IOException, JAXBException {

        logger.info("==== Starting DecathlonApplication : main ====");
        StdConversion stdConversion = new StdConversion();
        PointsCalculatorService service = new PointsCalculatorService(stdConversion);
        AthletePointsCalculator athletePointsCalculator = new AthletePointsCalculator(service);

        athletePointsCalculator.readAthletesFromFileAndCalculateScore(PATH, SPLIT_BY, RESULT_PATH);

        logger.info("==== Ending DecathlonApplication : main ====");
    }

}
