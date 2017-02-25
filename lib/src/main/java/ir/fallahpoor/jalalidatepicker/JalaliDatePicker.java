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
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * <code>JalaliDatePicker</code> is a Swing component that provides an easy way
 * for selecting a Jalali (a.k.a. Shamsi) date.
 *
 * @author Masood Fallahpoor
 */
public class JalaliDatePicker extends JComponent {

    private ArrayList<DateChangeListener> dataChangeListeners;
    private String previousDate;
    private String currentDate;
    private JTextField dateTextField;

    /**
     * Constructs an instance of <code>JalaliDatePicker</code>.
     */
    public JalaliDatePicker() {

        dataChangeListeners = new ArrayList<>();
        previousDate = "";
        currentDate = "";

        setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        JPanel panel = new JPanel();

        dateTextField = new JTextField(7);
        dateTextField.setEditable(false);

        JButton mShowDatePickerButton = new JButton("...");
        mShowDatePickerButton.addActionListener(
                new ShowDatePickerDialogAction());

        panel.add(dateTextField);
        panel.add(mShowDatePickerButton);

        add(panel);

    } // end of JalaliDatePicker's constructor method

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
     * Removes given <code>DateChangeListener</code> from
     * <code>JalaliDatePicker</code>.
     *
     * @param dateChangeListener the listener to be removed
     */
    public void removeDateChangeListener(DateChangeListener dateChangeListener) {
        dataChangeListeners.remove(dateChangeListener);
    }

    /**
     * Returns the date of <code>JalaliDatePicker</code> as a String with the
     * format of yyyy/mm/dd.
     *
     * @return Date of <code>JalaliDatePicker</code>
     */
    public String getDate() {
        return dateTextField.getText();
    }

    /**
     * Sets the date of <code>JalaliDatePicker</code> to given date. Argument
     * <code>date</code> should be a valid Jalali date with general format of
     * yyyy/mm/dd or otherwise the date of <code>JalaliDatePicker</code> will be
     * set to current date.
     *
     * @param date Date to be set as <code>JalaliDatePicker</code>'s date.
     */
    public void setDate(String date) {

        if (Utils.isDateValid(date)) {
            dateTextField.setText(Utils.toPersianNumber(date));
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", new ULocale("fa-IR"));
            Calendar calendar = Calendar.getInstance(new ULocale("@calendar=persian"));

            dateTextField.setText(Utils.toPersianNumber(sdf.format(calendar.getTime())));
        }

    } // end of method setDate

    /**
     * Clears the date of <code>JalaliDatePicker</code>.
     */
    public void clearDate() {
        dateTextField.setText("");
    }

    private void notifyListeners() {

        for (DateChangeListener listener : dataChangeListeners) {
            listener.dateChanged(currentDate);
        }

    }

    private class ShowDatePickerDialogAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            final DatePickerDialog datePickerDialog = new DatePickerDialog();

            datePickerDialog.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {

                    currentDate = datePickerDialog.getPickedDate();

                    if (!currentDate.equalsIgnoreCase(previousDate)) {
                        previousDate = currentDate;
                        dateTextField.setText(currentDate);
                        notifyListeners();
                    }

                }
            });

        } // end of method actionPerformed

    } // end of inner class ShowDatePickerDialogAction

} // end of class JalaliDatePicker

