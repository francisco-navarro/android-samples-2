package com.alopec.rpg.udp;


import java.net.*;

import com.alopec.rpg.jaxb.MapData;
import com.alopec.rpg.reflection.util.ExecuteResponse;
import com.alopec.rpg.reflection.xml.XmlParserFactory;



public class UDPClient 
{
	private static int PUERTO_ESCUCHA=9879;
	private static final int TAM_BUFFER=1024;
	private static String SERVER_HOST="192.168.1.11";//79.109.132.58//192.168.1.11

	private DatagramSocket clientSocket ;
	private InetAddress IPAddress ;
	private byte[] sendData;
	private byte[] receiveData;


	public static void main(String args[]) throws Exception
	{
		//Solo por pruenbas
		ExecuteResponse respuesta=new UDPClient().run("com.alopec.rpg.world.engine.Login##false##p##2##");
		ExecuteResponse respuesta2=new UDPClient().run("com.alopec.rpg.world.engine.MoveTo##false##110##120##");
		String data =new String(respuesta.getData());
		System.out.println(data);
		//XmlParserFactory factory= new  XmlParserFactory();
		//MapData m =factory.byte2mapData(respuesta.getData());
	}

	public UDPClient() {
		try {
			IPAddress = InetAddress.getByName(SERVER_HOST);
			sendData = new byte[TAM_BUFFER];
			receiveData = new byte[TAM_BUFFER];
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public UDPClient(String HOST) {
		
		UDPClient.SERVER_HOST=HOST;
		try {
			IPAddress = InetAddress.getByName(SERVER_HOST);
			sendData = new byte[TAM_BUFFER];
			receiveData = new byte[TAM_BUFFER];
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public UDPClient(String HOST,int port) {
		
		UDPClient.PUERTO_ESCUCHA=port;
		UDPClient.SERVER_HOST=HOST;
		try {
			IPAddress = InetAddress.getByName(SERVER_HOST);
			sendData = new byte[TAM_BUFFER];
			receiveData = new byte[TAM_BUFFER];
		} catch (Exception e) {
			e.printStackTrace();
		}
				
	}


	public synchronized ExecuteResponse run(String args) {

		String sentence=args;
		try{
			
			clientSocket = new DatagramSocket();

			sendData = sentence.getBytes();
			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, PUERTO_ESCUCHA);
			clientSocket.send(sendPacket);
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			clientSocket.receive(receivePacket);
			ExecuteResponse respuesta=new ExecuteResponse(receivePacket.getData());
			System.out.println("FROM SERVER:" + respuesta.getMsg());
			clientSocket.close();

			return respuesta;

		}catch (Exception e) {
			e.printStackTrace();
			return new ExecuteResponse(e.getMessage(),ExecuteResponse.CODE_KO);
		}
	}
}