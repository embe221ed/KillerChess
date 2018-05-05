package com.killerchess.core.chessmans;

import com.killerchess.core.chessboard.ChessBoard;
import javafx.util.Pair;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Set;

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
        ApplicationContext context = new ClassPathXmlApplicationContext("META-INF/Beans.xml");
//        Chessman chessman = (Chessman)context.getBean("helloWorld");
//        chessman.getChessman();
    }

    public ChessmanColourEnum getColour() {
        return colour;
    }

    public abstract Character getSymbol();

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

    public abstract Set<Pair<Integer, Integer>> getPossibleMoves(ChessBoard chessBoard, Pair<Integer, Integer> position);

    public abstract Set<Pair<Integer, Integer>> getPossibleCaptures(ChessBoard chessBoard, Pair<Integer, Integer> position);

    public boolean isFieldWithinBoard(Pair<Integer, Integer> field) {
        Integer fieldRow = field.getKey();
        Integer fieldCol = field.getValue();
        return fieldRow >= 0 && fieldRow <= 7
                && fieldCol >= 0 && fieldCol <= 7;
    }

    public boolean isFieldEmpty(ChessBoard chessBoard, Pair<Integer, Integer> possibleFieldToMove) {
        Integer fieldRow = possibleFieldToMove.getKey();
        Integer fieldCol = possibleFieldToMove.getValue();
        return chessBoard.getChessmanAt(fieldRow, fieldCol).getSymbol().equals('X');
    }
}
