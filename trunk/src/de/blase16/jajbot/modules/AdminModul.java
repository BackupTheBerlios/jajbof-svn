/*
 *  AdminModul.java - is an administration module for JAJBoF
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
import org.jivesoftware.smack.filter.AndFilter;
import org.jivesoftware.smack.filter.FromMatchesFilter;
import org.jivesoftware.smack.filter.OrFilter;
import org.jivesoftware.smack.filter.PacketTypeFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;

import de.blase16.jajbot.JAJBotModule;
import de.blase16.jajbot.JAJBotModuleI;

public class AdminModul extends JAJBotModule implements JAJBotModuleI {

    protected AndFilter filter;
    protected String[] admins = { "kalkin@jabber.ccc.de", "vt100@jabber.ccc.de" };

    public AdminModul(XMPPConnection conn) {
        super(conn);
        this.modPrefix = "";
        this.about = "Das ist ein Bot. Es benutzt das JAJBoF.\n"
                + "Der Bot gehšrt kalkin@jabber.ccc.de.\n "
                + "Mit !shutdown kann kalkin und vt100 ihn abschalten";
        this.version = "0.2";

        OrFilter orFilter = new OrFilter();

        for (String admin : admins) {
            orFilter.addFilter(new FromMatchesFilter(admin));
        }

        filter = new AndFilter(new PacketTypeFilter(Message.class));
        filter.addFilter(orFilter);
    }

    public void cmdShutdown(Packet packet) {
        sendMessage(packet.getFrom(),
                "Marx died, Lenin died and I have to go also...");
        System.exit(0);
    }

}
