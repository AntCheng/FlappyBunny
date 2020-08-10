package ui;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class SoundEffect {

    Clip clip;
    AudioInputStream audioInputStream;


    public void playButtonSound() {
        try {
            clip = AudioSystem.getClip();
            audioInputStream = AudioSystem.getAudioInputStream(
                    new File("src/main/resources/Sound/smw_stomp.wav").getAbsoluteFile());
            clip.open(audioInputStream);
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
        clip.start();
    }

    public void playGameBGM() {
        try {
            clip = AudioSystem.getClip();
            String bgm = "src/main/resources/Sound/Happy_Dreams.wav";
            audioInputStream = AudioSystem.getAudioInputStream(new File(bgm).getAbsoluteFile());
            clip.open(audioInputStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
        clip.start();
    }

    public void stop() {
        clip.stop();
    }
}
