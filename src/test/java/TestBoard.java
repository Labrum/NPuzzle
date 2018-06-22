import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestBoard {

    private Board board;

    @Before
    public void setUp() {
        board = new Board(3);
    }

    @Test
    public void testMoves() {
        board.move(Directions.DOWN);

        assertEquals(board.getTile(0,0),3);
        assertEquals(board.getTile(0,1),-1);

        board.move(Directions.UP);
        assertEquals(board.getTile(0,0), -1);

        board.move(Directions.RIGHT);

        assertEquals(board.getTile(0,0),1);
        assertEquals(board.getTile(1,0),-1);

        board.move(Directions.LEFT);
        assertEquals(board.getTile(0,0), -1);
    }


}
