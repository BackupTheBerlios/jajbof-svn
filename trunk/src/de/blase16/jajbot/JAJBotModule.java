/**
 * 
 */
package de.blase16.jajbot;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;

import de.blase16.jajbot.filters.NotAPacketFilter;

/**
 * @author kalkin
 * 
 */
public abstract class JAJBotModule implements JAJBotModuleI {

    /**
     * This is the connection object.
     */
    protected XMPPConnection connection;

    protected PacketFilter filter;

    protected String version = "Version of the JAJBotModule is";

    protected String about = "FooBar";

    protected String modPrefix = this.getClass().getName()
	    .replace("Module", "").toLowerCase();

    protected JAJBotModule(XMPPConnection conn) {
	this.connection = conn;
    }

    /**
     * Sends some about message to the user
     * 
     * @see de.blase16.jajbot.JAJBotModuleI#cmdAbout(org.jivesoftware.smack.packet
     *      .Packet)
     */
    public void cmdAbout(Packet packet) {
	sendMessage(packet.getFrom(), this.about);

    }

    /**
     * Uses the reflection api for generating a list of all possible commands
     * used by this module
     * 
     * @see de.blase16.jajbot.JAJBotModuleI#cmdHelp(org.jivesoftware.smack.packet
     *      .Packet)
     */
    public void cmdHelp(Packet packet) {
	Method[] meth = this.getClass().getMethods();
	String body = "The modul admin contains following commands:\n";
	for (Method method : meth) {
	    Type[] types = method.getGenericParameterTypes();
	    if (types.length == 1 && types[0].equals(Packet.class)
		    && method.getName().startsWith("cmd")) {
		String cmd = method.getName().substring(3).toLowerCase();
		if (!this.modPrefix.equalsIgnoreCase("")) {
		    cmd = this.modPrefix + " " + cmd;
		}
		body += "!" + cmd + "\n";
	    }
	}
	sendMessage(packet.getFrom(), body);
    }

    /**
     * Returns the version number of this module.
     * 
     * @see de.blase16.jajbot.JAJBotModuleI#cmdVersion(org.jivesoftware.smack.packet
     *      .Packet)
     */
    public void cmdVersion(Packet packet) {
	Message msg = new Message(packet.getFrom());
	msg.setBody(this.version);
	this.connection.sendPacket(msg);
    }

    /**
     * Reserved for future use.
     * 
     * @see de.blase16.jajbot.JAJBotModuleI#getCompatibility()
     */
    public String getCompatibility() {
	// TODO Auto-generated method stub
	return null;
    }

    /**
     * Returns an instance of the PacketFilter. All packets accepted by this
     * filter re passed to this module via processPacket
     * 
     * @see org.jivesoftware.smack.filters.PacketFilter
     * @see de.blase16.jajbot.JAJBotModuleI#getFilter()
     */
    public PacketFilter getFilter() {
	return this.filter;
    }

    /**
     * Every packet which is accepted by the packetfilter of this class is
     * passed to the module with this method
     * 
     * @see de.blase16.jajbot.JAJBotModuleI#processPacket(org.jivesoftware.smack.
     *      packet.Packet)
     */
    public void processPacket(Packet packet) {
	Message msg = (Message) packet;
	String body = msg.getBody();
	System.out.println(body);
	System.out.println(this.modPrefix);
	String cmd = extractCommand(body);
	System.out.println(cmd);
	if (cmd == null)
	    return;
	cmd = "cmd" + cmd.substring(0, 1).toUpperCase() + cmd.substring(1);

	try {
	    this.getClass().getMethod(cmd, Packet.class).invoke(this, packet);
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    /**
     * Extracts from the message the command string
     * 
     * @param body
     *            String containing the message from user
     * @return the extracted command string
     */
    protected String extractCommand(String body) {
	if (!body.startsWith("!" + this.modPrefix)) {
	    return null;
	} else {
	    body = body.substring(this.modPrefix.length() + 1).trim();
	    if (body.contains(" ")) {
		int t = body.indexOf(" ");
		return body.substring(0, t);
	    } else {
		return body;
	    }

	}
    }

    /**
     * Sends a jabber message to an user.
     * 
     * @param to
     *            The Jid of the receiver
     * @param msg
     *            The message text
     */
    protected void sendMessage(String to, String msg) {
	if (msg.equals("") || msg == null) {
	    return;
	} else {
	    Message message = new Message(to);
	    message.setBody(msg);
	    connection.sendPacket(message);
	}
    }

}
