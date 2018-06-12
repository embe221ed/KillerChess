package com.killerchess.view.utils;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class SoundPlayer {

    private final String end_game = "view/sounds/end_game.wav";
    private final String start_game = "view/sounds/start_game.wav";
    private final String move = "view/sounds/move.wav";
    private final String click = "view/sounds/click.wav";

    private void playSound(String filePath) {

        try {
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(new BufferedInputStream(new FileInputStream(filePath)));

            Clip clip = AudioSystem.getClip();
            clip.open(inputStream);
            clip.setFramePosition(0);

            clip.start();
            while (clip.getFramePosition() != clip.getFrameLength()) {
            }
            clip.close();

            inputStream.close();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }

    }

    public void playOnChessmanMove() {
        playSound(move);
    }

    public void playOnChessmanClick() {
        playSound(click);
    }

    public void playOnGameStart() {
        playSound(start_game);
    }

    public void playOnGameEnd() {
        playSound(end_game);
    }

}
