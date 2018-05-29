package com.killerchess.core.chessmans;

import com.killerchess.core.chessboard.state.interpreter.StateInterpreter;
import javafx.util.Pair;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PawnTest {

    private Pawn whitePawn;
    private Pawn blackPawn;
    private StateInterpreter stateInterpreter;

    @BeforeAll
    void setUp() {
        whitePawn = new Pawn(ChessmanColourEnum.WHITE);
        blackPawn = new Pawn(ChessmanColourEnum.BLACK);
        stateInterpreter = new StateInterpreter();
    }

    @Test
    void whenWhitePawnIsOnTheEndOfBoardThenItsPromoted() {
        var position = new Pair<>(7, 5);
        assertTrue(whitePawn.isPromotionAvailable(position));
    }

    @Test
    void whenWhitePawnIsNotOnTheEndOfBoardThenItsNotPromoted() {
        var position = new Pair<>(5, 5);
        assertFalse(whitePawn.isPromotionAvailable(position));
    }

    @Test
    void whenBlackPawnIsOnTheEndOfBoardThenItsPromoted() {
        var position = new Pair<>(0, 5);
        assertTrue(blackPawn.isPromotionAvailable(position));
    }

    @Test
    void whenBlackPawnIsNotOnTheEndOfBoardThenItsNotPromoted() {
        var position = new Pair<>(5, 5);
        assertFalse(blackPawn.isPromotionAvailable(position));
    }

    @Test
    void whitePawnHasNoMoves() {
        var boardArrangement = "{   \"1\" : [     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"2\" : [     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"3\" : [     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"4\" : [     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"5\" : [     \"PW\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"6\" : [     \"QW\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"7\" : [     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"8\" : [     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ] }";
        var pawnRow = 4;
        var pawnCol = 0;
        var chessBoard = stateInterpreter.convertJsonBoardToChessBoard(boardArrangement);

        var whitePawn = chessBoard.getChessmanAt(pawnRow, pawnCol);
        assertTrue(whitePawn instanceof Pawn);
        assertEquals(whitePawn.getColour(), ChessmanColourEnum.WHITE);
        var possibleMoves = whitePawn.getPossibleMoves(chessBoard, new Pair<>(pawnRow, pawnCol));
        assertEquals(0, possibleMoves.size());
    }

    @Test
    void whitePawnHasOneMove() {
        var boardArrangement = "{   \"1\" : [     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"2\" : [     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"3\" : [     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"4\" : [     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"5\" : [     \"PW\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"6\" : [     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"7\" : [     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"8\" : [     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ] }";
        var pawnRow = 4;
        var pawnCol = 0;
        var chessBoard = stateInterpreter.convertJsonBoardToChessBoard(boardArrangement);

        var whitePawn = chessBoard.getChessmanAt(pawnRow, pawnCol);
        assertTrue(whitePawn instanceof Pawn);
        assertEquals(whitePawn.getColour(), ChessmanColourEnum.WHITE);
        var possibleMoves = whitePawn.getPossibleMoves(chessBoard, new Pair<>(pawnRow, pawnCol));
        assertEquals(1, possibleMoves.size());

        var expectedMoves = new HashSet<Pair<Integer, Integer>>();
        expectedMoves.add(new Pair<>(5, 0));
        assertEquals(expectedMoves, possibleMoves);
    }

    @Test
    void whitePawnHasTwoMoves() {
        var boardArrangement = "{\"1\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"2\":[\"PW\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"3\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"4\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"5\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"6\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"7\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"8\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"]}";
        var pawnRow = 1;
        var pawnCol = 0;
        var chessBoard = stateInterpreter.convertJsonBoardToChessBoard(boardArrangement);

        var whitePawn = chessBoard.getChessmanAt(pawnRow, pawnCol);
        assertTrue(whitePawn instanceof Pawn);
        assertEquals(whitePawn.getColour(), ChessmanColourEnum.WHITE);
        var possibleMoves = whitePawn.getPossibleMoves(chessBoard, new Pair<>(pawnRow, pawnCol));
        assertEquals(2, possibleMoves.size());

        var expectedMoves = new HashSet<Pair<Integer, Integer>>();
        expectedMoves.add(new Pair<>(2, 0));
        expectedMoves.add(new Pair<>(3, 0));
        assertEquals(expectedMoves, possibleMoves);
    }

    @Test
    void blackPawnHasNoMoves() {
        var boardArrangement = "{   \"1\" : [     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"2\" : [     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"3\" : [     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"4\" : [     \"QB\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"5\" : [     \"PB\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"6\" : [     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"7\" : [     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"8\" : [     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ] }";
        var pawnRow = 4;
        var pawnCol = 0;
        var chessBoard = stateInterpreter.convertJsonBoardToChessBoard(boardArrangement);

        var blackPawn = chessBoard.getChessmanAt(pawnRow, pawnCol);
        assertTrue(blackPawn instanceof Pawn);
        assertEquals(blackPawn.getColour(), ChessmanColourEnum.BLACK);
        var possibleMoves = blackPawn.getPossibleMoves(chessBoard, new Pair<>(pawnRow, pawnCol));
        assertEquals(0, possibleMoves.size());
    }

    @Test
    void blackPawnHasOneMove() {
        var boardArrangement = "{   \"1\" : [     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"2\" : [     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"3\" : [     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"4\" : [     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"5\" : [     \"PB\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"6\" : [     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"7\" : [     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"8\" : [     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ] }";
        var pawnRow = 4;
        var pawnCol = 0;
        var chessBoard = stateInterpreter.convertJsonBoardToChessBoard(boardArrangement);

        var blackPawn = chessBoard.getChessmanAt(pawnRow, pawnCol);
        assertTrue(blackPawn instanceof Pawn);
        assertEquals(blackPawn.getColour(), ChessmanColourEnum.BLACK);
        var possibleMoves = blackPawn.getPossibleMoves(chessBoard, new Pair<>(pawnRow, pawnCol));
        assertEquals(1, possibleMoves.size());

        var expectedMoves = new HashSet<Pair<Integer, Integer>>();
        expectedMoves.add(new Pair<>(3, 0));
        assertEquals(expectedMoves, possibleMoves);
    }

    @Test
    void blackPawnHasTwoMoves() {
        var boardArrangement = "{\"1\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"2\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"3\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"4\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"5\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"6\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"7\":[\"PB\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"8\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"]}";
        var pawnRow = 6;
        var pawnCol = 0;
        var chessBoard = stateInterpreter.convertJsonBoardToChessBoard(boardArrangement);

        var blackPawn = chessBoard.getChessmanAt(pawnRow, pawnCol);
        assertTrue(blackPawn instanceof Pawn);
        assertEquals(blackPawn.getColour(), ChessmanColourEnum.BLACK);
        var possibleMoves = blackPawn.getPossibleMoves(chessBoard, new Pair<>(pawnRow, pawnCol));
        assertEquals(2, possibleMoves.size());

        var expectedMoves = new HashSet<Pair<Integer, Integer>>();
        expectedMoves.add(new Pair<>(5, 0));
        expectedMoves.add(new Pair<>(4, 0));
        assertEquals(expectedMoves, possibleMoves);
    }

    @Test
    void whitePawnHasNoCaptures() {
        var boardArrangement = "{   \"1\" : [     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"2\" : [     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"3\" : [     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"4\" : [     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"5\" : [     \"PW\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"6\" : [     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"7\" : [     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"8\" : [     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ] }";
        var pawnRow = 4;
        var pawnCol = 0;
        var chessBoard = stateInterpreter.convertJsonBoardToChessBoard(boardArrangement);

        var whitePawn = chessBoard.getChessmanAt(pawnRow, pawnCol);
        assertTrue(whitePawn instanceof Pawn);
        assertEquals(whitePawn.getColour(), ChessmanColourEnum.WHITE);
        var possibleCaptures = whitePawn.getPossibleCaptures(chessBoard, new Pair<>(pawnRow, pawnCol));
        assertEquals(0, possibleCaptures.size());
    }

    @Test
    void whitePawnHasOneRightCapture() {
        var boardArrangement = "{   \"1\" : [     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"2\" : [     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"3\" : [     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"4\" : [     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"5\" : [     \"PW\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"6\" : [     \"XX\",     \"QB\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"7\" : [     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"8\" : [     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ] }";
        var pawnRow = 4;
        var pawnCol = 0;
        var chessBoard = stateInterpreter.convertJsonBoardToChessBoard(boardArrangement);

        var whitePawn = chessBoard.getChessmanAt(pawnRow, pawnCol);
        assertTrue(whitePawn instanceof Pawn);
        assertEquals(whitePawn.getColour(), ChessmanColourEnum.WHITE);
        var possibleCaptures = whitePawn.getPossibleCaptures(chessBoard, new Pair<>(pawnRow, pawnCol));
        assertEquals(1, possibleCaptures.size());

        var expectedCaptures = new HashSet<Pair<Integer, Integer>>();
        expectedCaptures.add(new Pair<>(5, 1));
        assertEquals(expectedCaptures, possibleCaptures);
    }

    @Test
    void whitePawnHasOneLeftCapture() {
        var boardArrangement = "{   \"1\" : [     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"2\" : [     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"3\" : [     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"4\" : [     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"5\" : [     \"XX\",     \"PW\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"6\" : [     \"QB\",     \"XX\",     \"HW\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"7\" : [     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"8\" : [     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ] }";
        var pawnRow = 4;
        var pawnCol = 1;
        var chessBoard = stateInterpreter.convertJsonBoardToChessBoard(boardArrangement);

        var whitePawn = chessBoard.getChessmanAt(pawnRow, pawnCol);
        assertTrue(whitePawn instanceof Pawn);
        assertEquals(whitePawn.getColour(), ChessmanColourEnum.WHITE);
        var possibleCaptures = whitePawn.getPossibleCaptures(chessBoard, new Pair<>(pawnRow, pawnCol));
        assertEquals(1, possibleCaptures.size());

        var expectedCaptures = new HashSet<Pair<Integer, Integer>>();
        expectedCaptures.add(new Pair<>(5, 0));
        assertEquals(expectedCaptures, possibleCaptures);
    }

    @Test
    void whitePawnHasTwoCaptures() {
        var boardArrangement = "{   \"1\" : [     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"2\" : [     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"3\" : [     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"4\" : [     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"5\" : [     \"XX\",     \"PW\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"6\" : [     \"QB\",     \"XX\",     \"HB\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"7\" : [     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"8\" : [     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ] }";
        var pawnRow = 4;
        var pawnCol = 1;
        var chessBoard = stateInterpreter.convertJsonBoardToChessBoard(boardArrangement);

        var whitePawn = chessBoard.getChessmanAt(pawnRow, pawnCol);
        assertTrue(whitePawn instanceof Pawn);
        assertEquals(whitePawn.getColour(), ChessmanColourEnum.WHITE);
        var possibleCaptures = whitePawn.getPossibleCaptures(chessBoard, new Pair<>(pawnRow, pawnCol));
        assertEquals(2, possibleCaptures.size());

        var expectedCaptures = new HashSet<Pair<Integer, Integer>>();
        expectedCaptures.add(new Pair<>(5, 0));
        expectedCaptures.add(new Pair<>(5, 2));
        assertEquals(expectedCaptures, possibleCaptures);
    }

    @Test
    void blackPawnHasNoCaptures() {
        var boardArrangement = "{   \"1\" : [     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"2\" : [     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"3\" : [     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"4\" : [     \"QB\",     \"XX\",     \"HB\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"5\" : [     \"XX\",     \"PB\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"6\" : [     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"7\" : [     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"8\" : [     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ] }";
        var pawnRow = 4;
        var pawnCol = 1;
        var chessBoard = stateInterpreter.convertJsonBoardToChessBoard(boardArrangement);

        var blackPawn = chessBoard.getChessmanAt(pawnRow, pawnCol);
        assertTrue(blackPawn instanceof Pawn);
        assertEquals(blackPawn.getColour(), ChessmanColourEnum.BLACK);
        var possibleCaptures = blackPawn.getPossibleCaptures(chessBoard, new Pair<>(pawnRow, pawnCol));
        assertEquals(0, possibleCaptures.size());
    }

    @Test
    void blackPawnHasOneRightCapture() {
        var boardArrangement = "{   \"1\" : [     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"2\" : [     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"3\" : [     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"4\" : [     \"QB\",     \"XX\",     \"HW\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"5\" : [     \"XX\",     \"PB\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"6\" : [     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"7\" : [     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"8\" : [     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ] }";
        var pawnRow = 4;
        var pawnCol = 1;
        var chessBoard = stateInterpreter.convertJsonBoardToChessBoard(boardArrangement);

        var blackPawn = chessBoard.getChessmanAt(pawnRow, pawnCol);
        assertTrue(blackPawn instanceof Pawn);
        assertEquals(blackPawn.getColour(), ChessmanColourEnum.BLACK);
        var possibleCaptures = blackPawn.getPossibleCaptures(chessBoard, new Pair<>(pawnRow, pawnCol));
        assertEquals(1, possibleCaptures.size());

        var expectedCaptures = new HashSet<Pair<Integer, Integer>>();
        expectedCaptures.add(new Pair<>(3, 2));
        assertEquals(expectedCaptures, possibleCaptures);
    }

    @Test
    void blackPawnHasOneLeftCapture() {
        var boardArrangement = "{   \"1\" : [     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"2\" : [     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"3\" : [     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"4\" : [     \"QW\",     \"XX\",     \"HB\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"5\" : [     \"XX\",     \"PB\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"6\" : [     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"7\" : [     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"8\" : [     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ] }";
        var pawnRow = 4;
        var pawnCol = 1;
        var chessBoard = stateInterpreter.convertJsonBoardToChessBoard(boardArrangement);

        var blackPawn = chessBoard.getChessmanAt(pawnRow, pawnCol);
        assertTrue(blackPawn instanceof Pawn);
        assertEquals(blackPawn.getColour(), ChessmanColourEnum.BLACK);
        var possibleCaptures = blackPawn.getPossibleCaptures(chessBoard, new Pair<>(pawnRow, pawnCol));
        assertEquals(1, possibleCaptures.size());

        var expectedCaptures = new HashSet<Pair<Integer, Integer>>();
        expectedCaptures.add(new Pair<>(3, 0));
        assertEquals(expectedCaptures, possibleCaptures);
    }

    @Test
    void blackPawnHasTwoCaptures() {
        var boardArrangement = "{   \"1\" : [     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"2\" : [     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"3\" : [     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"4\" : [     \"QW\",     \"XX\",     \"HW\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"5\" : [     \"XX\",     \"PB\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"6\" : [     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"7\" : [     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ],   \"8\" : [     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\",     \"XX\"   ] }";
        var pawnRow = 4;
        var pawnCol = 1;
        var chessBoard = stateInterpreter.convertJsonBoardToChessBoard(boardArrangement);

        var blackPawn = chessBoard.getChessmanAt(pawnRow, pawnCol);
        assertTrue(blackPawn instanceof Pawn);
        assertEquals(blackPawn.getColour(), ChessmanColourEnum.BLACK);
        var possibleCaptures = blackPawn.getPossibleCaptures(chessBoard, new Pair<>(pawnRow, pawnCol));
        assertEquals(2, possibleCaptures.size());

        var expectedCaptures = new HashSet<Pair<Integer, Integer>>();
        expectedCaptures.add(new Pair<>(3, 0));
        expectedCaptures.add(new Pair<>(3, 2));
        assertEquals(expectedCaptures, possibleCaptures);
    }
}