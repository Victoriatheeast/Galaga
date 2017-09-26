package mvc.model;

import mvc.view.GameFrame;

import java.awt.*;
import java.awt.image.BufferedImage;
/**
 * The Sprite class is the main model for all objects in the program
 * It is a Movable object
 *
 * @author Anqi Wu
 * @version 1.0
 * @since 2016-11-27
 */
public abstract class Sprite implements Movable {

    /** The dimensions of the sprite */
    private Dimension mDim;

    /** The position of the sprite */
    protected Point mPos;

    /**The movement in X*/
    private double dDeltaX;

    /**The movement in Y*/
    private double dDeltaY;

    /** The texture of the sprite */
    private BufferedImage mTex;

    /**The player of the sprite*/
    private Player mPlayer;


    /**
     * Constructs a sprite
     * @param initPos The position of the sprite
     * @param dim the dimension of the sprite
     * @param texture the texture of the sprite
     */

    public Sprite(Point initPos, Dimension dim, BufferedImage texture) {
        this.mPos = initPos;
        this.mDim = dim;
        this.mTex = texture;
    }

    /**
     * Get the position of the sprite
     * @return the position of the sprite in a point format
     */

    public Point getPosition(){
        return this.mPos;
    }

    /**
     * Set the location of the sprite
     * @param point the location
     */
    public void setLocation(Point point) {
        this.mPos = point;
    }

    /**
     * Set the movement in X of the sprite
     * @param deltaX the movement in X
     */
    public void setDeltaX(double deltaX) {
        this.dDeltaX = deltaX;
    }

    /**
     * Set the movement in Y of the sprite
     * @param deltaY the movement in Y
     */

    public void setDeltaY(double deltaY) {
        this.dDeltaY = deltaY;
    }

    /**
     * Get the movement in X of the sprite
     * @return the movement in X
     */

    public double getDeltaX() {
        return this.dDeltaX;
    }

    /**
     * Get the movement in Y of the sprite
     * @return the movement in Y
     */

    public double getDeltaY() {
        return this.dDeltaY;
    }

    /**
     * Get the dimension of the sprite
     * @return the dimension of the sprite
     */
    public Dimension getDimension(){
        return this.mDim;
    }

    /**
     * Get the bounds of the sprite
     * @return the bounds of the sprite
     */
    public Rectangle getBounds() {
        return new Rectangle(this.mPos.x,this.mPos.y, this.mDim.width, this.mDim.height);
    }

    /**
     * Get the player of the sprite
     * @return the player of the sprite
     */
    public Player getPlayer(){
        return mPlayer;
    }

    /**
     * Set the player of the sprite
     * @param player the player of the sprite
     */
    public void setPlayer(Player player){
        this.mPlayer = player;
    }


    /**
     * Set the move pattern of the sprite
     */
    public void move(){

        Point point = getPosition();
        double dX = point.x + getDeltaX();
        double dY = point.y + getDeltaY();

        //Keeps the sprite inside the bounds of the frame

        if (point.x > GameFrame.FRAME_DIM.width) {
            setDeltaX(0);

        } else if (point.x < 0) {
            setDeltaX(0);
        } else if (point.y > GameFrame.FRAME_DIM.height) {
            setDeltaY(0);

        } else if (point.y < 0) {
            setDeltaY(0);
        } else {
            setLocation(new Point((int) dX, (int) dY));
        }

    }

    /**
     * Draw the sprite
     * @param g Graphics
     */

    public void draw (Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            //System.out.println( (int) this.mPos.getX()+" "+(int) this.mPos.getY());
            g2d.drawImage(this.mTex, (int) this.mPos.getX(), (int) this.mPos.getY(), (int) this.mDim.getWidth(),
                    (int) this.mDim.getHeight(), null);

    }

}
