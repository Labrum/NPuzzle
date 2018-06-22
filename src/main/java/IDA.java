import java.util.Stack;

public class IDA extends Solver {

	private double cutOff;
	private double biggerThanCutOff = 0;
	int count =0;

	public IDA() {
		this.cutOff = 0;
	}

	public Stack<SearchNode> search(SearchNode root) {

		while (true) {
			SearchNode result = expand(root);
			if (result == null) {
				cutOff = biggerThanCutOff ;
			}else{
				System.out.println("Number of visited nodes : "+count);
				return result.getPath();
			}
		}
	}

	public SearchNode expand(SearchNode node) {

		if (node.cost > cutOff) {
			biggerThanCutOff = node.cost;
			return null;
		}

		if (goalTest(node)) {
			return node;
		}
		
		if (node.state.getBlankX() != 0) {
			Board left = node.state.copyBoard();
			left.moveLeft();
			SearchNode leftNode = new SearchNode(left, node, 0, calcManhattanDistance(left) + node.depth + 1);
			leftNode = expand(leftNode);
			if (leftNode != null) {
				return leftNode;
			}
		}

		if (node.state.getBlankX() != node.state.N - 1) {
			Board right = node.state.copyBoard();
			right.moveRight();
			SearchNode rightNode = new SearchNode(right, node, 1, calcManhattanDistance(right) + node.depth + 1);
			rightNode = expand(rightNode);
			if (rightNode != null) {
				return rightNode;
			}
		}

		if (node.state.getBlankY() != 0) {
			Board up = node.state.copyBoard();
			up.moveUp();
			SearchNode upNode = new SearchNode(up, node, 2, calcManhattanDistance(up) + node.depth + 1);
			upNode = expand(upNode);
			if (upNode != null) {
				return upNode;
			}
		}

		if (node.state.getBlankY() != node.state.N - 1) {
			Board down = node.state.copyBoard();
			down.moveDown();
			SearchNode downNode = new SearchNode(down, node, 3, calcManhattanDistance(down) + node.depth + 1);
			downNode = expand(downNode);
			if (downNode != null) {
				return downNode;
			}
		}

		return null;
	}
}