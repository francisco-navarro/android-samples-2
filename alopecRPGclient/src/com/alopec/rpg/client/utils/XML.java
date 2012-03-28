package com.alopec.rpg.client.utils;

import java.io.ByteArrayInputStream;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import com.alopec.rpg.jaxb.MapData;

public class XML {

	 

	public static MapData deserializaXml(byte[] data){
		
		try {			
			ByteArrayInputStream is=new ByteArrayInputStream(data);
			Serializer serializer = new Persister();
			MapData example = serializer.read(MapData.class,is);
			return example;
		} catch (Exception e) {			
			e.printStackTrace();
		}
		return null;
	}
}
