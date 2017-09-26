package mvc.model;

import images.SpriteTexLoader;

import java.awt.*;

/**
 * The Redfighter renders and updates a Redfighter object
 * subclass of Fighter class
 *
 * @author Anqi Wu
 * @version 1.0
 * @since 2016-11-27
 */
public class RedFighter extends Fighter {


    /**
     * Constructs a Red Fighter
     * @param initPos the position of the red figher
     */
    public RedFighter(Point initPos){

        super(initPos, SpriteTexLoader.load(SpriteTexLoader.SpriteTex.RED_FIGHTER));

    }


}
