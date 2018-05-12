package com.killerchess.core.chessmans;

import com.killerchess.core.chessboard.state.interpreter.StateInterpreter;
import javafx.util.Pair;
import junit.framework.TestCase;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.IOException;
import java.util.HashSet;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class QueenTest extends TestCase {

    private StateInterpreter stateInterpreter;

    @BeforeAll
    public void setUp() {
        stateInterpreter = new StateInterpreter();
    }

    @Test
    void whenWhiteQueenHasNoMoves() throws IOException {
        var boardArrangement = "{\"1\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"2\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"3\":[\"XX\",\"XX\",\"KW\",\"QW\",\"KB\",\"XX\",\"XX\",\"XX\"],\"4\":[\"XX\",\"XX\",\"PB\",\"QW\",\"PW\",\"XX\",\"XX\",\"XX\"],\"5\":[\"XX\",\"XX\",\"PW\",\"KB\",\"PW\",\"XX\",\"XX\",\"XX\"],\"6\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"7\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"8\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"]}";
        var queenRow = 3;
        var queenCol = 3;
        var chessBoard = stateInterpreter.convertJsonBoardToChessBoard(boardArrangement);

        var whiteQueen = chessBoard.getChessmanAt(queenRow, queenCol);
        assertTrue(whiteQueen instanceof Queen);
        assertEquals(whiteQueen.getColour(), ChessmanColourEnum.WHITE);
        var possibleMoves = whiteQueen.getPossibleMoves(chessBoard, new Pair<>(queenRow, queenCol));
        assertEquals(0, possibleMoves.size());
    }

    @Test
    void whenWhiteQueenHasSomeMoves() throws IOException {
        var boardArrangement = "{\"1\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"2\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"3\":[\"XX\",\"XX\",\"KW\",\"QW\",\"XX\",\"XX\",\"XX\",\"XX\"],\"4\":[\"XX\",\"XX\",\"PB\",\"QW\",\"XX\",\"PW\",\"XX\",\"XX\"],\"5\":[\"XX\",\"XX\",\"PW\",\"KB\",\"PW\",\"XX\",\"XX\",\"XX\"],\"6\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"7\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"8\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"]}";
        var queenRow = 3;
        var queenCol = 3;
        var chessBoard = stateInterpreter.convertJsonBoardToChessBoard(boardArrangement);

        var whiteQueen = chessBoard.getChessmanAt(queenRow, queenCol);
        assertTrue(whiteQueen instanceof Queen);
        assertEquals(whiteQueen.getColour(), ChessmanColourEnum.WHITE);
        var possibleMoves = whiteQueen.getPossibleMoves(chessBoard, new Pair<>(queenRow, queenCol));
        assertEquals(4, possibleMoves.size());

        var expectedMoves = new HashSet<Pair<Integer, Integer>>();
        expectedMoves.add(new Pair<>(0, 6));
        expectedMoves.add(new Pair<>(1, 5));
        expectedMoves.add(new Pair<>(2, 4));
        expectedMoves.add(new Pair<>(3, 4));
        assertEquals(expectedMoves, possibleMoves);
    }

    @Test
    void whenWhiteQueenHasAllMoves() throws IOException {
        var boardArrangement = "{\"1\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"2\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"3\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"4\":[\"XX\",\"XX\",\"XX\",\"QW\",\"XX\",\"XX\",\"XX\",\"XX\"],\"5\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"6\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"7\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"8\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"]}";
        var queenRow = 3;
        var queenCol = 3;
        var chessBoard = stateInterpreter.convertJsonBoardToChessBoard(boardArrangement);

        var whiteQueen = chessBoard.getChessmanAt(queenRow, queenCol);
        assertTrue(whiteQueen instanceof Queen);
        assertEquals(whiteQueen.getColour(), ChessmanColourEnum.WHITE);
        var possibleMoves = whiteQueen.getPossibleMoves(chessBoard, new Pair<>(queenRow, queenCol));
        assertEquals(27, possibleMoves.size());

        var expectedMoves = new HashSet<Pair<Integer, Integer>>();
        expectedMoves.add(new Pair<>(0, 6));
        expectedMoves.add(new Pair<>(0, 0));
        expectedMoves.add(new Pair<>(0, 3));
        expectedMoves.add(new Pair<>(1, 5));
        expectedMoves.add(new Pair<>(1, 1));
        expectedMoves.add(new Pair<>(1, 3));
        expectedMoves.add(new Pair<>(2, 2));
        expectedMoves.add(new Pair<>(2, 3));
        expectedMoves.add(new Pair<>(2, 4));
        expectedMoves.add(new Pair<>(3, 0));
        expectedMoves.add(new Pair<>(3, 1));
        expectedMoves.add(new Pair<>(3, 2));
        expectedMoves.add(new Pair<>(3, 4));
        expectedMoves.add(new Pair<>(3, 5));
        expectedMoves.add(new Pair<>(3, 6));
        expectedMoves.add(new Pair<>(3, 7));
        expectedMoves.add(new Pair<>(4, 2));
        expectedMoves.add(new Pair<>(4, 3));
        expectedMoves.add(new Pair<>(4, 4));
        expectedMoves.add(new Pair<>(5, 1));
        expectedMoves.add(new Pair<>(5, 3));
        expectedMoves.add(new Pair<>(5, 5));
        expectedMoves.add(new Pair<>(6, 0));
        expectedMoves.add(new Pair<>(6, 3));
        expectedMoves.add(new Pair<>(6, 6));
        expectedMoves.add(new Pair<>(7, 3));
        expectedMoves.add(new Pair<>(7, 7));
        assertEquals(expectedMoves, possibleMoves);
    }

    @Test
    void whenBlackQueenHasNoMoves() throws IOException {
        var boardArrangement = "{\"1\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"2\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"3\":[\"XX\",\"XX\",\"KW\",\"QW\",\"KB\",\"XX\",\"XX\",\"XX\"],\"4\":[\"XX\",\"XX\",\"PB\",\"QB\",\"PW\",\"XX\",\"XX\",\"XX\"],\"5\":[\"XX\",\"XX\",\"PW\",\"KB\",\"PW\",\"XX\",\"XX\",\"XX\"],\"6\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"7\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"8\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"]}";
        var queenRow = 3;
        var queenCol = 3;
        var chessBoard = stateInterpreter.convertJsonBoardToChessBoard(boardArrangement);

        var blackQueen = chessBoard.getChessmanAt(queenRow, queenCol);
        assertTrue(blackQueen instanceof Queen);
        assertEquals(blackQueen.getColour(), ChessmanColourEnum.BLACK);
        var possibleMoves = blackQueen.getPossibleMoves(chessBoard, new Pair<>(queenRow, queenCol));
        assertEquals(0, possibleMoves.size());

    }

    @Test
    void whenBlackQueenHasSomeMoves() throws IOException {
        var boardArrangement = "{\"1\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"2\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"3\":[\"XX\",\"XX\",\"KW\",\"QW\",\"KB\",\"XX\",\"XX\",\"XX\"],\"4\":[\"XX\",\"XX\",\"XX\",\"QB\",\"PW\",\"XX\",\"XX\",\"XX\"],\"5\":[\"XX\",\"XX\",\"PW\",\"KB\",\"XX\",\"XX\",\"XX\",\"XX\"],\"6\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"PW\",\"XX\",\"XX\"],\"7\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"8\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"]}";
        var queenRow = 3;
        var queenCol = 3;
        var chessBoard = stateInterpreter.convertJsonBoardToChessBoard(boardArrangement);

        var blackQueen = chessBoard.getChessmanAt(queenRow, queenCol);
        assertTrue(blackQueen instanceof Queen);
        assertEquals(blackQueen.getColour(), ChessmanColourEnum.BLACK);
        var possibleMoves = blackQueen.getPossibleMoves(chessBoard, new Pair<>(queenRow, queenCol));
        assertEquals(4, possibleMoves.size());

        var expectedMoves = new HashSet<Pair<Integer, Integer>>();
        expectedMoves.add(new Pair<>(4, 4));
        expectedMoves.add(new Pair<>(3, 0));
        expectedMoves.add(new Pair<>(3, 1));
        expectedMoves.add(new Pair<>(3, 2));
        assertEquals(expectedMoves, possibleMoves);
    }

    @Test
    void whenBlackQueenHasAllMoves() throws IOException {
        var boardArrangement = "{\"1\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"2\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"3\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"4\":[\"XX\",\"XX\",\"XX\",\"QB\",\"XX\",\"XX\",\"XX\",\"XX\"],\"5\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"6\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"7\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"8\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"]}";
        var queenRow = 3;
        var queenCol = 3;
        var chessBoard = stateInterpreter.convertJsonBoardToChessBoard(boardArrangement);

        var blackQueen = chessBoard.getChessmanAt(queenRow, queenCol);
        assertTrue(blackQueen instanceof Queen);
        assertEquals(blackQueen.getColour(), ChessmanColourEnum.BLACK);
        var possibleMoves = blackQueen.getPossibleMoves(chessBoard, new Pair<>(queenRow, queenCol));
        assertEquals(27, possibleMoves.size());

        var expectedMoves = new HashSet<Pair<Integer, Integer>>();
        expectedMoves.add(new Pair<>(0, 6));
        expectedMoves.add(new Pair<>(0, 0));
        expectedMoves.add(new Pair<>(0, 3));
        expectedMoves.add(new Pair<>(1, 5));
        expectedMoves.add(new Pair<>(1, 1));
        expectedMoves.add(new Pair<>(1, 3));
        expectedMoves.add(new Pair<>(2, 2));
        expectedMoves.add(new Pair<>(2, 3));
        expectedMoves.add(new Pair<>(2, 4));
        expectedMoves.add(new Pair<>(3, 0));
        expectedMoves.add(new Pair<>(3, 1));
        expectedMoves.add(new Pair<>(3, 2));
        expectedMoves.add(new Pair<>(3, 4));
        expectedMoves.add(new Pair<>(3, 5));
        expectedMoves.add(new Pair<>(3, 6));
        expectedMoves.add(new Pair<>(3, 7));
        expectedMoves.add(new Pair<>(4, 2));
        expectedMoves.add(new Pair<>(4, 3));
        expectedMoves.add(new Pair<>(4, 4));
        expectedMoves.add(new Pair<>(5, 1));
        expectedMoves.add(new Pair<>(5, 3));
        expectedMoves.add(new Pair<>(5, 5));
        expectedMoves.add(new Pair<>(6, 0));
        expectedMoves.add(new Pair<>(6, 3));
        expectedMoves.add(new Pair<>(6, 6));
        expectedMoves.add(new Pair<>(7, 3));
        expectedMoves.add(new Pair<>(7, 7));
        assertEquals(expectedMoves, possibleMoves);
    }

    @Test
    void whenWhiteQueenHasNoCaptures() throws IOException {
        var boardArrangement = "{\"1\":[\"HW\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"2\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"3\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"4\":[\"PB\",\"PW\",\"XX\",\"QW\",\"KW\",\"XX\",\"XX\",\"XX\"],\"5\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"6\":[\"XX\",\"XX\",\"XX\",\"KW\",\"XX\",\"XX\",\"XX\",\"XX\"],\"7\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"8\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"]}";
        var queenRow = 3;
        var queenCol = 3;
        var chessBoard = stateInterpreter.convertJsonBoardToChessBoard(boardArrangement);

        var whiteQueen = chessBoard.getChessmanAt(queenRow, queenCol);
        assertTrue(whiteQueen instanceof Queen);
        assertEquals(whiteQueen.getColour(), ChessmanColourEnum.WHITE);
        var possibleCaptures = whiteQueen.getPossibleCaptures(chessBoard, new Pair<>(queenRow, queenCol));
        assertEquals(0, possibleCaptures.size());
    }

    @Test
    void whenWhiteQueenHasSomeCaptures() throws IOException {
        var boardArrangement = "{\"1\":[\"PB\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"2\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"3\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"4\":[\"HB\",\"PW\",\"XX\",\"QW\",\"XX\",\"HB\",\"XX\",\"XX\"],\"5\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"6\":[\"XX\",\"XX\",\"XX\",\"PW\",\"XX\",\"XX\",\"XX\",\"XX\"],\"7\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"PB\",\"XX\"],\"8\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"]}";
        var queenRow = 3;
        var queenCol = 3;
        var chessBoard = stateInterpreter.convertJsonBoardToChessBoard(boardArrangement);

        var whiteQueen = chessBoard.getChessmanAt(queenRow, queenCol);
        assertTrue(whiteQueen instanceof Queen);
        assertEquals(whiteQueen.getColour(), ChessmanColourEnum.WHITE);
        var possibleCaptures = whiteQueen.getPossibleCaptures(chessBoard, new Pair<>(queenRow, queenCol));
        assertEquals(3, possibleCaptures.size());

        var expectedCaptures = new HashSet<Pair<Integer, Integer>>();
        expectedCaptures.add(new Pair<>(0, 0));
        expectedCaptures.add(new Pair<>(3, 5));
        expectedCaptures.add(new Pair<>(6, 6));
        assertEquals(expectedCaptures, possibleCaptures);
    }

    @Test
    void whenWhiteQueenHasAllCaptures() throws IOException {
        var boardArrangement = "{\"1\":[\"PB\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"PB\",\"XX\"],\"2\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"3\":[\"XX\",\"XX\",\"XX\",\"KB\",\"XX\",\"XX\",\"XX\",\"XX\"],\"4\":[\"XX\",\"XX\",\"HB\",\"QW\",\"HB\",\"XX\",\"XX\",\"XX\"],\"5\":[\"XX\",\"XX\",\"XX\",\"BB\",\"XX\",\"XX\",\"XX\",\"XX\"],\"6\":[\"XX\",\"BB\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"7\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"8\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"QB\"]}";
        var queenRow = 3;
        var queenCol = 3;
        var chessBoard = stateInterpreter.convertJsonBoardToChessBoard(boardArrangement);

        var whiteQueen = chessBoard.getChessmanAt(queenRow, queenCol);
        assertTrue(whiteQueen instanceof Queen);
        assertEquals(whiteQueen.getColour(), ChessmanColourEnum.WHITE);
        var possibleCaptures = whiteQueen.getPossibleCaptures(chessBoard, new Pair<>(queenRow, queenCol));
        assertEquals(8, possibleCaptures.size());

        var expectedCaptures = new HashSet<Pair<Integer, Integer>>();
        expectedCaptures.add(new Pair<>(0, 0));
        expectedCaptures.add(new Pair<>(0, 6));
        expectedCaptures.add(new Pair<>(2, 3));
        expectedCaptures.add(new Pair<>(3, 2));
        expectedCaptures.add(new Pair<>(3, 4));
        expectedCaptures.add(new Pair<>(4, 3));
        expectedCaptures.add(new Pair<>(5, 1));
        expectedCaptures.add(new Pair<>(7, 7));
        assertEquals(expectedCaptures, possibleCaptures);
    }

    @Test
    void whenBlackQueenHasNoCaptures() throws IOException {
        var boardArrangement = "{\"1\":[\"PW\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"2\":[\"XX\",\"PB\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"3\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"4\":[\"XX\",\"XX\",\"PB\",\"QB\",\"XX\",\"PB\",\"XX\",\"XX\"],\"5\":[\"XX\",\"XX\",\"XX\",\"PB\",\"XX\",\"XX\",\"XX\",\"XX\"],\"6\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"7\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"8\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"PB\"]}";
        var queenRow = 3;
        var queenCol = 3;
        var chessBoard = stateInterpreter.convertJsonBoardToChessBoard(boardArrangement);

        var blackQueen = chessBoard.getChessmanAt(queenRow, queenCol);
        assertTrue(blackQueen instanceof Queen);
        assertEquals(blackQueen.getColour(), ChessmanColourEnum.BLACK);
        var possibleCaptures = blackQueen.getPossibleCaptures(chessBoard, new Pair<>(queenRow, queenCol));
        assertEquals(0, possibleCaptures.size());
    }

    @Test
    void whenBlackQueenHasSomeCaptures() throws IOException {
        var boardArrangement = "{\"1\":[\"PW\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"2\":[\"XX\",\"PW\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"3\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"4\":[\"XX\",\"XX\",\"PW\",\"QB\",\"PW\",\"XX\",\"XX\",\"XX\"],\"5\":[\"XX\",\"XX\",\"XX\",\"PB\",\"XX\",\"XX\",\"XX\",\"XX\"],\"6\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"7\":[\"PW\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"8\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"PB\"]}";
        var queenRow = 3;
        var queenCol = 3;
        var chessBoard = stateInterpreter.convertJsonBoardToChessBoard(boardArrangement);

        var blackQueen = chessBoard.getChessmanAt(queenRow, queenCol);
        assertTrue(blackQueen instanceof Queen);
        assertEquals(blackQueen.getColour(), ChessmanColourEnum.BLACK);
        var possibleCaptures = blackQueen.getPossibleCaptures(chessBoard, new Pair<>(queenRow, queenCol));
        assertEquals(4, possibleCaptures.size());

        var expectedCaptures = new HashSet<Pair<Integer, Integer>>();
        expectedCaptures.add(new Pair<>(1, 1));
        expectedCaptures.add(new Pair<>(3, 2));
        expectedCaptures.add(new Pair<>(3, 4));
        expectedCaptures.add(new Pair<>(6, 0));
        assertEquals(expectedCaptures, possibleCaptures);
    }

    @Test
    void whenBlackQueenHasAllCaptures() throws IOException {
        var boardArrangement = "{\"1\":[\"PW\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"PW\",\"XX\"],\"2\":[\"XX\",\"PW\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"3\":[\"XX\",\"XX\",\"XX\",\"PW\",\"XX\",\"XX\",\"XX\",\"XX\"],\"4\":[\"XX\",\"XX\",\"PW\",\"QB\",\"PW\",\"XX\",\"XX\",\"XX\"],\"5\":[\"XX\",\"XX\",\"XX\",\"PW\",\"XX\",\"XX\",\"XX\",\"XX\"],\"6\":[\"XX\",\"PW\",\"XX\",\"PB\",\"XX\",\"XX\",\"XX\",\"XX\"],\"7\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"8\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"QW\"]}";
        var queenRow = 3;
        var queenCol = 3;
        var chessBoard = stateInterpreter.convertJsonBoardToChessBoard(boardArrangement);

        var blackQueen = chessBoard.getChessmanAt(queenRow, queenCol);
        assertTrue(blackQueen instanceof Queen);
        assertEquals(blackQueen.getColour(), ChessmanColourEnum.BLACK);
        var possibleCaptures = blackQueen.getPossibleCaptures(chessBoard, new Pair<>(queenRow, queenCol));
        assertEquals(8, possibleCaptures.size());

        var expectedCaptures = new HashSet<Pair<Integer, Integer>>();
        expectedCaptures.add(new Pair<>(0, 6));
        expectedCaptures.add(new Pair<>(1, 1));
        expectedCaptures.add(new Pair<>(2, 3));
        expectedCaptures.add(new Pair<>(3, 2));
        expectedCaptures.add(new Pair<>(3, 4));
        expectedCaptures.add(new Pair<>(4, 3));
        expectedCaptures.add(new Pair<>(5, 1));
        expectedCaptures.add(new Pair<>(7, 7));
        assertEquals(expectedCaptures, possibleCaptures);
    }

}