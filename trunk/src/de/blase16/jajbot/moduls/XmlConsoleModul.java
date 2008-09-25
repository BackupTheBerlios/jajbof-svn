/**
 * 
 */
package de.blase16.jajbot.moduls;

import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.packet.Packet;

import de.blase16.jajbot.JAJBofModul;
import de.blase16.jajbot.filters.NotAPacketFilter;

/**
 * @author kalkin
 *
 */
public class XmlConsoleModul implements JAJBofModul {

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

    public String about() {
	// TODO Auto-generated method stub
	return null;
    }

    public String getCompatibility() {
	// TODO Auto-generated method stub
	return null;
    }

    public String getHelp() {
	// TODO Auto-generated method stub
	return null;
    }

    public String getVersion() {
	// TODO Auto-generated method stub
	return null;
    }
    
}
