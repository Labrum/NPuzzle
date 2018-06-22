import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;
import java.util.TreeSet;

public class SimulatedIDA {
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

	public Stack<SearchNode> search() {
		boolean found = false;
		double cutoff = 0;
		Stack<SearchNode> path = null;
		while (!found) {
			path = searchToDepth(cutoff);
			if (path == null) {
				cutoff = largerThanCutoff;
			} else {
				found = true;
			}
		}
		System.out.println("Closed List size : "+closedList.size());
		return path;
	}

	public Stack<SearchNode> searchToDepth(double MAX_COST) {

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
				if (test.state.getTile(i, j) != j * test.state.N + i) {
					if (i != 0 || j != 0) {
						goal = false;
					}
				}
			}
		}

		return goal;

	}

	public Queue expand(SearchNode node, double MAX_COST) {

		LinkedList<SearchNode> successors = new LinkedList<SearchNode>();
		if (iterative && (node.cost > MAX_COST)) {
			largerThanCutoff = node.cost;
			return successors;
		}
		
		for (int i = 0; i < 4; i++) {
			Board child = node.state.copyBoard();
			if (child.move(Directions.values()[i])) {
				SearchNode childNode = new SearchNode(child, node, 0,
						calcManhattanDistance(child) + node.depth + 1);
				if(!closedList.contains(childNode)){
					successors.add(childNode);
				}
			}
		}
		
		closedList.add(node);
		return successors;
	}

	private static int calcManhattanDistance(Board board) {
		int sum = 0;

		for (int i = 0; i < board.N; i++) {
			for (int j = 0; j < board.N; j++) {
				int tile = board.getTile(i, j);

				if (tile != -1) {
					sum += (int) Math.abs((tile) / board.N - j);
					sum += (int) Math.abs((tile) % board.N - i);
				}
			}
		}

		return sum;
	}

}
