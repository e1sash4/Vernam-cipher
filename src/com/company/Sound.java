package com.company;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;

public class Sound {
    File Music;
    AudioInputStream audioStream;
    Clip ClipMusic;
    Sound(String link) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        Music = new File(link);
        audioStream = AudioSystem.getAudioInputStream(Music);
        ClipMusic = AudioSystem.getClip();
        ClipMusic.open(audioStream);
        ClipMusic.start();
    }
}