import java.util.Stack;

public abstract class Solver{

    public Stack<SearchNode> search(SearchNode root){
        return new Stack<>();
    }

    public boolean goalTest(SearchNode test) {
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

    public int calcManhattanDistance(Board board) {
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
