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
