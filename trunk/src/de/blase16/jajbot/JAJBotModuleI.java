/*
 * This is an interface for JAJBof modules.
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

package de.blase16.jajbot;

import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.packet.Packet;

/**
 * This is an interface for JAJBof modules.
 * 
 * @author kalkin
 * 
 */
public interface JAJBotModuleI extends PacketListener {

    /**
     * Do something with the packet
     */
    public void processPacket(Packet packet);

    /**
     * The filter is used for filtering the packets which belong to this modul
     * 
     * @return
     */
    public PacketFilter getFilter();

    /**
     * Something about this modul
     * 
     * @return
     */
    public void cmdAbout(Packet packet);

    /**
     * Will be used for versioning the moduls
     * 
     * @return version string
     */
    public void cmdVersion(Packet packet);

    /**
     * Will be used for checking if this modul could be used with this version
     * of the JAJBof
     * 
     * @return
     */
    public String getCompatibility();

    /**
     * Return some helpful information
     * 
     * @return helpful information
     */
    public void cmdHelp(Packet packet);

}
