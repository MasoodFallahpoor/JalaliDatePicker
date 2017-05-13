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
