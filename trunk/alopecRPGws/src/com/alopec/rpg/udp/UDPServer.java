package com.alopec.rpg.udp;

import java.io.*;
import java.net.*;

import org.apache.log4j.Logger;



import com.alopec.rpg.reflection.ReflectionFactory;
import com.alopec.rpg.reflection.util.ExecuteResponse;
import com.alopec.rpg.reflection.util.ServerReflectionException;

public class UDPServer extends Thread
{
	static Logger logger = Logger.getLogger(UDPServer.class);
	
	public static final int PUERTO_ESCUCHA=9879;
	private ReflectionFactory reflectionFactory;

	public UDPServer()   {

		reflectionFactory=new ReflectionFactory();

	}

	@Override
	public void run() {
		try {
			DatagramSocket serverSocket = new DatagramSocket(PUERTO_ESCUCHA);
			byte[] receiveData = new byte[1024];
			byte[] sendData = new byte[1024];
			ExecuteResponse executeResponse=null;
			while(true)
			{
				DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

				//Se espera a recibir un paquete en este puerto 
				serverSocket.receive(receivePacket);
				String datosRecibidos = new String( 
						receivePacket.getData() ); //Recoge los bytes de los datos que recibe
				InetAddress IPAddress = receivePacket.getAddress();//Recoge la ip de destino
				int port = receivePacket.getPort();//Recoge el puerto al que ha sido enviado
				
				logger.debug(IPAddress+" RECEIVED: " + datosRecibidos);
				
				try{
					executeResponse=reflectionFactory.executeAction(datosRecibidos,IPAddress);
					sendData = executeResponse.getBytes();
				}catch (ServerReflectionException e) {
					logger.error("Error ejecutando acción "+e.getMessage());
					e.printStackTrace();
					executeResponse=e.getExecuteResponse();
					sendData = executeResponse.getBytes();
				}				
				
				DatagramPacket sendPacket =
						new DatagramPacket(sendData, sendData.length, IPAddress, port);
				serverSocket.send(sendPacket);
			}
		} catch (SocketException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}
}
