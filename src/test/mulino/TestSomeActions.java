package test.mulino;

import aima.core.search.adversarial.AdversarialSearch;
import aima.core.search.adversarial.IterativeDeepeningAlphaBetaSearch;

import game.general.GameAction;
import game.general.GameState;
import it.unibo.ai.didattica.mulino.domain.State.Checker;
import mulino.Board;
import mulino.MulinoAction;
import mulino.MulinoGame;
import mulino.MulinoState;
import mulino.Position;
import mulino.ia.FunzioneEuristica;
import mulino.strategy.MulinoIterativeAlphaBeta;

public class TestSomeActions {
	
	public static void main(String[] args) {
		
//		MulinoGame game = new MulinoGame();
//		AdversarialSearch<MulinoState,MulinoAction> search = IterativeDeepeningAlphaBetaSearch.createFor(game,-1,1,40);
//		
//
//		((IterativeDeepeningAlphaBetaSearch<?, ?, ?>) search).setLogEnabled(true);
		Board b = new Board();
		
//		MulinoState state = new MulinoState(b, 0, 0);
		MulinoGame game = new MulinoGame(100, -100); // 2, -1
		FunzioneEuristica heuristic = new FunzioneEuristica();
		MulinoIterativeAlphaBeta search = new MulinoIterativeAlphaBeta(game, heuristic);
		
//		//es 1 : il bianco ha posizionato due pedine ai vertici opposti, il nero cosa fa? E il bianco
//		//poi riesce a fare almeno un mulino?
		b.put(new Position(3,3),Checker.WHITE);
		b.put(new Position(-3,-3),Checker.WHITE);
		
//		b.put(new Position(3,-3),Checker.WHITE);
		
		b.put(new Position(-2,2),Checker.BLACK);

//		b.put(new Position(3, 0),Checker.BLACK);
//		b.put(new Position(-3,3),Checker.BLACK);
		
		MulinoState state = new MulinoState(b,7,8);
		state.setDutyPlayer(Checker.BLACK);

//		//es2 : il nero deve bloccare un mulino
//		b.put(new Position(-2,2),Checker.WHITE);
//		b.put(new Position(-2,-2),Checker.WHITE);
//		
//		b.put(new Position(2,2),Checker.BLACK);
//		
//		MulinoState state = new MulinoState(b,7,8);
//		state.setDutyPlayer(Checker.BLACK);
		
//		//es3 :  il nero deve decidere se fare un mulino o bloccarlo
//		b.put(new Position(-3,-3),Checker.WHITE);
//		b.put(new Position(-2,2),Checker.WHITE);
//		b.put(new Position(0,2),Checker.WHITE);
//
//		
//		b.put(new Position(0,-2),Checker.BLACK);
//		b.put(new Position(2,-2),Checker.BLACK);
//		
//		MulinoState state = new MulinoState(b,6,7);
//		state.setDutyPlayer(Checker.BLACK);

//		//es4 :  c'è un mulino a disposizione del bianco ma se sposta una pedina, blocca al nero
//		//ogni mossa e quindi vince
//		b.put(new Position(0,3),Checker.WHITE);
//		b.put(new Position(-2,2),Checker.WHITE);
//		b.put(new Position(-2,0),Checker.WHITE);
//		b.put(new Position(0,1),Checker.WHITE);
//		b.put(new Position(2,0),Checker.WHITE);
//		b.put(new Position(1,0),Checker.WHITE);
//		b.put(new Position(0,-1),Checker.WHITE);
//		b.put(new Position(0,-2),Checker.WHITE);
//		b.put(new Position(3,-3),Checker.WHITE);
//
//		b.put(new Position(3,3),Checker.BLACK);
//		b.put(new Position(0,2),Checker.BLACK);
//		b.put(new Position(2,2),Checker.BLACK);
//		b.put(new Position(1,1),Checker.BLACK);
//		b.put(new Position(3,0),Checker.BLACK);
//		b.put(new Position(1,-1),Checker.BLACK);
//		b.put(new Position(2,-2),Checker.BLACK);
//		b.put(new Position(0,-3),Checker.BLACK);
//		b.put(new Position(-3,-3),Checker.BLACK);
//
//		
//		MulinoState state = new MulinoState(b,0,0);
		
//		//es5 : il nero deve bloccare 2 mulini o fare tris?
//		b.put(new Position(2,2),Checker.WHITE);
//		b.put(new Position(0,2),Checker.WHITE);
//		b.put(new Position(-2,0),Checker.WHITE);
//		b.put(new Position(-2,-2),Checker.WHITE);
//		b.put(new Position(-3,-3),Checker.WHITE);
//
//		b.put(new Position(1,0),Checker.BLACK);
//		b.put(new Position(2,0),Checker.BLACK);
//		b.put(new Position(2,-2),Checker.BLACK);
//		b.put(new Position(0,-2),Checker.BLACK);
//		
//		MulinoState state = new MulinoState(b,4,5);
//		state.setDutyPlayer(Checker.BLACK);
		
//		//es6 : fase 3 meglio fare mulino o bloccarlo?
//		b.put(new Position(2,2),Checker.WHITE);
//		b.put(new Position(2,-2),Checker.WHITE);
//		b.put(new Position(3,-3),Checker.WHITE);
//
//		b.put(new Position(3,3),Checker.BLACK);
//		b.put(new Position(-2,2),Checker.BLACK);
//		b.put(new Position(-2,0),Checker.BLACK);
//		
//		MulinoState state = new MulinoState(b,0,0);
		
//		//es7 :
//		
//		MulinoState state = new MulinoState(b,0,0);

//		//es8 : non tento di posizionare il bianco nel terzo angolo del quadrato
//		//prima devo bloccare il mulino nero
//		b.put(new Position(3,3),Checker.WHITE);
//		b.put(new Position(-3,-3),Checker.WHITE);
//
//		b.put(new Position(0,1),Checker.BLACK);
//		b.put(new Position(0,2),Checker.BLACK);
//		
//		MulinoState state = new MulinoState(b,7,7);
		
//		//es 9:in fase 2 il nero deve spostare la pedina da (0,1) a (0,2)
//		//per evitare che il bianco vada avanti e torni indietro
//		b.put(new Position(3,3),Checker.WHITE);
//		b.put(new Position(-3,3),Checker.WHITE);
//		b.put(new Position(0,3),Checker.WHITE);
//		b.put(new Position(3,0),Checker.WHITE);
//		b.put(new Position(-3,-3),Checker.WHITE);
//		b.put(new Position(0,-1),Checker.WHITE);
//		b.put(new Position(1,-1),Checker.WHITE);
//
//		b.put(new Position(-1,1),Checker.BLACK);
//		b.put(new Position(0,1),Checker.BLACK);
//		b.put(new Position(1,1),Checker.BLACK);
//		b.put(new Position(2,0),Checker.BLACK);
//		b.put(new Position(3,-3),Checker.BLACK);
//		b.put(new Position(-1,-1),Checker.BLACK);
//		
//		MulinoState state = new MulinoState(b,0,0);
//		state.setDutyPlayer(Checker.BLACK);
		
		//es 10:in fase 2  quando mancano 4 pedine non conviene fare mulino ma bloccare
		
//		b.put(new Position(3,3),Checker.WHITE);
//		b.put(new Position(-3,3),Checker.WHITE);
//		b.put(new Position(0,3),Checker.WHITE);
//		b.put(new Position(0,-3),Checker.WHITE);
//		b.put(new Position(0,-1),Checker.WHITE);
//		b.put(new Position(1,-1),Checker.WHITE);
//
//		b.put(new Position(1,1),Checker.BLACK);
//		b.put(new Position(1,0),Checker.BLACK);
//		b.put(new Position(-1,1),Checker.BLACK);
//		b.put(new Position(-1,-1),Checker.BLACK);
//
//		MulinoState state = new MulinoState(b,0,0);
//
//		
//		System.out.println("DEBUG:STATO INIZIALE");
//		System.out.println(state.toString());
//		
//				
//		GameAction action = search.makeDecision((MulinoState)state);
//		MulinoState newState= game.getResult((MulinoState)state, (MulinoAction)action);
//		System.out.println(newState.toString());
		
		System.out.println(state);
		GameAction action = search.makeDecision(state);
		System.out.println(action);
		GameState nextState = action.perform(state);
		System.out.println(nextState);
		
		System.out.println("---------------------");
		
		// seconda mossa
		GameAction action2 = search.makeDecision((MulinoState) nextState);
		System.out.println(action2);
		GameState nextState2 = action2.perform(nextState);
		System.out.println(nextState2);
		
		// terza mossa
		GameAction action3 = search.makeDecision((MulinoState) nextState2);
		System.out.println(action3);
		GameState nextState3 = action3.perform(nextState2);
		System.out.println(nextState3);

		// quarta mossa
		GameAction action4 = search.makeDecision((MulinoState) nextState3);
		System.out.println(action4);
		GameState nextState4 = action4.perform(nextState3);
		System.out.println(nextState4);
		
		
		System.out.println("---------------");
		System.out.println(nextState);
		System.out.println(nextState2);
		System.out.println(nextState3);
		System.out.println(nextState4);
		
	}

}
