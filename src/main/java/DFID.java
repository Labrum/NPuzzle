import java.util.Stack;

public class DFID extends Solver {

	private int MAX_DEPTH;
	int count =0;

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
				System.out.println("Number of visited nodes : "+count);
				return result.getPath();
			}
		}
	}

	public SearchNode expand(SearchNode node) {
		
		count++;
		if (node.depth >= MAX_DEPTH) {
			return null;
		}

		if (goalTest(node)) {
			return node;
		}
		
		for (int i = 0; i < 4; i++) {
			Board child = node.state.copyBoard();
			if (child.move(Directions.values()[i])) {
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
		Board board = new Board(3);
		int[][] setup = new int[][] { { 4,3, 1  }, { 8,-1,2 }, { 7,6,5 } };
		board.setBoard(setup);
	//	board.shuffle(30);
		System.out.println(board);
		
		SearchNode node = new SearchNode(board);
		DFID dfs = new DFID(0);
		SimulatedDFID dfid = new SimulatedDFID();

		long startTime = System.nanoTime();
		Stack<SearchNode> pathDFID = dfid.search(node);
		long endTime = System.nanoTime();
		long duration = (endTime - startTime);
		System.out.println("Simulated DFID");
		System.out.println(pathDFID.size());
		System.out.println(duration);
	
		startTime = System.nanoTime();
		Stack<SearchNode> pathDFS = dfs.search(node);
		endTime = System.nanoTime();
		duration = (endTime - startTime);
		System.out.println("Real DFID");
		System.out.println(pathDFS.size());
		System.out.println(duration);
	}
}