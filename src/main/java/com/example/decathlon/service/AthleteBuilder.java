package com.example.decathlon.service;

import com.example.decathlon.athlete.Athlete;
import com.example.decathlon.service.dataprep.DataPreparation;

import java.text.ParseException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


/*
 * Class containing methods related to Athlete Building.
 * */
public class AthleteBuilder {

    /*
     * Create Athlete Object from input Line read from File.
     * */
    public Athlete creatAthleteObjectFromInput(String[] player) throws ParseException {
        Athlete athlete = new Athlete();
        // Read and set the name of each player.
        Optional<String> athleteName = Arrays.stream(player).findFirst();
        DataPreparation dataPreparation = new DataPreparation();
        if (athleteName.isPresent()) {
            athlete.setName(athleteName.get());
            // Skip the first field (player name) of each line and read rest of his/her performance.
            List<String> athletePerformance = Arrays.stream(player).skip(1).collect(Collectors.toList());
            Map<String, Double> tempMap = dataPreparation.convertUnitsBasedOnEventsType(athletePerformance);
            athlete.setEventPerformance(tempMap);
        }
        return athlete;
    }


}
