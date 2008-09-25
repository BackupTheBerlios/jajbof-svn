/*
 *  History.java - organize the message history.
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

import java.util.LinkedList;

/**
 * @author <a href="mailto:kalkin-@web.de">kalkin</a>
 * 
 */
public class History extends LinkedList<String> {

    /**
         * 
         */
   private static final long serialVersionUID = -3475428678636945627L;

    /**
         * 
         */
    private int initialCapacity;

    /**
         * A long which contains the time, the History was changed last time.
         */
    private long lastUsedTime;

    /**
         * 
         */

    private long last20Time = System.currentTimeMillis();

    private boolean warned = false, blocked = false;

    private boolean limited;

    private boolean spam = false; 
    
    /**
         * 
         */
    public History() {
	super();
	limited = false;
    }

    /**
         * @param arg0
         */
    public History(int arg0) {
	super();
	limited = true;
	initialCapacity = arg0;
    }

    public void spamDisabled()
    {
	this.spam = true;
    }
    
    public void spamEnabled()
    {
	this.spam = false;
    }
    /**
         * @param msg
         */
    public void addMessage(String msg) {
	if (limited && size() == initialCapacity)
	    remove();
	add(msg);
	lastUsedTime = System.currentTimeMillis();
	if (size() % 20 == 0 && this.spam) {
	    if (size() >= 20) {
		if (lastUsedTime - last20Time <= 18000) {
		    if (warned){
			blocked = true;
			System.out.println("blocked");
		    }
		    if (!warned){
			warned = true;
			System.out.println("warned");
		    }
		}
	    }
	    last20Time = System.currentTimeMillis();
	}
    }

    /**
         * Generates the user history and returns it as a String
         * 
         * @return user history.
         */
    public String getUserHistory() {
	String history = toString();
	history = history.substring(1);
	history = history.substring(0, history.length() - 1);
	history = "\n" + history.replaceAll(", ", "\n");
	return history;
    }

    /**
         * Returns the {@link long} lastUsedTime which contains the time, the
         * History was changed.
         * 
         * @return lastUsedTime
         */
    protected long lastUsed() {
	return lastUsedTime;
    }
    
    protected boolean isBlocked(){
	return blocked;
    }
    
    protected boolean isWarned(){
	return warned;
    }
}