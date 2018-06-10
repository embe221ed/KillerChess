package com.killerchess.core.util;

import com.killerchess.core.session.LocalSessionSingleton;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

public class Listener implements Runnable {

    private final String REQUEST_URL = "http://localhost:8080/gameStateChanged";

    @Override
    public void run() {
        LocalSessionSingleton localSessionSingleton = LocalSessionSingleton.getInstance();
        ResponseEntity<Boolean> responseEntity;
        try {
            do {
                Thread.sleep(15000);
                responseEntity = localSessionSingleton.
                        exchange(REQUEST_URL, HttpMethod.GET, null, Boolean.class);

            } while (!responseEntity.getBody());
            System.out.println("Thread finished");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
