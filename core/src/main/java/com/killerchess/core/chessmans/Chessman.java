package com.killerchess.core.chessmans;

/*
 * Chessman class in core module. Spring MVC is connected with that module. Below is simple hello world
 * in Spring. Setter and getter is neccessary for getBean method.
 */

public abstract class Chessman {
    private String chessman;

    public static void main(String[] args) {
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
