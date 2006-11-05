/*
 *  JAJBot.java - the main part of the JAJBoF.
 *  Copyright (C) 2006  kalkin (Bahtiar Gadimov)
 *  This file is part of JAJBoF (Just Another Jabber Bot Framework) .
 *   
 *  JAJBoF (Just Another Jabber Bot Framework) is free software; 
 *  you can redistribute it and/or modify it under the terms of 
 *  the GNU General Public License as published by the Free Software 
 *  Foundation; either version 2 of the License.
 *  
 *  
 *  JAJBoF is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *  
 *  
 *  You should have received a copy of the GNU General Public License
 *  along with JAJBoF; if not, write to the Free Software
 *  Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA
 */

package de.blase16.jajbof;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Properties;

import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.SSLXMPPConnection;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.filter.PacketTypeFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;

/**
 * @author <a href="mailto:blase16@blase16.de">kalkin</a>
 * 
 */
public class JAJBot {

    private boolean useSSL = false;

    private XMPPConnection connection;

    private Properties config;

    private String jid, pw;

    private String ressource = "JAJBof";

    private String about = "Just another bot which is using JAJBoF";

    private String help = "Sorry but i can't help you :(";

    private int session_timeout = 300;

    private int availableStat = 10;

    private int dndStat = 25;

    private int warnLimit = 100;

    private int bannedLimit = 200;

    private String warnMessage = "Don't spam!\nYou will be banned!";

    private String blockedMessage = "You are banned, you fucking spammer!";

    private boolean loggingOnConsole = true;

    private String freeForChatMessage = "I feel lonely talk with me";

    private String availableMessage = "Here I'm!";

    private String dndMessage = "I hate my Work! :(";

    private boolean isConnected = false;

    private int historySize = 200;

    // TODO: remove my JID
    private String admin = "kalkin@jabber.ccc.de";

    private Roster roster;

    protected HashMap<String, History> map = new HashMap<String, History>();

    protected LinkedList<String> warned = new LinkedList();

    protected LinkedList<String> banned = new LinkedList();

    protected HashMap<String, Thread> threads = new HashMap<String, Thread>();

    public JAJBot(String fileName) throws XMPPException {
	System.out.println(printLicense());
	if (loadConfig(new File(fileName))) {
	    connect(jid, pw, ressource);
	    waitForPackets();
	    while (true)
		try {
		    Thread.sleep(2000);
		    if (map.size() > 0)
			updateSessions();
		} catch (InterruptedException e) {
		    e.printStackTrace();
		}
	} else
	    System.out.println("The file does not exist or has wrong syntax.");
    }

    public JAJBot(String jid, String pw) throws XMPPException {
	System.out.println(printLicense());
	this.jid = jid;
	this.pw = pw;
	connect(this.jid, this.pw, ressource);
	waitForPackets();
	while (true)
	    try {
		Thread.sleep(2000);
		if (map.size() > 0)
		    updateSessions();
	    } catch (InterruptedException e) {
		e.printStackTrace();
	    }
    }

    public JAJBot(String jid, String pw, boolean ssl) throws XMPPException {
	System.out.println(printLicense());
	this.jid = jid;
	this.pw = pw;
	useSSL = ssl;
	connect(this.jid, this.pw, ressource);
	waitForPackets();
	while (true)
	    try {
		Thread.sleep(2000);
		if (map.size() > 0)
		    updateSessions();
	    } catch (InterruptedException e) {
		e.printStackTrace();
	    }
    }

    public JAJBot(String jid, String pw, String ressource, boolean ssl)
	    throws XMPPException {
	System.out.println(printLicense());
	this.jid = jid;
	this.pw = pw;
	this.ressource = ressource;
	useSSL = ssl;
	connect(this.jid, this.pw, this.ressource);
	waitForPackets();
	while (true)
	    try {
		Thread.sleep(2000);
		if (map.size() > 0)
		    updateSessions();
	    } catch (InterruptedException e) {
		e.printStackTrace();
	    }
    }

    /**
         * @param fileName
         * @return
         */
    private boolean loadConfig(File fileName) {
	config = new Properties();
	try {
	    config.load(new FileInputStream(fileName));

	    jid = config.getProperty("JID");

	    pw = config.getProperty("PASSWORD");

	    if (config.getProperty("SSL") != null)
		useSSL = Boolean.parseBoolean(config.getProperty("SSL"));

	    if (config.getProperty("ABOUT") != null)
		about = config.getProperty("ABOUT");

	    if (config.getProperty("HELP") != null)
		help = config.getProperty("HELP");

	    if (config.getProperty("SESSION_TIMEOUT") != null)
		session_timeout = Integer.parseInt(config
			.getProperty("SESSION_TIMEOUT"));

	    if (config.getProperty("AVAILABLE_STAT") != null)
		availableStat = Integer.parseInt(config
			.getProperty("AVAILABLE_STAT"));
	    if (config.getProperty("DND_STAT") != null)
		dndStat = Integer.parseInt(config.getProperty("DND_STAT"));

	    if (config.getProperty("WARN_LIMIT") != null)
		warnLimit = Integer.parseInt(config.getProperty("WARN_LIMIT"));

	    if (config.getProperty("BANNED_LIMIT") != null)
		bannedLimit = Integer.parseInt(config
			.getProperty("BANNED_LIMIT"));

	    if (config.getProperty("WARN_MSG") != null)
		warnMessage = config.getProperty("WARN_MSG");

	    if (config.getProperty("BANNED_MSG") != null)
		blockedMessage = config.getProperty("BANNED_MSG");

	    if (config.getProperty("LOG_ON_CONSOLE") != null)
		loggingOnConsole = Boolean.parseBoolean(config
			.getProperty("LOG_ON_CONSOLE"));

	    if (config.getProperty("CHAT_MSG") != null)
		freeForChatMessage = config.getProperty("CHAT_MSG");

	    if (config.getProperty("AVAILABLE_MSG") != null)
		availableMessage = config.getProperty("AVAILABLE_MSG");

	    if (config.getProperty("DND_MSG") != null)
		dndMessage = config.getProperty("DND_MSG");

	    if (config.getProperty("RESSOURCE") != null)
		ressource = config.getProperty("RESSOURCE");

	    if (config.getProperty("HISTORY_SIZE") != null) {
		historySize = Integer.parseInt(config
			.getProperty("HISTORY_SIZE"));
		if (historySize < 0)
		    historySize = 0;
	    }
	    if (config.getProperty("ADMIN") != null)
		admin = config.getProperty("ADMIN");
	} catch (FileNotFoundException e) {
	    System.out.println("Damn it there is no " + fileName);
	} catch (IOException e) {
	    e.printStackTrace();
	}
	if (jid != null && pw != null)
	    return true;
	else
	    return false;
    }

    private void connect(String jid, String pw, String ressource)
	    throws XMPPException {
	if (!isConnected) {
	    if (useSSL)
		connection = new SSLXMPPConnection(parseJID(jid)[1]);
	    if (!useSSL)
		connection = new XMPPConnection(parseJID(jid)[1]);

	    connection.login(parseJID(jid)[0], pw, ressource);
	    roster = connection.getRoster();
	    roster.setSubscriptionMode(Roster.SUBSCRIPTION_ACCEPT_ALL);
	}
    }

    private void waitForPackets() {
	PacketFilter filter = new PacketTypeFilter(Message.class);
	PacketListener myListener = new PacketListener() {
	    public void processPacket(Packet packet) {
		final Message message = (Message) packet;
		final String user = message.getFrom().substring(0,
			message.getFrom().indexOf('/'));
		if (!banned.contains(user)) {
		    if (!map.containsKey(user)) {
			History history;
			if (historySize > 0)
			    history = new History(historySize);
			else
			    history = new History();

			history.addMessage(message.getBody());
			map.put(user, history);
		    } else {
			History history = map.get(user);
			history.addMessage(message.getBody());
			map.put(user, history);
		    }
		    if (!threads.containsKey(user)) {
			Thread session = new Thread() {
			    @Override
			    public void run() {
				History history = map.get(user);
				int index = history.indexOf(message.getBody());
				Message.Type msgType = message.getType();
				String msg = history.get(index);
				Message answer = new Message();
				answer.setTo(user);
				answer.setType(msgType);
				do {
				    msg = history.get(index);
				    System.out.println("<" + user + "> " + msg);
				    if (history.isBlocked()) {
					banned.add(user);
					answer.setBody(blockedMessage);
					connection.sendPacket(answer);
					break;
				    }
				    if (history.isWarned()
					    && !warned.contains(user)) {
					warned.add(user);
					answer.setBody(warnMessage);
					connection.sendPacket(answer);
				    } else {
					answer
						.setBody(handleMessage(msg,
							user));
					connection.sendPacket(answer);
				    }
				    history = map.get(user);
				    index++;
				} while (!msg.equals(history.getLast()));
				threads.toString();
				threads.remove(user);
			    }
			};
			threads.put(user, session);
			session.start();
		    }
		}
	    }
	};
	connection.addPacketListener(myListener, filter);
    }

    private String handleMessage(String msg, String user) {
	if (user.equals(admin) && msg.equalsIgnoreCase("die")) {
	    connection.close();
	    System.exit(0);
	} else if (!user.equals(admin) && msg.equalsIgnoreCase("die"))
	    return "I'm immortal! Only root can kill me. *mad laughing*";
	else if (msg.equalsIgnoreCase("about"))
	    return about;
	else if (msg.equalsIgnoreCase("help"))
	    return help;
	else if (msg
		.equalsIgnoreCase("the answer to life, the universe and everything"))
	    return "42";
	else if (msg.equalsIgnoreCase("history"))
	    return map.get(user).getUserHistory();
	else if (msg.equalsIgnoreCase("lizense"))
	    return printLicense();

	return unknownMessage(map.get(user));
    }

    /**
         * This method have to be replaced when you write your own bot
         * 
         * @param history
         * @return A string which is send to the user.
         */
    public String unknownMessage(History history) {
	return null;
    }

    public String[] parseJID(String jid) {
	String result[] = new String[2];
	result[0] = jid.substring(0, jid.indexOf('@'));
	result[1] = jid.substring(jid.indexOf('@') + 1);
	return result;
    }

    public void updateSessions() {
	Iterator keys = map.keySet().iterator();
	while (keys.hasNext()) {
	    String key = (String) keys.next();
	    History history = map.get(key);
	    if (System.currentTimeMillis() - history.lastUsed() > session_timeout * 1000)
		map.remove(key);
	}
    } // end of updateSessions()

    private String printLicense() {
	String lizense = "\nJAJBoF (Just Another Jabber Bot Framework) version 0.23,\n";
	lizense += "Copyright (C) 2006 kalkin (Bahtiar Gadimov)\n";
	lizense += "JAJBoF comes with ABSOLUTELY NO WARRANTY. This is free\n";
	lizense += "software, and you are welcome to redistribute it under\n";
	lizense += "certain conditions; for details see the LICENSE file.\n";
	return lizense;
    }
}
