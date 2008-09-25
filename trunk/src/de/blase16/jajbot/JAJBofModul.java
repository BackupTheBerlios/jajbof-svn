/**
 * This is an interface for JAJBof Moduls.
 */
package de.blase16.jajbot;

import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.packet.Packet;

/**
 * @author kalkin
 * 
 */
public interface JAJBofModul extends PacketListener {

    /**
     * Do something with the packet
     */
    public void processPacket(Packet packet);

    /**
     * The filter is used for filtering the packets which belong to this modul
     * @return
     */
    public PacketFilter getFilter();

    /**
     * Something about this modul
     * @return
     */
    public String about();

    /**
     * Will be used for versioning the moduls
     * 
     * @return version string
     */
    public String getVersion();

    /**
     * Will be used for checking if this modul could be used with this version
     * of the JAJBof
     * 
     * @return
     */
    public String getCompatibility();
    
    /**
     * Return some helpful information
     * @return helpful information
     */
    public String getHelp();

}
