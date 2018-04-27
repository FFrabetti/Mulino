package test.game.interaction;

import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	public static void main(String[] args) throws Exception {
		ServerSocket server = new ServerSocket(1111);
		
		while(true)
		 {
			 // si mette in ascolto
			 Socket socket = server.accept();
			 System.out.println("Server HTTP: ricevuta richiesta");
			 // esegue un thread in modo parallelo
			 new ServerThread(socket).start();
		 }
	}
}
