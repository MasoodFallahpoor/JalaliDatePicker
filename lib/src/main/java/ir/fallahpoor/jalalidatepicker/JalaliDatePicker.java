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

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import javax.swing.*;

/**
 * <code>JalaliDatePicker</code> is a Swing component that provides an easy way for selecting a Jalali (a.k.a. Shamsi)
 * date.
 *
 * @author Masood Fallahpoor
 */
public class JalaliDatePicker extends JComponent {

    private ArrayList<DateChangeListener> dataChangeListeners;
    private Calendar previousDate;
    private Calendar currentDate;
    private JTextField dateTextField;
    private JFrame parentFrame;

    /**
     * Constructs an instance of <code>JalaliDatePicker</code> with given date as initial date.
     *
     * @param year  default year. Year must be in range [current year - 50, current year + 50]
     * @param month default month. Month is 1-based i.e. first month is 1
     * @param day   default day of month. Day of month is 1-based i.e. first day of month is 1
     */
    public JalaliDatePicker(JFrame parentFrame, int year, int month, int day) {

        this(parentFrame);
        setDate(year, month, day);
    }

    /**
     * Constructs an instance of <code>JalaliDatePicker</code> with its initial date set to current date.
     */
    public JalaliDatePicker(JFrame parentFrame) {

        this.parentFrame = parentFrame;

        dataChangeListeners = new ArrayList<>();
        resetDates();

        setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        JPanel panel = new JPanel();

        dateTextField = new JTextField(7);
        dateTextField.setEditable(false);

        JButton showDatePickerButton = new JButton("...");
        showDatePickerButton.addActionListener(new ShowDatePickerDialogAction());

        panel.add(dateTextField);
        panel.add(showDatePickerButton);

        add(panel);

    } // end of JalaliDatePicker's constructor

    /**
     * Adds a <code>DateChangeListener</code> to <code>JalaliDatePicker</code>.
     *
     * @param dateChangeListener the <code>DateChangeListener</code> to be added
     */
    public void addDateChangeListener(DateChangeListener dateChangeListener) {

        if (!dataChangeListeners.contains(dateChangeListener)) {
            dataChangeListeners.add(dateChangeListener);
        }

    }

    /**
     * Removes given <code>DateChangeListener</code> from <code>JalaliDatePicker</code>.
     *
     * @param dateChangeListener the listener to be removed
     */
    public void removeDateChangeListener(DateChangeListener dateChangeListener) {
        dataChangeListeners.remove(dateChangeListener);
    }

    /**
     * Returns selected year
     *
     * @return selected year
     */
    public int getYear() {
        return currentDate.get(Calendar.YEAR);
    }

    /**
     * Returns selected month. Month is 1-based i.e. first month is 1.
     *
     * @return selected month
     */
    public int getMonth() {
        return (currentDate.get(Calendar.MONTH) + 1);
    }

    /**
     * Returns selected day of month. Day of month is 1-based i.e. first day of month is 1.
     *
     * @return selected day of month
     */
    public int getDay() {
        return currentDate.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * Sets the date of <code>JalaliDatePicker</code>. If provided date is invalid then <code>JalaliDatePicker</code>'s
     * date is set to current date.
     *
     * @param year  the year to set. Year must be in range [current year - 50, current year + 50]
     * @param month the month to set. Month is 1-based i.e. first month is 1
     * @param day   day of month to set. Day of month is 1-based i.e. first day of month is 1
     */
    public void setDate(int year, int month, int day) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", new ULocale("fa-IR"));

        if (Utils.isDateValid(year, month, day)) {
            currentDate.set(year, month - 1, day);
        }

        dateTextField.setText(Utils.toPersianNumber(sdf.format(currentDate.getTime())));

    }

    /**
     * Clears the date of <code>JalaliDatePicker</code>.
     */
    public void clearDate() {
        dateTextField.setText("");
        resetDates();
    }

    private void notifyListeners() {
        dataChangeListeners.forEach(l -> l.dateChanged(currentDate.get(Calendar.YEAR),
                currentDate.get(Calendar.MONTH) + 1,
                currentDate.get(Calendar.DAY_OF_MONTH)));
    }

    private void resetDates() {
        previousDate = Calendar.getInstance(new ULocale("@calendar=persian"));
        currentDate = (Calendar) previousDate.clone();
    }

    private class ShowDatePickerDialogAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            DatePickerDialog datePickerDialog = new DatePickerDialog(parentFrame);

            datePickerDialog.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {

                    currentDate.set(datePickerDialog.getPickedYear(),
                            datePickerDialog.getPickedMonth(),
                            datePickerDialog.getPickedDay());

                    if (currentDate.compareTo(previousDate) != 0) {
                        previousDate = (Calendar) currentDate.clone();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", new ULocale("fa-IR"));
                        dateTextField.setText(Utils.toPersianNumber(sdf.format(currentDate.getTime())));
                        notifyListeners();
                    }

                }
            });

        } // end of method actionPerformed

    } // end of inner class ShowDatePickerDialogAction

} // end of class JalaliDatePicker

