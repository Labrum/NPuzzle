import java.util.HashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

public class SimulatedIDA extends FringeSolver {
	private SearchNode root;
	private PriorityQueue<SearchNode> fringe = new PriorityQueue<SearchNode>(100,
			new AStarComparator());
	private boolean iterative;
	private double largerThanCutoff;
	private HashSet<SearchNode> closedList = new HashSet<SearchNode>();

	public SimulatedIDA(SearchNode root, boolean iterative) {
		this.root = root;
		this.iterative = iterative;
	}

	public Stack<SearchNode> search(SearchNode root) {
		boolean found = false;
		double cutoff = 0;
		Stack<SearchNode> path = null;
		fringe.add(root);
		while (!found) {
			path = searchToDepth(fringe, cutoff);
			if (path == null) {
				cutoff = largerThanCutoff;
			} else {
				found = true;
			}
		}
		System.out.println("Closed List size : "+closedList.size());
		return path;
	}

	@Override
	public Queue<SearchNode> expand(SearchNode node, double MAX_COST) {
		LinkedList<SearchNode> successors = new LinkedList<SearchNode>();
		if (iterative && (node.cost > MAX_COST)) {
			largerThanCutoff = node.cost;
			return successors;
		}

		for (Directions direction : Directions.values()) {
			Board child = node.state.copyBoard();
			if (child.move(direction)) {
				SearchNode childNode = new SearchNode(child, node, 0,
						Solver.calcManhattanDistance(child) + node.depth + 1);
				if(!closedList.contains(childNode)){
					successors.add(childNode);
				}
			}
		}

		closedList.add(node);
		return successors;
	}
}
