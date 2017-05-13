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

import com.ibm.icu.text.SimpleDateFormat;
import com.ibm.icu.util.Calendar;
import com.ibm.icu.util.ULocale;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.function.Consumer;

/**
 * @author Masood Fallahpoor
 */
class DatePickerDialog extends JDialog {

    private int currentYear;
    private int currentMonth;
    private int currentDay;
    private Calendar calendar;
    private ULocale persianLocale;
    private JLabel currentMonthLabel;
    private JLabel[] dayNameLabels;
    private JButton[] dayButtons;
    private NumberSpinner yearSpinner;
    private JButton nextMonthButton;
    private JButton previousMonthButton;

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

        dayNameLabels = new JLabel[7];

        for (int i = 0; i < dayNameLabels.length; i++) {

            dayNameLabels[i] = new JLabel(dayNames[i], JLabel.CENTER);
            dayNameLabels[i].setForeground(Color.red);

            daysPanel.add(dayNameLabels[i]);

        }

        dayButtons = new JButton[42];

        for (int i = 0; i < dayButtons.length; i++) {

            dayButtons[i] = new JButton();
            dayButtons[i].setFocusPainted(false);
            dayButtons[i].setBackground(Color.white);

            daysPanel.add(dayButtons[i]);

        }

        JPanel navigationPanel = new JPanel(new GridLayout(1, 3));

        nextMonthButton = new JButton(
                stringsBundle.getString("next_month"));

        nextMonthButton.addActionListener(actionEvent -> {

            Comparable yearMaxValue = ((SpinnerNumberModel) yearSpinner.getModel()).getMaximum();

            if (currentMonth == 11) {
                if (!yearMaxValue.equals(currentYear)) {
                    currentMonth = 0;
                    yearSpinner.setValue(yearSpinner.getNextValue());
                }
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

        previousMonthButton = new JButton(
                stringsBundle.getString("previous_month"));

        previousMonthButton.addActionListener(actionEvent -> {

            Comparable yearMinValue = ((SpinnerNumberModel) yearSpinner.getModel()).getMinimum();

            if (currentMonth == 0) {
                if (!yearMinValue.equals(currentYear)) {
                    currentMonth = 11;
                    yearSpinner.setValue(yearSpinner.getPreviousValue());
                }
            } else {
                currentMonth--;
            }
            displayDate(currentYear, currentMonth, currentDay);
        });

        navigationPanel.add(previousMonthButton);

        add(daysPanel, BorderLayout.CENTER);
        add(navigationPanel, BorderLayout.SOUTH);
        pack();
        setLocationRelativeTo(parentFrame);
        displayDate(currentYear, currentMonth, currentDay);

    } // end of JalaliDatePicker's constructor method

    @Override
    public void setFont(Font font) {

        super.setFont(font);

        currentMonthLabel.setFont(font);
        yearSpinner.setFont(font);
        nextMonthButton.setFont(font);
        previousMonthButton.setFont(font);
        performActionOnDayButtons(b -> b.setFont(font));
        for (JLabel label : dayNameLabels) {
            label.setFont(font);
        }

    }

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

    private void displayDate(int year, int month, int day) {

        SimpleDateFormat sdf;
        int dayOfWeek;
        int daysInMonth;

        sdf = new SimpleDateFormat("MMMM", persianLocale);

        calendar.set(year, month, day);
        dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) + 1;
        daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        performActionOnDayButtons(b -> {
            b.setText("");
            for (ActionListener listener : b.getActionListeners()) {
                b.removeActionListener(listener);
            }
        });

        for (int i = dayOfWeek - 1, dayCounter = 1; dayCounter <= daysInMonth; i++, dayCounter++) {

            final int selection = i;

            dayButtons[i].setText(Utils.toPersianNumber(String.valueOf(dayCounter)));
            dayButtons[i].addActionListener(actionEvent -> {
                currentDay = Integer.parseInt(dayButtons[selection]
                        .getActionCommand());
                setVisible(false);
            });

        }

        yearSpinner.setValue(year);

        currentMonthLabel.setText(sdf.format(calendar.getTime()));

    } // end of method displayDate

    private void performActionOnDayButtons(Consumer<JButton> consumer) {
        Arrays.stream(dayButtons).forEach(consumer);
    }

} // end of class DatePickerDialog
