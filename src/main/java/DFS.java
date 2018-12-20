import java.util.Stack;

public class DFS extends DepthFirstSearchSolver {

	private SearchNode root;
	private int MAX_DEPTH;
	int count =0;

	public DFS(SearchNode root) {
		this.root = root;
		this.MAX_DEPTH = 0;
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

	@Override
	void preExpandHook(SearchNode node) {
	}
}