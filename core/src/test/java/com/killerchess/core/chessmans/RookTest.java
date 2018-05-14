package com.killerchess.core.chessmans;

import com.killerchess.core.chessboard.state.interpreter.StateInterpreter;
import javafx.util.Pair;
import junit.framework.TestCase;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.HashSet;
import java.util.Set;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RookTest extends TestCase {

    private StateInterpreter stateInterpreter;

    @BeforeAll
    public void setUp() {
        stateInterpreter = new StateInterpreter();
    }

    @Test
    void whiteRookHasNoMoves() {
        var boardArrangement = "{\"1\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"2\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"3\":[\"XX\",\"XX\",\"XX\",\"QW\",\"XX\",\"XX\",\"XX\",\"XX\"],\"4\":[\"XX\",\"XX\",\"PB\",\"RW\",\"PW\",\"XX\",\"XX\",\"XX\"],\"5\":[\"XX\",\"XX\",\"XX\",\"KB\",\"XX\",\"XX\",\"XX\",\"XX\"],\"6\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"7\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"8\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"]}";
        var rookRow = 3;
        var rookCol = 3;
        var chessBoard = stateInterpreter.convertJsonBoardToChessBoard(boardArrangement);

        var whiteRook = chessBoard.getChessmanAt(rookRow, rookCol);
        assertTrue(whiteRook instanceof Rook);
        assertEquals(whiteRook.getColour(), ChessmanColourEnum.WHITE);
        Set possibleMoves = whiteRook.getPossibleMoves(chessBoard, new Pair<>(rookRow, rookCol));
        assertEquals(0, possibleMoves.size());
    }

    @Test
    void whiteRookHasSomeMoves() {
        var boardArrangement = "{\"1\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"2\":[\"XX\",\"XX\",\"XX\",\"QW\",\"XX\",\"XX\",\"XX\",\"XX\"],\"3\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"4\":[\"XX\",\"XX\",\"PB\",\"RW\",\"XX\",\"KB\",\"XX\",\"XX\"],\"5\":[\"XX\",\"XX\",\"XX\",\"KB\",\"XX\",\"XX\",\"XX\",\"XX\"],\"6\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"7\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"8\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"]}";
        var rookRow = 3;
        var rookCol = 3;
        var chessBoard = stateInterpreter.convertJsonBoardToChessBoard(boardArrangement);

        var whiteRook = chessBoard.getChessmanAt(rookRow, rookCol);
        assertTrue(whiteRook instanceof Rook);
        assertEquals(whiteRook.getColour(), ChessmanColourEnum.WHITE);
        Set possibleMoves = whiteRook.getPossibleMoves(chessBoard, new Pair<>(rookRow, rookCol));
        assertEquals(2, possibleMoves.size());

        var expectedMoves = new HashSet<Pair<Integer, Integer>>();
        expectedMoves.add(new Pair<>(3, 4));
        expectedMoves.add(new Pair<>(2, 3));
        assertEquals(expectedMoves, possibleMoves);
    }

    @Test
    void whiteRookHasAllMoves() {
        var boardArrangement = "{\"1\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"2\":[\"QW\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"3\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"4\":[\"XX\",\"XX\",\"XX\",\"RW\",\"XX\",\"XX\",\"XX\",\"XX\"],\"5\":[\"XX\",\"XX\",\"XX\",\"XX\",\"HB\",\"XX\",\"XX\",\"XX\"],\"6\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"7\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"8\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"]}";
        var rookRow = 3;
        var rookCol = 3;
        var chessBoard = stateInterpreter.convertJsonBoardToChessBoard(boardArrangement);

        var whiteRook = chessBoard.getChessmanAt(rookRow, rookCol);
        assertTrue(whiteRook instanceof Rook);
        assertEquals(whiteRook.getColour(), ChessmanColourEnum.WHITE);
        Set possibleMoves = whiteRook.getPossibleMoves(chessBoard, new Pair<>(rookRow, rookCol));
        assertEquals(14, possibleMoves.size());

        var expectedMoves = new HashSet<Pair<Integer, Integer>>();
        expectedMoves.add(new Pair<>(3, 0));
        expectedMoves.add(new Pair<>(3, 1));
        expectedMoves.add(new Pair<>(3, 2));
        expectedMoves.add(new Pair<>(3, 4));
        expectedMoves.add(new Pair<>(3, 5));
        expectedMoves.add(new Pair<>(3, 6));
        expectedMoves.add(new Pair<>(3, 7));
        expectedMoves.add(new Pair<>(0, 3));
        expectedMoves.add(new Pair<>(1, 3));
        expectedMoves.add(new Pair<>(2, 3));
        expectedMoves.add(new Pair<>(4, 3));
        expectedMoves.add(new Pair<>(5, 3));
        expectedMoves.add(new Pair<>(6, 3));
        expectedMoves.add(new Pair<>(7, 3));
        assertEquals(expectedMoves, possibleMoves);
    }

    @Test
    void blackRookHasNoMoves() {
        var boardArrangement = "{\"1\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"2\":[\"QW\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"3\":[\"XX\",\"XX\",\"XX\",\"KW\",\"XX\",\"XX\",\"XX\",\"XX\"],\"4\":[\"XX\",\"XX\",\"QW\",\"RB\",\"PB\",\"XX\",\"XX\",\"XX\"],\"5\":[\"XX\",\"XX\",\"XX\",\"HB\",\"HB\",\"XX\",\"XX\",\"XX\"],\"6\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"7\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"8\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"]}";
        var rookRow = 3;
        var rookCol = 3;
        var chessBoard = stateInterpreter.convertJsonBoardToChessBoard(boardArrangement);

        var blackRook = chessBoard.getChessmanAt(rookRow, rookCol);
        assertTrue(blackRook instanceof Rook);
        assertEquals(blackRook.getColour(), ChessmanColourEnum.BLACK);
        Set possibleMoves = blackRook.getPossibleMoves(chessBoard, new Pair<>(rookRow, rookCol));
        assertEquals(0, possibleMoves.size());

    }

    @Test
    void blackRookHasSomeMoves() {
        var boardArrangement = "{\"1\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"2\":[\"QW\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"3\":[\"XX\",\"XX\",\"XX\",\"KW\",\"XX\",\"XX\",\"XX\",\"XX\"],\"4\":[\"XX\",\"QW\",\"XX\",\"RB\",\"PB\",\"XX\",\"XX\",\"XX\"],\"5\":[\"XX\",\"XX\",\"XX\",\"XX\",\"HB\",\"XX\",\"XX\",\"XX\"],\"6\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"7\":[\"XX\",\"XX\",\"XX\",\"PB\",\"XX\",\"XX\",\"XX\",\"XX\"],\"8\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"]}";
        var rookRow = 3;
        var rookCol = 3;
        var chessBoard = stateInterpreter.convertJsonBoardToChessBoard(boardArrangement);

        var blackRook = chessBoard.getChessmanAt(rookRow, rookCol);
        assertTrue(blackRook instanceof Rook);
        assertEquals(blackRook.getColour(), ChessmanColourEnum.BLACK);
        Set possibleMoves = blackRook.getPossibleMoves(chessBoard, new Pair<>(rookRow, rookCol));
        assertEquals(3, possibleMoves.size());

        var expectedMoves = new HashSet<Pair<Integer, Integer>>();
        expectedMoves.add(new Pair<>(3, 2));
        expectedMoves.add(new Pair<>(4, 3));
        expectedMoves.add(new Pair<>(5, 3));
        assertEquals(expectedMoves, possibleMoves);
    }

    @Test
    void blackRookHasAllMoves() {
        var boardArrangement = "{\"1\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"2\":[\"QW\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"3\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"4\":[\"XX\",\"XX\",\"XX\",\"RB\",\"XX\",\"XX\",\"XX\",\"XX\"],\"5\":[\"XX\",\"XX\",\"XX\",\"XX\",\"HB\",\"XX\",\"XX\",\"XX\"],\"6\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"7\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"8\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"]}";
        var rookRow = 3;
        var rookCol = 3;
        var chessBoard = stateInterpreter.convertJsonBoardToChessBoard(boardArrangement);

        var blackRook = chessBoard.getChessmanAt(rookRow, rookCol);
        assertTrue(blackRook instanceof Rook);
        assertEquals(blackRook.getColour(), ChessmanColourEnum.BLACK);
        Set possibleMoves = blackRook.getPossibleMoves(chessBoard, new Pair<>(rookRow, rookCol));
        assertEquals(14, possibleMoves.size());

        var expectedMoves = new HashSet<Pair<Integer, Integer>>();
        expectedMoves.add(new Pair<>(3, 0));
        expectedMoves.add(new Pair<>(3, 1));
        expectedMoves.add(new Pair<>(3, 2));
        expectedMoves.add(new Pair<>(3, 4));
        expectedMoves.add(new Pair<>(3, 5));
        expectedMoves.add(new Pair<>(3, 6));
        expectedMoves.add(new Pair<>(3, 7));
        expectedMoves.add(new Pair<>(0, 3));
        expectedMoves.add(new Pair<>(1, 3));
        expectedMoves.add(new Pair<>(2, 3));
        expectedMoves.add(new Pair<>(4, 3));
        expectedMoves.add(new Pair<>(5, 3));
        expectedMoves.add(new Pair<>(6, 3));
        expectedMoves.add(new Pair<>(7, 3));
        assertEquals(expectedMoves, possibleMoves);
    }

    @Test
    void whiteRookHasNoCaptures() {
        var boardArrangement = "{\"1\":[\"XX\",\"XX\",\"XX\",\"PB\",\"XX\",\"XX\",\"XX\",\"XX\"],\"2\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"3\":[\"XX\",\"XX\",\"XX\",\"QW\",\"XX\",\"XX\",\"XX\",\"XX\"],\"4\":[\"XX\",\"XX\",\"PW\",\"RW\",\"XX\",\"XX\",\"XX\",\"XX\"],\"5\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"6\":[\"XX\",\"XX\",\"XX\",\"QW\",\"XX\",\"XX\",\"XX\",\"XX\"],\"7\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"8\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"]}";
        var rookRow = 3;
        var rookCol = 3;
        var chessBoard = stateInterpreter.convertJsonBoardToChessBoard(boardArrangement);

        var whiteRook = chessBoard.getChessmanAt(rookRow, rookCol);
        assertTrue(whiteRook instanceof Rook);
        assertEquals(whiteRook.getColour(), ChessmanColourEnum.WHITE);
        var possibleCaptures = whiteRook.getPossibleCaptures(chessBoard, new Pair<>(rookRow, rookCol));
        assertEquals(0, possibleCaptures.size());
    }

    @Test
    void whiteRookHasSomeCaptures() {
        var boardArrangement = "{\"1\":[\"XX\",\"XX\",\"XX\",\"PB\",\"XX\",\"XX\",\"XX\",\"XX\"],\"2\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"3\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"4\":[\"XX\",\"XX\",\"PB\",\"RW\",\"XX\",\"XX\",\"XX\",\"XX\"],\"5\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"6\":[\"XX\",\"XX\",\"XX\",\"QW\",\"XX\",\"XX\",\"XX\",\"XX\"],\"7\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"8\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"]}";
        var rookRow = 3;
        var rookCol = 3;
        var chessBoard = stateInterpreter.convertJsonBoardToChessBoard(boardArrangement);

        var whiteRook = chessBoard.getChessmanAt(rookRow, rookCol);
        assertTrue(whiteRook instanceof Rook);
        assertEquals(whiteRook.getColour(), ChessmanColourEnum.WHITE);
        var possibleCaptures = whiteRook.getPossibleCaptures(chessBoard, new Pair<>(rookRow, rookCol));
        assertEquals(2, possibleCaptures.size());

        var expectedCaptures = new HashSet<Pair<Integer, Integer>>();
        expectedCaptures.add(new Pair<>(0, 3));
        expectedCaptures.add(new Pair<>(3, 2));
        assertEquals(expectedCaptures, possibleCaptures);
    }

    @Test
    void whiteRookHasAllCaptures() {
        var boardArrangement = "{\"1\":[\"XX\",\"XX\",\"XX\",\"HB\",\"XX\",\"XX\",\"XX\",\"XX\"],\"2\":[\"XX\",\"XX\",\"KB\",\"XX\",\"PB\",\"XX\",\"XX\",\"XX\"],\"3\":[\"XX\",\"PB\",\"XX\",\"XX\",\"XX\",\"PB\",\"XX\",\"XX\"],\"4\":[\"XX\",\"XX\",\"PB\",\"RW\",\"XX\",\"QB\",\"XX\",\"XX\"],\"5\":[\"XX\",\"QB\",\"XX\",\"PB\",\"XX\",\"PB\",\"XX\",\"XX\"],\"6\":[\"XX\",\"XX\",\"PB\",\"XX\",\"KB\",\"XX\",\"XX\",\"XX\"],\"7\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"8\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"]}";
        var rookRow = 3;
        var rookCol = 3;
        var chessBoard = stateInterpreter.convertJsonBoardToChessBoard(boardArrangement);

        var whiteRook = chessBoard.getChessmanAt(rookRow, rookCol);
        assertTrue(whiteRook instanceof Rook);
        assertEquals(whiteRook.getColour(), ChessmanColourEnum.WHITE);
        var possibleCaptures = whiteRook.getPossibleCaptures(chessBoard, new Pair<>(rookRow, rookCol));
        assertEquals(4, possibleCaptures.size());

        var expectedCaptures = new HashSet<Pair<Integer, Integer>>();
        expectedCaptures.add(new Pair<>(0, 3));
        expectedCaptures.add(new Pair<>(3, 2));
        expectedCaptures.add(new Pair<>(3, 5));
        expectedCaptures.add(new Pair<>(4, 3));
        assertEquals(expectedCaptures, possibleCaptures);
    }

    @Test
    void blackRookHasNoCaptures() {
        var boardArrangement = "{\"1\":[\"XX\",\"XX\",\"XX\",\"PW\",\"XX\",\"XX\",\"XX\",\"XX\"],\"2\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"3\":[\"XX\",\"XX\",\"XX\",\"QB\",\"XX\",\"XX\",\"XX\",\"XX\"],\"4\":[\"XX\",\"XX\",\"PB\",\"RB\",\"XX\",\"XX\",\"XX\",\"XX\"],\"5\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"6\":[\"XX\",\"XX\",\"XX\",\"QB\",\"XX\",\"XX\",\"XX\",\"XX\"],\"7\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"8\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"]}";
        var rookRow = 3;
        var rookCol = 3;
        var chessBoard = stateInterpreter.convertJsonBoardToChessBoard(boardArrangement);

        var blackRook = chessBoard.getChessmanAt(rookRow, rookCol);
        assertTrue(blackRook instanceof Rook);
        assertEquals(blackRook.getColour(), ChessmanColourEnum.BLACK);
        var possibleCaptures = blackRook.getPossibleCaptures(chessBoard, new Pair<>(rookRow, rookCol));
        assertEquals(0, possibleCaptures.size());
    }

    @Test
    void blackRookHasSomeCaptures() {
        var boardArrangement = "{\"1\":[\"XX\",\"XX\",\"XX\",\"PW\",\"XX\",\"XX\",\"XX\",\"XX\"],\"2\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"3\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"4\":[\"XX\",\"PW\",\"XX\",\"RB\",\"XX\",\"XX\",\"XX\",\"XX\"],\"5\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"6\":[\"XX\",\"XX\",\"XX\",\"QB\",\"XX\",\"XX\",\"XX\",\"XX\"],\"7\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"8\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"]}";
        var rookRow = 3;
        var rookCol = 3;
        var chessBoard = stateInterpreter.convertJsonBoardToChessBoard(boardArrangement);

        var blackRook = chessBoard.getChessmanAt(rookRow, rookCol);
        assertTrue(blackRook instanceof Rook);
        assertEquals(blackRook.getColour(), ChessmanColourEnum.BLACK);
        var possibleCaptures = blackRook.getPossibleCaptures(chessBoard, new Pair<>(rookRow, rookCol));
        assertEquals(2, possibleCaptures.size());

        var expectedCaptures = new HashSet<Pair<Integer, Integer>>();
        expectedCaptures.add(new Pair<>(0, 3));
        expectedCaptures.add(new Pair<>(3, 1));
        assertEquals(expectedCaptures, possibleCaptures);
    }

    @Test
    void blackRookHasAllCaptures() {
        var boardArrangement = "{\"1\":[\"XX\",\"XX\",\"XX\",\"PW\",\"XX\",\"XX\",\"XX\",\"XX\"],\"2\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"3\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"4\":[\"XX\",\"PW\",\"XX\",\"RB\",\"HW\",\"XX\",\"XX\",\"XX\"],\"5\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"6\":[\"XX\",\"XX\",\"XX\",\"QW\",\"XX\",\"XX\",\"XX\",\"XX\"],\"7\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"8\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"]}";
        var rookRow = 3;
        var rookCol = 3;
        var chessBoard = stateInterpreter.convertJsonBoardToChessBoard(boardArrangement);

        var blackRook = chessBoard.getChessmanAt(rookRow, rookCol);
        assertTrue(blackRook instanceof Rook);
        assertEquals(blackRook.getColour(), ChessmanColourEnum.BLACK);
        var possibleCaptures = blackRook.getPossibleCaptures(chessBoard, new Pair<>(rookRow, rookCol));
        assertEquals(4, possibleCaptures.size());

        var expectedCaptures = new HashSet<Pair<Integer, Integer>>();
        expectedCaptures.add(new Pair<>(0, 3));
        expectedCaptures.add(new Pair<>(3, 1));
        expectedCaptures.add(new Pair<>(3, 4));
        expectedCaptures.add(new Pair<>(5, 3));
        assertEquals(expectedCaptures, possibleCaptures);
    }

}