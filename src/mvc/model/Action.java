package mvc.model;

/**
 * The Fighter class renders action for the movable object
 *
 * @author Anqi Wu
 * @version 1.0
 * @since 2016-11-27
 */
public class Action {

    /**Option for the Action*/
    public enum Option {
        ADD, REMOVE
    }

    /**Movable object*/
    private Movable mMovable;

    /**Option*/
    private Option mOption;

    /**
     * Constructs an Action
     * @param movable movable object
     * @param op option
     */
    public Action(Movable movable, Option op) {
        this.mMovable = movable;
        this.mOption = op;
    }

    /**
     * Get the movable object
     * @return the movable object
     */
    public Movable getMovable() {
        return this.mMovable;
    }

    /**
     * Get the action option
     * @return the action option
     */
    public Option getOption() {
        return this.mOption;
    }

}
