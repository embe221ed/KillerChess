package com.killerchess.core.chessmans;

import com.killerchess.core.chessboard.state.interpreter.StateInterpreter;
import org.springframework.data.util.Pair;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class KingTest {

    private StateInterpreter stateInterpreter;

    @BeforeAll
    void setUp() {
        stateInterpreter = new StateInterpreter();
    }

    @Test
    void whiteKingHasNoMoves() {
        var boardArrangement = "{\"1\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"2\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"3\":[\"XX\",\"XX\",\"KB\",\"HB\",\"QB\",\"XX\",\"XX\",\"XX\"],\"4\":[\"XX\",\"XX\",\"HW\",\"KW\",\"HW\",\"XX\",\"XX\",\"XX\"],\"5\":[\"XX\",\"XX\",\"PW\",\"PW\",\"HW\",\"XX\",\"XX\",\"XX\"],\"6\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"7\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"8\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"]}";
        var kingRow = 3;
        var kingCol = 3;
        var chessBoard = stateInterpreter.convertJsonBoardToChessBoard(boardArrangement);

        var whiteKing = chessBoard.getChessmanAt(kingRow, kingCol);
        assertTrue(whiteKing instanceof King);
        assertEquals(whiteKing.getColour(), ChessmanColourEnum.WHITE);
        var possibleMoves = whiteKing.getPossibleMoves(chessBoard, Pair.of(kingRow, kingCol));
        assertEquals(0, possibleMoves.size());
    }

    @Test
    void whiteKingHasSomeMoves() {
        var boardArrangement = "{\"1\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"2\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"3\":[\"XX\",\"XX\",\"XX\",\"HB\",\"XX\",\"XX\",\"XX\",\"XX\"],\"4\":[\"XX\",\"XX\",\"HW\",\"KW\",\"HW\",\"XX\",\"XX\",\"XX\"],\"5\":[\"XX\",\"XX\",\"XX\",\"PW\",\"HW\",\"XX\",\"XX\",\"XX\"],\"6\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"7\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"8\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"]}";
        var kingRow = 3;
        var kingCol = 3;
        var chessBoard = stateInterpreter.convertJsonBoardToChessBoard(boardArrangement);

        var whiteKing = chessBoard.getChessmanAt(kingRow, kingCol);
        assertTrue(whiteKing instanceof King);
        assertEquals(whiteKing.getColour(), ChessmanColourEnum.WHITE);
        var possibleMoves = whiteKing.getPossibleMoves(chessBoard, Pair.of(kingRow, kingCol));
        assertEquals(3, possibleMoves.size());

        var expectedMoves = new HashSet<Pair<Integer, Integer>>();
        expectedMoves.add(Pair.of(2, 2));
        expectedMoves.add(Pair.of(2, 4));
        expectedMoves.add(Pair.of(4, 2));
        assertEquals(expectedMoves, possibleMoves);
    }

    @Test
    void whiteKingHasAllMoves() {
        var boardArrangement = "{\"1\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"2\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"3\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"4\":[\"XX\",\"XX\",\"XX\",\"KW\",\"XX\",\"XX\",\"XX\",\"XX\"],\"5\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"6\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"7\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"8\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"]}";
        var kingRow = 3;
        var kingCol = 3;
        var chessBoard = stateInterpreter.convertJsonBoardToChessBoard(boardArrangement);

        var whiteKing = chessBoard.getChessmanAt(kingRow, kingCol);
        assertTrue(whiteKing instanceof King);
        assertEquals(whiteKing.getColour(), ChessmanColourEnum.WHITE);
        var possibleMoves = whiteKing.getPossibleMoves(chessBoard, Pair.of(kingRow, kingCol));
        assertEquals(8, possibleMoves.size());

        var expectedMoves = new HashSet<Pair<Integer, Integer>>();
        expectedMoves.add(Pair.of(2, 2));
        expectedMoves.add(Pair.of(2, 3));
        expectedMoves.add(Pair.of(2, 4));
        expectedMoves.add(Pair.of(3, 2));
        expectedMoves.add(Pair.of(3, 4));
        expectedMoves.add(Pair.of(4, 2));
        expectedMoves.add(Pair.of(4, 3));
        expectedMoves.add(Pair.of(4, 4));
        assertEquals(expectedMoves, possibleMoves);
    }

    @Test
    void blackKingHasNoMoves() {
        var boardArrangement = "{\"1\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"2\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"3\":[\"XX\",\"XX\",\"KB\",\"HB\",\"QB\",\"XX\",\"XX\",\"XX\"],\"4\":[\"XX\",\"XX\",\"HW\",\"KB\",\"HW\",\"XX\",\"XX\",\"XX\"],\"5\":[\"XX\",\"XX\",\"PW\",\"PW\",\"HW\",\"XX\",\"XX\",\"XX\"],\"6\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"7\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"8\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"]}";
        var kingRow = 3;
        var kingCol = 3;
        var chessBoard = stateInterpreter.convertJsonBoardToChessBoard(boardArrangement);

        var blackKing = chessBoard.getChessmanAt(kingRow, kingCol);
        assertTrue(blackKing instanceof King);
        assertEquals(blackKing.getColour(), ChessmanColourEnum.BLACK);
        var possibleMoves = blackKing.getPossibleMoves(chessBoard, Pair.of(kingRow, kingCol));
        assertEquals(0, possibleMoves.size());

    }

    @Test
    void blackKingHasSomeMoves() {
        var boardArrangement = "{\"1\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"2\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"3\":[\"XX\",\"XX\",\"XX\",\"HB\",\"XX\",\"XX\",\"XX\",\"XX\"],\"4\":[\"XX\",\"XX\",\"HW\",\"KB\",\"HW\",\"XX\",\"XX\",\"XX\"],\"5\":[\"XX\",\"XX\",\"XX\",\"PW\",\"HW\",\"XX\",\"XX\",\"XX\"],\"6\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"7\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"8\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"]}";
        var kingRow = 3;
        var kingCol = 3;
        var chessBoard = stateInterpreter.convertJsonBoardToChessBoard(boardArrangement);

        var blackKing = chessBoard.getChessmanAt(kingRow, kingCol);
        assertTrue(blackKing instanceof King);
        assertEquals(blackKing.getColour(), ChessmanColourEnum.BLACK);
        var possibleMoves = blackKing.getPossibleMoves(chessBoard, Pair.of(kingRow, kingCol));
        assertEquals(3, possibleMoves.size());

        var expectedMoves = new HashSet<Pair<Integer, Integer>>();
        expectedMoves.add(Pair.of(2, 2));
        expectedMoves.add(Pair.of(2, 4));
        expectedMoves.add(Pair.of(4, 2));
        assertEquals(expectedMoves, possibleMoves);
    }

    @Test
    void blackKingHasAllMoves() {
        var boardArrangement = "{\"1\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"2\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"3\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"4\":[\"XX\",\"XX\",\"XX\",\"KB\",\"XX\",\"XX\",\"XX\",\"XX\"],\"5\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"6\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"7\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"8\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"]}";
        var kingRow = 3;
        var kingCol = 3;
        var chessBoard = stateInterpreter.convertJsonBoardToChessBoard(boardArrangement);

        var blackKing = chessBoard.getChessmanAt(kingRow, kingCol);
        assertTrue(blackKing instanceof King);
        assertEquals(blackKing.getColour(), ChessmanColourEnum.BLACK);
        var possibleMoves = blackKing.getPossibleMoves(chessBoard, Pair.of(kingRow, kingCol));
        assertEquals(8, possibleMoves.size());

        var expectedMoves = new HashSet<Pair<Integer, Integer>>();
        expectedMoves.add(Pair.of(2, 2));
        expectedMoves.add(Pair.of(2, 3));
        expectedMoves.add(Pair.of(2, 4));
        expectedMoves.add(Pair.of(3, 2));
        expectedMoves.add(Pair.of(3, 4));
        expectedMoves.add(Pair.of(4, 2));
        expectedMoves.add(Pair.of(4, 3));
        expectedMoves.add(Pair.of(4, 4));
        assertEquals(expectedMoves, possibleMoves);
    }

    @Test
    void whiteKingHasNoCaptures() {
        var boardArrangement = "{\"1\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"2\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"3\":[\"XX\",\"XX\",\"XX\",\"XX\",\"QW\",\"XX\",\"XX\",\"XX\"],\"4\":[\"XX\",\"XX\",\"HW\",\"KW\",\"HW\",\"XX\",\"XX\",\"XX\"],\"5\":[\"XX\",\"XX\",\"PW\",\"PW\",\"HW\",\"XX\",\"XX\",\"XX\"],\"6\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"7\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"8\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"]}";
        var kingRow = 3;
        var kingCol = 3;
        var chessBoard = stateInterpreter.convertJsonBoardToChessBoard(boardArrangement);

        var whiteKing = chessBoard.getChessmanAt(kingRow, kingCol);
        assertTrue(whiteKing instanceof King);
        assertEquals(whiteKing.getColour(), ChessmanColourEnum.WHITE);
        var possibleCaptures = whiteKing.getPossibleCaptures(chessBoard, Pair.of(kingRow, kingCol));
        assertEquals(0, possibleCaptures.size());
    }

    @Test
    void whiteKingHasSomeCaptures() {
        var boardArrangement = "{\"1\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"2\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"3\":[\"XX\",\"XX\",\"XX\",\"XX\",\"QW\",\"XX\",\"XX\",\"XX\"],\"4\":[\"XX\",\"XX\",\"HW\",\"KW\",\"HB\",\"XX\",\"XX\",\"XX\"],\"5\":[\"XX\",\"XX\",\"PW\",\"PB\",\"HB\",\"XX\",\"XX\",\"XX\"],\"6\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"7\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"8\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"]}";
        var kingRow = 3;
        var kingCol = 3;
        var chessBoard = stateInterpreter.convertJsonBoardToChessBoard(boardArrangement);

        var whiteKing = chessBoard.getChessmanAt(kingRow, kingCol);
        assertTrue(whiteKing instanceof King);
        assertEquals(whiteKing.getColour(), ChessmanColourEnum.WHITE);
        var possibleCaptures = whiteKing.getPossibleCaptures(chessBoard, Pair.of(kingRow, kingCol));
        assertEquals(3, possibleCaptures.size());

        var expectedCaptures = new HashSet<Pair<Integer, Integer>>();
        expectedCaptures.add(Pair.of(3, 4));
        expectedCaptures.add(Pair.of(4, 3));
        expectedCaptures.add(Pair.of(4, 4));
        assertEquals(expectedCaptures, possibleCaptures);
    }

    @Test
    void whiteKingHasAllCaptures() {
        var boardArrangement = "{\"1\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"2\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"3\":[\"XX\",\"XX\",\"KB\",\"HB\",\"QB\",\"XX\",\"XX\",\"XX\"],\"4\":[\"XX\",\"XX\",\"HB\",\"KW\",\"HB\",\"XX\",\"XX\",\"XX\"],\"5\":[\"XX\",\"XX\",\"PB\",\"PB\",\"HB\",\"XX\",\"XX\",\"XX\"],\"6\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"7\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"8\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"]}";
        var kingRow = 3;
        var kingCol = 3;
        var chessBoard = stateInterpreter.convertJsonBoardToChessBoard(boardArrangement);

        var whiteKing = chessBoard.getChessmanAt(kingRow, kingCol);
        assertTrue(whiteKing instanceof King);
        assertEquals(whiteKing.getColour(), ChessmanColourEnum.WHITE);
        var possibleCaptures = whiteKing.getPossibleCaptures(chessBoard, Pair.of(kingRow, kingCol));
        assertEquals(8, possibleCaptures.size());

        var expectedCaptures = new HashSet<Pair<Integer, Integer>>();
        expectedCaptures.add(Pair.of(2, 2));
        expectedCaptures.add(Pair.of(2, 3));
        expectedCaptures.add(Pair.of(2, 4));
        expectedCaptures.add(Pair.of(3, 2));
        expectedCaptures.add(Pair.of(3, 4));
        expectedCaptures.add(Pair.of(4, 2));
        expectedCaptures.add(Pair.of(4, 3));
        expectedCaptures.add(Pair.of(4, 4));
        assertEquals(expectedCaptures, possibleCaptures);
    }

    @Test
    void blackKingHasNoCaptures() {
        var boardArrangement = "{\"1\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"2\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"3\":[\"XX\",\"XX\",\"XX\",\"XX\",\"QB\",\"XX\",\"XX\",\"XX\"],\"4\":[\"XX\",\"XX\",\"HB\",\"KB\",\"HB\",\"XX\",\"XX\",\"XX\"],\"5\":[\"XX\",\"XX\",\"PB\",\"PB\",\"HB\",\"XX\",\"XX\",\"XX\"],\"6\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"7\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"8\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"]}";
        var kingRow = 3;
        var kingCol = 3;
        var chessBoard = stateInterpreter.convertJsonBoardToChessBoard(boardArrangement);

        var blackKing = chessBoard.getChessmanAt(kingRow, kingCol);
        assertTrue(blackKing instanceof King);
        assertEquals(blackKing.getColour(), ChessmanColourEnum.BLACK);
        var possibleCaptures = blackKing.getPossibleCaptures(chessBoard, Pair.of(kingRow, kingCol));
        assertEquals(0, possibleCaptures.size());
    }

    @Test
    void blackKingHasSomeCaptures() {
        var boardArrangement = "{\"1\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"2\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"3\":[\"XX\",\"XX\",\"XX\",\"XX\",\"QB\",\"XX\",\"XX\",\"XX\"],\"4\":[\"XX\",\"XX\",\"HB\",\"KB\",\"HW\",\"XX\",\"XX\",\"XX\"],\"5\":[\"XX\",\"XX\",\"PB\",\"PW\",\"HW\",\"XX\",\"XX\",\"XX\"],\"6\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"7\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"8\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"]}";
        var kingRow = 3;
        var kingCol = 3;
        var chessBoard = stateInterpreter.convertJsonBoardToChessBoard(boardArrangement);

        var blackKing = chessBoard.getChessmanAt(kingRow, kingCol);
        assertTrue(blackKing instanceof King);
        assertEquals(blackKing.getColour(), ChessmanColourEnum.BLACK);
        var possibleCaptures = blackKing.getPossibleCaptures(chessBoard, Pair.of(kingRow, kingCol));
        assertEquals(3, possibleCaptures.size());

        var expectedCaptures = new HashSet<Pair<Integer, Integer>>();
        expectedCaptures.add(Pair.of(3, 4));
        expectedCaptures.add(Pair.of(4, 3));
        expectedCaptures.add(Pair.of(4, 4));
        assertEquals(expectedCaptures, possibleCaptures);
    }

    @Test
    void blackKingHasAllCaptures() {
        var boardArrangement = "{\"1\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"2\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"3\":[\"XX\",\"XX\",\"KW\",\"HW\",\"QW\",\"XX\",\"XX\",\"XX\"],\"4\":[\"XX\",\"XX\",\"HW\",\"KB\",\"HW\",\"XX\",\"XX\",\"XX\"],\"5\":[\"XX\",\"XX\",\"PW\",\"PW\",\"HW\",\"XX\",\"XX\",\"XX\"],\"6\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"7\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"8\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"]}";
        var kingRow = 3;
        var kingCol = 3;
        var chessBoard = stateInterpreter.convertJsonBoardToChessBoard(boardArrangement);

        var blackKing = chessBoard.getChessmanAt(kingRow, kingCol);
        assertTrue(blackKing instanceof King);
        assertEquals(blackKing.getColour(), ChessmanColourEnum.BLACK);
        var possibleCaptures = blackKing.getPossibleCaptures(chessBoard, Pair.of(kingRow, kingCol));
        assertEquals(8, possibleCaptures.size());

        var expectedCaptures = new HashSet<Pair<Integer, Integer>>();
        expectedCaptures.add(Pair.of(2, 2));
        expectedCaptures.add(Pair.of(2, 3));
        expectedCaptures.add(Pair.of(2, 4));
        expectedCaptures.add(Pair.of(3, 2));
        expectedCaptures.add(Pair.of(3, 4));
        expectedCaptures.add(Pair.of(4, 2));
        expectedCaptures.add(Pair.of(4, 3));
        expectedCaptures.add(Pair.of(4, 4));
        assertEquals(expectedCaptures, possibleCaptures);
    }

}