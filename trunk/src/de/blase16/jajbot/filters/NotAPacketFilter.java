/*
 *  NotAPacketFilter.java - is some kind of filter based on org.jivesoftware.smack.filter.PacketFilter
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

package de.blase16.jajbot.filters;

import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.packet.Packet;

/**
 * This class is an workaround for collecting all packets. XMPPConnection can
 * use a PacketListener together with an PacketFilter. So this is a class which
 * implements the PacketFilter interface and accepts every packet
 * 
 * @author <a href="mailto:mail@kalkin.de">kalkin</a>
 */
public class NotAPacketFilter implements PacketFilter {
    /**
     * 
     * @see org.jivesoftware.smack.filter.PacketFilter#accept(org.jivesoftware.smack.packet.Packet)
     * @return true Returns always true
     */
    public boolean accept(Packet packet) {
        return true;
    }

}
