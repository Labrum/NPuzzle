import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

public class AStar implements Solver {

	private PriorityQueue<SearchNode> openList = new PriorityQueue<SearchNode>(
			100, new AStarComparator());
	private HashSet<SearchNode> closedList = new HashSet<SearchNode>();

	public AStar() {

	}

	public Stack<SearchNode> search(SearchNode root) {
		// Root added with only manhattan distance to goal
		root.cost = Solver.calcManhattanDistance(root.state);
		openList.add(root);
		while (!openList.isEmpty()) {
			SearchNode current = openList.poll();
			if (Solver.goalTest(current)) {
				return current.getPath();
			}
			Queue<SearchNode> successors = Solver.generateSuccessors(current);

			for (SearchNode successor : successors) {
				if (closedList.contains(successor)) {
					if (successor.depth > current.depth + 1) {
						closedList.remove(successor);
						openList.add(successor);
					}
				} else {
					openList.add(successor);
				}
			}

			closedList.add(current);

		}

		return null;
	}
}
