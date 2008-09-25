package de.blase16.jajbot.bots;

import org.jivesoftware.smack.XMPPException;

import de.blase16.jajbot.JAJBot;

public class SimplestBot extends JAJBot {

    public SimplestBot(String file) throws XMPPException {
	super(file);
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
	try {
	    @SuppressWarnings("unused")
	    SimplestBot me = new SimplestBot("config.properties");
	} catch (XMPPException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

    }

    public String nextMessage(String msg, String user) {
	System.out.println(msg + " : " + user);
	return null;
    }

}
