package ir.fallahpoor.sample;

import ir.fallahpoor.jalalidatepicker.JalaliDatePicker;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame();
            frame.add(new JalaliDatePicker());
            frame.setSize(250, 100);
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });

    }

}
