package com.salehi.persiandatepicker.util;

import com.salehi.persiandatepicker.date.PersianDateImpl;


@Deprecated
public class PersianDateParser {

    private String dateString;
    private String delimiter = "/";

    /**
     * <pre>
     * construct parser with date string assigned
     * the default delimiter is '/'.
     *
     * To assign deferment delimiter use:
     * {@link #PersianDateParser(String dateString, String delimiter)}
     *
     *                     Example
     *
     *  {@code
     *    PersianCalendar pCal =
     *     new PersianDateParser("1361/3/1").getPersianDate();
     *  }
     * </pre>
     *
     * @param dateString
     */
    public PersianDateParser(String dateString) {
        this.dateString = dateString;
    }

    /**
     * <pre>
     * construct parser with date string assigned
     * the default delimiter is '/'. with this constructor
     * you can set different delimiter to parse the date
     * based on this delimiter.
     * see also:
     * {@link #PersianDateParser(String dateString)}
     *
     *                     Example
     *
     *  {@code
     *    PersianCalendar pCal =
     *     new PersianDateParser("1361-3-1","-").getPersianDate();
     *  }
     * </pre>
     *
     * @param dateString
     * @param delimiter
     */
    public PersianDateParser(String dateString, String delimiter) {
        this(dateString);
        this.delimiter = delimiter;
    }

    /**
     * Produce the PersianCalendar object from given DateString throws Exception
     * if couldn't parse the text.
     *
     * @return PersianCalendar object
     * @throws RuntimeException
     */
    public PersianCalendar getPersianDate() {

        checkDateStringInitialValidation();

        String tokens[] = splitDateString(normalizeDateString(dateString));
        int year = Integer.parseInt(tokens[0]);
        int month = Integer.parseInt(tokens[1]);
        int day = Integer.parseInt(tokens[2]);

        checkPersianDateValidation(year, month, day);

        PersianCalendar pCal = new PersianCalendar();
        pCal.setPersianDate(year, month, day);

        return pCal;
    }

    /**
     * validate the given date
     *
     * @param year
     * @param month
     * @param day
     */
    private void checkPersianDateValidation(int year, int month, int day) {
        if (year < 1)
            throw new RuntimeException("year is not valid");
        if (month < 1 || month > 12)
            throw new RuntimeException("month is not valid");
        if (day < 1 || day > 31)
            throw new RuntimeException("day is not valid");
        if (month > 6 && day == 31)
            throw new RuntimeException("day is not valid");
        if (month == 12 && day == 30 && !PersianDateImpl.isLeapYear(year))
            throw new RuntimeException("day is not valid " + year + " is not a leap year");
    }

    /**
     * planned for further calculation before parsing the text
     *
     * @param dateString
     * @return
     */
    private String normalizeDateString(String dateString) {
        // dateString = dateString.replace("-", delimiter);
        return dateString;
    }

    private String[] splitDateString(String dateString) {
        String tokens[] = dateString.split(delimiter);
        if (tokens.length != 3)
            throw new RuntimeException("wrong date:" + dateString + " is not a Persian Date or can not be parsed");
        return tokens;
    }

    private void checkDateStringInitialValidation() {
        if (dateString == null)
            throw new RuntimeException("input didn't assing please use setDateString()");
        // if(dateString.length()>10)
        // throw new RuntimeException("wrong date:" + dateString +
        // " is not a Persian Date or can not be parsed" );
    }

    public String getDateString() {
        return dateString;
    }

    public void setDateString(String dateString) {
        this.dateString = dateString;
    }

    public String getDelimiter() {
        return delimiter;
    }

    public void setDelimiter(String delimiter) {
        this.delimiter = delimiter;
    }

}
