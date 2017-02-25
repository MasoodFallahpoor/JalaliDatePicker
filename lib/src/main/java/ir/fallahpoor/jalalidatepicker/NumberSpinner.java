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

import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 * @author Masood Fallahpoor
 */
class NumberSpinner extends JSpinner {

    NumberSpinner(int value, int minimum, int maximum, int stepSize) {

        SpinnerNumberModel spinnerModel = new SpinnerNumberModel(value, minimum,
                maximum, stepSize);

        setModel(spinnerModel);
        setEditor(new NumberEditor(this, "####"));

        JTextField spinnerTextField = ((NumberEditor) getEditor()).getTextField();
        spinnerTextField.setEditable(false);
        spinnerTextField.setDocument(new CustomPlainDocument());
        spinnerTextField.setText(String.valueOf(value));

    }

    private class CustomPlainDocument extends PlainDocument {

        @Override
        public void insertString(int offset, String str, AttributeSet attr)
                throws BadLocationException {

            if (str == null) {
                return;
            }

            String str1 = Utils.toPersianNumber(str);
            super.insertString(offset, str1, attr);

        }

    } // end of class CustomPlainDocument

} // end of class NumberSpinner
