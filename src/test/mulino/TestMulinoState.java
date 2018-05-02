package test.mulino;

import game.general.GameState;
import it.unibo.ai.didattica.mulino.domain.State;
import mulino.MulinoFactory;
import mulino.MulinoState;

public class TestMulinoState {

	public static void main(String[] args) {
			
		State s = new State();
		
		System.out.println(s.toString());
		
		MulinoFactory fact = new MulinoFactory();
		GameState state = fact.fromState(s);
		MulinoState ms = (MulinoState)state;
		
		// testo i cambiamenti di stato
		System.out.println("currentPhase = " + ms.getCurrentPhase());
		
		System.out.println("Azzero i W");
		ms.setWhiteCheckers(0);
		ms.updatePhase();
		System.out.println("currentPhase = " + ms.getCurrentPhase());
		
		// provo la clone()
		ms = ms.clone();
		System.out.println("Azzero i B");
		ms.setBlackCheckers(0);
		ms.updatePhase();
		System.out.println("currentPhase = " + ms.getCurrentPhase());
		
		System.out.println("Metto a 3 i WB");
		ms.setWhiteCheckersOnBoard(3);
		ms.updatePhase();
		System.out.println("currentPhase = " + ms.getCurrentPhase());
		
		System.out.println("Metto a 3 i BB");
		ms.setBlackCheckersOnBoard(3);
		ms.updatePhase();
		System.out.println("currentPhase = " + ms.getCurrentPhase());
	}

}
