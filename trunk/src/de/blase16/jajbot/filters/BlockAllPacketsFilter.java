/*
 *  BlockAllPacketsFilter.java - is some kind of filter based on org.jivesoftware.smack.filter.PacketFilter
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
 * @author <a href="mailto:mail@kalkin.de">kalkin</a>
 */
public class BlockAllPacketsFilter implements PacketFilter {

    /**
     * Rejects all Packets.
     * @see org.jivesoftware.smack.filter.PacketFilter#accept(org.jivesoftware.smack.packet.Packet)
     */
    public boolean accept(Packet arg0) {
        return false;
    }

}
