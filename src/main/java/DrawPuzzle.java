import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import java.awt.*;
import java.util.Stack;

public class DrawPuzzle {

    private static IDA astar = new IDA();
    private static int TILE_PIXEL_WIDTH = 0;
    private static int TOTAL_PIXEL_WIDTH = 600;
    private static double TOTAL_PATH_LENGTH = 0.0;
    private static int HEURISTIC_STARTING_POINT = 0;

    public static void main(String[] args) {

        Board board = new Board(4);
        board.shuffle(60);

        TILE_PIXEL_WIDTH = TOTAL_PIXEL_WIDTH/board.N;

        DrawPuzzle draw = new DrawPuzzle();
        JFrame frame = draw.buildFrame();

        Stack<SearchNode> path = astar.search(new SearchNode(board));

        TOTAL_PATH_LENGTH = (double)path.size();
        HEURISTIC_STARTING_POINT = astar.calcManhattanDistance(board)+4;
        System.out.println ("Path found length : " + path.size() );

        while (!path.empty()) {
            SearchNode node = path.pop();
            frame.getContentPane().removeAll();
            frame.setContentPane(draw.drawBoard(node.state));
            frame.validate();
            frame.repaint();
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            frame.getContentPane().removeAll();
            frame.setContentPane(draw.boardEvaluation(node.state, node.depth + 1, node));
            frame.validate();
            frame.repaint();
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public JPanel boardEvaluation(Board board, int depth, SearchNode node) {
        return new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                drawBlankBoard(g, board.N);
                Board possibleMoves[] = new Board[4];
                for (int i = 0; i < 4; i++) {
                    possibleMoves[i] = board.copyBoard();
                    if (possibleMoves[i].move(Directions.values()[i])) {
                        drawEvaluationRect(g,possibleMoves[i], depth);
                    }
                }
                drawTileText(g,node.state);
            }
        };
    }

    private void drawEvaluationRect(Graphics g, Board board, int depth) {

        double maxManhattanDist = (double) HEURISTIC_STARTING_POINT;
        int manhattanDist = astar.calcManhattanDistance(board);

        System.out.println(manhattanDist+" / "+maxManhattanDist);
        int redValue = (int) ((manhattanDist/maxManhattanDist) * (255));
        int greenValue = (int) (depth/(TOTAL_PATH_LENGTH) * (255));

        System.out.println(redValue+" : "+ greenValue);
        Color heuristic = new Color(redValue,greenValue,0);
        g.setColor(heuristic);
        g.fillRect(board.getBlankX() * TILE_PIXEL_WIDTH,board.getBlankY() * TILE_PIXEL_WIDTH, TILE_PIXEL_WIDTH, TILE_PIXEL_WIDTH);
        g.setColor(Color.black);
    }

    private void drawBlankBoard(Graphics g, int columns) {
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0,0,TOTAL_PIXEL_WIDTH,TOTAL_PIXEL_WIDTH);
        drawGrid(g, columns);
    }

    private void drawGrid(Graphics g, int columns) {
        g.setColor(Color.black);
        for (int i = 1; i < columns; i++) {
            g.drawLine(i*TILE_PIXEL_WIDTH,0,i*TILE_PIXEL_WIDTH,TOTAL_PIXEL_WIDTH);
        }

        for (int i = 0; i < columns; i++) {
            g.drawLine(0,i*TILE_PIXEL_WIDTH,TOTAL_PIXEL_WIDTH,i*TILE_PIXEL_WIDTH);
        }
    }

    private void drawTileText(Graphics g, Board board) {
        Font newFont = Font.getFont(Font.SANS_SERIF);
        g.setFont(newFont);
        for (int i = 0; i < board.N; i++) {
            for (int j = 0; j < board.N; j++){
                String tile = board.getTile(i, j) + "";
                if (!tile.equals("-1")){
                    g.drawString(board.getTile(i, j)+"", (TILE_PIXEL_WIDTH/2)+ i * TILE_PIXEL_WIDTH , (TILE_PIXEL_WIDTH/2) + j*TILE_PIXEL_WIDTH);
                }
            }
        }
    }

    public JPanel drawBoard(Board board){
        return new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                drawBlankBoard(g,board.N);
                drawTileText(g,board);
            }
        };
    }

    private JFrame buildFrame() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(TOTAL_PIXEL_WIDTH, TOTAL_PIXEL_WIDTH);
        frame.setVisible(true);
        return frame;
    }
}
