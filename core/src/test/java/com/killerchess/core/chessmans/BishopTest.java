package com.killerchess.core.chessmans;

import com.killerchess.core.chessboard.state.interpreter.StateInterpreter;
import javafx.util.Pair;
import junit.framework.TestCase;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BishopTest extends TestCase {

    private StateInterpreter stateInterpreter;

    @BeforeAll
    public void setUp() {
        stateInterpreter = new StateInterpreter();
    }

    @Test
    void whenWhiteBishopHasNoMoves() throws IOException {
        String boardArrangement = "{\"1\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"2\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"3\":[\"XX\",\"XX\",\"QW\",\"QW\",\"HB\",\"XX\",\"XX\",\"XX\"],\"4\":[\"XX\",\"XX\",\"PB\",\"BW\",\"PW\",\"XX\",\"XX\",\"XX\"],\"5\":[\"XX\",\"XX\",\"KW\",\"KB\",\"PB\",\"XX\",\"XX\",\"XX\"],\"6\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"7\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"8\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"]}";
        int bishopRow = 3;
        int bishopCol = 3;
        var chessBoard = stateInterpreter.convertJsonBoardToChessBoard(boardArrangement);

        var whiteBishop = chessBoard.getChessmanAt(bishopRow, bishopCol);
        assertTrue(whiteBishop instanceof Bishop);
        assertEquals(whiteBishop.getColour(), ChessmanColourEnum.WHITE);
        Set possibleMoves = whiteBishop.getPossibleMoves(chessBoard, new Pair<>(bishopRow, bishopCol));
        assertEquals(0, possibleMoves.size());
    }

    @Test
    void whenWhiteBishopHasSomeMoves() throws IOException {
        String boardArrangement = "{\"1\":[\"QW\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"2\":[\"XX\",\"KB\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"3\":[\"XX\",\"XX\",\"XX\",\"QW\",\"HB\",\"XX\",\"XX\",\"XX\"],\"4\":[\"XX\",\"XX\",\"PB\",\"BW\",\"PW\",\"XX\",\"XX\",\"XX\"],\"5\":[\"XX\",\"XX\",\"KW\",\"KB\",\"XX\",\"XX\",\"XX\",\"XX\"],\"6\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"7\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"HB\",\"XX\"],\"8\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"]}";
        int bishopRow = 3;
        int bishopCol = 3;
        var chessBoard = stateInterpreter.convertJsonBoardToChessBoard(boardArrangement);

        var whiteBishop = chessBoard.getChessmanAt(bishopRow, bishopCol);
        assertTrue(whiteBishop instanceof Bishop);
        assertEquals(whiteBishop.getColour(), ChessmanColourEnum.WHITE);
        Set possibleMoves = whiteBishop.getPossibleMoves(chessBoard, new Pair<>(bishopRow, bishopCol));
        assertEquals(3, possibleMoves.size());

        Set<Pair<Integer, Integer>> expectedMoves = new HashSet<>();
        expectedMoves.add(new Pair<>(2, 2));
        expectedMoves.add(new Pair<>(4, 4));
        expectedMoves.add(new Pair<>(5, 5));
        assertEquals(expectedMoves, possibleMoves);
    }

    @Test
    void whenWhiteBishopHasAllMoves() throws IOException {
        String boardArrangement = "{\"1\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"2\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"3\":[\"XX\",\"XX\",\"XX\",\"QW\",\"XX\",\"XX\",\"XX\",\"XX\"],\"4\":[\"XX\",\"XX\",\"PB\",\"BW\",\"PW\",\"XX\",\"XX\",\"XX\"],\"5\":[\"XX\",\"XX\",\"XX\",\"KB\",\"XX\",\"XX\",\"XX\",\"XX\"],\"6\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"7\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"8\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"]}";
        int bishopRow = 3;
        int bishopCol = 3;
        var chessBoard = stateInterpreter.convertJsonBoardToChessBoard(boardArrangement);

        var whiteBishop = chessBoard.getChessmanAt(bishopRow, bishopCol);
        assertTrue(whiteBishop instanceof Bishop);
        assertEquals(whiteBishop.getColour(), ChessmanColourEnum.WHITE);
        Set possibleMoves = whiteBishop.getPossibleMoves(chessBoard, new Pair<>(bishopRow, bishopCol));
        assertEquals(13, possibleMoves.size());

        Set<Pair<Integer, Integer>> expectedMoves = new HashSet<>();
        expectedMoves.add(new Pair<>(0, 0));
        expectedMoves.add(new Pair<>(1, 1));
        expectedMoves.add(new Pair<>(2, 2));
        expectedMoves.add(new Pair<>(4, 4));
        expectedMoves.add(new Pair<>(5, 5));
        expectedMoves.add(new Pair<>(6, 6));
        expectedMoves.add(new Pair<>(7, 7));
        expectedMoves.add(new Pair<>(0, 6));
        expectedMoves.add(new Pair<>(1, 5));
        expectedMoves.add(new Pair<>(2, 4));
        expectedMoves.add(new Pair<>(4, 2));
        expectedMoves.add(new Pair<>(5, 1));
        expectedMoves.add(new Pair<>(6, 0));
        assertEquals(expectedMoves, possibleMoves);
    }

    @Test
    void whenBlackBishopHasNoMoves() throws IOException {
        String boardArrangement = "{\"1\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"2\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"3\":[\"XX\",\"XX\",\"QW\",\"QW\",\"HB\",\"XX\",\"XX\",\"XX\"],\"4\":[\"XX\",\"XX\",\"PB\",\"BB\",\"PW\",\"XX\",\"XX\",\"XX\"],\"5\":[\"XX\",\"XX\",\"KW\",\"KB\",\"PB\",\"XX\",\"XX\",\"XX\"],\"6\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"7\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"8\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"]}";
        int bishopRow = 3;
        int bishopCol = 3;
        var chessBoard = stateInterpreter.convertJsonBoardToChessBoard(boardArrangement);

        var blackBishop = chessBoard.getChessmanAt(bishopRow, bishopCol);
        assertTrue(blackBishop instanceof Bishop);
        assertEquals(blackBishop.getColour(), ChessmanColourEnum.BLACK);
        Set possibleMoves = blackBishop.getPossibleMoves(chessBoard, new Pair<>(bishopRow, bishopCol));
        assertEquals(0, possibleMoves.size());

    }

    @Test
    void whenBlackBishopHasSomeMoves() throws IOException {
        String boardArrangement = "{\"1\":[\"QW\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"2\":[\"XX\",\"KB\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"3\":[\"XX\",\"XX\",\"XX\",\"QW\",\"HB\",\"XX\",\"XX\",\"XX\"],\"4\":[\"XX\",\"XX\",\"PB\",\"BB\",\"PW\",\"XX\",\"XX\",\"XX\"],\"5\":[\"XX\",\"XX\",\"KW\",\"KB\",\"XX\",\"XX\",\"XX\",\"XX\"],\"6\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"7\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"HB\",\"XX\"],\"8\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"]}";
        int bishopRow = 3;
        int bishopCol = 3;
        var chessBoard = stateInterpreter.convertJsonBoardToChessBoard(boardArrangement);

        var blackBishop = chessBoard.getChessmanAt(bishopRow, bishopCol);
        assertTrue(blackBishop instanceof Bishop);
        assertEquals(blackBishop.getColour(), ChessmanColourEnum.BLACK);
        Set possibleMoves = blackBishop.getPossibleMoves(chessBoard, new Pair<>(bishopRow, bishopCol));
        assertEquals(3, possibleMoves.size());

        Set<Pair<Integer, Integer>> expectedMoves = new HashSet<>();
        expectedMoves.add(new Pair<>(2, 2));
        expectedMoves.add(new Pair<>(4, 4));
        expectedMoves.add(new Pair<>(5, 5));
        assertEquals(expectedMoves, possibleMoves);
    }

    @Test
    void whenBlackBishopHasAllMoves() throws IOException {
        String boardArrangement = "{\"1\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"2\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"3\":[\"XX\",\"XX\",\"XX\",\"QW\",\"XX\",\"XX\",\"XX\",\"XX\"],\"4\":[\"XX\",\"XX\",\"PB\",\"BB\",\"PW\",\"XX\",\"XX\",\"XX\"],\"5\":[\"XX\",\"XX\",\"XX\",\"KB\",\"XX\",\"XX\",\"XX\",\"XX\"],\"6\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"7\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"8\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"]}";
        int bishopRow = 3;
        int bishopCol = 3;
        var chessBoard = stateInterpreter.convertJsonBoardToChessBoard(boardArrangement);

        var blackBishop = chessBoard.getChessmanAt(bishopRow, bishopCol);
        assertTrue(blackBishop instanceof Bishop);
        assertEquals(blackBishop.getColour(), ChessmanColourEnum.BLACK);
        Set possibleMoves = blackBishop.getPossibleMoves(chessBoard, new Pair<>(bishopRow, bishopCol));
        assertEquals(13, possibleMoves.size());

        Set<Pair<Integer, Integer>> expectedMoves = new HashSet<>();
        expectedMoves.add(new Pair<>(0, 0));
        expectedMoves.add(new Pair<>(1, 1));
        expectedMoves.add(new Pair<>(2, 2));
        expectedMoves.add(new Pair<>(4, 4));
        expectedMoves.add(new Pair<>(5, 5));
        expectedMoves.add(new Pair<>(6, 6));
        expectedMoves.add(new Pair<>(7, 7));
        expectedMoves.add(new Pair<>(0, 6));
        expectedMoves.add(new Pair<>(1, 5));
        expectedMoves.add(new Pair<>(2, 4));
        expectedMoves.add(new Pair<>(4, 2));
        expectedMoves.add(new Pair<>(5, 1));
        expectedMoves.add(new Pair<>(6, 0));
        assertEquals(expectedMoves, possibleMoves);
    }

    @Test
    void whenWhiteBishopHasNoCaptures() throws IOException {
        String boardArrangement = "{\"1\":[\"RW\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"PW\",\"XX\"],\"2\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"3\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"4\":[\"XX\",\"XX\",\"XX\",\"BW\",\"XX\",\"XX\",\"XX\",\"XX\"],\"5\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"6\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"7\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"QW\"],\"8\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"PW\"]}";
        int bishopRow = 3;
        int bishopCol = 3;
        var chessBoard = stateInterpreter.convertJsonBoardToChessBoard(boardArrangement);

        var whiteBishop = chessBoard.getChessmanAt(bishopRow, bishopCol);
        assertTrue(whiteBishop instanceof Bishop);
        assertEquals(whiteBishop.getColour(), ChessmanColourEnum.WHITE);
        Set possibleCaptures = whiteBishop.getPossibleCaptures(chessBoard, new Pair<>(bishopRow, bishopCol));
        assertEquals(0, possibleCaptures.size());
    }

    @Test
    void whenWhiteBishopHasSomeCaptures() throws IOException {
        String boardArrangement = "{\"1\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"2\":[\"XX\",\"KB\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"3\":[\"XX\",\"XX\",\"PB\",\"XX\",\"PB\",\"XX\",\"XX\",\"XX\"],\"4\":[\"XX\",\"XX\",\"XX\",\"BW\",\"XX\",\"XX\",\"XX\",\"XX\"],\"5\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"6\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"7\":[\"KW\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"8\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"PB\"]}";
        int bishopRow = 3;
        int bishopCol = 3;
        var chessBoard = stateInterpreter.convertJsonBoardToChessBoard(boardArrangement);

        var whiteBishop = chessBoard.getChessmanAt(bishopRow, bishopCol);
        assertTrue(whiteBishop instanceof Bishop);
        assertEquals(whiteBishop.getColour(), ChessmanColourEnum.WHITE);
        Set possibleCaptures = whiteBishop.getPossibleCaptures(chessBoard, new Pair<>(bishopRow, bishopCol));
        assertEquals(3, possibleCaptures.size());

        Set<Pair<Integer, Integer>> expectedCaptures = new HashSet<>();
        expectedCaptures.add(new Pair<>(2, 2));
        expectedCaptures.add(new Pair<>(2, 4));
        expectedCaptures.add(new Pair<>(7, 7));
        assertEquals(expectedCaptures, possibleCaptures);
    }

    @Test
    void whenWhiteBishopHasAllCaptures() throws IOException {
        String boardArrangement = "{\"1\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"2\":[\"XX\",\"KB\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"3\":[\"XX\",\"XX\",\"PB\",\"XX\",\"PB\",\"XX\",\"XX\",\"XX\"],\"4\":[\"XX\",\"XX\",\"XX\",\"BW\",\"XX\",\"XX\",\"XX\",\"XX\"],\"5\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"6\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"7\":[\"KB\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"PB\",\"XX\"],\"8\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"PB\"]}";
        int bishopRow = 3;
        int bishopCol = 3;
        var chessBoard = stateInterpreter.convertJsonBoardToChessBoard(boardArrangement);

        var whiteBishop = chessBoard.getChessmanAt(bishopRow, bishopCol);
        assertTrue(whiteBishop instanceof Bishop);
        assertEquals(whiteBishop.getColour(), ChessmanColourEnum.WHITE);
        Set possibleCaptures = whiteBishop.getPossibleCaptures(chessBoard, new Pair<>(bishopRow, bishopCol));
        assertEquals(4, possibleCaptures.size());

        Set<Pair<Integer, Integer>> expectedCaptures = new HashSet<>();
        expectedCaptures.add(new Pair<>(2, 2));
        expectedCaptures.add(new Pair<>(2, 4));
        expectedCaptures.add(new Pair<>(6, 0));
        expectedCaptures.add(new Pair<>(6, 6));
        assertEquals(expectedCaptures, possibleCaptures);
    }

    @Test
    void whenBlackBishopHasNoCaptures() throws IOException {
        String boardArrangement = "{\"1\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"2\":[\"XX\",\"KB\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"3\":[\"XX\",\"XX\",\"XX\",\"XX\",\"PB\",\"XX\",\"XX\",\"XX\"],\"4\":[\"XX\",\"XX\",\"XX\",\"BB\",\"XX\",\"XX\",\"XX\",\"XX\"],\"5\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"6\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"7\":[\"HB\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"8\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"]}";
        int bishopRow = 3;
        int bishopCol = 3;
        var chessBoard = stateInterpreter.convertJsonBoardToChessBoard(boardArrangement);

        var blackBishop = chessBoard.getChessmanAt(bishopRow, bishopCol);
        assertTrue(blackBishop instanceof Bishop);
        assertEquals(blackBishop.getColour(), ChessmanColourEnum.BLACK);
        Set possibleCaptures = blackBishop.getPossibleCaptures(chessBoard, new Pair<>(bishopRow, bishopCol));
        assertEquals(0, possibleCaptures.size());
    }

    @Test
    void whenBlackBishopHasSomeCaptures() throws IOException {
        String boardArrangement = "{\"1\":[\"HW\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"2\":[\"XX\",\"KW\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"3\":[\"XX\",\"XX\",\"XX\",\"XX\",\"PB\",\"XX\",\"XX\",\"XX\"],\"4\":[\"XX\",\"XX\",\"XX\",\"BB\",\"XX\",\"XX\",\"XX\",\"XX\"],\"5\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"6\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"7\":[\"HB\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"8\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"]}";
        int bishopRow = 3;
        int bishopCol = 3;
        var chessBoard = stateInterpreter.convertJsonBoardToChessBoard(boardArrangement);

        var blackBishop = chessBoard.getChessmanAt(bishopRow, bishopCol);
        assertTrue(blackBishop instanceof Bishop);
        assertEquals(blackBishop.getColour(), ChessmanColourEnum.BLACK);
        Set possibleCaptures = blackBishop.getPossibleCaptures(chessBoard, new Pair<>(bishopRow, bishopCol));
        assertEquals(1, possibleCaptures.size());

        Set<Pair<Integer, Integer>> expectedCaptures = new HashSet<>();
        expectedCaptures.add(new Pair<>(1, 1));
        assertEquals(expectedCaptures, possibleCaptures);
    }

    @Test
    void whenBlackBishopHasAllCaptures() throws IOException {
        String boardArrangement = "{\"1\":[\"HW\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"2\":[\"XX\",\"KW\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"3\":[\"XX\",\"XX\",\"XX\",\"XX\",\"PW\",\"XX\",\"XX\",\"XX\"],\"4\":[\"XX\",\"XX\",\"XX\",\"BB\",\"XX\",\"XX\",\"XX\",\"XX\"],\"5\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"6\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"PW\",\"XX\",\"XX\"],\"7\":[\"HW\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"8\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"]}";
        int bishopRow = 3;
        int bishopCol = 3;
        var chessBoard = stateInterpreter.convertJsonBoardToChessBoard(boardArrangement);

        var blackBishop = chessBoard.getChessmanAt(bishopRow, bishopCol);
        assertTrue(blackBishop instanceof Bishop);
        assertEquals(blackBishop.getColour(), ChessmanColourEnum.BLACK);
        Set possibleCaptures = blackBishop.getPossibleCaptures(chessBoard, new Pair<>(bishopRow, bishopCol));
        assertEquals(4, possibleCaptures.size());

        Set<Pair<Integer, Integer>> expectedCaptures = new HashSet<>();
        expectedCaptures.add(new Pair<>(1, 1));
        expectedCaptures.add(new Pair<>(2, 4));
        expectedCaptures.add(new Pair<>(5, 5));
        expectedCaptures.add(new Pair<>(6, 0));
        assertEquals(expectedCaptures, possibleCaptures);
    }

}