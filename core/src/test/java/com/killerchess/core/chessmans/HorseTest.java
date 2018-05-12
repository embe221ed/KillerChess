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
class HorseTest extends TestCase {

    private StateInterpreter stateInterpreter;

    @BeforeAll
    public void setUp() {
        stateInterpreter = new StateInterpreter();
    }

    @Test
    void whenWhiteHorseHasNoMoves() throws IOException {
        String boardArrangement = "{\"1\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"2\":[\"XX\",\"XX\",\"KW\",\"XX\",\"PW\",\"XX\",\"XX\",\"XX\"],\"3\":[\"XX\",\"PW\",\"XX\",\"XX\",\"XX\",\"PW\",\"XX\",\"XX\"],\"4\":[\"XX\",\"XX\",\"XX\",\"HW\",\"XX\",\"XX\",\"XX\",\"XX\"],\"5\":[\"XX\",\"QW\",\"XX\",\"XX\",\"XX\",\"PW\",\"XX\",\"XX\"],\"6\":[\"XX\",\"XX\",\"PW\",\"XX\",\"KW\",\"XX\",\"XX\",\"XX\"],\"7\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"8\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"]}";
        int kingRow = 3;
        int kingCol = 3;
        var chessBoard = stateInterpreter.convertJsonBoardToChessBoard(boardArrangement);

        var whiteHorse = chessBoard.getChessmanAt(kingRow, kingCol);
        assertTrue(whiteHorse instanceof Horse);
        assertEquals(whiteHorse.getColour(), ChessmanColourEnum.WHITE);
        Set possibleMoves = whiteHorse.getPossibleMoves(chessBoard, new Pair<>(kingRow, kingCol));
        assertEquals(0, possibleMoves.size());
    }

    @Test
    void whenWhiteHorseHasSomeMoves() throws IOException {
        String boardArrangement = "{\"1\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"2\":[\"XX\",\"XX\",\"XX\",\"XX\",\"PB\",\"XX\",\"XX\",\"XX\"],\"3\":[\"XX\",\"PW\",\"XX\",\"XX\",\"XX\",\"PW\",\"XX\",\"XX\"],\"4\":[\"XX\",\"XX\",\"XX\",\"HW\",\"XX\",\"XX\",\"XX\",\"XX\"],\"5\":[\"XX\",\"QW\",\"XX\",\"XX\",\"XX\",\"PB\",\"XX\",\"XX\"],\"6\":[\"XX\",\"XX\",\"PW\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"7\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"8\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"]}";
        int kingRow = 3;
        int kingCol = 3;
        var chessBoard = stateInterpreter.convertJsonBoardToChessBoard(boardArrangement);

        var whiteHorse = chessBoard.getChessmanAt(kingRow, kingCol);
        assertTrue(whiteHorse instanceof Horse);
        assertEquals(whiteHorse.getColour(), ChessmanColourEnum.WHITE);
        Set possibleMoves = whiteHorse.getPossibleMoves(chessBoard, new Pair<>(kingRow, kingCol));
        assertEquals(2, possibleMoves.size());

        Set<Pair<Integer, Integer>> expectedMoves = new HashSet<>();
        expectedMoves.add(new Pair<>(1, 2));
        expectedMoves.add(new Pair<>(5, 4));
        assertEquals(expectedMoves, possibleMoves);
    }

    @Test
    void whenWhiteHorseHasAllMoves() throws IOException {
        String boardArrangement = "{\"1\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"2\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"3\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"4\":[\"XX\",\"XX\",\"XX\",\"HW\",\"XX\",\"XX\",\"XX\",\"XX\"],\"5\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"6\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"7\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"8\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"]}";
        int kingRow = 3;
        int kingCol = 3;
        var chessBoard = stateInterpreter.convertJsonBoardToChessBoard(boardArrangement);

        var whiteHorse = chessBoard.getChessmanAt(kingRow, kingCol);
        assertTrue(whiteHorse instanceof Horse);
        assertEquals(whiteHorse.getColour(), ChessmanColourEnum.WHITE);
        Set possibleMoves = whiteHorse.getPossibleMoves(chessBoard, new Pair<>(kingRow, kingCol));
        assertEquals(8, possibleMoves.size());

        Set<Pair<Integer, Integer>> expectedMoves = new HashSet<>();
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
    void whenBlackHorseHasNoMoves() throws IOException {
        String boardArrangement = "{\"1\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"2\":[\"XX\",\"XX\",\"KW\",\"XX\",\"PW\",\"XX\",\"XX\",\"XX\"],\"3\":[\"XX\",\"PW\",\"XX\",\"XX\",\"XX\",\"PB\",\"XX\",\"XX\"],\"4\":[\"XX\",\"XX\",\"XX\",\"HB\",\"XX\",\"XX\",\"XX\",\"XX\"],\"5\":[\"XX\",\"QW\",\"XX\",\"XX\",\"XX\",\"PW\",\"XX\",\"XX\"],\"6\":[\"XX\",\"XX\",\"PW\",\"XX\",\"KW\",\"XX\",\"XX\",\"XX\"],\"7\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"8\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"]}";
        int kingRow = 3;
        int kingCol = 3;
        var chessBoard = stateInterpreter.convertJsonBoardToChessBoard(boardArrangement);

        var blackHorse = chessBoard.getChessmanAt(kingRow, kingCol);
        assertTrue(blackHorse instanceof Horse);
        assertEquals(blackHorse.getColour(), ChessmanColourEnum.BLACK);
        Set possibleMoves = blackHorse.getPossibleMoves(chessBoard, new Pair<>(kingRow, kingCol));
        assertEquals(0, possibleMoves.size());

    }

    @Test
    void whenBlackHorseHasSomeMoves() throws IOException {
        String boardArrangement = "{\"1\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"2\":[\"XX\",\"XX\",\"XX\",\"XX\",\"PB\",\"XX\",\"XX\",\"XX\"],\"3\":[\"XX\",\"PW\",\"XX\",\"XX\",\"XX\",\"PW\",\"XX\",\"XX\"],\"4\":[\"XX\",\"XX\",\"XX\",\"HB\",\"XX\",\"XX\",\"XX\",\"XX\"],\"5\":[\"XX\",\"QB\",\"XX\",\"XX\",\"XX\",\"PB\",\"XX\",\"XX\"],\"6\":[\"XX\",\"XX\",\"PW\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"7\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"8\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"]}";
        int kingRow = 3;
        int kingCol = 3;
        var chessBoard = stateInterpreter.convertJsonBoardToChessBoard(boardArrangement);

        var blackHorse = chessBoard.getChessmanAt(kingRow, kingCol);
        assertTrue(blackHorse instanceof Horse);
        assertEquals(blackHorse.getColour(), ChessmanColourEnum.BLACK);
        Set possibleMoves = blackHorse.getPossibleMoves(chessBoard, new Pair<>(kingRow, kingCol));
        assertEquals(2, possibleMoves.size());

        Set<Pair<Integer, Integer>> expectedMoves = new HashSet<>();
        expectedMoves.add(new Pair<>(1, 2));
        expectedMoves.add(new Pair<>(5, 4));
        assertEquals(expectedMoves, possibleMoves);
    }

    @Test
    void whenBlackHorseHasAllMoves() throws IOException {
        String boardArrangement = "{\"1\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"2\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"3\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"4\":[\"XX\",\"XX\",\"XX\",\"HB\",\"XX\",\"XX\",\"XX\",\"XX\"],\"5\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"6\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"7\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"8\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"]}";
        int kingRow = 3;
        int kingCol = 3;
        var chessBoard = stateInterpreter.convertJsonBoardToChessBoard(boardArrangement);

        var blackHorse = chessBoard.getChessmanAt(kingRow, kingCol);
        assertTrue(blackHorse instanceof Horse);
        assertEquals(blackHorse.getColour(), ChessmanColourEnum.BLACK);
        Set possibleMoves = blackHorse.getPossibleMoves(chessBoard, new Pair<>(kingRow, kingCol));
        assertEquals(8, possibleMoves.size());

        Set<Pair<Integer, Integer>> expectedMoves = new HashSet<>();
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
    void whenWhiteHorseHasNoCaptures() throws IOException {
        String boardArrangement = "{\"1\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"2\":[\"XX\",\"XX\",\"KW\",\"XX\",\"PW\",\"XX\",\"XX\",\"XX\"],\"3\":[\"XX\",\"PW\",\"XX\",\"XX\",\"XX\",\"PW\",\"XX\",\"XX\"],\"4\":[\"XX\",\"XX\",\"XX\",\"HW\",\"XX\",\"XX\",\"XX\",\"XX\"],\"5\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"PW\",\"XX\",\"XX\"],\"6\":[\"XX\",\"XX\",\"XX\",\"XX\",\"KW\",\"XX\",\"XX\",\"XX\"],\"7\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"8\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"]}";
        int kingRow = 3;
        int kingCol = 3;
        var chessBoard = stateInterpreter.convertJsonBoardToChessBoard(boardArrangement);

        var whiteHorse = chessBoard.getChessmanAt(kingRow, kingCol);
        assertTrue(whiteHorse instanceof Horse);
        assertEquals(whiteHorse.getColour(), ChessmanColourEnum.WHITE);
        Set possibleCaptures = whiteHorse.getPossibleCaptures(chessBoard, new Pair<>(kingRow, kingCol));
        assertEquals(0, possibleCaptures.size());
    }

    @Test
    void whenWhiteHorseHasSomeCaptures() throws IOException {
        String boardArrangement = "{\"1\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"2\":[\"XX\",\"XX\",\"KB\",\"XX\",\"PW\",\"XX\",\"XX\",\"XX\"],\"3\":[\"XX\",\"PW\",\"XX\",\"XX\",\"XX\",\"PW\",\"XX\",\"XX\"],\"4\":[\"XX\",\"XX\",\"XX\",\"HW\",\"XX\",\"XX\",\"XX\",\"XX\"],\"5\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"QB\",\"XX\",\"XX\"],\"6\":[\"XX\",\"XX\",\"XX\",\"XX\",\"KW\",\"XX\",\"XX\",\"XX\"],\"7\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"8\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"]}";
        int kingRow = 3;
        int kingCol = 3;
        var chessBoard = stateInterpreter.convertJsonBoardToChessBoard(boardArrangement);

        var whiteHorse = chessBoard.getChessmanAt(kingRow, kingCol);
        assertTrue(whiteHorse instanceof Horse);
        assertEquals(whiteHorse.getColour(), ChessmanColourEnum.WHITE);
        Set possibleCaptures = whiteHorse.getPossibleCaptures(chessBoard, new Pair<>(kingRow, kingCol));
        assertEquals(2, possibleCaptures.size());

        Set<Pair<Integer, Integer>> expectedCaptures = new HashSet<>();
        expectedCaptures.add(new Pair<>(1, 2));
        expectedCaptures.add(new Pair<>(4, 5));
        assertEquals(expectedCaptures, possibleCaptures);
    }

    @Test
    void whenWhiteHorseHasAllCaptures() throws IOException {
        String boardArrangement = "{\"1\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"2\":[\"XX\",\"XX\",\"KB\",\"XX\",\"PB\",\"XX\",\"XX\",\"XX\"],\"3\":[\"XX\",\"PB\",\"XX\",\"XX\",\"XX\",\"PB\",\"XX\",\"XX\"],\"4\":[\"XX\",\"XX\",\"XX\",\"HW\",\"XX\",\"XX\",\"XX\",\"XX\"],\"5\":[\"XX\",\"QB\",\"XX\",\"XX\",\"XX\",\"PB\",\"XX\",\"XX\"],\"6\":[\"XX\",\"XX\",\"PB\",\"XX\",\"KB\",\"XX\",\"XX\",\"XX\"],\"7\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"8\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"]}";
        int kingRow = 3;
        int kingCol = 3;
        var chessBoard = stateInterpreter.convertJsonBoardToChessBoard(boardArrangement);

        var whiteHorse = chessBoard.getChessmanAt(kingRow, kingCol);
        assertTrue(whiteHorse instanceof Horse);
        assertEquals(whiteHorse.getColour(), ChessmanColourEnum.WHITE);
        Set possibleCaptures = whiteHorse.getPossibleCaptures(chessBoard, new Pair<>(kingRow, kingCol));
        assertEquals(8, possibleCaptures.size());

        Set<Pair<Integer, Integer>> expectedCaptures = new HashSet<>();
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
    void whenBlackHorseHasNoCaptures() throws IOException {
        String boardArrangement = "{\"1\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"2\":[\"XX\",\"XX\",\"KB\",\"XX\",\"PB\",\"XX\",\"XX\",\"XX\"],\"3\":[\"XX\",\"PB\",\"XX\",\"XX\",\"XX\",\"PB\",\"XX\",\"XX\"],\"4\":[\"XX\",\"XX\",\"XX\",\"HB\",\"XX\",\"XX\",\"XX\",\"XX\"],\"5\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"PB\",\"XX\",\"XX\"],\"6\":[\"XX\",\"XX\",\"XX\",\"XX\",\"KB\",\"XX\",\"XX\",\"XX\"],\"7\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"8\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"]}";
        int kingRow = 3;
        int kingCol = 3;
        var chessBoard = stateInterpreter.convertJsonBoardToChessBoard(boardArrangement);

        var blackHorse = chessBoard.getChessmanAt(kingRow, kingCol);
        assertTrue(blackHorse instanceof Horse);
        assertEquals(blackHorse.getColour(), ChessmanColourEnum.BLACK);
        Set possibleCaptures = blackHorse.getPossibleCaptures(chessBoard, new Pair<>(kingRow, kingCol));
        assertEquals(0, possibleCaptures.size());
    }

    @Test
    void whenBlackHorseHasSomeCaptures() throws IOException {
        String boardArrangement = "{\"1\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"2\":[\"XX\",\"XX\",\"KB\",\"XX\",\"PW\",\"XX\",\"XX\",\"XX\"],\"3\":[\"XX\",\"PB\",\"XX\",\"XX\",\"XX\",\"PB\",\"XX\",\"XX\"],\"4\":[\"XX\",\"XX\",\"XX\",\"HB\",\"XX\",\"XX\",\"XX\",\"XX\"],\"5\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"PW\",\"XX\",\"XX\"],\"6\":[\"XX\",\"XX\",\"XX\",\"XX\",\"KB\",\"XX\",\"XX\",\"XX\"],\"7\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"8\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"]}";
        int kingRow = 3;
        int kingCol = 3;
        var chessBoard = stateInterpreter.convertJsonBoardToChessBoard(boardArrangement);

        var blackHorse = chessBoard.getChessmanAt(kingRow, kingCol);
        assertTrue(blackHorse instanceof Horse);
        assertEquals(blackHorse.getColour(), ChessmanColourEnum.BLACK);
        Set possibleCaptures = blackHorse.getPossibleCaptures(chessBoard, new Pair<>(kingRow, kingCol));
        assertEquals(2, possibleCaptures.size());

        Set<Pair<Integer, Integer>> expectedCaptures = new HashSet<>();
        expectedCaptures.add(new Pair<>(1, 4));
        expectedCaptures.add(new Pair<>(4, 5));
        assertEquals(expectedCaptures, possibleCaptures);
    }

    @Test
    void whenBlackHorseHasAllCaptures() throws IOException {
        String boardArrangement = "{\"1\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"2\":[\"XX\",\"XX\",\"KW\",\"XX\",\"PW\",\"XX\",\"XX\",\"XX\"],\"3\":[\"XX\",\"PW\",\"XX\",\"XX\",\"XX\",\"PW\",\"XX\",\"XX\"],\"4\":[\"XX\",\"XX\",\"XX\",\"HB\",\"XX\",\"XX\",\"XX\",\"XX\"],\"5\":[\"XX\",\"QW\",\"XX\",\"XX\",\"XX\",\"PW\",\"XX\",\"XX\"],\"6\":[\"XX\",\"XX\",\"PW\",\"XX\",\"KW\",\"XX\",\"XX\",\"XX\"],\"7\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"8\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"]}";
        int kingRow = 3;
        int kingCol = 3;
        var chessBoard = stateInterpreter.convertJsonBoardToChessBoard(boardArrangement);

        var blackHorse = chessBoard.getChessmanAt(kingRow, kingCol);
        assertTrue(blackHorse instanceof Horse);
        assertEquals(blackHorse.getColour(), ChessmanColourEnum.BLACK);
        Set possibleCaptures = blackHorse.getPossibleCaptures(chessBoard, new Pair<>(kingRow, kingCol));
        assertEquals(8, possibleCaptures.size());

        Set<Pair<Integer, Integer>> expectedCaptures = new HashSet<>();
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