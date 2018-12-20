import java.util.Queue;
import java.util.Stack;

abstract class FringeSolver implements Solver {

    Stack<SearchNode> searchToDepth(Queue<SearchNode> fringe, double MAX_COST) {

        while (true) {
            if (fringe.size() == 0) {
                return null;
            }

            SearchNode node = fringe.poll();

            if (Solver.goalTest(node)) {
                return node.getPath();
            }
            fringe.addAll(expand(node, MAX_COST));
        }
    }

    abstract Queue<SearchNode> expand(SearchNode node, double MAX_COST);

    @Override
    public Stack<SearchNode> search(SearchNode root) {
        return null;
    }
}
