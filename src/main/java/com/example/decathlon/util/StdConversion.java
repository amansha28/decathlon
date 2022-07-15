package com.example.decathlon.util;

import com.example.decathlon.service.AthletePointsCalculator;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StdConversion {

    public static final Logger logger = Logger.getLogger(StdConversion.class.getName());

    // Step 1 : Calculate the timeStamp in milliseconds for the Start time.
    // Used in conversion of calculation of complex time duration like mm:ss.SSS into seconds.
    // Calculate the Base time stamp in milliseconds for : Jan 01 00:00:00 IST 1970
    // used to calculate the Base time for 1500m race, in milliSeconds and secs.
    public long getStartConvertedDateInSecs() throws ParseException {
        logger.info("==== Starting StdConversion : getStartConvertedDateInSecs ====");
        DateFormat simpleDateFormat;
        Date startConvertDate = null;
//        try{
             simpleDateFormat = new SimpleDateFormat("mm:ss.SSS", Locale.getDefault());
             startConvertDate = simpleDateFormat.parse("00:00.000");

//        }catch(ParseException e){
//            logger.log(Level.SEVERE,"ParseException occurred while parsing the date : ",e.getMessage());
//            e.printStackTrace();
//        }
        final long startConvertedDateInSecs = Math.abs(startConvertDate.getTime());
        logger.info("start time : " + startConvertDate + " in secs : " + startConvertedDateInSecs);
        logger.info("==== Ending StdConversion : getStartConvertedDateInSecs ====");
        return startConvertedDateInSecs;
    }
}
