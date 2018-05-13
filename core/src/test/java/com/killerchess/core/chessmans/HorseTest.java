package com.killerchess.core.chessmans;

import com.killerchess.core.chessboard.state.interpreter.StateInterpreter;
import javafx.util.Pair;
import junit.framework.TestCase;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.HashSet;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class HorseTest extends TestCase {

    private StateInterpreter stateInterpreter;

    @BeforeAll
    public void setUp() {
        stateInterpreter = new StateInterpreter();
    }

    @Test
    void whiteHorseHasNoMoves() {
        var boardArrangement = "{\"1\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"2\":[\"XX\",\"XX\",\"KW\",\"XX\",\"PW\",\"XX\",\"XX\",\"XX\"],\"3\":[\"XX\",\"PW\",\"XX\",\"XX\",\"XX\",\"PW\",\"XX\",\"XX\"],\"4\":[\"XX\",\"XX\",\"XX\",\"HW\",\"XX\",\"XX\",\"XX\",\"XX\"],\"5\":[\"XX\",\"QW\",\"XX\",\"XX\",\"XX\",\"PW\",\"XX\",\"XX\"],\"6\":[\"XX\",\"XX\",\"PW\",\"XX\",\"KW\",\"XX\",\"XX\",\"XX\"],\"7\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"8\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"]}";
        var kingRow = 3;
        var kingCol = 3;
        var chessBoard = stateInterpreter.convertJsonBoardToChessBoard(boardArrangement);

        var whiteHorse = chessBoard.getChessmanAt(kingRow, kingCol);
        assertTrue(whiteHorse instanceof Horse);
        assertEquals(whiteHorse.getColour(), ChessmanColourEnum.WHITE);
        var possibleMoves = whiteHorse.getPossibleMoves(chessBoard, new Pair<>(kingRow, kingCol));
        assertEquals(0, possibleMoves.size());
    }

    @Test
    void whiteHorseHasSomeMoves() {
        var boardArrangement = "{\"1\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"2\":[\"XX\",\"XX\",\"XX\",\"XX\",\"PB\",\"XX\",\"XX\",\"XX\"],\"3\":[\"XX\",\"PW\",\"XX\",\"XX\",\"XX\",\"PW\",\"XX\",\"XX\"],\"4\":[\"XX\",\"XX\",\"XX\",\"HW\",\"XX\",\"XX\",\"XX\",\"XX\"],\"5\":[\"XX\",\"QW\",\"XX\",\"XX\",\"XX\",\"PB\",\"XX\",\"XX\"],\"6\":[\"XX\",\"XX\",\"PW\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"7\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"8\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"]}";
        var kingRow = 3;
        var kingCol = 3;
        var chessBoard = stateInterpreter.convertJsonBoardToChessBoard(boardArrangement);

        var whiteHorse = chessBoard.getChessmanAt(kingRow, kingCol);
        assertTrue(whiteHorse instanceof Horse);
        assertEquals(whiteHorse.getColour(), ChessmanColourEnum.WHITE);
        var possibleMoves = whiteHorse.getPossibleMoves(chessBoard, new Pair<>(kingRow, kingCol));
        assertEquals(2, possibleMoves.size());

        var expectedMoves = new HashSet<Pair<Integer, Integer>>();
        expectedMoves.add(new Pair<>(1, 2));
        expectedMoves.add(new Pair<>(5, 4));
        assertEquals(expectedMoves, possibleMoves);
    }

    @Test
    void whiteHorseHasAllMoves() {
        var boardArrangement = "{\"1\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"2\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"3\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"4\":[\"XX\",\"XX\",\"XX\",\"HW\",\"XX\",\"XX\",\"XX\",\"XX\"],\"5\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"6\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"7\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"8\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"]}";
        var kingRow = 3;
        var kingCol = 3;
        var chessBoard = stateInterpreter.convertJsonBoardToChessBoard(boardArrangement);

        var whiteHorse = chessBoard.getChessmanAt(kingRow, kingCol);
        assertTrue(whiteHorse instanceof Horse);
        assertEquals(whiteHorse.getColour(), ChessmanColourEnum.WHITE);
        var possibleMoves = whiteHorse.getPossibleMoves(chessBoard, new Pair<>(kingRow, kingCol));
        assertEquals(8, possibleMoves.size());

        var expectedMoves = new HashSet<Pair<Integer, Integer>>();
        expectedMoves.add(new Pair<>(1, 2));
        expectedMoves.add(new Pair<>(1, 4));
        expectedMoves.add(new Pair<>(2, 1));
        expectedMoves.add(new Pair<>(2, 5));
        expectedMoves.add(new Pair<>(4, 1));
        expectedMoves.add(new Pair<>(4, 5));
        expectedMoves.add(new Pair<>(5, 2));
        expectedMoves.add(new Pair<>(5, 4));
        assertEquals(expectedMoves, possibleMoves);
    }

    @Test
    void blackHorseHasNoMoves() {
        var boardArrangement = "{\"1\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"2\":[\"XX\",\"XX\",\"KW\",\"XX\",\"PW\",\"XX\",\"XX\",\"XX\"],\"3\":[\"XX\",\"PW\",\"XX\",\"XX\",\"XX\",\"PB\",\"XX\",\"XX\"],\"4\":[\"XX\",\"XX\",\"XX\",\"HB\",\"XX\",\"XX\",\"XX\",\"XX\"],\"5\":[\"XX\",\"QW\",\"XX\",\"XX\",\"XX\",\"PW\",\"XX\",\"XX\"],\"6\":[\"XX\",\"XX\",\"PW\",\"XX\",\"KW\",\"XX\",\"XX\",\"XX\"],\"7\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"8\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"]}";
        var kingRow = 3;
        var kingCol = 3;
        var chessBoard = stateInterpreter.convertJsonBoardToChessBoard(boardArrangement);

        var blackHorse = chessBoard.getChessmanAt(kingRow, kingCol);
        assertTrue(blackHorse instanceof Horse);
        assertEquals(blackHorse.getColour(), ChessmanColourEnum.BLACK);
        var possibleMoves = blackHorse.getPossibleMoves(chessBoard, new Pair<>(kingRow, kingCol));
        assertEquals(0, possibleMoves.size());

    }

    @Test
    void blackHorseHasSomeMoves() {
        var boardArrangement = "{\"1\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"2\":[\"XX\",\"XX\",\"XX\",\"XX\",\"PB\",\"XX\",\"XX\",\"XX\"],\"3\":[\"XX\",\"PW\",\"XX\",\"XX\",\"XX\",\"PW\",\"XX\",\"XX\"],\"4\":[\"XX\",\"XX\",\"XX\",\"HB\",\"XX\",\"XX\",\"XX\",\"XX\"],\"5\":[\"XX\",\"QB\",\"XX\",\"XX\",\"XX\",\"PB\",\"XX\",\"XX\"],\"6\":[\"XX\",\"XX\",\"PW\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"7\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"8\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"]}";
        var kingRow = 3;
        var kingCol = 3;
        var chessBoard = stateInterpreter.convertJsonBoardToChessBoard(boardArrangement);

        var blackHorse = chessBoard.getChessmanAt(kingRow, kingCol);
        assertTrue(blackHorse instanceof Horse);
        assertEquals(blackHorse.getColour(), ChessmanColourEnum.BLACK);
        var possibleMoves = blackHorse.getPossibleMoves(chessBoard, new Pair<>(kingRow, kingCol));
        assertEquals(2, possibleMoves.size());

        var expectedMoves = new HashSet<Pair<Integer, Integer>>();
        expectedMoves.add(new Pair<>(1, 2));
        expectedMoves.add(new Pair<>(5, 4));
        assertEquals(expectedMoves, possibleMoves);
    }

    @Test
    void blackHorseHasAllMoves() {
        var boardArrangement = "{\"1\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"2\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"3\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"4\":[\"XX\",\"XX\",\"XX\",\"HB\",\"XX\",\"XX\",\"XX\",\"XX\"],\"5\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"6\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"7\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"8\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"]}";
        var kingRow = 3;
        var kingCol = 3;
        var chessBoard = stateInterpreter.convertJsonBoardToChessBoard(boardArrangement);

        var blackHorse = chessBoard.getChessmanAt(kingRow, kingCol);
        assertTrue(blackHorse instanceof Horse);
        assertEquals(blackHorse.getColour(), ChessmanColourEnum.BLACK);
        var possibleMoves = blackHorse.getPossibleMoves(chessBoard, new Pair<>(kingRow, kingCol));
        assertEquals(8, possibleMoves.size());

        var expectedMoves = new HashSet<Pair<Integer, Integer>>();
        expectedMoves.add(new Pair<>(1, 2));
        expectedMoves.add(new Pair<>(1, 4));
        expectedMoves.add(new Pair<>(2, 1));
        expectedMoves.add(new Pair<>(2, 5));
        expectedMoves.add(new Pair<>(4, 1));
        expectedMoves.add(new Pair<>(4, 5));
        expectedMoves.add(new Pair<>(5, 2));
        expectedMoves.add(new Pair<>(5, 4));
        assertEquals(expectedMoves, possibleMoves);
    }

    @Test
    void whiteHorseHasNoCaptures() {
        var boardArrangement = "{\"1\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"2\":[\"XX\",\"XX\",\"KW\",\"XX\",\"PW\",\"XX\",\"XX\",\"XX\"],\"3\":[\"XX\",\"PW\",\"XX\",\"XX\",\"XX\",\"PW\",\"XX\",\"XX\"],\"4\":[\"XX\",\"XX\",\"XX\",\"HW\",\"XX\",\"XX\",\"XX\",\"XX\"],\"5\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"PW\",\"XX\",\"XX\"],\"6\":[\"XX\",\"XX\",\"XX\",\"XX\",\"KW\",\"XX\",\"XX\",\"XX\"],\"7\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"8\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"]}";
        var kingRow = 3;
        var kingCol = 3;
        var chessBoard = stateInterpreter.convertJsonBoardToChessBoard(boardArrangement);

        var whiteHorse = chessBoard.getChessmanAt(kingRow, kingCol);
        assertTrue(whiteHorse instanceof Horse);
        assertEquals(whiteHorse.getColour(), ChessmanColourEnum.WHITE);
        var possibleCaptures = whiteHorse.getPossibleCaptures(chessBoard, new Pair<>(kingRow, kingCol));
        assertEquals(0, possibleCaptures.size());
    }

    @Test
    void whiteHorseHasSomeCaptures() {
        var boardArrangement = "{\"1\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"2\":[\"XX\",\"XX\",\"KB\",\"XX\",\"PW\",\"XX\",\"XX\",\"XX\"],\"3\":[\"XX\",\"PW\",\"XX\",\"XX\",\"XX\",\"PW\",\"XX\",\"XX\"],\"4\":[\"XX\",\"XX\",\"XX\",\"HW\",\"XX\",\"XX\",\"XX\",\"XX\"],\"5\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"QB\",\"XX\",\"XX\"],\"6\":[\"XX\",\"XX\",\"XX\",\"XX\",\"KW\",\"XX\",\"XX\",\"XX\"],\"7\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"8\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"]}";
        var kingRow = 3;
        var kingCol = 3;
        var chessBoard = stateInterpreter.convertJsonBoardToChessBoard(boardArrangement);

        var whiteHorse = chessBoard.getChessmanAt(kingRow, kingCol);
        assertTrue(whiteHorse instanceof Horse);
        assertEquals(whiteHorse.getColour(), ChessmanColourEnum.WHITE);
        var possibleCaptures = whiteHorse.getPossibleCaptures(chessBoard, new Pair<>(kingRow, kingCol));
        assertEquals(2, possibleCaptures.size());

        var expectedCaptures = new HashSet<Pair<Integer, Integer>>();
        expectedCaptures.add(new Pair<>(1, 2));
        expectedCaptures.add(new Pair<>(4, 5));
        assertEquals(expectedCaptures, possibleCaptures);
    }

    @Test
    void whiteHorseHasAllCaptures() {
        var boardArrangement = "{\"1\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"2\":[\"XX\",\"XX\",\"KB\",\"XX\",\"PB\",\"XX\",\"XX\",\"XX\"],\"3\":[\"XX\",\"PB\",\"XX\",\"XX\",\"XX\",\"PB\",\"XX\",\"XX\"],\"4\":[\"XX\",\"XX\",\"XX\",\"HW\",\"XX\",\"XX\",\"XX\",\"XX\"],\"5\":[\"XX\",\"QB\",\"XX\",\"XX\",\"XX\",\"PB\",\"XX\",\"XX\"],\"6\":[\"XX\",\"XX\",\"PB\",\"XX\",\"KB\",\"XX\",\"XX\",\"XX\"],\"7\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"8\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"]}";
        var kingRow = 3;
        var kingCol = 3;
        var chessBoard = stateInterpreter.convertJsonBoardToChessBoard(boardArrangement);

        var whiteHorse = chessBoard.getChessmanAt(kingRow, kingCol);
        assertTrue(whiteHorse instanceof Horse);
        assertEquals(whiteHorse.getColour(), ChessmanColourEnum.WHITE);
        var possibleCaptures = whiteHorse.getPossibleCaptures(chessBoard, new Pair<>(kingRow, kingCol));
        assertEquals(8, possibleCaptures.size());

        var expectedCaptures = new HashSet<Pair<Integer, Integer>>();
        expectedCaptures.add(new Pair<>(1, 2));
        expectedCaptures.add(new Pair<>(1, 4));
        expectedCaptures.add(new Pair<>(2, 1));
        expectedCaptures.add(new Pair<>(2, 5));
        expectedCaptures.add(new Pair<>(4, 1));
        expectedCaptures.add(new Pair<>(4, 5));
        expectedCaptures.add(new Pair<>(5, 2));
        expectedCaptures.add(new Pair<>(5, 4));
        assertEquals(expectedCaptures, possibleCaptures);
    }

    @Test
    void blackHorseHasNoCaptures() {
        var boardArrangement = "{\"1\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"2\":[\"XX\",\"XX\",\"KB\",\"XX\",\"PB\",\"XX\",\"XX\",\"XX\"],\"3\":[\"XX\",\"PB\",\"XX\",\"XX\",\"XX\",\"PB\",\"XX\",\"XX\"],\"4\":[\"XX\",\"XX\",\"XX\",\"HB\",\"XX\",\"XX\",\"XX\",\"XX\"],\"5\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"PB\",\"XX\",\"XX\"],\"6\":[\"XX\",\"XX\",\"XX\",\"XX\",\"KB\",\"XX\",\"XX\",\"XX\"],\"7\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"8\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"]}";
        var kingRow = 3;
        var kingCol = 3;
        var chessBoard = stateInterpreter.convertJsonBoardToChessBoard(boardArrangement);

        var blackHorse = chessBoard.getChessmanAt(kingRow, kingCol);
        assertTrue(blackHorse instanceof Horse);
        assertEquals(blackHorse.getColour(), ChessmanColourEnum.BLACK);
        var possibleCaptures = blackHorse.getPossibleCaptures(chessBoard, new Pair<>(kingRow, kingCol));
        assertEquals(0, possibleCaptures.size());
    }

    @Test
    void blackHorseHasSomeCaptures() {
        var boardArrangement = "{\"1\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"2\":[\"XX\",\"XX\",\"KB\",\"XX\",\"PW\",\"XX\",\"XX\",\"XX\"],\"3\":[\"XX\",\"PB\",\"XX\",\"XX\",\"XX\",\"PB\",\"XX\",\"XX\"],\"4\":[\"XX\",\"XX\",\"XX\",\"HB\",\"XX\",\"XX\",\"XX\",\"XX\"],\"5\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"PW\",\"XX\",\"XX\"],\"6\":[\"XX\",\"XX\",\"XX\",\"XX\",\"KB\",\"XX\",\"XX\",\"XX\"],\"7\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"8\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"]}";
        var kingRow = 3;
        var kingCol = 3;
        var chessBoard = stateInterpreter.convertJsonBoardToChessBoard(boardArrangement);

        var blackHorse = chessBoard.getChessmanAt(kingRow, kingCol);
        assertTrue(blackHorse instanceof Horse);
        assertEquals(blackHorse.getColour(), ChessmanColourEnum.BLACK);
        var possibleCaptures = blackHorse.getPossibleCaptures(chessBoard, new Pair<>(kingRow, kingCol));
        assertEquals(2, possibleCaptures.size());

        var expectedCaptures = new HashSet<Pair<Integer, Integer>>();
        expectedCaptures.add(new Pair<>(1, 4));
        expectedCaptures.add(new Pair<>(4, 5));
        assertEquals(expectedCaptures, possibleCaptures);
    }

    @Test
    void blackHorseHasAllCaptures() {
        var boardArrangement = "{\"1\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"2\":[\"XX\",\"XX\",\"KW\",\"XX\",\"PW\",\"XX\",\"XX\",\"XX\"],\"3\":[\"XX\",\"PW\",\"XX\",\"XX\",\"XX\",\"PW\",\"XX\",\"XX\"],\"4\":[\"XX\",\"XX\",\"XX\",\"HB\",\"XX\",\"XX\",\"XX\",\"XX\"],\"5\":[\"XX\",\"QW\",\"XX\",\"XX\",\"XX\",\"PW\",\"XX\",\"XX\"],\"6\":[\"XX\",\"XX\",\"PW\",\"XX\",\"KW\",\"XX\",\"XX\",\"XX\"],\"7\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"8\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"]}";
        var kingRow = 3;
        var kingCol = 3;
        var chessBoard = stateInterpreter.convertJsonBoardToChessBoard(boardArrangement);

        var blackHorse = chessBoard.getChessmanAt(kingRow, kingCol);
        assertTrue(blackHorse instanceof Horse);
        assertEquals(blackHorse.getColour(), ChessmanColourEnum.BLACK);
        var possibleCaptures = blackHorse.getPossibleCaptures(chessBoard, new Pair<>(kingRow, kingCol));
        assertEquals(8, possibleCaptures.size());

        var expectedCaptures = new HashSet<Pair<Integer, Integer>>();
        expectedCaptures.add(new Pair<>(1, 2));
        expectedCaptures.add(new Pair<>(1, 4));
        expectedCaptures.add(new Pair<>(2, 1));
        expectedCaptures.add(new Pair<>(2, 5));
        expectedCaptures.add(new Pair<>(4, 1));
        expectedCaptures.add(new Pair<>(4, 5));
        expectedCaptures.add(new Pair<>(5, 2));
        expectedCaptures.add(new Pair<>(5, 4));
        assertEquals(expectedCaptures, possibleCaptures);
    }

}