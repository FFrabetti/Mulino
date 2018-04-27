package test.game.interaction;

import game.interaction.Diplomat;

public class TestDiplomat {

	//main di test
	public static void main(String[] args) throws Exception {
		Diplomat d = new Diplomat();
		
		Object toSend= "Message from player";
		d.write(toSend);
		String received = (String) d.read();
		System.out.println("Ricevuto " +received);
	}
	
}
