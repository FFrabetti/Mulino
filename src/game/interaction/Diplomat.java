package game.interaction;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Diplomat {

	private String ip;
	private int port;
	private Socket playerSocket;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	
	
	//constructors
	public Diplomat (String ip, int port) throws IOException {
		this.ip=ip;
		this.port=port;
		
		playerSocket = new Socket(ip, port);
		out = new ObjectOutputStream(playerSocket.getOutputStream());
		in = new ObjectInputStream(new BufferedInputStream(playerSocket.getInputStream()));
	}
	
	public Socket getSocket() {
		return playerSocket;
	}
	
	public Diplomat(String ip) throws IOException {
		this(ip,1111);
	}
	
	public Diplomat(int port) throws IOException {
		this("localhost",port);
	}
	
	public Diplomat() throws IOException {
		this("localhost",1111);
	}
	
	//getters and setters of ip and port ??
	
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	
	//methods
	
	//reads an object from server
	public Object read() throws ClassNotFoundException, IOException {
		return in.readObject();
		//controllare se deve leggere un GameState o un generico Object
	}
	
	//write an object to the server
	public void write(Object o) throws IOException {
		out.writeObject(o);
		//controllare se deve scrivere un generico Object o una GameAction
	}
	
}
