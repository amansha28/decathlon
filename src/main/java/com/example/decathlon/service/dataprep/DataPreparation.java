package com.example.decathlon.service.dataprep;

import com.example.decathlon.athlete.Athlete;
import com.example.decathlon.events.Events;
import com.example.decathlon.events.utils.SportsCategory;
import com.example.decathlon.events.utils.Units;
import com.example.decathlon.util.StdConversion;
import com.example.decathlon.util.validator.ListValidator;
import com.example.decathlon.util.validator.Validator;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/*
 * Class containing methods to implement specific requirements to prepare the given data.
 * */
public class DataPreparation {

    public static final Logger logger = Logger.getLogger(DataPreparation.class.getName());

    Validator listValidator = new ListValidator();

    /*
     * For Jumping related sports, convert metres to centimeters (before passing for calculations of points)
     */
    public List<Athlete> convertFromMetresToCentiMForJumpingSports(List<Athlete> list) {
        logger.info("==== Starting DataPreparation : convertFromMetresToCentiMForJumpingSports ====");
        listValidator.validate(list);
        list.stream().forEach(
                ath -> {
                    Map<String, Double> athPerf = ath.getEventPerformance();
                    for (int i = 0; i < athPerf.size(); i++) {
                        if (SportsCategory.JUMPING.equals(Events.getSportsList().get(i).getCategory())) {
                            // rounding off the number to 2 digits after decimal
                            athPerf.put(Events.getSportsList().get(i).getName(), Math.round(athPerf.get(Events.getSportsList().get(i).getName()) * 100.0 * 100.0) / 100.0);

                            // this is for more accurate calculation but gives deviated numbers for sports under JUMPING category. like 239.999999999 instead of 240.0
                            //athPerf.put(DecathlonApplication.events.getSportsList().get(i).getName(), (athPerf.get(DecathlonApplication.events.getSportsList().get(i).getName()) * 100.00));
                        }
                        logger.info("Sport Name : " + Events.getSportsList().get(i).getName() + " - Category : " + Events.getSportsList().get(i).getCategory() + " - Performance :" + athPerf.get(Events.getSportsList().get(i).getName()));
                    }
                }
        );
        logger.info("==== Ending DataPreparation : convertFromMetresToCentiMForJumpingSports ====");
        return list;
    }


    /*
     * Based on type of Event, convert the units in which it's measured to required type for Score calculations.
     * */
    public Map<String, Double> convertUnitsBasedOnEventsType(List<String> athletePerformance) throws ParseException {
        long startConvertedDateInSecs = StdConversion.getStartConvertedDateInSecs();
        LinkedHashMap<String, Double> tempMap = new LinkedHashMap<>();
        int count = 0;
        for (String athPerf : athletePerformance) {
            // Special care to read and convert time of events which has units as MINUTESSECONDS ( like 1500m race ) .
            // For rest of events, read as it is.
            if (athPerf != null && !athPerf.isEmpty() && Units.MINUTESSECONDS.equals(Events.getSportsList().get(count).getUnit())) {
                DateFormat dateFormat = new SimpleDateFormat("mm:ss.SSS");

                // Note : concatenated 0 at end (=multiplied by 10, to convert SS -> SSS format) for last part of timestamp.
                Date convertedDate = dateFormat.parse(athletePerformance.get(count) + "0");

                tempMap.put(Events.getSportsList().get(count).getName(), (double) (startConvertedDateInSecs - Math.abs(convertedDate.getTime())) / 1000);
            } else
                tempMap.put(Events.getSportsList().get(count).getName(), Double.valueOf(athPerf));
            count++;
        }

        return tempMap;
    }
}
