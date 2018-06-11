package com.killerchess.core.chessboard.state.interpreter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.killerchess.core.chessboard.ChessBoard;
import com.killerchess.core.chessmans.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;

@Component
public class StateInterpreter {

    public ChessBoard convertJsonBoardToChessBoard(String jsonBoard) {
        var jsonNode = getJsonNodeFromJsonString(jsonBoard);
        var elements = jsonNode.elements();

        var chessBoard = new ArrayList<ArrayList<Chessman>>();

        elements.forEachRemaining(node -> chessBoard.add(getChessmanListFromLine(node)));

        return new ChessBoard(chessBoard);
    }

    public ObjectNode convertChessBoardToJsonBoard(ChessBoard chessBoard) {
        var colSize = chessBoard.getChessBoardColumnsSize();
        var rowSize = chessBoard.getChessBoardRowsSize();

        var mapper = new ObjectMapper();
        var jsonChessBoard = mapper.createObjectNode();

        for (var i = 0; i < colSize; ++i) {
            var currentRowArrayNode = mapper.createArrayNode();

            for (var j = 0; j < rowSize; ++j) {
                var currentChessman = chessBoard.getChessmanAt(i, j);
                var symbolValue = currentChessman.getSymbol().toString();
                var colourValue = currentChessman.getColour().getSymbol();
                var currentChessmanStringValue = symbolValue + colourValue;
                currentRowArrayNode.add(currentChessmanStringValue);
            }

            var currentNodeKey = Integer.valueOf(i + 1).toString();
            jsonChessBoard.putPOJO(currentNodeKey, currentRowArrayNode);
        }

        return jsonChessBoard;
    }

    private JsonNode getJsonNodeFromJsonString(String jsonBoard) {
        var mapper = new ObjectMapper();
        try {
            return mapper.readTree(jsonBoard);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
