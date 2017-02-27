package ir.fallahpoor.sample;

import ir.fallahpoor.jalalidatepicker.JalaliDatePicker;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            JFrame frame = new JFrame();

            frame.setLayout(new BorderLayout());

            JalaliDatePicker jalaliDatePicker = new JalaliDatePicker(frame);
            // Use the following line to set the initial date of JalaliDatePicker
            // JalaliDatePicker jalaliDatePicker = new JalaliDatePicker(frame, 1367, 8, 6);

            // Use setDate to set the date of JalaliDatePicker
            // jalaliDatePicker.setDate(1367, 6, 20);

            // Use setDatePickerDialogSize to set the size of date picker dialog
            // jalaliDatePicker.setDatePickerDialogSize(600, 400);

            // Use setDatePickerDialogMinimumSize to set the minimum size of date picker dialog
            // jalaliDatePicker.setDatePickerDialogMinimumSize(450, 300);

            // Use setDatePickerDialogResizable to set whether date picker dialog is resizable by user
            // jalaliDatePicker.setDatePickerDialogResizable(false);

            // Add a DateChangeListener to be notified when date is changed
            jalaliDatePicker.addDateChangeListener((newYear, newMonth, newDay) ->
                    System.out.println("Date changed. New date is " + newYear + "/" + newMonth + "/" + newDay));

            frame.add(jalaliDatePicker, BorderLayout.CENTER);

            JButton resetDateButton = new JButton("Reset date");
            resetDateButton.addActionListener(e -> jalaliDatePicker.resetDate());
            frame.add(resetDateButton, BorderLayout.SOUTH);

            frame.setSize(250, 100);
            frame.setResizable(false);
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });

    }

}