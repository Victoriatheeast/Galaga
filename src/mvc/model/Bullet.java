package mvc.model;

import images.SpriteTexLoader;

import java.awt.*;

/**
 * The Bullet class renders and updates a Bullet object
 *
 * @author Anqi Wu
 * @version 1.0
 * @since 2016-11-27
 */
public class Bullet extends Sprite {

    /**The speed of a bullet*/
    private final int BULLET_SPEED = 20;

    /**The dimension of the bullet*/
    private final static Dimension BULLET_DIM = new Dimension(15,15);

    /**
     * Constructs a bullet for the ship
     * @param ship a ship object
     */
    public Bullet(Ship ship) {

        super(ship.getPosition(), BULLET_DIM, SpriteTexLoader.load(SpriteTexLoader.SpriteTex.BLUE_BULLET));
        setPlayer(Player.FRIEND);
        setDeltaX(0);
        setDeltaY(- BULLET_SPEED);
    }

    /**
     * Constructs a bullet for the fighter
     * @param fighter fighter object
     */
    public Bullet(Fighter fighter){

        super(fighter.getPosition(), BULLET_DIM, SpriteTexLoader.load(SpriteTexLoader.SpriteTex.FIGHTER_BULLET));
        setPlayer(Player.ENEMY);
        setDeltaX(0);
        setDeltaY(BULLET_SPEED);
    }


}
