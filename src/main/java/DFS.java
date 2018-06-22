import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

public class DFS {

	private SearchNode root;
	private int MAX_DEPTH;
	int count =0;

	public DFS(SearchNode root) {
		this.root = root;
		this.MAX_DEPTH = 0;
	}

	public Stack<SearchNode> search() {

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

	public boolean goalTest(SearchNode test) {
		boolean goal = true;
		for (int j = 0; j < test.state.N; j++) {
			for (int i = 0; i < test.state.N; i++) {
				if (test.state.getTile(i, j) != j * test.state.N + i ) {
					if (i != 0|| j != 0) {
						goal = false;
					}
				}
			}
		}

		return goal;

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
		DFS dfs = new DFS(node);
		SimulatedDFID dfid = new SimulatedDFID();

		long startTime = System.nanoTime();
		Stack<SearchNode> pathDFID = dfid.search(node);
		long endTime = System.nanoTime();
		long duration = (endTime - startTime);
		System.out.println("Simulated DFID");
		System.out.println(pathDFID.size());
		System.out.println(duration);
	
		startTime = System.nanoTime();
		Stack<SearchNode> pathDFS = dfs.search();		
		endTime = System.nanoTime();
		duration = (endTime - startTime);
		System.out.println("Real DFID");
		System.out.println(pathDFS.size());
		System.out.println(duration);


		
		
	}
}