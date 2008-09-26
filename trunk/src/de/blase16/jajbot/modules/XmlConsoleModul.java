/*
 *  XmlConsoleModul.java - is a module for JAJBoF
 *  Copyright (C) 2006  kalkin
 *  This file is part of JAJBoF (Just Another Jabber Bot Framework) .
 *   
 *  JAJBoF (Just Another Jabber Bot Framework) is free software; 
 *  you can redistribute it and/or modify it under the terms of 
 *  the GNU Lesser General Public License as published by the Free Software 
 *  Foundation; version 2 of the License.
 *  
 *  
 *  JAJBoF is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser General Public License for more details.
 *  
 *  
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with JAJBoF; if not, write to the Free Software
 *  Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA
 */

package de.blase16.jajbot.modules;

import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.packet.Packet;

import de.blase16.jajbot.JAJBotModule;
import de.blase16.jajbot.JAJBotModuleI;
import de.blase16.jajbot.filters.NotAPacketFilter;

/**
 * @author kalkin
 *
 */
public class XmlConsoleModul extends JAJBotModule implements JAJBotModuleI {

    private NotAPacketFilter filter;
    
    public XmlConsoleModul(XMPPConnection connection) {
	super(connection);
	filter  = new NotAPacketFilter();
	this.modPrefix = "xml";
	this.about = null;
	this.version = null;
    }

    public void processPacket(Packet packet) {
	System.out.println(packet.toXML());
	super.processPacket(packet);
    }
    
    public PacketFilter getFilter()
    {
	return filter;
    }

    public String getCompatibility() {
	return null;
    }
    
}
