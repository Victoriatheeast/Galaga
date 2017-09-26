package images;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;


/**
 * This class easily loads the textures for the sprites in the game
 *
 * @version  1.0
 * @author Anqi Wu
 * @since  11-13-16
 */

public class SpriteTexLoader {

    private static SpriteTexLoader sInstance = new SpriteTexLoader();

    public enum SpriteTex {
        SHIP,
        BLUE_FIGHTER,
        RED_FIGHTER,
        BOSS_GALAGA,
        BLUE_BULLET,
        FIGHTER_BULLET,
        SPACE_BACKGROUND,
        GALAGA,
        CAPTURE
    }

    private SpriteTexLoader() {}
    /**
     * Retrieves the file path for the a Sprite texture in the images file
     * @param sprite - the sprite file path to retrieve
     */
    private static String getSpriteFile(SpriteTex sprite) {

        String file = "";
        switch (sprite) {
            case SHIP:
                file = "ship.png";
                break;
            case BLUE_FIGHTER:
                file = "blue_fighter.png";
                break;
            case RED_FIGHTER:
                file = "red_fighter.png";
                break;
            case BOSS_GALAGA:
                file = "boss_galaga.png";
                break;
            case BLUE_BULLET:
                file = "blue_bullet.png";
                break;
            case FIGHTER_BULLET:
                file = "fighter_bullet.png";
                break;
            case SPACE_BACKGROUND:
                file = "space_background.png";
                break;
            case GALAGA:
                file = "galaga.png";
                break;
            case CAPTURE:
                file = "capture.png";
                break;
        }
        return file;
    }

    /**
     * Returns a buffered image from the images directory of a particular sprite
     * @param sprite - the sprite texture to load
     * @throws IOException
     * @throws IllegalArgumentException
     */
    public static BufferedImage load(SpriteTex sprite)  throws IllegalArgumentException {

        if (sprite == null){
            throw new IllegalArgumentException("Sprite texture parameter must not be null");
        }
        BufferedImage img = null;
        try {
            String file = getSpriteFile(sprite);
            img = ImageIO.read(sInstance.getClass().getResource(file));
        }catch (IOException e){
            System.out.print("Could not open texture :" + sprite.toString());
            System.exit(1);
        }
        return img;
    }
}
