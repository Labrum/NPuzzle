import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;

public class NPuzzle {

	public static void main(String[] args) {
		InputStreamReader ISR = new InputStreamReader(System.in);
		BufferedReader BR = new BufferedReader(ISR);

		System.out.println("Select the algorithm you wish to run");
		System.out.println("0. BFS");
		System.out.println("1. DFS");
		System.out.println("2. Simulated DFID");
		System.out.println("3. DFID");
		System.out.println("4. A*");
		System.out.println("5. Simulated IDA*");
		System.out.println("6. Fringe Search");
		System.out.println("7. IDA*");
		int selectedAlg = -1;
		int N = -1;
		int randomMoves = -1;
		String printPath = "";
		try {
			selectedAlg = Integer.parseInt(BR.readLine());

			System.out.println("Choose size of N (8-puzzle = 3, 15-puzzle =4)");
			N = Integer.parseInt(BR.readLine());
			System.out.println("Choose number of random moves");
			randomMoves = Integer.parseInt(BR.readLine());
			System.out.println("Print found path? y/n");
			printPath = BR.readLine();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Board board = new Board(N);
		board.shuffle(randomMoves);
		SearchNode root = new SearchNode(board);
		Stack<SearchNode> path = new Stack<SearchNode>();
		long startTime=0, endTime =0;
		switch (selectedAlg) {
		case 0:
			BFS bfs = new BFS();
			startTime = System.nanoTime();
			path = bfs.search(root);
			endTime = System.nanoTime();
			break;
		case 1:
			DFID dfs = new DFID(Integer.MAX_VALUE);
			startTime = System.nanoTime();
			path = dfs.search(root);
			endTime = System.nanoTime();
			break;
		case 2:
			SimulatedDFID simDfid = new SimulatedDFID();
			startTime = System.nanoTime();
			path = simDfid.search(root);
			endTime = System.nanoTime();
			break;
		case 3:
			DFID dfid = new DFID( 0);
			startTime = System.nanoTime();
			path = dfid.search(root);
			endTime = System.nanoTime();
			break;
		case 4:
			AStar aStar = new AStar();
			startTime = System.nanoTime();
			path = aStar.search(root);
			endTime = System.nanoTime();
			break;
		case 5:
			SimulatedIDA simIDA = new SimulatedIDA(root, true);
			startTime = System.nanoTime();
			path = simIDA.search();
			endTime = System.nanoTime();
			break;
		case 6:
			FringeSearch fSearch = new FringeSearch();
			startTime = System.nanoTime();
			path = fSearch.search(root);
			endTime = System.nanoTime();
			break;
		case 7:
			IDA ida = new IDA();
			startTime = System.nanoTime();
			path = ida.search(root);
			endTime = System.nanoTime();
			break;
		default:
			break;
		}
		long duration = endTime-startTime;
		System.out.println("Solution found in:");
		System.out.println(duration + " nanoseconds");
		System.out.println(duration/1000000 + " milliseconds");
		if (printPath.equals("y")) {
			while (!path.isEmpty()) {
				SearchNode pathNode = path.pop();
				System.out.println(pathNode.state);
			}
		}
	}

}
