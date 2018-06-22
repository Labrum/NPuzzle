import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;


public class FringeSearch extends Solver {
	private PriorityQueue<SearchNode> fringe = new PriorityQueue<SearchNode>(
			100, new AStarComparator());
	private PriorityQueue<SearchNode> later = new PriorityQueue<SearchNode>(
			100, new AStarComparator());

	private double largerThanCutoff = Double.MAX_VALUE;

	public FringeSearch() {
	}
	
	public Stack<SearchNode> search(SearchNode root) {
		boolean found = false;
		double cutoff = 0;
		Stack<SearchNode> path = null;
		fringe.add(root);
		while (!found) {
			path = searchToDepth(cutoff);
			if (path == null) {
				cutoff = largerThanCutoff;
				fringe = later;
				later = new PriorityQueue<SearchNode>(100,	new AStarComparator());
			} else {
				found = true;
			}
		}
		return path;
	}

	public Stack<SearchNode> searchToDepth(double MAX_COST) {

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

	public Queue expand(SearchNode node, double MAX_COST) {

		LinkedList<SearchNode> successors = new LinkedList<SearchNode>();
		if (node.cost > MAX_COST) {
			if (node.cost < largerThanCutoff) {
				largerThanCutoff = node.cost;
			}
			later.add(node);
			return successors;
		}

		for (int i = 0; i < 4; i++) {
			Board child = node.state.copyBoard();
			if (child.move(Directions.values()[i])) {
				SearchNode childNode = new SearchNode(child, node, 0,
						calcManhattanDistance(child) + node.depth + 1);
				if (childNode.cost <= MAX_COST) {
					successors.add(childNode);
				} else {
					later.add(childNode);
				}

			}
		}

		return successors;
	}
}
