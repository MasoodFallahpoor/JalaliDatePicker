/*
 * Copyright (C) 2017 Masood Fallahpoor
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package ir.fallahpoor.jalalidatepicker;

import java.util.regex.Pattern;

/**
 * Class Utils provides utility methods for other classes.
 *
 * @author Masood Fallahpoor
 */
class Utils {

    private static Pattern datePattern;

    static {
        datePattern = Pattern.compile("\\d{4}(/\\d{2}){2}", Pattern.UNICODE_CHARACTER_CLASS);
    }

    private Utils() {
    }

    static boolean isDateValid(String date) {

        int[] dateComponents;
        int[] daysOfMonth1 = {31, 31, 31, 31, 31, 31, 30, 30, 30, 30, 30, 30};
        int[] daysOfMonth2 = {31, 31, 31, 31, 31, 31, 30, 30, 30, 30, 30, 29};
        int year, month, day;

        if (date == null || !datePattern.matcher(date).matches()) {
            return false;
        } else {
            dateComponents = getDateComponents(date);

            year = dateComponents[0];
            month = dateComponents[1];
            day = dateComponents[2];

            return (1 <= month && month <= 12)
                    && (1 <= day && day <= (isLeapYear(year) ? daysOfMonth1[month - 1] : daysOfMonth2[month - 1]));
        }

    } // end of method isDateValid

    private static int[] getDateComponents(String date) {

        String[] dateTokens = date.split("/");

        return new int[]{Integer.parseInt(dateTokens[0]),
                Integer.parseInt(dateTokens[1]),
                Integer.parseInt(dateTokens[2])};

    }

    static String toPersianNumber(String englishNumber) {

        StringBuilder persianNumber = new StringBuilder(englishNumber.length());
        String englishDigits = "0123456789";
        char ch;

        for (int i = 0; i < englishNumber.length(); i++) {

            ch = englishNumber.charAt(i);

            if (englishDigits.indexOf(ch) != -1) {
                persianNumber.append(englishDigitToPersian(ch));
            } else {
                persianNumber.append(ch);
            }

        }

        return persianNumber.toString();

    } // end of method toPersianNumber

    private static char englishDigitToPersian(char englishDigit) {
        return (char) (((int) englishDigit) - ((int) '0' - (int) '\u06f0'));
    }

    private static boolean isLeapYear(long year) {

        long a = year - 474L;
        long b = mod(a, 2820L) + 474L;

        return mod((b + 38D) * 682D, 2816D) < 682L;

    }

    private static long mod(double a, double b) {
        return (long) (a - b * Math.floor(a / b));
    }

} // end of class Utils
