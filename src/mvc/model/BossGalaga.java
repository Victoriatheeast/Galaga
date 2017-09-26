package mvc.model;

import images.SpriteTexLoader;
import mvc.view.GameFrame;

import java.awt.*;

/**
 * The BossGalaga renders and updates a Bossgalaga object
 *
 * @author Anqi Wu
 * @version 1.0
 * @since 2016-11-27
 */
public class BossGalaga extends Fighter {

    /**The speed of boss galaga*/
    private final int BOSS_SPEED = 10;

    /**
     * Constructs a Boss Galaga
     * @param initPos
     */
    public BossGalaga(Point initPos){
        super(initPos, SpriteTexLoader.load(SpriteTexLoader.SpriteTex.BOSS_GALAGA));
        setDeltaY(BOSS_SPEED);
        setDeltaX(0);
        setPlayer(Player.ENEMY);
    }

    /**
     * The move pattern of a boss galaga
     */
    @Override
    public void move() {

        Point point = getPosition();
        double dX = point.x + getDeltaX();
        double dY = point.y + getDeltaY();

        if (point.y > GameFrame.FRAME_DIM.height - 150) {
            setLocation(new Point(point.x, GameFrame.FRAME_DIM.height - 150));
            setDeltaY(-BOSS_SPEED);
        }
        else if(point.y < 100){
            setLocation(new Point(point.x, 100));
            setDeltaY(BOSS_SPEED);
        }
        else{
            setLocation(new Point((int) dX, (int) dY));
        }
    }


}
