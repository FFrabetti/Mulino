package test.game.interaction;

import game.interaction.Diplomat;

public class TestDiplomat2 {

	//main di test
	public static void main(String[] args) throws Exception {
		Diplomat d = new Diplomat(1112);
		
		Object toSend= "Message from player2";
		d.write(toSend);
		String received = (String) d.read();
		System.out.println("Ricevuto " +received);
		toSend="Another message from player2";
		//d.read();
	}
}
