/*
 * Copyright 2016 Masood Fallahpoor

 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.

 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0

 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package ir.fallahpoor.jalalidatepicker;

import com.ibm.icu.util.Calendar;
import com.ibm.icu.util.ULocale;

/**
 * Class Utils provides utility methods for other classes.
 *
 * @author Masood Fallahpoor
 */
class Utils {

    private Utils() {
    }

    static boolean isDateValid(int year, int month, int day) {

        int[] daysOfMonth1 = {31, 31, 31, 31, 31, 31, 30, 30, 30, 30, 30, 30};
        int[] daysOfMonth2 = {31, 31, 31, 31, 31, 31, 30, 30, 30, 30, 30, 29};
        Calendar calendar = Calendar.getInstance(new ULocale("@calendar=persian"));
        int currentYear = calendar.get(Calendar.YEAR);

        return (currentYear - 50 <= year && year <= currentYear + 50) &&
                (1 <= month && month <= 12) &&
                (1 <= day && day <= (isLeapYear(year) ? daysOfMonth1[month - 1] : daysOfMonth2[month - 1]));

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
