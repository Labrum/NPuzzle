import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;


public class SimulatedDFID implements Solver {
	private Stack<SearchNode> fringe = new Stack<>();
	int count = 0;
	public SimulatedDFID() {
	}
	
	public Stack<SearchNode> search(SearchNode root) {
		boolean found = false;
		int iteration = 0;
		Stack<SearchNode> path = null;
		while(!found){
			path = searchToDepth(root, iteration);
			if(path == null){
				iteration++;
			}else{
				found = true;
			}
		}
		return path;
	}

	public Stack<SearchNode> searchToDepth(SearchNode root, int MAX_DEPTH) {

		fringe.add(root);

		while (true) {
			if (fringe.size() == 0) {
				return null;
			}

			SearchNode node = fringe.pop();

			if (Solver.goalTest(node)) {
				System.out.println("Number of visited nodes : "+count);
				return node.getPath();
			}
			expand(node, MAX_DEPTH).stream().map(fringe::push);
			fringe.addAll(expand(node, MAX_DEPTH));
		}
	}

	public Queue<SearchNode> expand(SearchNode node, int MAX_DEPTH) {
		count++;
		LinkedList<SearchNode> successors = new LinkedList<>();

		if (node.depth == MAX_DEPTH) {
			return successors;
		}

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
