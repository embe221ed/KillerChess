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

    public abstract Set<Pair<Integer, Integer>> getPossibleMoves(ChessBoard chessBoard,
                                                                 Pair<Integer, Integer> position);

    public abstract Set<Pair<Integer, Integer>> getPossibleCaptures(ChessBoard chessBoard,
                                                                    Pair<Integer, Integer> position);

    boolean isFieldWithinBoard(Pair<Integer, Integer> field) {
        var fieldRow = field.getKey();
        var fieldCol = field.getValue();
        return fieldRow >= 0 && fieldRow <= 7
                && fieldCol >= 0 && fieldCol <= 7;
    }

    boolean isFieldEmpty(ChessBoard chessBoard, Pair<Integer, Integer> field) {
        return chessBoard.getChessmanAt(field).getSymbol().equals('X');
    }

    boolean isFieldWithinBoardAndEmpty(ChessBoard chessBoard, Pair<Integer, Integer> field) {
        return isFieldWithinBoard(field) && isFieldEmpty(chessBoard, field);
    }

    boolean isFieldWithinBoardAndNotEmpty(ChessBoard chessBoard, Pair<Integer, Integer> field) {
        return isFieldWithinBoard(field) && !isFieldEmpty(chessBoard, field);
    }

    boolean addNonEmptyChessmanFromGivenColorToFieldSetAndUpdateBlockade(ChessBoard chessBoard,
                                                                         ChessmanColourEnum color,
                                                                         Set<Pair<Integer, Integer>> fieldSet,
                                                                         Integer col, int row) {
        var field = new Pair<>(row, col);
        if (!isFieldEmpty(chessBoard, field)) {
            if (chessBoard.getChessmanAt(field).getColour().equals(color))
                fieldSet.add(field);
            return true;
        }
        return false;
    }

    void addFieldIfIsWithinBoardAndMatchesColour(ChessBoard chessBoard,
                                                 Set<Pair<Integer, Integer>> fieldSet,
                                                 Pair<Integer, Integer> field,
                                                 ChessmanColourEnum colour) {
        if (isFieldWithinBoardAndNotEmpty(chessBoard, field)
                && chessBoard.getChessmanAt(field).getColour().equals(colour)) {
            fieldSet.add(field);
        }
    }

    boolean addEmptyFieldAndUpdateBlockade(ChessBoard chessBoard,
                                           Set<Pair<Integer, Integer>> field,
                                           Pair<Integer, Integer> fieldSet,
                                           boolean isBlocked) {
        if (isFieldWithinBoardAndEmpty(chessBoard, fieldSet)) {
            field.add(fieldSet);
        } else {
            isBlocked = true;
        }
        return isBlocked;
    }

    boolean addNonEmptyFieldAndUpdateBlockade(ChessBoard chessBoard,
                                              Set<Pair<Integer, Integer>> fieldSet,
                                              Pair<Integer, Integer> field,
                                              boolean isBlocked,
                                              ChessmanColourEnum color) {
        if (isFieldWithinBoardAndNotEmpty(chessBoard, field)) {
            if (chessBoard.getChessmanAt(field).getColour().equals(color))
                fieldSet.add(field);
            isBlocked = true;
        }
        return isBlocked;
    }
}
