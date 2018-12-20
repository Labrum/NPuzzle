import java.util.Stack;

public class DFID implements Solver {

	private int MAX_DEPTH;
	int count = 0;

	public DFID(int max_depth) {
		this.MAX_DEPTH = max_depth;
	}

	public Stack<SearchNode> search(SearchNode root) {

		SearchNode node = root;
		while (true) {
			SearchNode result = expand(node);
			if (result == null) {
				MAX_DEPTH++;
			}else{
				System.out.println("Number of visited nodes : " + count);
				return result.getPath();
			}
		}
	}

	public SearchNode expand(SearchNode node) {
		count++;
		if (node.depth == MAX_DEPTH) {
			return null;
		}

		if (Solver.goalTest(node)) {
			return node;
		}
		
		for (Directions direction : Directions.values()) {
			Board child = node.state.copyBoard();
			if (child.move(direction)) {
				SearchNode childNode = new SearchNode(child, node, 0, 0);
				childNode = expand(childNode);
				if(childNode != null){
					return childNode;
				}
			}
		}
		
		return null;
	}

	public static void main(String[] args) {

		long simTotalDuration = 0;
		long realTotalDuration = 0;
		for (int i =0; i < 50; i++) {
			Board board = new Board(3);
			board.shuffle(30);
			System.out.println(board);

			SearchNode node = new SearchNode(board);
			SimulatedDFID dfs = new SimulatedDFID();
			DFID dfid = new DFID(0);

			long startTime = System.nanoTime();
			Stack<SearchNode> pathDFID = dfid.search(node);
			long endTime = System.nanoTime();
			long duration = (endTime - startTime);
			simTotalDuration += duration;
			//System.out.println("Simulated DFID");
			//System.out.println(pathDFID.size());
			//System.out.println(duration);

			startTime = System.nanoTime();
			Stack<SearchNode> pathDFS = dfs.search(node);
			endTime = System.nanoTime();
			duration = (endTime - startTime);
			//System.out.println("Real DFID");
			//System.out.println(pathDFS.size());
			//System.out.println(duration);

			realTotalDuration += duration;
		}
		System.out.println("Sim total duration "+simTotalDuration);
		System.out.println("Real total duration "+realTotalDuration);
		System.out.println("Sim is faster by : " + (realTotalDuration - simTotalDuration));
	}
}