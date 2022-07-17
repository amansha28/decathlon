package com.example.decathlon.service;

import com.example.decathlon.DecathlonApplication;
import com.example.decathlon.athlete.Athlete;
import com.example.decathlon.athlete.Athletes;
import com.example.decathlon.events.Sport;
import com.example.decathlon.events.utils.SportsCategory;
import com.example.decathlon.events.utils.SportsType;
import com.example.decathlon.events.utils.Units;
import com.example.decathlon.util.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;


/*
 * Simple Class containing all the business logic, segregated into separate methods.
 */
public class PointsCalculatorService {
    public static final Logger logger = Logger.getLogger(PointsCalculatorService.class.getName());

    StdConversion stdConversion;
    Validator pathValidator = new PathValidator();
    Validator splitByValidator = new SplitByValidator();
    Validator fileValidator = new FileValidator();
    Validator listValidator = new ListValidator();
    public PointsCalculatorService(StdConversion stdConversion) {
        this.stdConversion = stdConversion;
    }

    /*
     * Read the given file and create a list of Athletes out of it.
     */
    public List<Athlete> processFileToAthleteList(String inputPathLocation, String splitBy) {
        logger.info("==== Starting PointsCalculatorService : PointsCalculatorService ====");

        pathValidator.validate(inputPathLocation);
        splitByValidator.validate(splitBy);
        fileValidator.validate(inputPathLocation);

        File file = new File(inputPathLocation);
        List<Athlete> list = new ArrayList<>();
        String line = null;

        try (FileInputStream fileInputStream = new FileInputStream(file);
             InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, StandardCharsets.UTF_8);
             BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {
            // Step 1.1 : Read each line of input file at a time containing details of each athlete + performance in Decathlon.
            // Read each line from file, convert to athlete obj and store it into a List of athletes.
            while ((line = bufferedReader.readLine()) != null && line.length() > 0) {
                Athlete athlete = new Athlete();
                String[] player = line.split(splitBy);
                checkNoOfFieldsInPlayer(player);
                creatAthleteObjectFromInput(athlete, player);
                list.add(athlete);
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "IOException occurred while reading in the input file: " + e.getMessage());
            throw new RuntimeException(e);
        } catch (ParseException e) {
            logger.log(Level.SEVERE, "ParseException occurred while parsing in the time - Throwing RuntimeException: " + e.getMessage());
            throw new RuntimeException(e);
        }
        logger.info("==== Ending PointsCalculatorService : PointsCalculatorService ====");
        return list;
    }


    /*
    * Check No. of fields which are read from each Line from the input File.
    * */
    public void checkNoOfFieldsInPlayer(String[] player) {
        // Each line of the input file should contain = Athlete Name + performances in 10 sports of Decathlon event.
        if (player.length != (DecathlonApplication.events.getSportsList().size() + 1)) {
            logger.log(Level.SEVERE, "No. of fields does not match required : Throwing RuntimeException");
            throw new RuntimeException("Number off fields in the input file does not match the required number");
        }
    }

    /*
    * Create Athlete Object from input Line read from File.
    * */
    public void creatAthleteObjectFromInput(Athlete athlete, String[] player) throws ParseException {
        // Read and set the name of each player.
        Optional<String> athleteName = Arrays.stream(player).findFirst();

        if (athleteName.isPresent()) {
            athlete.setName(athleteName.get());
            // Skip the first field (player name) of each line and read rest of his/her performance.
            List<String> athletePerformance = Arrays.stream(player).skip(1).collect(Collectors.toList());
            LinkedHashMap<String, Double> tempMap = convertUnitsBasedOnEventsType(athletePerformance);
            athlete.setEventPerformance(tempMap);
        }
    }


    /*
    * Based on type of Event, convert the units in which its measured to required type for Score calculations.
    * */
    public LinkedHashMap<String, Double> convertUnitsBasedOnEventsType(List<String> athletePerformance) throws ParseException {
        long startConvertedDateInSecs = stdConversion.getStartConvertedDateInSecs();
        LinkedHashMap<String, Double> tempMap = new LinkedHashMap<>();
        int count = 0;
        for (int i = 0; i < athletePerformance.size(); i++) {
            // Step 1.1.3 : Special care to read and convert time of events which has units as MINUTESSECONDS ( like 1500m race ) .
            // For rest of events, read as it is.
            if (athletePerformance.get(i) != null && !athletePerformance.get(i).isEmpty() && Units.MINUTESSECONDS.equals(DecathlonApplication.events.getSportsList().get(count).getUnit())) {
                DateFormat dateFormat = new SimpleDateFormat("mm:ss.SSS");

                // Note : concatenated 0 at end (=multiplied by 10, to convert SS -> SSS format) for last part of timestamp.
                Date convertedDate = dateFormat.parse(athletePerformance.get(count) + "0");

                tempMap.put(DecathlonApplication.events.getSportsList().get(count).getName(), (double) (startConvertedDateInSecs - Math.abs(convertedDate.getTime())) / 1000);
            } else
                tempMap.put(DecathlonApplication.events.getSportsList().get(count).getName(), Double.valueOf(athletePerformance.get(i)));

            count++;
        }
        return tempMap;
    }

    /*
     * For Jumping related sports, convert metres to centimeters (before passing for calculations of points)
     */
    public List<Athlete> convertFromMetresToCentiMForJumpingSports(List<Athlete> list) {
        logger.info("==== Starting PointsCalculatorService : convertFromMetresToCentiMForJumpingSports ====");
        listValidator.validate(list);

        for (Athlete ath : list) {
            LinkedHashMap<String, Double> athPerf = ath.getEventPerformance();
            for (int i = 0; i < athPerf.size(); i++) {
                if (SportsCategory.JUMPING.equals(DecathlonApplication.events.getSportsList().get(i).getCategory())) {
                    // rounding off the number to 2 digits after decimal
                    athPerf.put(DecathlonApplication.events.getSportsList().get(i).getName(), Math.round(athPerf.get(DecathlonApplication.events.getSportsList().get(i).getName()) * 100.0 * 100.0) / 100.0);
                    // this is for more accurate calculation but gives deviated numbers for sports under JUMPING category. like 239.999999999 instead of 240.0
                    //athPerf.put(DecathlonApplication.events.getSportsList().get(i).getName(), (athPerf.get(DecathlonApplication.events.getSportsList().get(i).getName()) * 100.00));
                }
                logger.info("Sport Name : " + DecathlonApplication.events.getSportsList().get(i).getName() + " - Category : " + DecathlonApplication.events.getSportsList().get(i).getCategory() + " - Performance :" + athPerf.get(DecathlonApplication.events.getSportsList().get(i).getName()));
            }
        }
        logger.info("==== Ending PointsCalculatorService : convertFromMetresToCentiMForJumpingSports ====");
        return list;
    }


    /**
     * Calculate the score of each athlete in each sport based category of sport : TRACK or FIELD.
     * Set it back to the respective athlete.
     */
    public List<Athlete> calculateScoreBySportsType(List<Athlete> list) {
        logger.info("==== Starting PointsCalculatorService : calculateScoreBySportsType ====");
        listValidator.validate(list);

        for (Athlete ath : list) {
            LinkedHashMap<String, Double> athPerf = ath.getEventPerformance();
            for (int i = 0; i < athPerf.size(); i++) {
                Sport currSport = DecathlonApplication.events.getSportsList().get(i);
                int tempScore = calculateAthleteScoreBasedOnPerformanceInSport(currSport, athPerf);
                ath.setTotalScore(ath.getTotalScore() + tempScore);
            }
        }
        logger.info("==== Ending PointsCalculatorService : calculateScoreBySportsType ====");
        return list;
    }


    public int calculateAthleteScoreBasedOnPerformanceInSport(Sport currSport, LinkedHashMap<String, Double> athPerf) {
        int tempScore = 0;
        // Since there are only 2 categories we have created a separate method.
        // Otherwise, we can create Interface of Event type with 1 calculateScore(B,P,C) and its concrete classes implementing own version
        // Based on Sport Type, call the concrete class method.
        if (SportsType.TRACK.equals(currSport.getType())) {
            tempScore = (int) (currSport.getParameters().getA() * (Math.pow((currSport.getParameters().getB() - athPerf.get(currSport.getName())), currSport.getParameters().getC())));
        } else {
            tempScore = (int) (currSport.getParameters().getA() * (Math.pow((athPerf.get(currSport.getName()) - currSport.getParameters().getB()), currSport.getParameters().getC())));
        }

        return tempScore;
    }


    /*
     * Compare & sort Athletes in descending order based on their totalScore
     * And calculate and set rank for each athlete
     */
    public List<Athlete> sortAthletesByTotalScoreAndSetRank(List<Athlete> list) {
        logger.info("==== Starting PointsCalculatorService : sortAthletesByTotalScoreAndSetRank ====");
        listValidator.validate(list);
        Collections.sort(list, Comparator.comparing(Athlete::getTotalScore).reversed());

        // 2-counter approach to calculate rank of each athlete
        for (int start = 0, end = 1; end <= list.size(); end++) {
            if (end == list.size() || list.get(end).getTotalScore() != list.get(start).getTotalScore()) {
                StringBuffer rank = new StringBuffer(String.valueOf(start + 1));
                if (start + 1 == end) {
                    rank.append("");
                } else {
                    int temp = start + 2;
                    while (temp <= end) {
                        rank.append("-" + temp);
                        temp++;
                    }
                }
                while (start < end) list.get(start++).setRank(rank.toString());
            }
        }
        logger.info("==== Ending PointsCalculatorService : sortAthletesByTotalScoreAndSetRank ====");
        return list;
    }

    /*
     * Convert a List of Athletes to XML format and write it into a file at given path.
     */
    public void convertListOfAthletesToXMLFile(List<Athlete> list, String resultPath) throws JAXBException {
        logger.info("==== Starting PointsCalculatorService : convertListOfAthletesToXMLFile ====");

        listValidator.validate(list);
        pathValidator.validate(resultPath);

        Athletes xmlOutputList = new Athletes();
        File resultFile = new File(resultPath);

        xmlOutputList.setList(list);
        JAXBContext jaxbContext = JAXBContext.newInstance(Athletes.class);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(xmlOutputList, resultFile);

        logger.info("==== Ending PointsCalculatorService : convertListOfAthletesToXMLFile ====");
    }
}
