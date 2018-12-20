import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public interface Solver{

    Stack<SearchNode> search(SearchNode root);

    static boolean goalTest(SearchNode test) {
        boolean goal = true;
        for (int j = 0; j < test.state.N; j++) {
            for (int i = 0; i < test.state.N; i++) {
                if (test.state.getTile(i, j) != j * test.state.N + i ) {
                    if (i != 0 || j != 0) {
                        goal = false;
                    }
                }
            }
        }

        return goal;
    }

    static Queue<SearchNode> generateSuccessors(SearchNode node) {

        LinkedList<SearchNode> successors = new LinkedList<SearchNode>();

        for (Directions direction : Directions.values()) {
            Board child = node.state.copyBoard();
            if (child.move(direction)) {
                SearchNode childNode = new SearchNode(child, node, 0,
                        Solver.calcManhattanDistance(child) + node.depth + 1);
                successors.add(childNode);
            }
        }

        return successors;
    }

    static int calcManhattanDistance(Board board) {
        int sum = 0;

        for (int i = 0; i < board.N; i++) {
            for (int j = 0; j < board.N; j++) {
                int tile = board.getTile(i, j);

                if (tile != -1) {
                    sum += (int) Math.abs((tile) / board.N - j);
                    sum += (int) Math.abs((tile) % board.N - i);
                }
            }
        }

        return sum;
    }
}
