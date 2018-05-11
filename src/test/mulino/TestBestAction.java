package test.mulino;

import aima.core.agent.Action;
import aima.core.search.adversarial.AdversarialSearch;
import aima.core.search.adversarial.AlphaBetaSearch;
import aima.core.search.adversarial.IterativeDeepeningAlphaBetaSearch;
import aima.core.search.framework.SearchAgent;
import aima.core.search.framework.problem.Problem;
import aima.core.search.framework.qsearch.GraphSearch;
import aima.core.search.informed.AStarSearch;
import game.general.GameAction;
import it.unibo.ai.didattica.mulino.domain.State.Checker;
import mulino.Board;
import mulino.MulinoAction;
import mulino.MulinoGame;
import mulino.MulinoState;
import mulino.Position;
import mulino.ia.FunzioneEuristica;
import mulino.ia.MulinoActionsFunction;
import mulino.ia.MulinoGoalTest;
import mulino.ia.MulinoResultFunction;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

public class TestBestAction {
	
	//questo main serve per vedere se dato uno stato con una mossa ovvia per vincere, l'algoritmo trova
	//la mossa giusta: in questo caso abbiamo 5 pedine bianche e 3 nere. Le nere sono disposte in modo
	//sfigato mentre per le bianche sono disposte negli angoli in basso a destra (3,-3) e in quello in altro
	//a sinistra (3,3). Al giocatore bianco non resta che spostare una pedina in (3,0) per fare un mulino e battere
	//il nero. Il giocatore bianco pensa di spostare la pedina in (2,0) in (3,0) vincendo la partita.
	
	//PURTROPPO VA SOLO SE GLI MANCA UNA MOSSA!!!!!!
	
	public static void main(String[] args) {
		MulinoGame game = new MulinoGame();
		//AdversarialSearch<MulinoState,MulinoAction> search = IterativeDeepeningAlphaBetaSearch.createFor(game,-1,1,40);
		//AdversarialSearch<MulinoState, MulinoAction> search = AlphaBetaSearch.createFor(game);

		AStarSearch aSearch = new AStarSearch(new GraphSearch(),new FunzioneEuristica());
		
		
//		((IterativeDeepeningAlphaBetaSearch<?, ?, ?>) search).setLogEnabled(true);
		Board b = new Board();
		
		//stato ad hoc affinchè manchi una mossa per la vittora del bianco 
		// -> dovrebbe riuscire a calcolare la mossa in pochi istanti
		b.put(new Position(3,3),Checker.WHITE);
		b.put(new Position(3,-3),Checker.WHITE);
		b.put(new Position(1,0),Checker.WHITE);
		b.put(new Position(0,-1),Checker.WHITE);
		b.put(new Position(-2,0),Checker.WHITE);
		
		b.put(new Position(-3,-3),Checker.BLACK);
		b.put(new Position(-2,2),Checker.BLACK);
		b.put(new Position(0,1),Checker.BLACK);
		
		//
//		for(Position p : b.freePositions()) {
//			b.put(p, Checker.WHITE);
//		}
//		b.remove(new Position(3,0));
//		
		//altro esempio funzionante
		
//		b.put(new Position(0,-2),Checker.WHITE);
//		b.put(new Position(-3,-3),Checker.WHITE);
//		b.put(new Position(3,-3),Checker.WHITE);
//		b.put(new Position(0,-1),Checker.WHITE);
//		
//		b.put(new Position(-3,0),Checker.BLACK);
//		b.put(new Position(-2,2),Checker.BLACK);
//		b.put(new Position(0,1),Checker.BLACK);
		

		MulinoState state = new MulinoState(b,0,0);
		System.out.println(state.toString());
		
		//prova a star
		
		Problem problem = new Problem(state,new MulinoActionsFunction(),new MulinoResultFunction(),new MulinoGoalTest());
		try {
			SearchAgent agent= new SearchAgent(problem,aSearch);
			printActions(agent.getActions());
			printInstrumentation(agent.getInstrumentation());
			MulinoAction action = (MulinoAction)agent.getActions().get(0);
			action.perform(state);
			System.out.println(action.perform(state).toString());

			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//GameAction action = search.makeDecision((MulinoState)state);
		//MulinoState newState= game.getResult((MulinoState)state, (MulinoAction)action);
		//System.out.println(newState.toString());
	}
	
	private static void printInstrumentation(Properties properties) {
		Iterator<Object> keys = properties.keySet().iterator();
		while (keys.hasNext()) {
			String key = (String) keys.next();
			String property = properties.getProperty(key);
			System.out.println(key + " : " + property.toString());
		}

	}

	private static void printActions(List<Action> actions) {
		System.out.println("SIZE:"+actions.size());
		for (int i = 0; i < actions.size(); i++) {
			String action = actions.get(i).toString();
			System.out.println(action);
		}
	}
}
