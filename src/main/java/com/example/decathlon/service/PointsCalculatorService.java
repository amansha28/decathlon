package com.example.decathlon.service;

import com.example.decathlon.DecathlonApplication;
import com.example.decathlon.athlete.Athlete;
import com.example.decathlon.athlete.Athletes;
import com.example.decathlon.events.Sport;
import com.example.decathlon.events.utils.SportsCategory;
import com.example.decathlon.events.utils.SportsType;
import com.example.decathlon.util.StdConversion;

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

public class PointsCalculatorService {
    public static final Logger logger = Logger.getLogger(PointsCalculatorService.class.getName());

    StdConversion stdConversion;

    public PointsCalculatorService(StdConversion stdConversion) {
        this.stdConversion = stdConversion;
    }

    public List<Athlete> processFileToAthleteList(File file, String splitBy) throws IOException {
        logger.info("==== Starting PointsCalculatorService : PointsCalculatorService ====");
        List<Athlete> list = new ArrayList<>();
        String line = null;

        try (FileInputStream fileInputStream = new FileInputStream(file);
             InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, StandardCharsets.UTF_8);
             BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {
            long startConvertedDateInSecs = stdConversion.getStartConvertedDateInSecs();
            // Step 2.1 : Read 1 line of file at a time containing details of each athlete + performance in Decathlon.
            // Read each line from file, convert to athlete obj and store a List of athletes.
            while ((line = bufferedReader.readLine()) != null && line.length() > 0) {
                Athlete athlete = new Athlete();
                String[] player = line.split(splitBy);

                // Step 2.1.1 : Read and set the name of each player.
                Optional<String> athleteName = Arrays.stream(player).findFirst();
                int count = 0;
                if (athleteName.isPresent()) {
                    athlete.setName(athleteName.get());

                    // Step 2.1.2 : Skip the first field (player name) of each line and read rest of his/her performance
                    List<String> collect = Arrays.stream(player).skip(1).collect(Collectors.toList());
                    LinkedHashMap<String, Double> tempMap = new LinkedHashMap<>();
                    for (int i = 0; i < collect.size(); i++) {

                        // Step 2.1.3 : Special care to read and convert time of 1500m race. For rest, read as it is.
                        if (collect.get(i) != null && !collect.get(i).isEmpty() && "1500 m".equals(DecathlonApplication.events.getSportsList().get(count).getName())) {
                            DateFormat dateFormat = new SimpleDateFormat("mm:ss.SSS");

                            // Note : concatenated 0 at end (=multiplied by 10, to convert SS -> SSS format) for last part of timestamp.
                            Date convertedDate = dateFormat.parse(collect.get(count) + "0");

                            //logger.info("time : " + convertedDate + " : timestamp : " + (double) (startConvertedDateInSecs - Math.abs(convertedDate.getTime())) / 1000 + " : abs value : " + (startConvertedDateInSecs - Math.abs(convertedDate.getTime())));
                            tempMap.put(DecathlonApplication.events.getSportsList().get(count).getName(), (double) (startConvertedDateInSecs - Math.abs(convertedDate.getTime())) / 1000);
                        } else
                            tempMap.put(DecathlonApplication.events.getSportsList().get(count).getName(), Double.valueOf(collect.get(i)));

                        count++;
                    }
                    athlete.setEventPerformance(tempMap);
                }
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

    // For Jumping related sports, convert metres to centimeters (before passing for calculations of points)
    public List<Athlete> convertFromMetresToCentiMForJumpingSports(List<Athlete> list) {
        logger.info("==== Starting PointsCalculatorService : convertFromMetresToCentiMForJumpingSports ====");
        for (Athlete ath : list) {
            LinkedHashMap<String, Double> athPerf = ath.getEventPerformance();
            for (int i = 0; i < athPerf.size(); i++) {
                if (SportsCategory.JUMPING.equals(DecathlonApplication.events.getSportsList().get(i).getCategory())) {
//                    athPerf.put(events.getSportsList().get(i).getName(), Math.round(athPerf.get(events.getSportsList().get(i).getName()) * 100.0*100.0)/100.0);
                    athPerf.put(DecathlonApplication.events.getSportsList().get(i).getName(), (athPerf.get(DecathlonApplication.events.getSportsList().get(i).getName()) * 100.00));
                }
                System.out.println("Sport Name : " + DecathlonApplication.events.getSportsList().get(i).getName() + " - Category : " + DecathlonApplication.events.getSportsList().get(i).getCategory() + " - Performance :" + athPerf.get(DecathlonApplication.events.getSportsList().get(i).getName()));
            }

        }
        logger.info("==== Ending PointsCalculatorService : convertFromMetresToCentiMForJumpingSports ====");
        return list;
    }

    // Calculate the score of each athlete in each sport based category of sport : TRACK or FIELD.
    public List<Athlete> calculateScoreBySportsType(List<Athlete> list) {
        logger.info("==== Starting PointsCalculatorService : calculateScoreBySportsType ====");
        for (Athlete ath : list) {
            LinkedHashMap<String, Double> athPerf = ath.getEventPerformance();
            for (int i = 0; i < athPerf.size(); i++) {
                int tempScore = 0;
                Sport currSport = DecathlonApplication.events.getSportsList().get(i);
                if (SportsType.TRACK.equals(currSport.getType())) {
                    tempScore = (int) (currSport.getParameters().getA() * (Math.pow((currSport.getParameters().getB() - athPerf.get(currSport.getName())), currSport.getParameters().getC())));
                } else {
                    // just else and not else-if as we have just 2 types of events : TRACK and FIELD.
                    tempScore = (int) (currSport.getParameters().getA() * (Math.pow((athPerf.get(currSport.getName()) - currSport.getParameters().getB()), currSport.getParameters().getC())));
                }

                ath.setTotalScore(ath.getTotalScore() + tempScore);
            }
        }
        logger.info("==== Ending PointsCalculatorService : calculateScoreBySportsType ====");
        return list;
    }


    // Compare & sort Athletes in descending order based on their totalScore
    // + calculate and set rank for each athlete
    public List<Athlete> sortAthletesByTotalScoreAndSetRank(List<Athlete> list) {
        logger.info("==== Starting PointsCalculatorService : sortAthletesByTotalScoreAndSetRank ====");
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

    public void convertListOfAthletesToXMLFile(List<Athlete> list, String resultPath) {
        logger.info("==== Starting PointsCalculatorService : convertListOfAthletesToXMLFile ====");
        Athletes xmlOutputList = new Athletes();
        try {
            xmlOutputList.setList(list);
            JAXBContext jaxbContext = JAXBContext.newInstance(Athletes.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            File resultFile = new File(resultPath);
            marshaller.marshal(xmlOutputList, resultFile);
        } catch (JAXBException e) {
            logger.log(Level.SEVERE, "JAXBException Occurred : " + e.getMessage());
            e.printStackTrace();
        }

        logger.info("==== Ending PointsCalculatorService : convertListOfAthletesToXMLFile ====");
    }
}
