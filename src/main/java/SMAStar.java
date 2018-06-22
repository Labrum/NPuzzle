import java.util.HashMap;
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
		
		
		for (int i = 0; i < 4; i++) {
			Board child = node.state.copyBoard();
			if (child.move(Directions.values()[i])) {
				SearchNode childNode = new SearchNode(child, node, 0,
						calcManhattanDistance(child) + node.depth + 1);
				successors.add(childNode);
			}
		}
		
		return successors;
	}

	public static void main(String[] args) {
		
	}
	private static int calcManhattanDistance(Board board){
		int sum = 0;
		
		for(int i = 0; i < board.N; i++){
			for(int j = 0; j < board.N; j++){
				int tile = board.getTile(i, j);

				if(tile != -1){	
					sum += (int) Math.abs((tile-1)/board.N - j);
					sum += (int) Math.abs((tile-1)%board.N - i);
				}
			}
		}
		
		return sum;		
	}
}