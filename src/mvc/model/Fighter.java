package mvc.model;

import mvc.view.GameFrame;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * The Fighter renders and updates a fighter object
 *
 * @author Anqi Wu
 * @version 1.0
 * @since 2016-11-27
 */
public class Fighter extends Sprite {

    /**The dimension of the fighter*/
    private final static Dimension FIGHTER_DIM = new Dimension(30,40);

    /**The speed of the fighter*/
    private final int FIGHTER_SPEED = 8;

    private boolean bMoveDown;

    /**
     * Constructs a fighter
     * @param initPos the position of the fighter
     * @param texture the image of the fighter
     */

    public Fighter(Point initPos, BufferedImage texture) {
        super(initPos, FIGHTER_DIM, texture);
        setPlayer(Player.ENEMY);
        setDeltaX(FIGHTER_SPEED);

    }

    /**
     * The move pattern of a fighter
     */
    @Override
    public void move(){
        Point point = getPosition();
        double dX = point.x + getDeltaX();
        double dY = point.y + getDeltaY();

        if (point.x > GameFrame.FRAME_DIM.width - 220) {
            setLocation(new Point(GameFrame.FRAME_DIM.width - 220, point.y));
            setDeltaX(-FIGHTER_SPEED);

        } else if (point.x < 200) {
            setLocation(new Point(200, point.y));
            setDeltaX(FIGHTER_SPEED);
        } else{
            setLocation(new Point((int) dX, (int) dY));
        }
    }

}

