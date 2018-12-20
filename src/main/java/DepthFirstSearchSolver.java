import java.util.Stack;

public abstract class DepthFirstSearchSolver implements Solver {

    public Stack<SearchNode> processResult(SearchNode result) {
        if (result == null) {
            return null;
        } else {
            return result.getPath();
        }
    }

    @Override
    public Stack<SearchNode> search(SearchNode root) {
        while (true) {
            SearchNode result = expand(root);
            Stack<SearchNode> resultPath = processResult(root);
            if (resultPath != null) {
                return resultPath;
            }
        }
    }

    abstract void preExpandHook(SearchNode node);

    public SearchNode expand(SearchNode node) {
        preExpandHook(node);

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
}
