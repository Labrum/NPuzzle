import java.util.HashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

public class AStar extends Solver {

	private PriorityQueue<SearchNode> openList = new PriorityQueue<SearchNode>(
			100, new AStarComparator());
	private HashSet<SearchNode> closedList = new HashSet<SearchNode>();

	public AStar() {

	}

	public Stack<SearchNode> search(SearchNode root) {
		// Root added with only manhattan distance to goal
		root.cost = calcManhattanDistance(root.state);
		openList.add(root);
		while (!openList.isEmpty()) {
			SearchNode current = openList.poll();
			if (goalTest(current)) {
				return current.getPath();
			}
			Queue<SearchNode> successors = generateSuccessors(current);

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

	private Queue<SearchNode> generateSuccessors(SearchNode node) {

		LinkedList<SearchNode> successors = new LinkedList<SearchNode>();
	
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
}
