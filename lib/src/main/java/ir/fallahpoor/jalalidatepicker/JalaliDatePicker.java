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

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * <code>JalaliDatePicker</code> is a Swing component that provides an easy way for selecting a Jalali (a.k.a. Shamsi)
 * date.
 *
 * @author Masood Fallahpoor
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class JalaliDatePicker extends JComponent {

    private List<DateChangeListener> dataChangeListeners;
    private Calendar previousDate;
    private Calendar currentDate;
    private Calendar defaultDate;
    private JTextField dateTextField;
    private DatePickerDialog datePickerDialog;

    /**
     * Constructs an instance of <code>JalaliDatePicker</code> with its default date set to current date.
     *
     * @param parentFrame the parent of JalaliDatePicker
     */
    public JalaliDatePicker(JFrame parentFrame) {
        this(parentFrame, null, null, null);
    }

    /**
     * Constructs an instance of <code>JalaliDatePicker</code> with given date as default date.
     *
     * @param parentFrame the parent of JalaliDatePicker
     * @param year        default year. Year must be in range [current year - 50, current year + 50]. If year is null
     *                    current year is used as default year.
     * @param month       default month. Month is 1-based i.e. first month is 1. If month is null current month is used
     *                    as default month.
     * @param day         default day of month. Day of month is 1-based i.e. first day of month is 1. If day of month is
     *                    null current day of month is used as default day of month.
     */
    public JalaliDatePicker(JFrame parentFrame, Integer year, Integer month, Integer day) {

        datePickerDialog = new DatePickerDialog(parentFrame, year, month, day);
        datePickerDialog.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentHidden(ComponentEvent e) {
                super.componentHidden(e);
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

        dataChangeListeners = new ArrayList<>();

        setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        JPanel panel = new JPanel();

        dateTextField = new JTextField(7);
        dateTextField.setEditable(false);

        JButton showDatePickerButton = new JButton("...");
        showDatePickerButton.addActionListener(e -> datePickerDialog.setVisible(true));

        panel.add(dateTextField);
        panel.add(showDatePickerButton);

        add(panel);

        Calendar calendar = Calendar.getInstance(new ULocale("@calendar=persian"));

        int intYear = (year == null ? calendar.get(Calendar.YEAR) : year);
        int intMonth = (month == null ? calendar.get(Calendar.MONTH) + 1 : month);
        int intDay = (day == null ? calendar.get(Calendar.DAY_OF_MONTH) : day);

        defaultDate = (Calendar) calendar.clone();

        defaultDate.set(intYear, intMonth - 1, intDay);
        previousDate = (Calendar) defaultDate.clone();
        currentDate = (Calendar) defaultDate.clone();

        setDate(intYear, intMonth, intDay);

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
        datePickerDialog.setDate(currentDate.get(Calendar.YEAR),
                currentDate.get(Calendar.MONTH),
                currentDate.get(Calendar.DAY_OF_MONTH));

    }

    /**
     * Clears the date of <code>JalaliDatePicker</code>.
     */
    public void resetDate() {

        previousDate = (Calendar) defaultDate.clone();
        currentDate = (Calendar) defaultDate.clone();

        setDate(defaultDate.get(Calendar.YEAR),
                defaultDate.get(Calendar.MONTH) + 1,
                defaultDate.get(Calendar.DAY_OF_MONTH));
    }

    /**
     * Resizes date picker dialog so that it has width <code>width</code> and height <code>height</code>.
     *
     * @param width  the new width in pixels
     * @param height the new height in pixels
     */
    public void setDatePickerDialogSize(int width, int height) {
        datePickerDialog.setSize(width, height);
    }

    /**
     * Sets the minimum size of date picker dialog
     *
     * @param width  the new width in pixels
     * @param height the new height in pixels
     */
    public void setDatePickerDialogMinimumSize(int width, int height) {
        datePickerDialog.setMinimumSize(new Dimension(width, height));
    }

    /**
     * Sets whether date picker dialog is resizable by user.
     *
     * @param resizable <code>true</code> if date picker dialog is resizable; <code>false</code> otherwise.
     */
    public void setDatePickerDialogResizable(boolean resizable) {
        datePickerDialog.setResizable(resizable);
    }

    /**
     * Sets the font of <code>JalaliDatePicker</code>
     *
     * @param font new font
     */
    public void setFont(Font font) {

        if (font != null) {
            dateTextField.setFont(font);
            datePickerDialog.setFont(font);
        }

    }

    private void notifyListeners() {
        dataChangeListeners.forEach(l -> l.dateChanged(currentDate.get(Calendar.YEAR),
                currentDate.get(Calendar.MONTH) + 1,
                currentDate.get(Calendar.DAY_OF_MONTH)));
    }

} // end of class JalaliDatePicker

