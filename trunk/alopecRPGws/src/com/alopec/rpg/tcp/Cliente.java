package com.alopec.rpg.tcp;

import java.io.*;
import java.net.*;

public class Cliente {

	static final String HOST = "192.168.1.11";
	static final int PUERTO=5201;

	public Cliente() {
		try{

			Socket skCliente = new Socket( HOST , PUERTO );

			InputStream aux = skCliente.getInputStream();

			DataInputStream flujo = new DataInputStream( aux );

			System.out.println( flujo.readUTF() );

			skCliente.close();

		} catch( Exception e ) {

			System.out.println( e.getMessage() );
		}
	}
	
	public static void main (String args[]){
		new Cliente();
	}
}