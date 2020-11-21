package org.assignmentSeera.dataProvider;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class DataManipulationBaseObject {

    Random random = new Random();
    Date dateNow = new Date();
    long now = new Date().getTime();
    final ObjectMapper mapper = new ObjectMapper();
    final String validDateFormat = "dd-MM-YYY";
    final String InvalidDateFormat = "dd.MM.YYY";
    final String invalidDate = "DD-MM-YY";

    public Date getDatePlusMinusNumOfDays(int numOfDays) {
        long aDay = TimeUnit.DAYS.toMillis(numOfDays);
        return new Date(now + aDay);

    }

    public Date getDatePlusMinusRandomDay(int minDaysToAdd, int maxDaysToAdd) {
        long aDay = TimeUnit.DAYS.toMillis(random.ints(minDaysToAdd, maxDaysToAdd).findAny().getAsInt());
        dateNow = new Date(now + aDay);
        return dateNow;

    }

    public String setDateFormat_RetunDateAsString(Date date, String dateFormatPattern) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(dateFormatPattern);
        return dateFormat.format(dateNow);
    }

    public String convertJavaObjectToJsonString(Object object) {
        String bodyAsString = null;
        try {
            bodyAsString = mapper.writeValueAsString(object);

        } catch (JsonProcessingException | NullPointerException e) {
            e.printStackTrace();
        }
        return bodyAsString;
    }

    public int getRandomIntegerRange(int firstNum, int lastNum) {
        try {
            return random.ints(firstNum, lastNum).findAny().getAsInt();
        } catch (Exception e) {
            e.getStackTrace();
        }
        return 0;
    }

    /**
     * This method combine setValidDateFormat_RetunDateAsString() and getDatePlusMinusRandomDay() together
     * it chooses a random number between two number as days and expand the current date accordingly
     *
     * @param start the start number of the range
     * @param end   the second number of the range
     * @return Date of type string after  the
     */
    public String getValidStringDate(int start, int end) {
        if (start > end || start < 0 || end == 0) {
            return setDateFormat_RetunDateAsString(getDatePlusMinusRandomDay(1, 10), validDateFormat);
        } else {
            return setDateFormat_RetunDateAsString(getDatePlusMinusRandomDay(start, end), validDateFormat);
        }
    }

    public String getInvalidStringDateFormat(int start, int end) {
        if (start > end || start < 0 || end == 0) {
            return setDateFormat_RetunDateAsString(getDatePlusMinusRandomDay(1, 10), InvalidDateFormat);
        } else {
            return setDateFormat_RetunDateAsString(getDatePlusMinusRandomDay(start, end), InvalidDateFormat);
        }
    }

    public String getInvalidStringDate(int start, int end) {
        if (start > end || start < 0 || end == 0) {
            return setDateFormat_RetunDateAsString(getDatePlusMinusRandomDay(1, 10), invalidDate);
        } else {
            return setDateFormat_RetunDateAsString(getDatePlusMinusRandomDay(start, end), invalidDate);
        }
    }

}
