package com.example.decathlon.reader;

import com.example.decathlon.athlete.Athlete;
import com.example.decathlon.service.AthleteBuilder;
import com.example.decathlon.util.validator.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * Implementation of Reader for Reading CSV types file.
 * */
public class CSVReader implements Reader {

    public static final Logger logger = Logger.getLogger(CSVReader.class.getName());

    String inputCSVFileLocation;
    File file;
    String splitBy = ",";

    Validator pathValidator = new PathValidator();
    Validator splitByValidator = new SplitByValidator();
    Validator fileValidator = new FileValidator();
    Validator athleteFieldValidator = new AthleteFieldValidator();
    AthleteBuilder athleteBuilder = new AthleteBuilder();

    /*
     * CSVReader's read method will parse the parse at given location
     * Create a List of Athlete's object from the input file.
     * */
    @Override
    public Object read(Map<String, String> map) {
        logger.info("==== Starting CSVReader : read ====");
        if (!map.isEmpty()) {
            if (map.containsKey(CSVReaderInputParams.INPUT_FILE_LOCATION) && map.get(CSVReaderInputParams.INPUT_FILE_LOCATION) != null) {
                inputCSVFileLocation = map.get(CSVReaderInputParams.INPUT_FILE_LOCATION);
                pathValidator.validate(inputCSVFileLocation);
                fileValidator.validate(inputCSVFileLocation);
                file = new File(inputCSVFileLocation);
            }
            if (map.containsKey(CSVReaderInputParams.SPLIT_BY)) {
                splitByValidator.validate(map.get(CSVReaderInputParams.SPLIT_BY));
                splitBy = map.get(CSVReaderInputParams.SPLIT_BY);
            }
        }

        // Code to read file and convert to list
        List<Athlete> list = new ArrayList<>();
        String line = null;

        try (FileInputStream fileInputStream = new FileInputStream(file);
             InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, StandardCharsets.UTF_8);
             BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {
            // Read each line of input file at a time containing details of each athlete + performance in Decathlon.
            // Once read, convert to athlete obj and store it into a List of athletes.
            while ((line = bufferedReader.readLine()) != null && line.length() > 0) {
                String[] player = line.split(splitBy);
                athleteFieldValidator.validate(player);
                list.add(athleteBuilder.creatAthleteObjectFromInput(player));
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, () -> "IOException occurred while reading in the input file: {0}." + e.getMessage());
        } catch (ParseException e) {
            logger.log(Level.SEVERE, () -> "ParseException occurred while parsing in the time - Throwing RuntimeException: {0} " + e.getMessage());
        }
        logger.info("==== Ending CSVReader : read ====");
        return list;
    }
}
