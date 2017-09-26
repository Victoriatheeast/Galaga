package mvc.model;

import images.SpriteTexLoader;
import mvc.view.GameFrame;

import java.awt.*;

/**
 * The Ship Class renders and updates a ship objects
 *
 * @author Anqi Wu
 * @version 1.0
 * @since 2016-11-27
 */
public class Ship extends Sprite {

    /**The dimension of the ship*/
    private final static Dimension SHIP_DIM = new Dimension(25,39);

    /**The movement in X of the ship*/
    private final static int SHIP_SPEED = 15;

    /**
     * Constructs a ship
     */
    public Ship() {
        super(new Point(GameFrame.FRAME_DIM.width/2, GameFrame.FRAME_DIM.height - 200), SHIP_DIM, SpriteTexLoader.load(SpriteTexLoader.SpriteTex.SHIP));
        setPlayer(Player.FRIEND);

    }

    /**
     * Move the ship left
     */
    public void moveLeft() {
        setDeltaX(-SHIP_SPEED);
    }

    /**
     * Move the ship right
     */
    public void moveRight() {
        setDeltaX(SHIP_SPEED);
    }

    /**
     * Stop the ship
     */
    public void stop() {
        setDeltaX(0);
    }

}
