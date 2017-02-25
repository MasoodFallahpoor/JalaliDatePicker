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

/**
 * The listener interface for receiving 'Date Change' events of
 * <code>JalaliDatePicker</code>. The class that is interested in processing a
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
     * @param newDate New date
     */
    void dateChanged(String newDate);

}
