package de.blase16.jajbot.moduls;

import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.filter.AndFilter;
import org.jivesoftware.smack.filter.FromMatchesFilter;
import org.jivesoftware.smack.filter.OrFilter;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.filter.PacketTypeFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;

import de.blase16.jajbot.JAJBofModul;
import de.blase16.jajbot.JAJBot;

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

    public String about() {
	return "This is the standart admin modul";
    }

    public String getCompatibility() {
	// TODO Auto-generated method stub
	return null;
    }

    public PacketFilter getFilter() {
	return this.filter;
    }

    public String getHelp() {
	// TODO Auto-generated method stub
	return null;
    }

    public String getVersion() {
	// TODO Auto-generated method stub
	return null;
    }

    public void processPacket(Packet packet) {
	Message msg = (Message) packet;
	String body = msg.getBody();
	if (body.toLowerCase().equals("die")) {
	    this.die(packet);
	}
    }

    private void die(Packet packet) {
	Message answer = new Message(packet.getFrom());
	answer.setBody("Marks died, Lenin died and I have to go also...");
	this.connection.sendPacket(answer);
	System.exit(0);
    }

}
