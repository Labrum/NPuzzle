import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

public class SMAStar {
	private SearchNode root;
	private PriorityQueue<SearchNode> fringe = new PriorityQueue<SearchNode>(100,new AStarComparator());
	private boolean iterative;
	
	public SMAStar(SearchNode root,boolean iterative) {
		this.root = root;
		this.iterative = iterative;
	}
	
	public Stack<SearchNode> search() {
		boolean found = false;
		int iteration = 0;
		Stack<SearchNode> path = null;
		while(!found){
			path = searchToDepth(iteration);
			if(path == null){
				iteration++;
			}else{
				found = true;
			}
		}
		return path;
	}

	public Stack<SearchNode> searchToDepth(	int MAX_COST) {

		fringe.add(root);

		while (true) {
			if (fringe.size() == 0) {
				return null;
			}

			SearchNode node = fringe.poll();

			if (goalTest(node)) {
				return node.getPath();
			}
			fringe.addAll(expand(node, MAX_COST));
		}
	}

	public boolean goalTest(SearchNode test) {
		boolean goal = true;
		for (int j = 0; j < test.state.N; j++) {
			for (int i = 0; i < test.state.N; i++) {
				if (test.state.getTile(i, j) != j * test.state.N + i + 1) {
					if(i != test.state.N-1 || j != test.state.N-1){
						goal = false;
					}
				}
			}
		}

		return goal;

	}

	public Queue expand(SearchNode node, int MAX_COST) {
		
		LinkedList<SearchNode> successors = new LinkedList<SearchNode>();

		if(iterative && (node.cost > MAX_COST)){
			return successors;
		}

		return Solver.generateSuccessors(node);
	}
}