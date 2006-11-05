/**
 * 
 */
package de.blase16.jajbot;

import org.jivesoftware.smack.XMPPException;

/**
 * @author blase16
 *
 */
public class TestBot extends JAJBot {

 
    /**
     * @param jid
     * @param pw
     * @throws XMPPException
     */
    public TestBot(String jid, String pw) throws XMPPException {
	super(jid, pw);
	// TODO Auto-generated constructor stub
    }

    public String unknownMessage(String msg, String user) {
	String result = "";
	if(msg.startsWith("suche:")){
	    int i = Integer.parseInt((msg.split("suche:")[1].trim()));
	    return "" + i++;
	}
	if(msg.equals("next")){
	    History history = map.get(user);
	    int j = history.lastIndexOf("suche:1");
	    
	    return "" + (history.size() - j);
	    
	}
	
	
	
	
	return result;
    }
}
