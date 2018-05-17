package mulino.ia;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import aima.core.search.framework.Node;
import aima.core.search.framework.NodeExpander;
import aima.core.search.framework.QueueFactory;
import aima.core.search.framework.SearchUtils;
import aima.core.search.framework.problem.Problem;
import aima.core.search.framework.qsearch.QueueSearch;
import aima.core.util.CancelableThread;
import mulino.MulinoState;


/**
 * Artificial Intelligence A Modern Approach (3rd Edition): Figure 3.7, page 77.
 * <br>
 * 
 * <pre>
 * function GRAPH-SEARCH(problem) returns a solution, or failure
 *   initialize the frontier using the initial state of problem
 *   initialize the explored set to be empty
 *   loop do
 *     if the frontier is empty then return failure
 *     choose a leaf node and remove it from the frontier
 *     if the node contains a goal state then return the corresponding solution
 *     add the node to the explored set
 *     expand the chosen node, adding the resulting nodes to the frontier
 *       only if not in the frontier or explored set
 * </pre>
 * 
 * Figure 3.7 An informal description of the general graph-search algorithm.
 * <br>
 * This implementation is based on the template method
 * {@link QueueSearch#findNode(Problem, Queue)} of the superclass and provides
 * implementations for the needed primitive operations. In contrast to the code
 * above, here, nodes resulting from node expansion are added to the frontier
 * even if nodes for the same states already exist there. This makes it possible
 * to use the implementation also in combination with priority queue frontiers.
 * This implementation avoids linear costs for frontier node removal (compared
 * to {@link GraphSearchReducedFrontier}) and gets by without node comparator
 * knowledge.
 * 
 * @author Ruediger Lunde
 */
public class MulinoSearch extends QueueSearch {

	private Set<Object> explored = new HashSet<Object>();
	private Set<Node> firstNodesFound = new HashSet<Node>(); 

	public MulinoSearch() {
		this(new NodeExpander());
		frontier= QueueFactory.createPriorityQueue(null);

	}

	public MulinoSearch(NodeExpander nodeExpander) {
		super(nodeExpander);
	}

	/**
	 * Clears the set of explored states and calls the search implementation of
	 * {@link QueueSearch}.
	 */
	@Override
	public Node findNode(Problem problem, Queue<Node> frontier) {
		System.out.println("DEBUG: Riparto con una frontiera grande: "+this.frontier.size());
		// initialize the explored set to be empty
		explored.clear();
		//return super.findNode(problem, frontier);
		if(this.frontier.isEmpty())
			this.frontier = frontier;
		clearInstrumentation();
		// initialize the frontier using the initial state of the problem
		Node root = nodeExpander.createRootNode(problem.getInitialState());
		System.out.println("DEBUG:Riparto dal nodo:");
		System.out.println(root.getState().toString());
		
		System.out.println("Le mosse possibili per questo stato sono:"+((MulinoState)root.getState()).legitActions().size());
		
		//se ho già calcolato qualcosa in fase di wait voglio ripartire da lì:
		//elimino i nodi pensati prima di ricevere l'ultima mossa che non
		//derivavano dalla mossa effettivamente eseguita: gli altri invece
		//li lascio per non doverli ripensare
		
		System.out.println("DEBUG:I primi nodi memorizzati sono "+firstNodesFound.size());
		
		//La root corrente va bene, ma non tenedo memoria del padre (essendo appunto la nuova root) 
		//quando controllo se i nodi della frontiera discendevano da questa nuova root
		//l'algoritmo dà sempre falso perchè se anche ne discendessero, nel controllo si guarda tutto:
		//non solo stato della root, ma anche azione che ha portato a quello stato, il path cost e 
		//soprattutto il padre. Se creiamo un nuovo nodo root l'unica cosa che possiamo comparare è
		//lo stato, tutto il resto viene perso. Quindi tengo memoria del "primo strato" di nodi creati al passo
		//precedente e se ne trovo uno con lo stesso stato della nuova root, si tratta sicuramente dello
		//stesso nodo. Così i controlli non falliscono. Sennò se per controllare se un nodo
		//discende da una root, ci si limita a cercare nella catena di derivazione uno stato uguale a quello di root,
		//potrebbe essere che magari è un caso che gli stati siano uguali, ma magari ci si è arrivati da mosse diverse e 
		//da padri diversi.
		Node newRoot=null;
		int cont=0;
		System.out.println("DEBUG:firstnodes");
		for(Node n: firstNodesFound) {
			System.out.println("NUMERO" + cont++);
			System.out.println(n);
			if(n.getState().equals(root.getState())) {
				newRoot=n;
			}
		}
		firstNodesFound.clear();
		System.out.println("DEBUG: identificato il nodo relativo alla nuova radice:");
		System.out.println(newRoot);

		//rimuovo i nodi calcolati al passo precedente che non derivano dalla nuova root
		List<Node> nodeToBeRemoved = new ArrayList<Node>();		
		for(Node n : this.frontier) {
			if(!discendsFrom(n, newRoot)) {
				nodeToBeRemoved.add(n);
			}
		}
		for(Node n: nodeToBeRemoved) {
			this.frontier.remove(n);
		}
		
		System.out.println("DEBUG: Tolgo dei nodi, mi rimane una frontiera grande: "+this.frontier.size());

		addToFrontier(root);
		if (earlyGoalTest && SearchUtils.isGoalState(problem, root)) {
			return getSolution(root);
		}
		
		while (!isFrontierEmpty() && !CancelableThread.currIsCanceled()) {
			// choose a leaf node and remove it from the frontier
			Node nodeToExpand = removeFromFrontier();
			// Only need to check the nodeToExpand if have not already
			// checked before adding to the frontier
			if (!earlyGoalTest && SearchUtils.isGoalState(problem, nodeToExpand)) {
				// if the node contains a goal state then return the
				// corresponding solution
				return getSolution(nodeToExpand);
			}

			// expand the chosen node, adding the resulting nodes to the
			// frontier
			if(this.frontier.size()==0) {
				firstNodesFound.addAll(nodeExpander.expand(nodeToExpand, problem));
				System.out.println("DEBUG:MEMORIZZO " +firstNodesFound.size()+" NODI PER CAPIRE DA DOVE VENIVA LA PROSSIMA MOSSA");

			}
			for (Node successor : nodeExpander.expand(nodeToExpand, problem)) {
//				System.out.println(successor.getState().toString());

				addToFrontier(successor);
				if (earlyGoalTest && SearchUtils.isGoalState(problem, successor)) {
					return getSolution(successor);

				}
			}
		}
		// if the frontier is empty then return failure
		return null;
	}

	/**
	 * Inserts the node at the tail of the frontier if the corresponding state
	 * was not yet explored.
	 */
	@Override
	protected void addToFrontier(Node node) {
		if (!explored.contains(node.getState())) {
			frontier.add(node);
			updateMetrics(frontier.size());
		}
	}

	/**
	 * Removes the node at the head of the frontier, adds the corresponding
	 * state to the explored set, and returns the node. Leading nodes of already
	 * explored states are dropped. So the resulting node state will always be
	 * unexplored yet.
	 * 
	 * @return the node at the head of the frontier.
	 */
	@Override
	protected Node removeFromFrontier() {
		cleanUpFrontier(); // not really necessary because isFrontierEmpty
							// should be called before...
		Node result = frontier.remove();
		explored.add(result.getState());
		updateMetrics(frontier.size());
		return result;
	}

	/**
	 * Pops nodes of already explored states from the head of the frontier
	 * and checks whether there are still some nodes left.
	 */
	@Override
	protected boolean isFrontierEmpty() {
		cleanUpFrontier();
		updateMetrics(frontier.size());
		return frontier.isEmpty();
	}

	/**
	 * Helper method which removes nodes of already explored states from the head
	 * of the frontier.
	 */
	private void cleanUpFrontier() {
		while (!frontier.isEmpty() && explored.contains(frontier.element().getState()))
			frontier.remove();
	}
	
	private Node getSolution(Node node) {
		metrics.set(METRIC_PATH_COST, node.getPathCost());
		return node;
	}
	
	//sembrava bastasse fare una contains nel discends ma, sebbene due nodi fossero uguali,
	//la equals della contains falliva per via delle azioni: sebbene fossero identiche la equals falliva
	//forse erano uguali ma con un id diverso? Tutto il resto cioè stato, genitore e pathcost invece andava
	int cont=0;
	private boolean discendsFrom(Node son, Node ancestor){
		Node rifNode=son.getPathFromRoot().get((int)(ancestor.getPathCost()));
		if(ancestor.getState().equals(rifNode.getState()) &&
				ancestor.getParent().equals(rifNode.getParent()) &&
				ancestor.getPathCost()==rifNode.getPathCost() &&
				ancestor.getAction().toString().equals(rifNode.getAction().toString())) {
			//aggiungo alla prima espansione di nodi dalla nuova root, i nodi discendenti direttamente dalla root
			//quindi in questo caso quelli in posizione +1 rispetto all'ancestor(=root)
			firstNodesFound.add(son.getPathFromRoot().get((int)(ancestor.getPathCost()+1)));
			System.out.println("Aggiunto il nodo "+son.getPathFromRoot().get((int)(ancestor.getPathCost()+1)));
			return true;
		}
				
		else return false;
	}
}