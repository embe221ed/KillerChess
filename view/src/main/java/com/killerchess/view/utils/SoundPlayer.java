package com.killerchess.view.utils;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class SoundPlayer {

    private String end_game = "view/sounds/end_game.wav";
    private String start_game = "view/sounds/start_game.wav";
    private String move = "view/sounds/move.wav";
    private String click = "view/sounds/click.wav";

    public static void main(String[] args) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        SoundPlayer soundPlayer = new SoundPlayer();
        soundPlayer.playOnGameStart();
        soundPlayer.playOnGameEnd();
        soundPlayer.playOnChessmanClick();
        soundPlayer.playOnChessmanMove();
    }

    private void playSound(String filePath) throws LineUnavailableException, IOException, UnsupportedAudioFileException {

        InputStream audioSrc = new FileInputStream(filePath);
        InputStream bufferedIn = new BufferedInputStream(audioSrc);
        AudioInputStream inputStream = AudioSystem.getAudioInputStream(bufferedIn);
        Clip clip = AudioSystem.getClip();
        clip.open(inputStream);
        FloatControl volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        volume.setValue(volume.getMinimum() + (0.8f * (volume.getMaximum() - volume.getMinimum())));
        clip.setFramePosition(0);
        clip.start();
        clip.loop(0);
        while (clip.isRunning()) {
        }
        inputStream.close();
    }

    public void playOnChessmanMove() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        playSound(move);
    }

    public void playOnChessmanClick() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        playSound(click);
    }

    public void playOnGameStart() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        playSound(start_game);
    }

    public void playOnGameEnd() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        playSound(end_game);
    }

}
