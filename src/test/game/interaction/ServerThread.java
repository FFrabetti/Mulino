package test.game.interaction;

import java.io.*;
import java.net.*;

class ServerThread extends Thread{
	private Socket s;

	public ServerThread(Socket s){
		this.s = s;
	}
 
	public void run(){
		try {
			System.out.println("Thread avviato!: ");
			// ottiene gli stream di I/O dalla socket
			ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
			ObjectInputStream in = new ObjectInputStream(s.getInputStream());
			 
			String received = (String) in.readObject();
			System.out.println("Ricevuto " +received);
			Object toSend= "Message from server";
			out.writeObject(toSend);
			received=(String)in.readObject();
			System.out.println("Ricevuto "+received);
			//in.readObject();
		}
		catch (Exception e) {
			System.out.println("ERRORE: "+e);
		}
	}
}