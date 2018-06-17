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
class BishopTest {

    private StateInterpreter stateInterpreter;

    @BeforeAll
    void setUp() {
        stateInterpreter = new StateInterpreter();
    }

    @Test
    void whiteBishopHasNoMoves() {
        var boardArrangement = "{\"1\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"2\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"3\":[\"XX\",\"XX\",\"QW\",\"QW\",\"HB\",\"XX\",\"XX\",\"XX\"],\"4\":[\"XX\",\"XX\",\"PB\",\"BW\",\"PW\",\"XX\",\"XX\",\"XX\"],\"5\":[\"XX\",\"XX\",\"KW\",\"KB\",\"PB\",\"XX\",\"XX\",\"XX\"],\"6\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"7\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"8\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"]}";
        var bishopRow = 3;
        var bishopCol = 3;
        var chessBoard = stateInterpreter.convertJsonBoardToChessBoard(boardArrangement);

        var whiteBishop = chessBoard.getChessmanAt(bishopRow, bishopCol);
        assertTrue(whiteBishop instanceof Bishop);
        assertEquals(whiteBishop.getColour(), ChessmanColourEnum.WHITE);
        var possibleMoves = whiteBishop.getPossibleMoves(chessBoard, Pair.of(bishopRow, bishopCol));
        assertEquals(0, possibleMoves.size());
    }

    @Test
    void whiteBishopHasSomeMoves() {
        var boardArrangement = "{\"1\":[\"QW\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"2\":[\"XX\",\"KB\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"3\":[\"XX\",\"XX\",\"XX\",\"QW\",\"HB\",\"XX\",\"XX\",\"XX\"],\"4\":[\"XX\",\"XX\",\"PB\",\"BW\",\"PW\",\"XX\",\"XX\",\"XX\"],\"5\":[\"XX\",\"XX\",\"KW\",\"KB\",\"XX\",\"XX\",\"XX\",\"XX\"],\"6\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"7\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"HB\",\"XX\"],\"8\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"]}";
        var bishopRow = 3;
        var bishopCol = 3;
        var chessBoard = stateInterpreter.convertJsonBoardToChessBoard(boardArrangement);

        var whiteBishop = chessBoard.getChessmanAt(bishopRow, bishopCol);
        assertTrue(whiteBishop instanceof Bishop);
        assertEquals(whiteBishop.getColour(), ChessmanColourEnum.WHITE);
        var possibleMoves = whiteBishop.getPossibleMoves(chessBoard, Pair.of(bishopRow, bishopCol));
        assertEquals(3, possibleMoves.size());

        var expectedMoves = new HashSet<Pair<Integer, Integer>>();
        expectedMoves.add(Pair.of(2, 2));
        expectedMoves.add(Pair.of(4, 4));
        expectedMoves.add(Pair.of(5, 5));
        assertEquals(expectedMoves, possibleMoves);
    }

    @Test
    void whiteBishopHasAllMoves() {
        var boardArrangement = "{\"1\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"2\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"3\":[\"XX\",\"XX\",\"XX\",\"QW\",\"XX\",\"XX\",\"XX\",\"XX\"],\"4\":[\"XX\",\"XX\",\"PB\",\"BW\",\"PW\",\"XX\",\"XX\",\"XX\"],\"5\":[\"XX\",\"XX\",\"XX\",\"KB\",\"XX\",\"XX\",\"XX\",\"XX\"],\"6\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"7\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"8\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"]}";
        var bishopRow = 3;
        var bishopCol = 3;
        var chessBoard = stateInterpreter.convertJsonBoardToChessBoard(boardArrangement);

        var whiteBishop = chessBoard.getChessmanAt(bishopRow, bishopCol);
        assertTrue(whiteBishop instanceof Bishop);
        assertEquals(whiteBishop.getColour(), ChessmanColourEnum.WHITE);
        var possibleMoves = whiteBishop.getPossibleMoves(chessBoard, Pair.of(bishopRow, bishopCol));
        assertEquals(13, possibleMoves.size());

        var expectedMoves = new HashSet<Pair<Integer, Integer>>();
        expectedMoves.add(Pair.of(0, 0));
        expectedMoves.add(Pair.of(1, 1));
        expectedMoves.add(Pair.of(2, 2));
        expectedMoves.add(Pair.of(4, 4));
        expectedMoves.add(Pair.of(5, 5));
        expectedMoves.add(Pair.of(6, 6));
        expectedMoves.add(Pair.of(7, 7));
        expectedMoves.add(Pair.of(0, 6));
        expectedMoves.add(Pair.of(1, 5));
        expectedMoves.add(Pair.of(2, 4));
        expectedMoves.add(Pair.of(4, 2));
        expectedMoves.add(Pair.of(5, 1));
        expectedMoves.add(Pair.of(6, 0));
        assertEquals(expectedMoves, possibleMoves);
    }

    @Test
    void blackBishopHasNoMoves() {
        var boardArrangement = "{\"1\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"2\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"3\":[\"XX\",\"XX\",\"QW\",\"QW\",\"HB\",\"XX\",\"XX\",\"XX\"],\"4\":[\"XX\",\"XX\",\"PB\",\"BB\",\"PW\",\"XX\",\"XX\",\"XX\"],\"5\":[\"XX\",\"XX\",\"KW\",\"KB\",\"PB\",\"XX\",\"XX\",\"XX\"],\"6\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"7\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"8\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"]}";
        var bishopRow = 3;
        var bishopCol = 3;
        var chessBoard = stateInterpreter.convertJsonBoardToChessBoard(boardArrangement);

        var blackBishop = chessBoard.getChessmanAt(bishopRow, bishopCol);
        assertTrue(blackBishop instanceof Bishop);
        assertEquals(blackBishop.getColour(), ChessmanColourEnum.BLACK);
        var possibleMoves = blackBishop.getPossibleMoves(chessBoard, Pair.of(bishopRow, bishopCol));
        assertEquals(0, possibleMoves.size());

    }

    @Test
    void blackBishopHasSomeMoves() {
        var boardArrangement = "{\"1\":[\"QW\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"2\":[\"XX\",\"KB\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"3\":[\"XX\",\"XX\",\"XX\",\"QW\",\"HB\",\"XX\",\"XX\",\"XX\"],\"4\":[\"XX\",\"XX\",\"PB\",\"BB\",\"PW\",\"XX\",\"XX\",\"XX\"],\"5\":[\"XX\",\"XX\",\"KW\",\"KB\",\"XX\",\"XX\",\"XX\",\"XX\"],\"6\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"7\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"HB\",\"XX\"],\"8\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"]}";
        var bishopRow = 3;
        var bishopCol = 3;
        var chessBoard = stateInterpreter.convertJsonBoardToChessBoard(boardArrangement);

        var blackBishop = chessBoard.getChessmanAt(bishopRow, bishopCol);
        assertTrue(blackBishop instanceof Bishop);
        assertEquals(blackBishop.getColour(), ChessmanColourEnum.BLACK);
        var possibleMoves = blackBishop.getPossibleMoves(chessBoard, Pair.of(bishopRow, bishopCol));
        assertEquals(3, possibleMoves.size());

        var expectedMoves = new HashSet<Pair<Integer, Integer>>();
        expectedMoves.add(Pair.of(2, 2));
        expectedMoves.add(Pair.of(4, 4));
        expectedMoves.add(Pair.of(5, 5));
        assertEquals(expectedMoves, possibleMoves);
    }

    @Test
    void blackBishopHasAllMoves() {
        var boardArrangement = "{\"1\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"2\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"3\":[\"XX\",\"XX\",\"XX\",\"QW\",\"XX\",\"XX\",\"XX\",\"XX\"],\"4\":[\"XX\",\"XX\",\"PB\",\"BB\",\"PW\",\"XX\",\"XX\",\"XX\"],\"5\":[\"XX\",\"XX\",\"XX\",\"KB\",\"XX\",\"XX\",\"XX\",\"XX\"],\"6\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"7\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"8\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"]}";
        var bishopRow = 3;
        var bishopCol = 3;
        var chessBoard = stateInterpreter.convertJsonBoardToChessBoard(boardArrangement);

        var blackBishop = chessBoard.getChessmanAt(bishopRow, bishopCol);
        assertTrue(blackBishop instanceof Bishop);
        assertEquals(blackBishop.getColour(), ChessmanColourEnum.BLACK);
        var possibleMoves = blackBishop.getPossibleMoves(chessBoard, Pair.of(bishopRow, bishopCol));
        assertEquals(13, possibleMoves.size());

        var expectedMoves = new HashSet<Pair<Integer, Integer>>();
        expectedMoves.add(Pair.of(0, 0));
        expectedMoves.add(Pair.of(1, 1));
        expectedMoves.add(Pair.of(2, 2));
        expectedMoves.add(Pair.of(4, 4));
        expectedMoves.add(Pair.of(5, 5));
        expectedMoves.add(Pair.of(6, 6));
        expectedMoves.add(Pair.of(7, 7));
        expectedMoves.add(Pair.of(0, 6));
        expectedMoves.add(Pair.of(1, 5));
        expectedMoves.add(Pair.of(2, 4));
        expectedMoves.add(Pair.of(4, 2));
        expectedMoves.add(Pair.of(5, 1));
        expectedMoves.add(Pair.of(6, 0));
        assertEquals(expectedMoves, possibleMoves);
    }

    @Test
    void whiteBishopHasNoCaptures() {
        var boardArrangement = "{\"1\":[\"RW\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"PW\",\"XX\"],\"2\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"3\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"4\":[\"XX\",\"XX\",\"XX\",\"BW\",\"XX\",\"XX\",\"XX\",\"XX\"],\"5\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"6\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"7\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"QW\"],\"8\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"PW\"]}";
        var bishopRow = 3;
        var bishopCol = 3;
        var chessBoard = stateInterpreter.convertJsonBoardToChessBoard(boardArrangement);

        var whiteBishop = chessBoard.getChessmanAt(bishopRow, bishopCol);
        assertTrue(whiteBishop instanceof Bishop);
        assertEquals(whiteBishop.getColour(), ChessmanColourEnum.WHITE);
        var possibleCaptures = whiteBishop.getPossibleCaptures(chessBoard, Pair.of(bishopRow, bishopCol));
        assertEquals(0, possibleCaptures.size());
    }

    @Test
    void whiteBishopHasSomeCaptures() {
        var boardArrangement = "{\"1\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"2\":[\"XX\",\"KB\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"3\":[\"XX\",\"XX\",\"PB\",\"XX\",\"PB\",\"XX\",\"XX\",\"XX\"],\"4\":[\"XX\",\"XX\",\"XX\",\"BW\",\"XX\",\"XX\",\"XX\",\"XX\"],\"5\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"6\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"7\":[\"KW\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"8\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"PB\"]}";
        var bishopRow = 3;
        var bishopCol = 3;
        var chessBoard = stateInterpreter.convertJsonBoardToChessBoard(boardArrangement);

        var whiteBishop = chessBoard.getChessmanAt(bishopRow, bishopCol);
        assertTrue(whiteBishop instanceof Bishop);
        assertEquals(whiteBishop.getColour(), ChessmanColourEnum.WHITE);
        var possibleCaptures = whiteBishop.getPossibleCaptures(chessBoard, Pair.of(bishopRow, bishopCol));
        assertEquals(3, possibleCaptures.size());

        var expectedCaptures = new HashSet<Pair<Integer, Integer>>();
        expectedCaptures.add(Pair.of(2, 2));
        expectedCaptures.add(Pair.of(2, 4));
        expectedCaptures.add(Pair.of(7, 7));
        assertEquals(expectedCaptures, possibleCaptures);
    }

    @Test
    void whiteBishopHasAllCaptures() {
        var boardArrangement = "{\"1\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"2\":[\"XX\",\"KB\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"3\":[\"XX\",\"XX\",\"PB\",\"XX\",\"PB\",\"XX\",\"XX\",\"XX\"],\"4\":[\"XX\",\"XX\",\"XX\",\"BW\",\"XX\",\"XX\",\"XX\",\"XX\"],\"5\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"6\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"7\":[\"KB\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"PB\",\"XX\"],\"8\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"PB\"]}";
        var bishopRow = 3;
        var bishopCol = 3;
        var chessBoard = stateInterpreter.convertJsonBoardToChessBoard(boardArrangement);

        var whiteBishop = chessBoard.getChessmanAt(bishopRow, bishopCol);
        assertTrue(whiteBishop instanceof Bishop);
        assertEquals(whiteBishop.getColour(), ChessmanColourEnum.WHITE);
        var possibleCaptures = whiteBishop.getPossibleCaptures(chessBoard, Pair.of(bishopRow, bishopCol));
        assertEquals(4, possibleCaptures.size());

        var expectedCaptures = new HashSet<Pair<Integer, Integer>>();
        expectedCaptures.add(Pair.of(2, 2));
        expectedCaptures.add(Pair.of(2, 4));
        expectedCaptures.add(Pair.of(6, 0));
        expectedCaptures.add(Pair.of(6, 6));
        assertEquals(expectedCaptures, possibleCaptures);
    }

    @Test
    void blackBishopHasNoCaptures() {
        var boardArrangement = "{\"1\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"2\":[\"XX\",\"KB\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"3\":[\"XX\",\"XX\",\"XX\",\"XX\",\"PB\",\"XX\",\"XX\",\"XX\"],\"4\":[\"XX\",\"XX\",\"XX\",\"BB\",\"XX\",\"XX\",\"XX\",\"XX\"],\"5\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"6\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"7\":[\"HB\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"8\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"]}";
        var bishopRow = 3;
        var bishopCol = 3;
        var chessBoard = stateInterpreter.convertJsonBoardToChessBoard(boardArrangement);

        var blackBishop = chessBoard.getChessmanAt(bishopRow, bishopCol);
        assertTrue(blackBishop instanceof Bishop);
        assertEquals(blackBishop.getColour(), ChessmanColourEnum.BLACK);
        var possibleCaptures = blackBishop.getPossibleCaptures(chessBoard, Pair.of(bishopRow, bishopCol));
        assertEquals(0, possibleCaptures.size());
    }

    @Test
    void blackBishopHasSomeCaptures() {
        var boardArrangement = "{\"1\":[\"HW\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"2\":[\"XX\",\"KW\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"3\":[\"XX\",\"XX\",\"XX\",\"XX\",\"PB\",\"XX\",\"XX\",\"XX\"],\"4\":[\"XX\",\"XX\",\"XX\",\"BB\",\"XX\",\"XX\",\"XX\",\"XX\"],\"5\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"6\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"7\":[\"HB\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"8\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"]}";
        var bishopRow = 3;
        var bishopCol = 3;
        var chessBoard = stateInterpreter.convertJsonBoardToChessBoard(boardArrangement);

        var blackBishop = chessBoard.getChessmanAt(bishopRow, bishopCol);
        assertTrue(blackBishop instanceof Bishop);
        assertEquals(blackBishop.getColour(), ChessmanColourEnum.BLACK);
        var possibleCaptures = blackBishop.getPossibleCaptures(chessBoard, Pair.of(bishopRow, bishopCol));
        assertEquals(1, possibleCaptures.size());

        var expectedCaptures = new HashSet<Pair<Integer, Integer>>();
        expectedCaptures.add(Pair.of(1, 1));
        assertEquals(expectedCaptures, possibleCaptures);
    }

    @Test
    void blackBishopHasAllCaptures() {
        var boardArrangement = "{\"1\":[\"HW\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"2\":[\"XX\",\"KW\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"3\":[\"XX\",\"XX\",\"XX\",\"XX\",\"PW\",\"XX\",\"XX\",\"XX\"],\"4\":[\"XX\",\"XX\",\"XX\",\"BB\",\"XX\",\"XX\",\"XX\",\"XX\"],\"5\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"6\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"PW\",\"XX\",\"XX\"],\"7\":[\"HW\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"8\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"]}";
        var bishopRow = 3;
        var bishopCol = 3;
        var chessBoard = stateInterpreter.convertJsonBoardToChessBoard(boardArrangement);

        var blackBishop = chessBoard.getChessmanAt(bishopRow, bishopCol);
        assertTrue(blackBishop instanceof Bishop);
        assertEquals(blackBishop.getColour(), ChessmanColourEnum.BLACK);
        var possibleCaptures = blackBishop.getPossibleCaptures(chessBoard, Pair.of(bishopRow, bishopCol));
        assertEquals(4, possibleCaptures.size());

        var expectedCaptures = new HashSet<Pair<Integer, Integer>>();
        expectedCaptures.add(Pair.of(1, 1));
        expectedCaptures.add(Pair.of(2, 4));
        expectedCaptures.add(Pair.of(5, 5));
        expectedCaptures.add(Pair.of(6, 0));
        assertEquals(expectedCaptures, possibleCaptures);
    }

}