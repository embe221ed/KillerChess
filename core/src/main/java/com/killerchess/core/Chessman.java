package com.killerchess.core;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/*
 * Klasa Chessman, w module core do którego podpięty został Spring MVC. Poniżej prosty hello world napisany
 * dla frameworka Spring. Setter i getter wymagany przez Spring do prawidłowego działania metody getBean.
 */

public class Chessman {
    private String chessman;

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
        Chessman chessman = (Chessman)context.getBean("helloWorld");
        chessman.getChessman();
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
