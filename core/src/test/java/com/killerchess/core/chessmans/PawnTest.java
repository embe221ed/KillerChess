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
class PawnTest extends TestCase {

    private Pawn whitePawn;
    private Pawn blackPawn;
    private StateInterpreter stateInterpreter;

    @BeforeAll
    public void setUp() {
        whitePawn = new Pawn(ChessmanColourEnum.WHITE);
        blackPawn = new Pawn(ChessmanColourEnum.BLACK);
        stateInterpreter = new StateInterpreter();
    }

    @Test
    void whenWhitePawnIsOnTheEndOfBoardThenItsPromotedTest() {
        var position = new Pair<>(7, 5);
        assertTrue(whitePawn.isPromotionAvailable(position));
    }

    @Test
    void whenWhitePawnIsNotOnTheEndOfBoardThenItsNotPromotedTest() {
        var position = new Pair<>(5, 5);
        assertFalse(whitePawn.isPromotionAvailable(position));
    }

    @Test
    void whenBlackPawnIsOnTheEndOfBoardThenItsPromotedTest() {
        var position = new Pair<>(0, 5);
        assertTrue(blackPawn.isPromotionAvailable(position));
    }

    @Test
    void whenBlackPawnIsNotOnTheEndOfBoardThenItsNotPromotedTest() {
        var position = new Pair<>(5, 5);
        assertFalse(blackPawn.isPromotionAvailable(position));
    }

    @Test
    void whenWhitePawnHasNoMoves() throws IOException {
        String boardArrangement = "{   \"1\" : [     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"2\" : [     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"3\" : [     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"4\" : [     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"5\" : [     \"PW\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"6\" : [     \"QW\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"7\" : [     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"8\" : [     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ] }";
        int pawnRow = 4;
        int pawnCol = 0;
        var chessBoard = stateInterpreter.convertJsonBoardToChessBoard(boardArrangement);

        var whitePawn = chessBoard.getChessmanAt(pawnRow, pawnCol);
        Set possibleMoves = whitePawn.getPossibleMoves(chessBoard, new Pair<>(pawnRow, pawnCol));
        assertEquals(0, possibleMoves.size());
    }

    @Test
    void whenWhitePawnHasOneMove() throws IOException {
        String boardArrangement = "{   \"1\" : [     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"2\" : [     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"3\" : [     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"4\" : [     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"5\" : [     \"PW\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"6\" : [     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"7\" : [     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"8\" : [     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ] }";
        int pawnRow = 4;
        int pawnCol = 0;
        var chessBoard = stateInterpreter.convertJsonBoardToChessBoard(boardArrangement);

        var whitePawn = chessBoard.getChessmanAt(pawnRow, pawnCol);
        Set possibleMoves = whitePawn.getPossibleMoves(chessBoard, new Pair<>(pawnRow, pawnCol));
        assertEquals(1, possibleMoves.size());

        Set<Pair<Integer, Integer>> expectedMoves = new HashSet<>();
        expectedMoves.add(new Pair<>(5, 0));
        assertEquals(expectedMoves, possibleMoves);
    }

    @Test
    void whenWhitePawnHasTwoMoves() throws IOException {
        String boardArrangement = "{\"1\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"2\":[\"PW\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"3\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"4\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"5\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"6\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"7\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"8\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"]}";
        int pawnRow = 1;
        int pawnCol = 0;
        var chessBoard = stateInterpreter.convertJsonBoardToChessBoard(boardArrangement);

        var whitePawn = chessBoard.getChessmanAt(pawnRow, pawnCol);
        Set possibleMoves = whitePawn.getPossibleMoves(chessBoard, new Pair<>(pawnRow, pawnCol));
        assertEquals(2, possibleMoves.size());

        Set<Pair<Integer, Integer>> expectedMoves = new HashSet<>();
        expectedMoves.add(new Pair<>(2, 0));
        expectedMoves.add(new Pair<>(3, 0));
        assertEquals(expectedMoves, possibleMoves);
    }

    @Test
    void whenBlackPawnHasNoMoves() throws IOException {
        String boardArrangement = "{   \"1\" : [     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"2\" : [     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"3\" : [     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"4\" : [     \"QB\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"5\" : [     \"PB\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"6\" : [     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"7\" : [     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"8\" : [     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ] }";
        int pawnRow = 4;
        int pawnCol = 0;
        var chessBoard = stateInterpreter.convertJsonBoardToChessBoard(boardArrangement);

        var blackPawn = chessBoard.getChessmanAt(pawnRow, pawnCol);
        Set possibleMoves = blackPawn.getPossibleMoves(chessBoard, new Pair<>(pawnRow, pawnCol));
        assertEquals(0, possibleMoves.size());
    }

    @Test
    void whenBlackPawnHasOneMove() throws IOException {
        String boardArrangement = "{   \"1\" : [     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"2\" : [     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"3\" : [     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"4\" : [     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"5\" : [     \"PB\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"6\" : [     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"7\" : [     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"8\" : [     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ] }";
        int pawnRow = 4;
        int pawnCol = 0;
        var chessBoard = stateInterpreter.convertJsonBoardToChessBoard(boardArrangement);

        var blackPawn = chessBoard.getChessmanAt(pawnRow, pawnCol);
        Set possibleMoves = blackPawn.getPossibleMoves(chessBoard, new Pair<>(pawnRow, pawnCol));
        assertEquals(1, possibleMoves.size());

        Set<Pair<Integer, Integer>> expectedMoves = new HashSet<>();
        expectedMoves.add(new Pair<>(3, 0));
        assertEquals(expectedMoves, possibleMoves);
    }

    @Test
    void whenBlackPawnHasTwoMoves() throws IOException {
        String boardArrangement = "{\"1\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"2\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"3\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"4\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"5\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"6\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"7\":[\"PB\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"8\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"]}";
        int pawnRow = 6;
        int pawnCol = 0;
        var chessBoard = stateInterpreter.convertJsonBoardToChessBoard(boardArrangement);

        var blackPawn = chessBoard.getChessmanAt(pawnRow, pawnCol);
        Set possibleMoves = blackPawn.getPossibleMoves(chessBoard, new Pair<>(pawnRow, pawnCol));
        assertEquals(2, possibleMoves.size());

        Set<Pair<Integer, Integer>> expectedMoves = new HashSet<>();
        expectedMoves.add(new Pair<>(5, 0));
        expectedMoves.add(new Pair<>(4, 0));
        assertEquals(expectedMoves, possibleMoves);
    }

    @Test
    void whenWhitePawnHasNoCaptures() throws IOException {
        String boardArrangement = "{   \"1\" : [     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"2\" : [     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"3\" : [     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"4\" : [     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"5\" : [     \"PW\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"6\" : [     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"7\" : [     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"8\" : [     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ] }";
        int pawnRow = 4;
        int pawnCol = 0;
        var chessBoard = stateInterpreter.convertJsonBoardToChessBoard(boardArrangement);

        var whitePawn = chessBoard.getChessmanAt(pawnRow, pawnCol);
        Set possibleCaptures = whitePawn.getPossibleCaptures(chessBoard, new Pair<>(pawnRow, pawnCol));
        assertEquals(0, possibleCaptures.size());
    }

    @Test
    void whenWhitePawnHasOneRightCapture() throws IOException {
        String boardArrangement = "{   \"1\" : [     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"2\" : [     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"3\" : [     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"4\" : [     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"5\" : [     \"PW\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"6\" : [     \"XX\",     \"QB\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"7\" : [     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"8\" : [     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ] }";
        int pawnRow = 4;
        int pawnCol = 0;
        var chessBoard = stateInterpreter.convertJsonBoardToChessBoard(boardArrangement);

        var whitePawn = chessBoard.getChessmanAt(pawnRow, pawnCol);
        Set possibleCaptures = whitePawn.getPossibleCaptures(chessBoard, new Pair<>(pawnRow, pawnCol));
        assertEquals(1, possibleCaptures.size());

        Set<Pair<Integer, Integer>> expectedCaptures = new HashSet<>();
        expectedCaptures.add(new Pair<>(5, 1));
        assertEquals(expectedCaptures, possibleCaptures);
    }

    @Test
    void whenWhitePawnHasOneLeftCapture() throws IOException {
        String boardArrangement = "{   \"1\" : [     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"2\" : [     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"3\" : [     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"4\" : [     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"5\" : [     \"XX\",     \"PW\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"6\" : [     \"QB\",     \"XX\",     \"HW\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"7\" : [     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"8\" : [     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ] }";
        int pawnRow = 4;
        int pawnCol = 1;
        var chessBoard = stateInterpreter.convertJsonBoardToChessBoard(boardArrangement);

        var whitePawn = chessBoard.getChessmanAt(pawnRow, pawnCol);
        Set possibleCaptures = whitePawn.getPossibleCaptures(chessBoard, new Pair<>(pawnRow, pawnCol));
        assertEquals(1, possibleCaptures.size());

        Set<Pair<Integer, Integer>> expectedCaptures = new HashSet<>();
        expectedCaptures.add(new Pair<>(5, 0));
        assertEquals(expectedCaptures, possibleCaptures);
    }

    @Test
    void whenWhitePawnHasTwoCaptures() throws IOException {
        String boardArrangement = "{   \"1\" : [     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"2\" : [     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"3\" : [     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"4\" : [     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"5\" : [     \"XX\",     \"PW\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"6\" : [     \"QB\",     \"XX\",     \"HB\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"7\" : [     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"8\" : [     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ] }";
        int pawnRow = 4;
        int pawnCol = 1;
        var chessBoard = stateInterpreter.convertJsonBoardToChessBoard(boardArrangement);

        var whitePawn = chessBoard.getChessmanAt(pawnRow, pawnCol);
        Set possibleCaptures = whitePawn.getPossibleCaptures(chessBoard, new Pair<>(pawnRow, pawnCol));
        assertEquals(2, possibleCaptures.size());

        Set<Pair<Integer, Integer>> expectedCaptures = new HashSet<>();
        expectedCaptures.add(new Pair<>(5, 0));
        expectedCaptures.add(new Pair<>(5, 2));
        assertEquals(expectedCaptures, possibleCaptures);
    }

    @Test
    void whenBlackPawnHasNoCaptures() throws IOException {
        String boardArrangement = "{   \"1\" : [     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"2\" : [     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"3\" : [     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"4\" : [     \"QB\",     \"XX\",     \"HB\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"5\" : [     \"XX\",     \"PB\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"6\" : [     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"7\" : [     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"8\" : [     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ] }";
        int pawnRow = 4;
        int pawnCol = 1;
        var chessBoard = stateInterpreter.convertJsonBoardToChessBoard(boardArrangement);

        var blackPawn = chessBoard.getChessmanAt(pawnRow, pawnCol);
        Set possibleCaptures = blackPawn.getPossibleCaptures(chessBoard, new Pair<>(pawnRow, pawnCol));
        assertEquals(0, possibleCaptures.size());
    }

    @Test
    void whenBlackPawnHasOneRightCapture() throws IOException {
        String boardArrangement = "{   \"1\" : [     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"2\" : [     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"3\" : [     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"4\" : [     \"QB\",     \"XX\",     \"HW\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"5\" : [     \"XX\",     \"PB\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"6\" : [     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"7\" : [     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"8\" : [     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ] }";
        int pawnRow = 4;
        int pawnCol = 1;
        var chessBoard = stateInterpreter.convertJsonBoardToChessBoard(boardArrangement);

        var blackPawn = chessBoard.getChessmanAt(pawnRow, pawnCol);
        Set possibleCaptures = blackPawn.getPossibleCaptures(chessBoard, new Pair<>(pawnRow, pawnCol));
        assertEquals(1, possibleCaptures.size());

        Set<Pair<Integer, Integer>> expectedCaptures = new HashSet<>();
        expectedCaptures.add(new Pair<>(3, 2));
        assertEquals(expectedCaptures, possibleCaptures);
    }

    @Test
    void whenBlackPawnHasOneLeftCapture() throws IOException {
        String boardArrangement = "{   \"1\" : [     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"2\" : [     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"3\" : [     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"4\" : [     \"QW\",     \"XX\",     \"HB\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"5\" : [     \"XX\",     \"PB\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"6\" : [     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"7\" : [     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"8\" : [     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ] }";
        int pawnRow = 4;
        int pawnCol = 1;
        var chessBoard = stateInterpreter.convertJsonBoardToChessBoard(boardArrangement);

        var blackPawn = chessBoard.getChessmanAt(pawnRow, pawnCol);
        Set possibleCaptures = blackPawn.getPossibleCaptures(chessBoard, new Pair<>(pawnRow, pawnCol));
        assertEquals(1, possibleCaptures.size());

        Set<Pair<Integer, Integer>> expectedCaptures = new HashSet<>();
        expectedCaptures.add(new Pair<>(3, 0));
        assertEquals(expectedCaptures, possibleCaptures);
    }

    @Test
    void whenBlackPawnHasTwoCaptures() throws IOException {
        String boardArrangement = "{   \"1\" : [     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"2\" : [     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"3\" : [     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"4\" : [     \"QW\",     \"XX\",     \"HW\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"5\" : [     \"XX\",     \"PB\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"6\" : [     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"7\" : [     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"8\" : [     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ] }";
        int pawnRow = 4;
        int pawnCol = 1;
        var chessBoard = stateInterpreter.convertJsonBoardToChessBoard(boardArrangement);

        var blackPawn = chessBoard.getChessmanAt(pawnRow, pawnCol);
        Set possibleCaptures = blackPawn.getPossibleCaptures(chessBoard, new Pair<>(pawnRow, pawnCol));
        assertEquals(2, possibleCaptures.size());

        Set<Pair<Integer, Integer>> expectedCaptures = new HashSet<>();
        expectedCaptures.add(new Pair<>(3, 0));
        expectedCaptures.add(new Pair<>(3, 2));
        assertEquals(expectedCaptures, possibleCaptures);
    }
}