package com.killerchess.core.chessboard.state.interpreter;

public class ColourNotFoundException extends Exception {

    public ColourNotFoundException(String message, char colourChar) {
        super(message + " : " + colourChar);
    }
}
