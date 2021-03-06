/*
 *  JAJBot.java - the main class of the JAJBoF.
 *  Copyright (C) 2006  kalkin
 *  This file is part of JAJBoF (Just Another Jabber Bot Framework) .
 *   
 *  JAJBoF (Just Another Jabber Bot Framework) is free software; 
 *  you can redistribute it and/or modify it under the terms of 
 *  the GNU Lesser General Public License as published by the Free Software 
 *  Foundation; version 2 of the License.
 *  
 *  
 *  JAJBoF is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser General Public License for more details.
 *  
 *  
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with JAJBoF; if not, write to the Free Software
 *  Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA
 */

package de.blase16.jajbot;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Properties;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.filter.PacketTypeFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.Presence;

/**
 * @author <a href="mailto:kalkin-@web.de">kalkin</a>
 * 
 */

public class JAJBot {

    private boolean useSSL = false;

    public XMPPConnection connection;

    private Properties config;

    private String jid, pw;

    private String ressource = "JAJBof";

    private String about = "Just another bot which is using JAJBoF";

    private String help = "Sorry but i can't help you :(";

    private int session_timeout = 300;

    private String warnMessage = "You are banned, you fucking spammer!";
    
    private String blockedMessage = "You are banned, you fucking spammer!";

    private boolean loggingOnConsole = true;

    private String freeForChatMessage = "I feel lonely talk with me";

    private String availableMessage = "Here I'm!";

    private String dndMessage = "I hate my Work! :(";

    private int availableStat = 10;

    private int dndStat = 25;

    private boolean isConnected = false;

    private int historySize = 200;

    private Presence presence;

    // TODO: remove my JID
    private String admin = "kalkin@jabber.ccc.de";

    private Roster roster;

    protected HashMap<String, History> map = new HashMap<String, History>();

    protected LinkedList<String> warned = new LinkedList<String>();

    protected LinkedList<String> banned = new LinkedList<String>();

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
     * With this method you can load the complete config from a *.properties
     * file
     * 
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

    /**
     * This method connect the bot to a jabber server.
     * 
     * @param jid
     * @param pw
     * @param ressource
     * @throws XMPPException
     */
    private void connect(String jid, String pw, String ressource)
	    throws XMPPException {
	
	if (!isConnected) {
	    if (useSSL) {
		ConnectionConfiguration conConf = new ConnectionConfiguration(
			parseJID(jid)[1]);
		conConf
			.setSecurityMode(ConnectionConfiguration.SecurityMode.required);
		connection = new XMPPConnection(conConf);
	    }
	    if (!useSSL)
		connection = new XMPPConnection(parseJID(jid)[1]);
	    connection.connect();
	    connection.login(parseJID(jid)[0], pw, ressource);
	    presence = new Presence(Presence.Type.available);
	    presence.setMode(Presence.Mode.chat);
	    presence.setStatus(freeForChatMessage);
	    connection.sendPacket(presence);
	    roster = connection.getRoster();
	    roster.setSubscriptionMode(Roster.SubscriptionMode.accept_all);
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
			makeHistory(user, message);
		    } else {
			History history = map.get(user);
			history.addMessage(message.getBody());
			map.put(user, history);
		    }
		    if (!threads.containsKey(user)) {
			Thread session = new Thread() {
			    @Override
			    public void run() {
				runThreadRun(user, message);
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

    private void runThreadRun(String user, Message message) {
	// Run Forrest, run!
	History history = map.get(user);
	int index = history.indexOf(message.getBody());
	String msg = history.get(index);
	Message answer = new Message();
	answer.setTo(user);
	answer.setType(message.getType());
	do {
	    msg = history.get(index);
	    if (loggingOnConsole)
		System.out.println("<" + user + "> " + msg);

	    if (history.isBlocked()) {
		banned.add(user);
		answer.setBody(blockedMessage);
		connection.sendPacket(answer);
		break;
	    }
	    if (history.isWarned() && !warned.contains(user)) {
		warned.add(user);
		answer.setBody(warnMessage);
		connection.sendPacket(answer);
	    } else {
		answer.setBody(handleMessage(msg, user));
		connection.sendPacket(answer);
	    }
	    history = map.get(user);
	    index++;
	} while (!msg.equals(history.getLast()));
	threads.toString();
	threads.remove(user);
    }

    private String handleMessage(String msg, String user) {
	if (user.equals(admin) && msg.equalsIgnoreCase("die")) {
	    connection.disconnect();
	    System.out
		    .println("\nMarks died, Lenin died and I have to go also...");
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

	return nextMessage(msg, user);
    }

    /**
     * This method have to be replaced when you write your own bot
     * 
     * @param history
     * @return A string which is send to the user.
     */
    public String nextMessage(String msg, String user) {
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
	    if (map.size() == 0) {
		presence.setMode(Presence.Mode.chat);
		presence.setStatus(freeForChatMessage);
		connection.sendPacket(presence);
	    } else if (map.size() < availableStat) {
		presence.setMode(Presence.Mode.available);
		presence.setStatus(availableMessage);
		connection.sendPacket(presence);
	    } else if (map.size() < dndStat) {
		presence.setMode(Presence.Mode.dnd);
		presence.setStatus(dndMessage);
		connection.sendPacket(presence);
	    }

	}
    } // end of updateSessions()

    private void makeHistory(String user, Message message) {
	History history;
	if (historySize > 0)
	    history = new History(historySize);
	else
	    history = new History();

	history.addMessage(message.getBody());
	map.put(user, history);
    }

    private String printLicense() {
	String lizense = "\nJAJBoF (Just Another Jabber Bot Framework) version 0.23,\n";
	lizense += "Copyright (C) 2006 kalkin\n";
	lizense += "JAJBoF comes with ABSOLUTELY NO WARRANTY. This is free\n";
	lizense += "software, and you are welcome to redistribute it under\n";
	lizense += "certain conditions; for details see the LICENSE file.\n";
	return lizense;
    }

}
