/**
 * 
 */
package de.blase16.jajbot.modules;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.AndFilter;
import org.jivesoftware.smack.filter.FromMatchesFilter;
import org.jivesoftware.smack.filter.MessageTypeFilter;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smackx.muc.MultiUserChat;

import de.blase16.jajbot.JAJBotModuleI;

/**
 * @author kalkin
 * 
 */
public class MucModule implements JAJBotModuleI {

    protected AndFilter filter;
    
    protected XMPPConnection connection;
    
    protected MultiUserChat muc;
    
    protected String about = "First Muc module";
    
    protected String version = "0.1a";

    protected String modPrefix = "";

    /**
     * @param conn
     */
    public MucModule(XMPPConnection conn, String channelname, String password) {
        this.connection = conn;
        this.filter = new AndFilter(new MessageTypeFilter(
                Message.Type.groupchat));
        this.filter.addFilter(new FromMatchesFilter(channelname));

        muc = new MultiUserChat(this.connection, channelname);
        try {
            muc.join("jajbof", password);
        } catch (XMPPException e) {
            e.printStackTrace();
        }
    }
    
    public void processPacket(Packet packet) {
        Message msg = (Message) packet;
        String body = msg.getBody();
        String cmd = extractCommand(body);
        if (cmd == null)
            return;
        cmd = "cmd" + cmd.substring(0, 1).toUpperCase() + cmd.substring(1);

        try {
            this.getClass().getMethod(cmd, Packet.class).invoke(this, packet);
        } catch (Exception e) {
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

    public void cmdAbout(Packet packet) {
        this.sendMessage(this.about);
    }

    public void cmdHelp(Packet packet) {
        Method[] meth = this.getClass().getMethods();
        String body = "The "+ this.getClass().getName() +" contains following commands:\n";
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
        this.sendMessage(body);
       
    }

    protected void sendMessage(String msg)
    {
        try {
            this.muc.sendMessage(msg);
        } catch (XMPPException e) {
            e.printStackTrace();
        }
    }
    
    protected void sendMessage(Message msg)
    {
        try {
            this.muc.sendMessage(msg);
        } catch (XMPPException e) {
            e.printStackTrace();
        }
    }
    
    public void cmdVersion(Packet packet) {
        this.sendMessage(this.version);
    }

    public String getCompatibility() {
        return null;
    }

    public PacketFilter getFilter() {
        return this.filter;
    }

}
