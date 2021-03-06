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
package org.openhab.binding.mpd;

import org.openhab.binding.mpd.internal.PlayerCommandTypeMapping;
import org.openhab.core.binding.BindingProvider;


/**
 * This interface is implemented by classes that can provide mapping information
 * between openHAB items and MPD items.
 * 
 * Implementing classes should register themselves as a service in order to be 
 * taken into account.
 * 
 * @author Thomas.Eichstaedt-Engelen
 * @since 0.8.0
 */
public interface MpdBindingProvider extends BindingProvider {

	/**
	 * Returns the matching player command (associated to <code>itemName</code>
	 * and <code>comnand</code>) or <code>null</code> if no playerCommand could
	 * be found.
	 * 
	 * @param itemName the item for which to find a mpdPlayerCommand
	 * @param command the openHAB command for which to find a mpdPlayerCommand
	 * 
	 * @return the matching mpdPlayerCommand or <code>null</code> if no matching
	 * mpdPlayerCommand could be found.
	 */
	String getPlayerCommand(String itemName, String command);
	
	/**
	 * Returns all Items associated to <code>playerId</code> and <code>playerCommand</code> 
	 * 
	 * @param playerId the id of the player for which items should be returned
	 * @param playerCommand the player command for which items should be returned
	 * 
	 * @return the name of all items which are associated to <code>playerId</code>
	 * and <code>playerComannd</code>
	 */
	String[] getItemNamesByPlayerAndPlayerCommand(String playerId, PlayerCommandTypeMapping playerCommand);

}
