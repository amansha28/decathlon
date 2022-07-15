package com.example.decathlon.service;

import com.example.decathlon.DecathlonApplication;
import com.example.decathlon.athlete.Athlete;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.logging.Logger;

public class AthletePointsCalculator {

    public static final Logger logger = Logger.getLogger(AthletePointsCalculator.class.getName());

    PointsCalculatorService pointsCalculatorService =null;

    public AthletePointsCalculator(PointsCalculatorService pointsCalculatorService) {
        this.pointsCalculatorService = pointsCalculatorService;
    }

    public void readAthletesFromFileAndCalculateScore(String inputFilePath, String splitBy, String resultPath) throws ParseException, IOException {

        logger.info("==== Starting AthletePointsCalculator : readAthletesFromFileAndCalculateScore ====");
        File inputDataFile = new File(inputFilePath);

        // Step 2 : Read the input file containing Athletes performance in Decathlon.
        List<Athlete> list = pointsCalculatorService.processFileToAthleteList(inputDataFile, splitBy);

        // Step 3 : For Sports in JUMPING category convert the readings from metres to centimeters
        // as per requirement, scoring formula assumes that JUMPING sports units will be in centimeters.
        list = pointsCalculatorService.convertFromMetresToCentiMForJumpingSports(list);

        // Step 4 : Apply the std given formula to calculate the points for Athlete's performance based on sports type.
        list = pointsCalculatorService.calculateScoreBySportsType(list);

        // Step 5 : Sort the Athletes in descending order based on Total Score. Assign the rank to each Athlete as suggested.
        list = pointsCalculatorService.sortAthletesByTotalScoreAndSetRank(list);

        // Step 6 : Convert the List of Athletes with there total-score and rank into xml output file.
        pointsCalculatorService.convertListOfAthletesToXMLFile(list, resultPath);
        logger.info("==== Ending AthletePointsCalculator : readAthletesFromFileAndCalculateScore ====");
    }

}
