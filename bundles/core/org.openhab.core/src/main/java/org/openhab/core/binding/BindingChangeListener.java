/**
 * openHAB, the open Home Automation Bus.
 * Copyright (C) 2010-2013, openHAB.org <admin@openhab.org>
 *
 * See the contributors.txt file in the distribution for a
 * full listing of individual contributors.
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation; either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see <http://www.gnu.org/licenses>.
 *
 * Additional permission under GNU GPL version 3 section 7
 *
 * If you modify this Program, or any covered work, by linking or
 * combining it with Eclipse (or a modified version of that library),
 * containing parts covered by the terms of the Eclipse Public License
 * (EPL), the licensors of this Program grant you additional permission
 * to convey the resulting work.
 */
package org.openhab.core.binding;


/**
 * This interface must be implemented by classes which want to be notified by a
 * {@link BindingProvider} about changes in the binding configuration.
 * 
 * @author Kai Kreuzer
 * @since 0.3.0
 */
public interface BindingChangeListener {
	
	/**
	 * Called, if a single binding has changed. The given item could have been
	 * added or removed.
	 * 
	 * @param provider the binding provider where the binding has changed
	 * @param itemName the item name for which the binding has changed
	 */
	public void bindingChanged(BindingProvider provider, String itemName);
	
	/**
	 * Called, if all bindings (might) have changed.
	 * 
	 * @param provider the binding provider whose bindings have changed
	 */
	public void allBindingsChanged(BindingProvider provider);
	
}
