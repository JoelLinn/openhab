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
package org.openhab.ui.webapp.internal.servlet;

import java.io.IOException;
import java.util.Hashtable;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.openhab.core.events.EventPublisher;
import org.openhab.core.items.GroupItem;
import org.openhab.core.items.Item;
import org.openhab.core.items.ItemNotFoundException;
import org.openhab.core.library.items.SwitchItem;
import org.openhab.core.library.types.OnOffType;
import org.openhab.core.types.Command;
import org.openhab.core.types.TypeParser;
import org.osgi.service.http.NamespaceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This servlet receives events from the web app and sends these as
 * commands to the bus.
 * 
 * @author Kai Kreuzer
 *
 */
public class CmdServlet extends BaseServlet {

	private static final Logger logger = LoggerFactory.getLogger(CmdServlet.class);

	public static final String SERVLET_NAME = "CMD";

	private EventPublisher eventPublisher;	

	
	public void setEventPublisher(EventPublisher eventPublisher) {
		this.eventPublisher = eventPublisher;
	}

	public void unsetEventPublisher(EventPublisher eventPublisher) {
		this.eventPublisher = null;
	}
	

	protected void activate() {
		try {
			logger.debug("Starting up CMD servlet at " + WEBAPP_ALIAS + SERVLET_NAME);

			Hashtable<String, String> props = new Hashtable<String, String>();
			httpService.registerServlet(WEBAPP_ALIAS + SERVLET_NAME, this, props, createHttpContext());
			
		} catch (NamespaceException e) {
			logger.error("Error during servlet startup", e);
		} catch (ServletException e) {
			logger.error("Error during servlet startup", e);
		}
	}

	protected void deactivate() {
		httpService.unregister(WEBAPP_ALIAS + SERVLET_NAME);
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void service(ServletRequest req, ServletResponse res)
			throws ServletException, IOException {
		for(Object key : req.getParameterMap().keySet()) {
			String itemName = key.toString();
			
			if(!itemName.startsWith("__")) { // all additional webapp params start with "__" and should be ignored
				String commandName = req.getParameter(itemName);
				try {
					Item item = itemRegistry.getItem(itemName);
					
					// we need a special treatment for the "TOGGLE" command of switches;
					// this is no command officially supported and must be translated 
					// into real commands by the webapp.
					if ((item instanceof SwitchItem || item instanceof GroupItem) && commandName.equals("TOGGLE")) {
						commandName = OnOffType.ON.equals(item.getStateAs(OnOffType.class)) ? "OFF" : "ON";
					}
					
					Command command = TypeParser.parseCommand(item.getAcceptedCommandTypes(), commandName);
					if(command!=null) {
						eventPublisher.sendCommand(itemName, command);
					} else {
						logger.warn("Received unknown command '{}' for item '{}'", commandName, itemName);						
					}
				} catch (ItemNotFoundException e) {
					logger.warn("Received command '{}' for item '{}', but the item does not exist in the registry", commandName, itemName);
				}
			}
		}
	}
	

}
