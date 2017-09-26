package mvc.model;

import java.awt.*;

/**
 * The Movable interface for all movable objects
 *
 * @author Anqi Wu
 * @version 1.0
 * @since 2016-11-27
 */
public interface Movable {

    // The player of the object
    public static enum Player{
        ENEMY, FRIEND
    }

    //Move the object
    public void move();

    //Draw the object
    public void draw(Graphics g);

    /**The three below are for collision detection*/
    //Get the position of the object
    public Point getPosition();
    //Get the player of the object
    public Player getPlayer();
    //Get the bounds of the object
    public Rectangle getBounds();



}
