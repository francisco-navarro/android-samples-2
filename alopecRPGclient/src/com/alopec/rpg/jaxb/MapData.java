//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.03.04 at 09:42:01 PM CET 
//


package com.alopec.rpg.jaxb;

import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;



@Root
public class MapData {

	

	@ElementList(name="players")
	protected List<Player> players;
    
    
   
	public void setPlayers(List<Player> players) {
		this.players = players;
	}



    public List<Player> getPlayers() {
        if (players == null) {
            players = new ArrayList<Player>();
        }
        return this.players;
    }

}
