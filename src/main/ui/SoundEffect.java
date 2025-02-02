package ui;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

// Class SoundEffect is for generating sounds in this application
public class SoundEffect {

    Clip clip;
    Clip gameBGM;
    Clip bunnyHurting = AudioSystem.getClip();
    AudioInputStream audioInputStream;

    //EFFECT: constructor, could throw a LineUnavailable Exception
    public SoundEffect() throws LineUnavailableException {
    }


    //EFFECTS: play the button sound effect when a button is clicked
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

    //MODIFIES: this
    //EFFECT: play the game BGM
    public void playGameBGM() {
        try {
            gameBGM = AudioSystem.getClip();
            String bgm = "src/main/resources/Sound/Happy_Dreams.wav";
            audioInputStream = AudioSystem.getAudioInputStream(new File(bgm).getAbsoluteFile());
            gameBGM.open(audioInputStream);
            gameBGM.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
        gameBGM.start();
    }

    //MODIFIES: this
    //EFFECT: play the sound of a bunny hurting
    public void playBunnyHurt() {
        if (!bunnyHurting.isRunning()) {
            try {
                bunnyHurting = AudioSystem.getClip();
                audioInputStream = AudioSystem.getAudioInputStream(
                        new File("src/main/resources/Sound/hurt_sound.wav").getAbsoluteFile());
                bunnyHurting.open(audioInputStream);
            } catch (UnsupportedAudioFileException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (LineUnavailableException e) {
                e.printStackTrace();
            }

            bunnyHurting.start();
        }


    }

    //Effect: stop playing the game Bgm
    public void stop() {
        gameBGM.stop();
    }
}
