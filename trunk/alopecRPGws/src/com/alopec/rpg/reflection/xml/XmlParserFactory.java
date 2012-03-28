package com.alopec.rpg.reflection.xml;

import java.io.ByteArrayInputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.axis.utils.ByteArrayOutputStream;

import com.alopec.rpg.jaxb.MapData;
import com.alopec.rpg.jaxb.ObjectFactory;
import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;

public class XmlParserFactory {
	
	private JAXBContext jc;
	private Unmarshaller u ;
	private Marshaller m ;
	private ObjectFactory factory;

	public XmlParserFactory(){
		
		try {
			
			jc = JAXBContext.newInstance( "com.alopec.rpg.jaxb");
			u= jc.createUnmarshaller();
			m= jc.createMarshaller();
			factory= new ObjectFactory();
					
		} catch (JAXBException e) {
			
			e.printStackTrace();
		}
		
	}
	
	public MapData newData(){
		
		MapData mapData= factory.createMapData();
		//mapData.getMobiles().add(factory.createMobile());
		//mapData.getPlayers().add(factory.createPlayer());
		
		
		return mapData;
		
	}
	
	public byte[] mapData2byte(MapData mapData){
		
		ByteArrayOutputStream os= new ByteArrayOutputStream();
	
		try {
			
			m.marshal(mapData, os);
			return os.toByteArray();
			
		} catch (JAXBException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public MapData byte2mapData(byte[] data){
		
		ByteArrayInputStream is = new ByteArrayInputStream(data);
		try {
			
			MapData map= (MapData)u.unmarshal(is);
			return map;
			
		} catch (JAXBException e) {
			e.printStackTrace();
			return null;
		}
	}
		
	
}
