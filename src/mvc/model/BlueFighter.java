package mvc.model;

import images.SpriteTexLoader;

import java.awt.*;

/**
 * The Bluefighter renders and updates a bluefighter object
 * subclass of Fighter class
 *
 * @author Anqi Wu
 * @version 1.0
 * @since 2016-11-27
 */
public class BlueFighter extends Fighter {

    /**
     * Constructs a BlueFighter
     * @param initPos the position of the BlueFighter
     */
    public BlueFighter(Point initPos){
        super(initPos, SpriteTexLoader.load(SpriteTexLoader.SpriteTex.BLUE_FIGHTER));
    }


}
