import java.util.Arrays;

/**
 * Represents the boardstate, additional functions to shuffle and instantiate
 * boards
 *
 */
public class Board implements BoardInterface {

	public int[][] board = null;
	public final int N;
	private static final int BLANK = -1;

	public int getTile(int x, int y) {
		return board[y][x];
	}

	public void setBoard(int[][] board) {
		this.board = new int[board.length][board.length];
		for (int i = 0; i < board.length; i++) {
			System.arraycopy(board[i],0, this.board[i], 0, board.length);
		}
	}

	public Board copyBoard() {
		Board copy = new Board(N);
		copy.setBoard(this.board);
		return copy;
	}

	public Board(int n) {
		N = n;
		board = new int[N][N];

		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				board[i][j] = i * N + j;
			}
		}

		board[0][0] = BLANK;
	}

	public boolean equals(Object o) {

		if (o instanceof Board) {
			Board oBoard = (Board) o;
			if (Arrays.deepEquals(oBoard.board, this.board)) {
				return true;
			}
		}

		return false;

	}

	public void shuffle(int numberOfShuffles) {;
		int moves = 0;
		while (moves  < numberOfShuffles) {
			if(move(Directions.randomDirection())){
				moves++;
			}
		}
	}

	public String toString() {
		String ret = "";
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				ret += board[i][j] + " ";
			}
			ret += "\n";
		}
		return ret;
	}

	/**
	 * Return the position of the blank block in the grid. The position is
	 * returned as a length 2 int array.
	 * 
	 * int[0] = x, int[1] =y
	 * 
	 * @return int[2]
	 */
	public int[] blankPosition() {
		int[] position = new int[2];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (board[i][j] == BLANK) {
					position[1] = i;
					position[0] = j;
				}
			}
		}

		return position;
	}

	public int getBlankX() {
		return blankPosition()[0];
	}

	public int getBlankY() {
		return blankPosition()[1];

	}

	/**
	 * Makes the board move in the selected direction.
	 */
	public boolean move(Directions move) {
		switch (move) {
            case UP:
				return moveUp();
			case DOWN:
				return moveDown();
			case LEFT:
				return moveLeft();
			case RIGHT:
				return moveRight();
			default:
				return false;
		}
	}

	/**
	 * Moves the blank tile up one space
	 */
	public boolean moveUp() {
		int[] XY = blankPosition();
		int Y = XY[1];
		int X = XY[0];
		if (Y == 0) {
			return false;
		}

		int temp = board[Y - 1][X];
		board[Y - 1][X] = BLANK;
		board[Y][X] = temp;
		return true;
	}

	/**
	 * Moves the blank tile up down one space
	 */
	public boolean moveDown() {
		int[] XY = blankPosition();
		int Y = XY[1];
		int X = XY[0];

		if (Y == N - 1) {
			return false;
		}

		int temp = board[Y + 1][X];
		board[Y + 1][X] = BLANK;
		board[Y][X] = temp;
		return true;
	}

	/**
	 * Moves the blank tile up one space
	 */
	public boolean moveLeft() {
		int[] XY = blankPosition();
		int Y = XY[1];
		int X = XY[0];
		if (X == 0) {
			return false;
		}

		int temp = board[Y][X - 1];
		board[Y][X - 1] = BLANK;
		board[Y][X] = temp;
		return true;
	}

	/**
	 * Moves the blank tile up down one space
	 */
	public boolean moveRight() {
		int[] XY = blankPosition();
		int Y = XY[1];
		int X = XY[0];

		if (X == N - 1) {
			return false;
		}

		int temp = board[Y][X + 1];
		board[Y][X + 1] = BLANK;
		board[Y][X] = temp;
		return true;
	}

}
