package test.mulino;

import aima.core.agent.Action;
import aima.core.search.adversarial.AdversarialSearch;
import aima.core.search.adversarial.AlphaBetaSearch;
import aima.core.search.adversarial.IterativeDeepeningAlphaBetaSearch;
import aima.core.search.framework.SearchAgent;
import aima.core.search.framework.problem.Problem;
import aima.core.search.framework.qsearch.GraphSearch;
import aima.core.search.framework.qsearch.QueueSearch;
import aima.core.search.informed.AStarSearch;
import game.general.GameAction;
import it.unibo.ai.didattica.mulino.domain.State.Checker;
import it.unibo.ai.didattica.mulino.domain.State.Phase;
import mulino.Board;
import mulino.MulinoAction;
import mulino.MulinoGame;
import mulino.MulinoState;
import mulino.Phase23MulinoAction;
import mulino.Position;
import mulino.ia.FunzioneEuristica;
import mulino.ia.MulinoActionsFunction;
import mulino.ia.MulinoGoalTest;
import mulino.ia.MulinoResultFunction;
import mulino.ia.MulinoSearch;

import java.util.Iterator;
import java.util.List;
import java.util.Properties;

public class TestMulinoSearch {
	
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
		
		MulinoSearch ms = new MulinoSearch();
		//GraphSearch gs = new GraphSearch();
		FunzioneEuristica f = new FunzioneEuristica();
		AStarSearch aSearch = new AStarSearch(ms,f);

		
		
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
		b.put(new Position(-2,-2),Checker.BLACK);
		b.put(new Position(0,1),Checker.BLACK);
		//b.put(new Position(0,-3), Checker.BLACK);
		
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
		state.setDutyPlayer(Checker.BLACK);
		System.out.println("DEBUG:STATO INIZIALE");
		System.out.println(state.toString());
		
		//prova a star
		
		
		Problem problem = new Problem(state,new MulinoActionsFunction(),new MulinoResultFunction(),new MulinoGoalTest());
		SearchAgent agent=null;
		
		//wait bianco
		try {
			System.out.println("-------------------------------WAIT BIANCO 1--------------------------------");
			agent=new SearchAgent(problem,aSearch);
			printActions(agent.getActions());
			printInstrumentation(agent.getInstrumentation());
//			int i = 1;
//			for(Action a : agent.getActions()) {
//				System.out.println("Mossa " + i++ + ": " + ((MulinoAction)a));
//				MulinoAction action = (MulinoAction)a;
//				state = (MulinoState)action.perform(state);
//				System.out.println(state.toString());
//			}
			MulinoAction whiteAction = (MulinoAction)agent.getActions().get(0);
			MulinoState tempState=(MulinoState)whiteAction.perform(state);
			System.out.println("BIANCO PENSA ALLA MOSSA DEL NERO:" + whiteAction.toString());
			System.out.println("porterebbe allo stato: ");
			System.out.println(tempState.toString());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		//mossa nero
		
		System.out.println("-------------------------------MOSSA NERO 1--------------------------------");

		MulinoAction blackAction = new Phase23MulinoAction(new Position(-2,-2), new Position(-3,3), Phase.FINAL);
		state=(MulinoState)blackAction.perform(state);
		System.out.println("MOSSA DEL NERO: "+blackAction.toString());
		System.out.println(state.toString());
		
		//play bianco
		try {
			System.out.println("-------------------------------PLAY BIANCO 1--------------------------------");
			problem = new Problem(state,new MulinoActionsFunction(),new MulinoResultFunction(),new MulinoGoalTest());
			agent=new SearchAgent(problem,aSearch);
			printActions(agent.getActions());
			printInstrumentation(agent.getInstrumentation());
//			int i = 1;
//			for(Action a : agent.getActions()) {
//				System.out.println("Mossa " + i++ + ": " + ((MulinoAction)a));
//				MulinoAction action = (MulinoAction)a;
//				state = (MulinoState)action.perform(state);
//				System.out.println(state.toString());
//			}
			MulinoAction whiteAction = (MulinoAction)agent.getActions().get(0);
			state=(MulinoState)whiteAction.perform(state);
			System.out.println("BIANCO FA LA MOSSA :" + whiteAction.toString());
			System.out.println(state.toString());


			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//wait bianco 2
		try {
			
			System.out.println("-------------------------------WAIT BIANCO 2--------------------------------");

			problem = new Problem(state,new MulinoActionsFunction(),new MulinoResultFunction(),new MulinoGoalTest());
			agent=new SearchAgent(problem,aSearch);
			printActions(agent.getActions());
			printInstrumentation(agent.getInstrumentation());
//			int i = 1;
//			for(Action a : agent.getActions()) {
//				System.out.println("Mossa " + i++ + ": " + ((MulinoAction)a));
//				MulinoAction action = (MulinoAction)a;
//				state = (MulinoState)action.perform(state);
//				System.out.println(state.toString());
//			}
			MulinoAction whiteAction = (MulinoAction)agent.getActions().get(0);
			MulinoState tempState=(MulinoState)whiteAction.perform(state);
			System.out.println("BIANCO PENSA ALLA MOSSA DEL NERO:" + whiteAction.toString());
			System.out.println("porterebbe allo stato: ");
			System.out.println(tempState.toString());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		//mossa nero 2
		
		System.out.println("-------------------------------MOSSA NERO 2--------------------------------");

		blackAction = new Phase23MulinoAction(new Position(-2,-2), new Position(-3,3), Phase.FINAL);
		state=(MulinoState)blackAction.perform(state);
		System.out.println("MOSSA DEL NERO: "+blackAction.toString());
		System.out.println(state.toString());
		
		//play bianco 2
		try {
			
			System.out.println("-------------------------------PLAY BIANCO 2--------------------------------");

			problem = new Problem(state,new MulinoActionsFunction(),new MulinoResultFunction(),new MulinoGoalTest());
			agent=new SearchAgent(problem,aSearch);
			printActions(agent.getActions());
			printInstrumentation(agent.getInstrumentation());
//			int i = 1;
//			for(Action a : agent.getActions()) {
//				System.out.println("Mossa " + i++ + ": " + ((MulinoAction)a));
//				MulinoAction action = (MulinoAction)a;
//				state = (MulinoState)action.perform(state);
//				System.out.println(state.toString());
//			}
			MulinoAction whiteAction = (MulinoAction)agent.getActions().get(0);
			state=(MulinoState)whiteAction.perform(state);
			System.out.println("BIANCO FA LA MOSSA :" + whiteAction.toString());
			System.out.println(state.toString());


			
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
