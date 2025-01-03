package com.salehi.persiandatepicker.util;

@Deprecated
public class PersianCalendarUtils {

    /**
     * Converts a provided Persian (Shamsi) date to the Julian Day Number (i.e.
     * the number of days since January 1 in the year 4713 BC). Since the
     * Persian calendar is a highly regular calendar, converting to and from a
     * Julian Day Number is not as difficult as it looks. Basically it's a
     * mather of dividing, rounding and multiplying. This routine uses Julian
     * Day Number 1948321 as focal point, since that Julian Day Number
     * corresponds with 1 Farvardin (1) 1.
     *
     * @param year  int persian year
     * @param month int persian month
     * @param day   int persian day
     * @return long
     */
    public static long persianToJulian(long year, int month, int day) {
        return 365L * ((ceil(year - 474L, 2820D) + 474L) - 1L) + ((long) Math.floor((682L * (ceil(year - 474L, 2820D) + 474L) - 110L) / 2816D)) + (PersianCalendarConstants.PERSIAN_EPOCH - 1L) + 1029983L
                * ((long) Math.floor((year - 474L) / 2820D)) + (month < 7 ? 31 * month : 30 * month + 6) + day;
    }

    /**
     * Calculate whether current year is Leap year in persian or not
     *
     * @return boolean
     */
    public static boolean isPersianLeapYear(int persianYear) {
        return PersianCalendarUtils.ceil((38D + (PersianCalendarUtils.ceil(persianYear - 474L, 2820L) + 474L)) * 682D, 2816D) < 682L;
    }

    /**
     * Converts a provided Julian Day Number (i.e. the number of days since
     * January 1 in the year 4713 BC) to the Persian (Shamsi) date. Since the
     * Persian calendar is a highly regular calendar, converting to and from a
     * Julian Day Number is not as difficult as it looks. Basically it's a
     * mather of dividing, rounding and multiplying.
     *
     * @param julianDate
     * @return long
     */
    public static long julianToPersian(long julianDate) {
        long persianEpochInJulian = julianDate - persianToJulian(475L, 0, 1);
        long cyear = ceil(persianEpochInJulian, 1029983D);
        long ycycle = cyear != 1029982L ? ((long) Math.floor((2816D * (double) cyear + 1031337D) / 1028522D)) : 2820L;
        long year = 474L + 2820L * ((long) Math.floor(persianEpochInJulian / 1029983D)) + ycycle;
        long aux = (1L + julianDate) - persianToJulian(year, 0, 1);
        int month = (int) (aux > 186L ? Math.ceil((double) (aux - 6L) / 30D) - 1 : Math.ceil((double) aux / 31D) - 1);
        int day = (int) (julianDate - (persianToJulian(year, month, 1) - 1L));
        return (year << 16) | (month << 8) | day;
    }

    /**
     * Ceil function in original algorithm
     *
     * @param double1
     * @param double2
     * @return long
     */
    public static long ceil(double double1, double double2) {
        return (long) (double1 - double2 * Math.floor(double1 / double2));
    }

}
