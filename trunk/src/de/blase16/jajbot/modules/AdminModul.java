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
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.filter.PacketTypeFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;

import de.blase16.jajbot.JAJBofModul;

public class AdminModul implements JAJBofModul {

    private AndFilter filter;

    private String[] admins = { "kalkin@jabber.ccc.de", "vt100@jabber.ccc.de" };

    private XMPPConnection connection;

    public AdminModul(XMPPConnection connection) {
	this.connection = connection;
	OrFilter orFilter = new OrFilter();

	for (String admin : admins) {
	    orFilter.addFilter(new FromMatchesFilter(admin));
	}

	filter = new AndFilter(new PacketTypeFilter(Message.class));
	filter.addFilter(orFilter);
    }

    public PacketFilter getFilter() {
	return this.filter;
    }

    public void processPacket(Packet packet) {
	Message msg = (Message) packet;
	String body = msg.getBody();
	String cmd = null;
	System.out.println(body);
	if (!body.startsWith("#!")) {
	    return;
	} else {
	    if (body.contains(" ")) {
		int t = body.indexOf(" ");
		cmd = body.substring(2, t);
	    } else {
		cmd = body.substring(2);
	    }
	    
	}
	try {
	    this.getClass().getMethod(cmd, Packet.class).invoke(this, packet);
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    public void die(Packet packet) {
	Message answer = new Message(packet.getFrom());
	answer.setBody("Marx died, Lenin died and I have to go also...");
	this.connection.sendPacket(answer);
	System.exit(0);
    }

    public void about(Packet packet) {
	Message answer = new Message(packet.getFrom());
	answer.setBody("I'm just a Bot");
	this.connection.sendPacket(answer);
    }

    public void help(Packet packet) {
    }

    public void version(Packet packet) {
    }

    public String getCompatibility() {
	return null;
    }

}
