package com.killerchess.core.chessboard.state.interpreter;

class ColourNotFoundException extends Exception {

    ColourNotFoundException(String message, char colourChar) {
        super(message + " : " + colourChar);
    }
}
