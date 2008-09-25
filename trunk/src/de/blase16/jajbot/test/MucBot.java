package de.blase16.jajbot.test;

import org.jivesoftware.smack.XMPPException;

import de.blase16.jajbot.JAJBot;

public class MucBot extends JAJBot {

    public MucBot(String jid, String pw) throws XMPPException {
	super(jid, pw);
	// TODO Auto-generated constructor stub
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
	try {
	    @SuppressWarnings("unused")
	    MucBot me = new MucBot("kalkin_bot@jabber.ccc.de",
		    "vegeta");
	} catch (XMPPException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

    }


}
