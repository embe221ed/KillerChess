package com.killerchess.core.util;

import com.killerchess.core.session.LocalSessionSingleton;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

public class Listener implements Runnable {

    // URL skąd będziemy pobierać informację czy stan się zmienił
    private final String REQUEST_URL = "http://localhost:8080/gameStateChanged";

    @Override
    public void run() {
        LocalSessionSingleton localSessionSingleton = LocalSessionSingleton.getInstance();
        ResponseEntity<Boolean> responseEntity;
        try {
            do {
                // czas pomiędzy kolejnymi zapytaniami
                Thread.sleep(15000);
                responseEntity = localSessionSingleton.
                        exchange(REQUEST_URL, HttpMethod.GET, null, Boolean.class);

            } while (!responseEntity.getBody());
            // zamiast tego będzie wywołanie metody z GameBoard.java, która aktualizuje GameState
            // pobierając tą informację z serwera
            // GameBoard.getInstance().updateGameState();
            System.out.println("Thread finished");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
