package test.game.interaction;

import game.interaction.Diplomat;

public class TestDiplomat1 {

	//main di test
	public static void main(String[] args) throws Exception {
		Diplomat d = new Diplomat(1111);
		
		Object toSend= "Message from player1";
		d.write(toSend);
		String received = (String) d.read();
		System.out.println("Ricevuto " +received);
		//d.write(toSend);
	}
	
}
