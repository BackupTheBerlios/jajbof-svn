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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.LinkedList;
import java.util.Properties;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.util.StringUtils;

import com.sun.org.apache.bcel.internal.util.ClassPath;

import de.blase16.jajbot.modules.AdminModul;

/**
 * @author <a href="mailto:mail@kalkin.de">kalkin</a>
 * 
 */

public class JAJBot {

    private boolean useSSL = false;

    public XMPPConnection connection;

    private Properties config;

    private String jid, pw;

    private String statusMsg = "Here we're. Born to be kings...";

    private String ressource = "JAJBof";

    private boolean isConnected = false;

    private Presence presence;

    private Roster roster;

    private String modulePath = "./";

    private String moduleFile = "modules";

    public JAJBot(String fileName) throws XMPPException {
	System.out.println(printLicense());
	if (loadConfig(new File(fileName))) {
	    connect(jid, pw, ressource);
	    waitForPackets();
	    while (true)
		try {
		    Thread.sleep(2000);
		} catch (InterruptedException e) {
		    e.printStackTrace();
		}
	} else
	    System.out.println("The file does not exist or has wrong syntax.");
    }

    /**
     * With this method you can load the complete configration from a
     * *.properties file
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

	    if (config.getProperty("RESSOURCE") != null)
		ressource = config.getProperty("RESSOURCE");

	    if (config.getProperty("STATUS_MSG") != null)
		statusMsg = config.getProperty("STATUS_MSG");

	    if (config.getProperty("MODULE_PATH") != null)
		modulePath = config.getProperty("MODULE_PATH");

	    if (config.getProperty("MODULE_FILE") != null)
		moduleFile = config.getProperty("MODULE_FILE");

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
			StringUtils.parseServer(jid));
		conConf
			.setSecurityMode(ConnectionConfiguration.SecurityMode.required);
		connection = new XMPPConnection(conConf);
	    }
	    if (!useSSL)
		connection = new XMPPConnection(StringUtils.parseServer(jid));
	    connection.connect();
	    connection.login(StringUtils.parseName(jid), pw, ressource);
	    presence = new Presence(Presence.Type.available);
	    presence.setMode(Presence.Mode.available);
	    presence.setStatus(this.statusMsg);
	    connection.sendPacket(presence);
	    roster = connection.getRoster();
	    roster.setSubscriptionMode(Roster.SubscriptionMode.accept_all);
	}
    }

    private void waitForPackets() {
	LinkedList<Class<JAJBotModuleI>> modules = getModules();
	if (modules == null) {
	    System.out.println("No modules loaded");
	    return;
	}
	for (Class<JAJBotModuleI> module : modules) {

	    try {
		JAJBotModuleI mod = (JAJBotModuleI) module.getConstructors()[0]
			.newInstance(connection);
		connection.addPacketListener(mod, mod.getFilter());
	    } catch (Exception e) {
		System.out.println("Couldn't create an instace of the module "
			+ module.getName());
	    }
	}
    }

    private String printLicense() {
	String lizense = "\nJAJBoF (Just Another Jabber Bot Framework) version 0.23,\n";
	lizense += "Copyright (C) 2006 kalkin\n";
	lizense += "JAJBoF comes with ABSOLUTELY NO WARRANTY. This is free\n";
	lizense += "software, and you are welcome to redistribute it under\n";
	lizense += "certain conditions; for details see the LICENSE file.\n";
	return lizense;
    }

    /**
     * Reads the module files and generates a {@link LinkedList} with all the
     * modules to load. Used by getModules();
     * 
     * @see JAJBot.getModules()
     * @return {@link LinkedList}
     */
    private LinkedList<String> getModulesNames() {
	LinkedList<String> modules = new LinkedList<String>();
	BufferedReader f = null;
	try {
	    f = new BufferedReader(new FileReader(new File(this.moduleFile)));
	    while (true) {
		String module = f.readLine();
		if (module == null) {
		    break;
		}
		modules.add(module);
	    }
	} catch (Exception e) {
	    System.out.println("The file " + this.moduleFile
		    + " doesn't exists");
	}

	if (modules.size() == 0) {
	    System.out.println("No modules to load");
	    return null;
	}

	return modules;
    }

    /**
     * Adds all jar files in modulPath/modules-enabled directory to the
     * classpath, and loads all modules from the modules file
     * 
     * @return {@link LinkedList}
     */
    @SuppressWarnings("unchecked")
    private LinkedList<Class<JAJBotModuleI>> getModules() {
	LinkedList<String> modulesNames = getModulesNames();
	LinkedList<Class<JAJBotModuleI>> loadedModules = new LinkedList<Class<JAJBotModuleI>>();
	if (modulesNames == null)
	    return null;

	File dir = new File(this.modulePath);
	File[] files = dir.listFiles();

	URL[] urls = new URL[files.length];
	for (int i = 0; i < files.length; i++) {
	    try {
		if (files[i].toURL().toString().endsWith("jar")) {
		    urls[i] = files[i].toURL();
		}
	    } catch (MalformedURLException e) {
		System.out.println(e.getMessage());
	    }
	}
	URLClassLoader cl = new URLClassLoader(urls);

	for (String name : modulesNames) {
	    System.out.println("Trying to load " + name);
	    try {
		loadedModules.add((Class<JAJBotModuleI>) Class.forName(name,
			false, cl));
		System.out.println("Loaded successfull " + name);
	    } catch (Exception e) {
		System.out.println("Can't find module " + name + "!");
	    }
	}

	if (loadedModules.size() == 0)
	    return null;
	return loadedModules;
    }
}
