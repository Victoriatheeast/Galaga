package mvc.model;

import java.util.LinkedList;

import java.util.concurrent.locks.ReentrantLock;
/**
 * The ActionList class renders and modifies an action list object
 *
 * @author Anqi Wu
 * @version 1.0
 * @since 2016-11-27
 */
public class ActionList extends LinkedList {

    /**The renentrant lock*/
    private ReentrantLock lock;

    /**
     * Constructs and action list
     */
    public ActionList() {
        this.lock =   new ReentrantLock();
    }

    /**
     * Add a movable object with specified action option
     * @param mov movable object
     * @param opt action option
     */
    public void enqueue(Movable mov, Action.Option opt) {
        try {
            lock.lock();
            addLast(new Action(mov, opt));
        } finally {
            lock.unlock();
        }
    }


    /**
     * Get the action of which the object is removed
     * @return the action which the object is removed
     */
    public Action dequeue() {
        try {
            lock.lock();
            return (Action) super.removeFirst();
        } finally {
            lock.unlock();
        }

    }
}
