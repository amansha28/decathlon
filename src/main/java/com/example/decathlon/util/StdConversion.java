package com.example.decathlon.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Logger;

public class StdConversion {

    public static final Logger logger = Logger.getLogger(StdConversion.class.getName());

    /*
     * Calculate the Base time stamp in milliseconds for : Jan 01 00:00:00 IST 1970
     * This timestamp is used as the Base time for events with Units as MINUTESSECONDS (1500 m race)
     */
    public long getStartConvertedDateInSecs() throws ParseException {
        logger.info("==== Starting StdConversion : getStartConvertedDateInSecs ====");
        DateFormat simpleDateFormat;
        Date startConvertDate = null;
        simpleDateFormat = new SimpleDateFormat("mm:ss.SSS", Locale.getDefault());
        startConvertDate = simpleDateFormat.parse("00:00.000");

        final long startConvertedDateInSecs = Math.abs(startConvertDate.getTime());
        logger.info("start time : " + startConvertDate + " in secs : " + startConvertedDateInSecs);
        logger.info("==== Ending StdConversion : getStartConvertedDateInSecs ====");
        return startConvertedDateInSecs;
    }
}
