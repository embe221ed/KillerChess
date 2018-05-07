package com.killerchess.core.chessmans;

/*
 * Chessman class in core module. Spring MVC is connected with that module. Below is simple hello world
 * in Spring. Setter and getter is neccessary for getBean method.
 */

public abstract class Chessman {

    private String chessman;
    private ChessmanColourEnum colour;

    public Chessman(ChessmanColourEnum colour) {
        this.colour = colour;
    }

    public static void main(String[] args) {
    }

    public ChessmanColourEnum getColour() {
        return colour;
    }

    public abstract Character getSymbol();

    public abstract Integer getPointsValue();

    public void setColour(ChessmanColourEnum colour) {
        this.colour = colour;
    }

    public void printName() {
        System.out.println(this.getClass().getName());
    }

    public void getChessman() {
        System.out.println(chessman);
    }

    public void setChessman(String chessman) {
        this.chessman = chessman;
    }
}
