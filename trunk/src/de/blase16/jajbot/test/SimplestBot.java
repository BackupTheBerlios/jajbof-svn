package de.blase16.jajbot.test;

import org.jivesoftware.smack.XMPPException;

import de.blase16.jajbot.JAJBot;

public class SimplestBot extends JAJBot {

    public SimplestBot(String user, String pw) throws XMPPException
    {
	super(user, pw);
    }
    
    /**
     * @param args
     */
    public static void main(String[] args) {
	try {
	    SimplestBot me = new SimplestBot("baader-meinhof@example.org", "komplex");
	} catch (XMPPException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	    
    }
    
    public String nextMessage(String msg, String user)
    {
	System.out.println(msg);
	return "Ok";
    }

}
