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

import de.blase16.jajbot.JAJBotModuleI;
import de.blase16.jajbot.filters.NotAPacketFilter;

/**
 * @author kalkin
 *
 */
public class XmlConsoleModul implements JAJBotModuleI {

    private NotAPacketFilter filter;
    
    /**
     * @param connection 
     * 
     */
    public XmlConsoleModul(XMPPConnection connection) {
	filter  = new NotAPacketFilter();
    }

    /* (non-Javadoc)
     * @see org.jivesoftware.smack.PacketListener#processPacket(org.jivesoftware.smack.packet.Packet)
     */
    public void processPacket(Packet packet) {
	System.out.println(packet.toXML());
    }

    public PacketFilter getFilter()
    {
	return filter;
    }

    public void cmdAbout(Packet packet) {
    }

    public void cmdHelp(Packet packet) {
    }

    public void cmdVersion(Packet packet) {
    }

    public String getCompatibility() {
	return null;
    }
    
}
