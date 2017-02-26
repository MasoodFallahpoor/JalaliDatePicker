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

import com.ibm.icu.text.SimpleDateFormat;
import com.ibm.icu.util.Calendar;
import com.ibm.icu.util.ULocale;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.swing.*;

/**
 * @author Masood Fallahpoor
 */
class DatePickerDialog extends JDialog {

    private int currentYear;
    private int currentMonth;
    private int currentDay;
    private JLabel currentMonthLabel;
    private JButton[] buttons;
    private static Calendar calendar;
    private static ULocale persianLocale;
    private NumberSpinner yearSpinner;

    DatePickerDialog(JFrame parentFrame, Integer year, Integer month, Integer day) {

        ResourceBundle stringsBundle = ResourceBundle.getBundle("strings", new Locale("fa", "IR"));
        String[] dayNames = {stringsBundle.getString("sat"), stringsBundle.getString("sun"),
                stringsBundle.getString("mon"), stringsBundle.getString("tue"),
                stringsBundle.getString("wed"), stringsBundle.getString("thu"),
                stringsBundle.getString("fri")};

        setTitle(stringsBundle.getString("select_date"));
        setModalityType(ModalityType.APPLICATION_MODAL);

        persianLocale = new ULocale("fa-IR");

        calendar = Calendar.getInstance(new ULocale("@calendar=persian"));

        currentYear = (year == null ? calendar.get(Calendar.YEAR) : year);
        currentMonth = (month == null ? calendar.get(Calendar.MONTH) : month - 1);
        currentDay = (day == null ? -1 : day);

        JPanel daysPanel = new JPanel(new GridLayout(7, 7));
        daysPanel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        daysPanel.setPreferredSize(new Dimension(500, 120));

        JLabel dayNameLabels[] = new JLabel[7];

        for (int i = 0; i < dayNameLabels.length; i++) {

            dayNameLabels[i] = new JLabel(dayNames[i], JLabel.CENTER);
            dayNameLabels[i].setForeground(Color.red);

            daysPanel.add(dayNameLabels[i]);

        }

        buttons = new JButton[42];

        for (int i = 0; i < buttons.length; i++) {

            buttons[i] = new JButton();
            buttons[i].setFocusPainted(false);
            buttons[i].setBackground(Color.white);

            daysPanel.add(buttons[i]);

        }

        JPanel navigationPanel = new JPanel(new GridLayout(1, 3));

        JButton nextMonthButton = new JButton(
                stringsBundle.getString("next_month"));

        nextMonthButton.addActionListener(actionEvent -> {
            if (currentMonth == 11) {
                currentMonth = 0;
                yearSpinner.setValue(yearSpinner.getNextValue());
            } else {
                currentMonth++;
            }

            displayDate(currentYear, currentMonth, currentDay);
        });

        navigationPanel.add(nextMonthButton);

        JPanel currentDatePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        yearSpinner = new NumberSpinner(currentYear, currentYear - 50,
                currentYear + 50, 1);

        yearSpinner.addChangeListener(e -> {
            JSpinner s = (JSpinner) e.getSource();
            currentYear = (Integer) s.getValue();
            displayDate(currentYear, currentMonth, currentDay);
        });
        currentDatePanel.add(yearSpinner);

        currentMonthLabel = new JLabel("", JLabel.CENTER);
        currentDatePanel.add(currentMonthLabel);

        navigationPanel.add(currentDatePanel);

        JButton previousMonthButton = new JButton(
                stringsBundle.getString("previous_month"));

        previousMonthButton.addActionListener(actionEvent -> {
            if (currentMonth == 0) {
                currentMonth = 11;
                yearSpinner.setValue(yearSpinner.getPreviousValue());
            } else {
                currentMonth--;
            }
            displayDate(currentYear, currentMonth, currentDay);
        });

        navigationPanel.add(previousMonthButton);

        add(daysPanel, BorderLayout.CENTER);
        add(navigationPanel, BorderLayout.SOUTH);
        pack();
        setResizable(false);
        setLocationRelativeTo(parentFrame);
        displayDate(currentYear, currentMonth, currentDay);

    } // end of JalaliDatePicker's constructor method

    private void displayDate(int year, int month, int day) {

        SimpleDateFormat sdf;
        int dayOfWeek;
        int daysInMonth;

        sdf = new SimpleDateFormat("MMMM", persianLocale);

        calendar.set(year, month, day);
        dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) + 1;
        daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        for (JButton button : buttons) {
            button.setText("");
        }

        for (int i = dayOfWeek - 1, dayCounter = 1; dayCounter <= daysInMonth; i++, dayCounter++) {

            final int selection = i;

            buttons[i].setText(Utils.toPersianNumber(String.valueOf(dayCounter)));
            buttons[i].addActionListener(actionEvent -> {
                currentDay = Integer.parseInt(buttons[selection]
                        .getActionCommand());
                setVisible(false);
            });

        }

        yearSpinner.setValue(year);

        currentMonthLabel.setText(sdf.format(calendar.getTime()));

    } // end of method displayDate

    void setDate(int year, int month, int day) {

        currentYear = year;
        currentMonth = month;
        currentDay = day;

        displayDate(currentYear, currentMonth, currentDay);

    }

    int getPickedYear() {
        return currentYear;
    }

    int getPickedMonth() {
        return currentMonth;
    }

    int getPickedDay() {
        return currentDay;
    }

} // end of class DatePickerDialog
