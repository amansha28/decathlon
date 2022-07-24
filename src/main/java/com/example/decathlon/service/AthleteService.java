package com.example.decathlon.service;

import com.example.decathlon.athlete.Athlete;
import com.example.decathlon.service.dataprep.DataPreparation;
import com.example.decathlon.service.rank.CalculateRank;
import com.example.decathlon.service.score.CalculateScore;

import java.util.List;

/*
 * Service class to call multiple methods to process Athletes as per requirements.
 * */

public class AthleteService {

    DataPreparation dataPreparation;
    CalculateScore calculateScore;
    CalculateRank calculateRank;

    public AthleteService(DataPreparation dataPreparation, CalculateScore calculateScore, CalculateRank calculateRank) {
        this.dataPreparation = dataPreparation;
        this.calculateScore = calculateScore;
        this.calculateRank = calculateRank;
    }

    public void processAthleteList(List<Athlete> athleteList) {

        // Data prep : Unit conversion based on Sport category.
        dataPreparation.convertFromMetresToCentiMForJumpingSports(athleteList);

        // Calculate score based on sport type.
        calculateScore.calculateScoreBySportsType(athleteList);

        // Sort list by total score and calculate Rank.
        calculateRank.sortAthleteBasedOnTotalScore(athleteList);
        calculateRank.calculateAndSetRankOfAthlete(athleteList);
    }
}
