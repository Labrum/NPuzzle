import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

public class BFS implements Solver{

	private PriorityQueue<SearchNode> fringe = new PriorityQueue<SearchNode>(100,new Fifo());

	public BFS() {
	}

	public Stack<SearchNode> search(SearchNode root) {
		fringe.add(root);

		while (true) {
			if (fringe.size() == 0) {
				return null;
			}

			SearchNode node = fringe.poll();

			if (Solver.goalTest(node)) {
				return node.getPath();
			}
			fringe.addAll(expand(node));
		}
	}

	public Queue expand(SearchNode node) {
		
		LinkedList<SearchNode> successors = new LinkedList<SearchNode>();

		if (node.state.getBlankX() != 0) {
			Board left = node.state.copyBoard();
			left.moveLeft();
			successors.add(new SearchNode(left, node, 2, 0));
		}
		
		if (node.state.getBlankX() != node.state.N - 1) {
			Board right = node.state.copyBoard();
			right.moveRight();
			successors.add(new SearchNode(right, node, 3, 0));
		}

		if (node.state.getBlankY() != 0) {
			Board up = node.state.copyBoard();
			up.moveUp();
			successors.add(new SearchNode(up, node, 0, 0));
		}
		
		if (node.state.getBlankY() != node.state.N - 1) {
			Board down = node.state.copyBoard();
			down.moveDown();
			successors.add(new SearchNode(down, node, 1, 0));
		}

		return successors;
	}

}
