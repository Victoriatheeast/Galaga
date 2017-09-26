package mvc.model;

import images.SpriteTexLoader;

import java.awt.*;

/**
 * The Capture class renders and updates a Capture object
 *
 * @author Anqi Wu
 * @version 1.0
 * @since 2016-11-27
 */
public class Capture extends Sprite{

    /**The speed of a capture*/
    private final int CAPTURE_SPEED = 10;

    /**The dimension of a capture*/
    private final static Dimension CAPTURE_DIM = new Dimension(50,200);

    /**
     * Constructs a Capture for the boss Galaga
     * @param bossGalaga boss galaga object
     */
    public Capture(BossGalaga bossGalaga) {

        super(bossGalaga.getPosition(), CAPTURE_DIM, SpriteTexLoader.load(SpriteTexLoader.SpriteTex.CAPTURE));
        setPlayer(Movable.Player.ENEMY);
        setDeltaX(0);
        setDeltaY(CAPTURE_SPEED);

    }



}
