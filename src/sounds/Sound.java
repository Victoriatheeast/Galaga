package sounds;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * This class handles playing sound effect
 * @version  1.0
 * @author Anqi Wu
 * @since  11-13-16
 */

public class Sound {

    public enum SoundEffect {
        FIRE_BULLET,
        ENEMY_FLYING,
        ENEMY_KILLED,
        SHIP_EXPLOSION,
        LEVEL_START,
        THEME_SONG,
        ENEMY_CAPTURE
    }

    /**
     * Retrieves the file path for a sound effect
     * @param effect - the file path of the sound effect to load
     */
    private static String getSoundEffectFile(SoundEffect effect) {

        String file = "";
        switch (effect) {
            case FIRE_BULLET:
                file = "firing.wav";
                break;
            case ENEMY_FLYING:
                file = "enemyFlying.wav";
                break;
            case ENEMY_KILLED:
                file = "enemyKilled.wav";
                break;
            case SHIP_EXPLOSION:
                file = "explosion.wav";
                break;
            case LEVEL_START:
                file = "levelStart.wav";
                break;
            case THEME_SONG:
                file = "themeSong.wav";
                break;
            case ENEMY_CAPTURE:
                file = "themeSong.wav";
                break;
        }
        return file;
    }

    /**
     * Plays an individual sound effect.
     * @param effect the type of sound effect to play
     * @cite: http://stackoverflow.com/questions/26305/how-can-i-play-sound-in-java
     * */
    public static synchronized void playSoundEffect(SoundEffect effect) {
        new Thread(new Runnable() {
            public void run() {
                try {
                    Clip clp = AudioSystem.getClip();

                    AudioInputStream aisStream =
                            AudioSystem.getAudioInputStream(Sound.class.getResource(getSoundEffectFile(effect)));


                    clp.open(aisStream);
                    clp.start();
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            }
        }).start();
    }


    /**
     * Continuously plays a sound effect file.
     * @param effect the path to the sound effect to load.
     * @cite: http://stackoverflow.com/questions/4875080/music-loop-in-java
     * */
    public static Clip clipForLoopFactory(SoundEffect effect){

        Clip clp = null;

        // this line caused the original exceptions
        try {
            AudioInputStream aisStream =
                    AudioSystem.getAudioInputStream(Sound.class.getResource(getSoundEffectFile(effect)));
            clp = AudioSystem.getClip();
            clp.open( aisStream );

        } catch (UnsupportedAudioFileException exp) {
            exp.printStackTrace();
        } catch(Exception exp){
            System.out.println("error");
        }

        return clp;
    }
}
