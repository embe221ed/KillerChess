package com.killerchess.core.chessboard.state.interpreter;

import com.killerchess.core.chessboard.ChessBoard;
import com.killerchess.core.chessmans.*;
import junit.framework.TestCase;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

class StateInterpreterTest extends TestCase {

    private static final String DEFAULT_ARRANGEMENT_JSON = "{\"1\":[\"RW\",\"HW\",\"BW\",\"QW\",\"KW\",\"BW\",\"HW\",\"RW\"],\"2\":[\"PW\",\"PW\",\"PW\",\"PW\",\"PW\",\"PW\",\"PW\",\"PW\"],\"3\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"4\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"5\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"6\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"7\":[\"PB\",\"PB\",\"PB\",\"PB\",\"PB\",\"PB\",\"PB\",\"PB\"],\"8\":[\"RB\",\"HB\",\"BB\",\"QB\",\"KB\",\"BB\",\"HB\",\"RB\"]}";

    @Test
    void stateInterpreterConvertsDefaultArrangementJsonToChessBoardTest() throws IOException {
        StateInterpreter stateInterpreter = new StateInterpreter();

        var chessBoard = stateInterpreter.convertJsonBoardToChessBoard(DEFAULT_ARRANGEMENT_JSON);

        assertEquals(chessBoard.getChessmanAt(0, 0).getSymbol(), Character.valueOf('R'));
        assertEquals(chessBoard.getChessmanAt(0, 1).getSymbol(), Character.valueOf('H'));
        assertEquals(chessBoard.getChessmanAt(0, 2).getSymbol(), Character.valueOf('B'));
        assertEquals(chessBoard.getChessmanAt(0, 3).getSymbol(), Character.valueOf('Q'));
        assertEquals(chessBoard.getChessmanAt(0, 4).getSymbol(), Character.valueOf('K'));
        assertEquals(chessBoard.getChessmanAt(0, 5).getSymbol(), Character.valueOf('B'));
        assertEquals(chessBoard.getChessmanAt(0, 6).getSymbol(), Character.valueOf('H'));
        assertEquals(chessBoard.getChessmanAt(0, 7).getSymbol(), Character.valueOf('R'));

        assertEquals(chessBoard.getChessmanAt(7, 0).getSymbol(), Character.valueOf('R'));
        assertEquals(chessBoard.getChessmanAt(7, 1).getSymbol(), Character.valueOf('H'));
        assertEquals(chessBoard.getChessmanAt(7, 2).getSymbol(), Character.valueOf('B'));
        assertEquals(chessBoard.getChessmanAt(7, 3).getSymbol(), Character.valueOf('Q'));
        assertEquals(chessBoard.getChessmanAt(7, 4).getSymbol(), Character.valueOf('K'));
        assertEquals(chessBoard.getChessmanAt(7, 5).getSymbol(), Character.valueOf('B'));
        assertEquals(chessBoard.getChessmanAt(7, 6).getSymbol(), Character.valueOf('H'));
        assertEquals(chessBoard.getChessmanAt(7, 7).getSymbol(), Character.valueOf('R'));

        for (int i = 0; i < 8; ++i) {
            assertEquals(chessBoard.getChessmanAt(1, i).getSymbol(), Character.valueOf('P'));
            assertEquals(chessBoard.getChessmanAt(6, i).getSymbol(), Character.valueOf('P'));

            assertEquals(chessBoard.getChessmanAt(2, i).getSymbol(), Character.valueOf('X'));
            assertEquals(chessBoard.getChessmanAt(3, i).getSymbol(), Character.valueOf('X'));
            assertEquals(chessBoard.getChessmanAt(4, i).getSymbol(), Character.valueOf('X'));
            assertEquals(chessBoard.getChessmanAt(5, i).getSymbol(), Character.valueOf('X'));
        }
    }

    @Test
    void stateInterpreterConvertsColoursTest() throws IOException {
        StateInterpreter stateInterpreter = new StateInterpreter();

        var chessBoard = stateInterpreter.convertJsonBoardToChessBoard(DEFAULT_ARRANGEMENT_JSON);

        for (int i = 0; i < 8; ++i) {
            assertEquals(chessBoard.getChessmanAt(0, i).getColour(), ChessmanColourEnum.WHITE);
            assertEquals(chessBoard.getChessmanAt(1, i).getColour(), ChessmanColourEnum.WHITE);
            assertEquals(chessBoard.getChessmanAt(2, i).getColour(), ChessmanColourEnum.EMPTY);
            assertEquals(chessBoard.getChessmanAt(3, i).getColour(), ChessmanColourEnum.EMPTY);
            assertEquals(chessBoard.getChessmanAt(4, i).getColour(), ChessmanColourEnum.EMPTY);
            assertEquals(chessBoard.getChessmanAt(5, i).getColour(), ChessmanColourEnum.EMPTY);
            assertEquals(chessBoard.getChessmanAt(6, i).getColour(), ChessmanColourEnum.BLACK);
            assertEquals(chessBoard.getChessmanAt(7, i).getColour(), ChessmanColourEnum.BLACK);
        }
    }


    @Test
    void stateInterpreterConvertsDefaultArrangementChessBoardToJson() {
        var stateInterpreter = new StateInterpreter();
        var chessBoard = createDefaultArrangementChessBoard();
        var jsonBoard = stateInterpreter.convertChessBoardToJsonBoard(chessBoard);
        var jsonBoardString = jsonBoard.toString();
        assertEquals(DEFAULT_ARRANGEMENT_JSON, jsonBoardString);
    }

    private ChessBoard createDefaultArrangementChessBoard() {
        var chessBoard = new ArrayList<ArrayList<Chessman>>();

        var chessBoardFirstRow = createFirstOrLastRowArrangement(ChessmanColourEnum.WHITE);
        var chessBoardSecondRow = createSecondOrSeventhRowArrangement(ChessmanColourEnum.WHITE);
        var chessBoardThirdRow = createEmptyRowArrangement();
        var chessBoardFourthRow = createEmptyRowArrangement();
        var chessBoardFifthRow = createEmptyRowArrangement();
        var chessBoardSixthRow = createEmptyRowArrangement();
        var chessBoardSeventhRow = createSecondOrSeventhRowArrangement(ChessmanColourEnum.BLACK);
        var chessBoardEighthRow = createFirstOrLastRowArrangement(ChessmanColourEnum.BLACK);

        chessBoard.add(chessBoardFirstRow);
        chessBoard.add(chessBoardSecondRow);
        chessBoard.add(chessBoardThirdRow);
        chessBoard.add(chessBoardFourthRow);
        chessBoard.add(chessBoardFifthRow);
        chessBoard.add(chessBoardSixthRow);
        chessBoard.add(chessBoardSeventhRow);
        chessBoard.add(chessBoardEighthRow);

        return new ChessBoard(chessBoard);
    }

    private ArrayList<Chessman> createFirstOrLastRowArrangement(ChessmanColourEnum colour) {
        var rowArrangement = new ArrayList<Chessman>();
        rowArrangement.add(new Rook(colour));
        rowArrangement.add(new Horse(colour));
        rowArrangement.add(new Bishop(colour));
        rowArrangement.add(new Queen(colour));
        rowArrangement.add(new King(colour));
        rowArrangement.add(new Bishop(colour));
        rowArrangement.add(new Horse(colour));
        rowArrangement.add(new Rook(colour));
        return rowArrangement;
    }

    private ArrayList<Chessman> createSecondOrSeventhRowArrangement(ChessmanColourEnum colour) {
        var rowArrangement = new ArrayList<Chessman>();
        rowArrangement.add(new Pawn(colour));
        rowArrangement.add(new Pawn(colour));
        rowArrangement.add(new Pawn(colour));
        rowArrangement.add(new Pawn(colour));
        rowArrangement.add(new Pawn(colour));
        rowArrangement.add(new Pawn(colour));
        rowArrangement.add(new Pawn(colour));
        rowArrangement.add(new Pawn(colour));
        return rowArrangement;
    }

    private ArrayList<Chessman> createEmptyRowArrangement() {
        var rowArrangement = new ArrayList<Chessman>();
        rowArrangement.add(new EmptyField(ChessmanColourEnum.EMPTY));
        rowArrangement.add(new EmptyField(ChessmanColourEnum.EMPTY));
        rowArrangement.add(new EmptyField(ChessmanColourEnum.EMPTY));
        rowArrangement.add(new EmptyField(ChessmanColourEnum.EMPTY));
        rowArrangement.add(new EmptyField(ChessmanColourEnum.EMPTY));
        rowArrangement.add(new EmptyField(ChessmanColourEnum.EMPTY));
        rowArrangement.add(new EmptyField(ChessmanColourEnum.EMPTY));
        rowArrangement.add(new EmptyField(ChessmanColourEnum.EMPTY));
        return rowArrangement;
    }
}

