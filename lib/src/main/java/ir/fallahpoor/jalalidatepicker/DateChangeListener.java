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

/**
 * The interface for receiving 'Date Change' events of
 * <code>JalaliDatePicker</code>. A class that is interested in processing a
 * 'Date Change' event implements this interface, and the object created with
 * that class is registered with <code>JalaliDatePicker</code>, using the
 * <code>JalaliDatePicker</code>'s <code>addDateChangeListener</code> method.
 * When the 'Date Change' event occurs, that object's <code>dateChanged</code>
 * method is invoked.
 *
 * @author Masood Fallahpoor
 */
public interface DateChangeListener {

    /**
     * Invoked when a 'Date Change' event occurs.
     *
     * @param newYear  new year
     * @param newMonth new month
     * @param newDay   new day
     */
    void dateChanged(int newYear, int newMonth, int newDay);

}
