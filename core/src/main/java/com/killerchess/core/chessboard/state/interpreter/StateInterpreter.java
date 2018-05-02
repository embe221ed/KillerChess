package com.killerchess.core.chessboard.state.interpreter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.killerchess.core.chessboard.ChessBoard;
import com.killerchess.core.chessmans.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class StateInterpreter {

    ChessBoard convertJsonBoardToChessBoard(String jsonBoard) throws IOException {
        var jsonNode = getJsonNodeFromJsonString(jsonBoard);
        var elements = jsonNode.elements();

        var chessBoard = new ArrayList<ArrayList<Chessman>>();

        elements.forEachRemaining(node -> chessBoard.add(getChessmanListFromLine(node)));

        return new ChessBoard(chessBoard);
    }

    private JsonNode getJsonNodeFromJsonString(String jsonBoard) throws IOException {
        var mapper = new ObjectMapper();
        return mapper.readTree(jsonBoard);
    }

    private ArrayList<Chessman> getChessmanListFromLine(JsonNode node) {
        var chessmanList = new ArrayList<Chessman>();
        node.forEach(jsonNode -> chessmanList.add(tryConvertJsonNodeToChessman(jsonNode)));
        return chessmanList;
    }

    private Chessman tryConvertJsonNodeToChessman(JsonNode jsonNode) {
        var chessmanStringValue = jsonNode.toString().replace("\"", "");
        Chessman chessman = null;
        try {
            chessman = createChessman(chessmanStringValue);
        } catch (NullPointerException | ColourNotFoundException e) {
            // TODO AK how to catch?
        }
        return chessman;
    }

    private Chessman createChessman(String chessmanStringValue) throws NullPointerException, ColourNotFoundException {
        var colour = tryGetColour(chessmanStringValue);
        var chessmanChar = tryGetChessmanChar(chessmanStringValue);
        switch (chessmanChar) {
            case 'B':
                return new Bishop(colour);
            case 'K':
                return new King(colour);
            case 'H':
                return new Horse(colour);
            case 'P':
                return new Pawn(colour);
            case 'R':
                return new Rook(colour);
            case 'Q':
                return new Queen(colour);
            case 'X':
                return new EmptyField(colour);
        }
        return null;
    }

    private char tryGetChessmanChar(String chessmanStringValue) throws NullPointerException {
        return chessmanStringValue.charAt(0);
    }

    private ChessmanColourEnum tryGetColour(String chessmanStringValue)
            throws NullPointerException, ColourNotFoundException {

        var colourChar = chessmanStringValue.charAt(1);
        if (colourChar == 'W') {
            return ChessmanColourEnum.WHITE;
        } else if (colourChar == 'B') {
            return ChessmanColourEnum.BLACK;
        } else if (colourChar == 'X') {
            return ChessmanColourEnum.EMPTY;
        }
        throw new ColourNotFoundException("Unexpected colour found", colourChar);
    }

}
