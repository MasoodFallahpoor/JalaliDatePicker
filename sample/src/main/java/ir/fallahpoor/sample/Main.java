package ir.fallahpoor.sample;

import ir.fallahpoor.jalalidatepicker.JalaliDatePicker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            JFrame frame = new JFrame();

            frame.setLayout(new BorderLayout());

            JalaliDatePicker jalaliDatePicker = new JalaliDatePicker();
            // Use the following line to set the initial date of JalaliDatePicker
            // JalaliDatePicker jalaliDatePicker = new JalaliDatePicker(1367, 8, 6);

            // Use setDate to set the date of JalaliDatePicker
            jalaliDatePicker.setDate(1367, 8, 6);

            // Add a DateChangeListener to be notified when date is changed
            jalaliDatePicker.addDateChangeListener((newYear, newMonth, newDay) -> {
                System.out.println("Date changed. New date is " + newYear + "/" + newMonth + "/" + newDay);
            });

            frame.add(jalaliDatePicker, BorderLayout.CENTER);

            JButton clearDateButton = new JButton("clear date");
            clearDateButton.addActionListener(e -> jalaliDatePicker.clearDate());
            frame.add(clearDateButton, BorderLayout.SOUTH);

            frame.setSize(250, 100);
            frame.setResizable(false);
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });

    }

}
