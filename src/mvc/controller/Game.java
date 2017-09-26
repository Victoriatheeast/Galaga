package mvc.controller;

import mvc.model.Action;
import mvc.model.RedFighter;
import mvc.model.BlueFighter;
import mvc.model.BossGalaga;
import mvc.model.Bullet;
import mvc.model.Capture;
import mvc.model.Ship;
import mvc.model.Control;
import mvc.model.Movable;
import mvc.model.Fighter;


import mvc.view.GameFrame;
import sounds.Sound;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.EventQueue;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * The Main Game controller
 *
 * @author Anqi Wu
 * @version 1.0
 * @since 2016-11-27
 */
public class Game implements Runnable, KeyListener {

    /**
     * Represents the JFrame for the game
     */
    private GameFrame mGameFrame;

    /**
     * Represents the Animation delay between frames
     */
    public final static int DRAW_DELAY = 45;

    /**
     * The thread that handles the render loop for the game
     */
    private Thread mRenderThread;

    public Game() {
        this.mGameFrame = new GameFrame(this);
    }


    /**
     * Starts the thread that will handle the render loop for the game
     */
    private void startRenderLoopThread() {
        //Check to make sure the render loop thread has not begun
        if (this.mRenderThread == null) {
            //All threads that are created in java need to be passed a Runnable object.
            //In this case we are making the "Runnable Object" the actual game instance.
            this.mRenderThread = new Thread(this);
            //Start the thread
            this.mRenderThread.start();
        }
    }

    /**
     * This represents the method that will be called for a Runnable object when a thread starts.
     * In this case, this run method represents the render loop.
     */
    public void run() {

        //Make this thread a low priority such that the main thread of the Event Dispatch is always is
        //running first.
        this.mRenderThread.setPriority(Thread.MIN_PRIORITY);

        //Get the current time of rendering this frame
        long elapsedTime = System.currentTimeMillis();

        long currentTime = 0;
        long lastTime = 0;
        long deltaTime = 0;

        // this thread animates the scene
        while (Thread.currentThread() == this.mRenderThread) {

            currentTime = System.currentTimeMillis();

            if (lastTime == 0) {
                lastTime = currentTime;
                deltaTime = 0;
            } else {
                deltaTime = currentTime - lastTime;
                lastTime = currentTime;
            }


            /*
             * - Move the game models
             * - Check for collisions between the bullet, or fighters and the ship
             * - Check whether we should move to a new level potentially.
             */
            /**Get the fighters to emit*/
            getFighterBullets(deltaTime);
            /**spawn Boss Galaga*/
            spawnBossGalaga(deltaTime);
            /**spawn the capture*/
            spawnCapture(deltaTime);

            /**Detect collision*/
            detectCollision();

            /**Detect whether the game is moved to a new level*/
            moveToNewLevel();

            //Redraw the game frame with to visually show the updated game state.
            this.mGameFrame.draw();

            try {
                /** We want to ensure that the drawing time is at least the DRAW_DELAY we specified. */
                elapsedTime += DRAW_DELAY;
                Thread.sleep(Math.max(0, elapsedTime - currentTime));
            } catch (InterruptedException e) {
                //If an interrupt occurs then you can just skip this current frame.
                continue;
            }
        }
    }

    /**
     * Initialize fighters
     * @param column the number of columns in the fighter grid
     */
    private void initializeFighters(int column) {
        BlueFighter[][] blueFighters = new BlueFighter[2][column];
        RedFighter[][] redFighters = new RedFighter[2][column];
        for(int i = 0; i < 2; i++){
            for (int j = 0; j < column; j++){
                BlueFighter blue = new BlueFighter(new Point(260 + 40 * j, 100 + 50 * i));
                RedFighter red = new RedFighter(new Point(260 + 40 * j, 200 + 50 * i));
                blueFighters[i][j] = blue;
                redFighters[i][j] = red;
                Control.getInstance().getActionList().enqueue(blue, Action.Option.ADD);
                Control.getInstance().getActionList().enqueue(red, Action.Option.ADD);
            }
        }
    }

    /**
     * Spawn new capture
     * @param deltaTime elapsed time
     */
    private void spawnCapture(long deltaTime){
        if(deltaTime % 60 == 0) {
            for (Movable movable : Control.getInstance().getMovEnemies()) {
                if (movable instanceof BossGalaga) {
                    Control.getInstance().getActionList().enqueue(new Capture((BossGalaga) movable), Action.Option.ADD);
                }

            }
        }
    }


    /**
     * Spawn new Boss Galaga
     * @param deltaTime elapsed time
     */
    private void spawnBossGalaga(long deltaTime) {
        if(deltaTime % 60 == 0) {
            BossGalaga b1 = new BossGalaga(new Point(300, 40));
            BossGalaga b2 = new BossGalaga(new Point(800,40));
            Control.getInstance().getActionList().enqueue(b1, Action.Option.ADD);
            Control.getInstance().getActionList().enqueue(b2, Action.Option.ADD);
        }
    }

    /**
     * The fighers get the bullets
     * @param deltaTime elapsed time
     */
    private void getFighterBullets(long deltaTime){
        if(deltaTime % 40 == 0) {
            for (Movable movable : Control.getInstance().getMovEnemies()) {
                if (movable instanceof RedFighter) {
                    RedFighter rf = (RedFighter)movable;
                        Control.getInstance().getActionList().enqueue(new Bullet(rf), Action.Option.ADD);
                }
            }
        }
    }

    /**
     * Detect collision
     */
    private void detectCollision(){

        Rectangle rFriend, rEnemy;

        for(Movable friend : Control.getInstance().getMovFriends()){
            for (Movable enemy: Control.getInstance().getMovEnemies()){
                rFriend = friend.getBounds();
                rEnemy = enemy.getBounds();

                if(rFriend.intersects(rEnemy)){

                    if (friend instanceof Ship) {
                        Control.getInstance().getActionList().enqueue(friend, Action.Option.REMOVE);
                        Control.getInstance().launchShip(false);
                    } else {

                        Control.getInstance().getActionList().enqueue(friend, Action.Option.REMOVE);
                    }
                    Sound.playSoundEffect(Sound.SoundEffect.SHIP_EXPLOSION);


                    if (enemy instanceof Fighter) {

                        Control.getInstance().addScore(1);
                        Sound.playSoundEffect(Sound.SoundEffect.ENEMY_KILLED);

                        Control.getInstance().getActionList().enqueue(enemy, Action.Option.REMOVE);
                        //spawn new enemies here
                        int r = (int)Math.random() * GameFrame.FRAME_DIM.width;
                        Control.getInstance().getActionList().enqueue(new RedFighter(new Point(r, 300)), Action.Option.ADD);

                    }
                }
            }
        }


        /**
         * Take action either to add or remove from the list after collision
         */
        while(!Control.getInstance().getActionList().isEmpty()){
            Action act =  Control.getInstance().getActionList().dequeue();
            Movable movable = act.getMovable();
            Action.Option option = act.getOption();

            switch (movable.getPlayer()){
                case ENEMY:
                    if (option == Action.Option.ADD){
                        Control.getInstance().getMovEnemies().add(movable);
                    } else {
                        Control.getInstance().getMovEnemies().remove(movable);
                    }

                    break;
                case FRIEND:
                    if (option == Action.Option.ADD){
                        Control.getInstance().getMovFriends().add(movable);
                    } else {
                        Control.getInstance().getMovFriends().remove(movable);
                    }
                    break;
            }
        }
        System.gc();

    }

    /**
     * If there are fewer than 10 fighters on screen we can move to a new level
     * @return
     */

    private boolean isLevelAlmostClear(){
        boolean bFightersClear = true;
        for (Movable movable : Control.getInstance().getMovEnemies()) {
            if (movable instanceof Fighter) {
                bFightersClear = false;
                break;
            }
        }
        return bFightersClear;

    }


    /**
     * Move to new level
     */
    private void moveToNewLevel(){

        if (Control.getInstance().isPlaying() && isLevelAlmostClear()){
            if (Control.getInstance().getShip() !=null)
            Control.getInstance().setLevel(Control.getInstance().getLevel() + 1);
            Control.getInstance().clearAll();
            initializeFighters(Control.getInstance().getLevel()+1);
            Sound.playSoundEffect(Sound.SoundEffect.LEVEL_START);
        }
    }


    /**
     * Start the game
     */
    private void startGame() {
        Control.getInstance().clearAll();
        Control.getInstance().initGame();
        Control.getInstance().setLevel(0);
        initializeFighters(2);
        Control.getInstance().setPlaying(true);
        Control.getInstance().setPaused(false);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        Ship ship = Control.getInstance().getShip();
        int nKey = e.getKeyCode();
        if (nKey == KeyEvent.VK_S && !Control.getInstance().isPlaying())
            startGame();

        if(ship!= null) {
            switch (nKey) {
                case KeyEvent.VK_LEFT:
                    ship.moveLeft();
                    break;
                case KeyEvent.VK_RIGHT:
                    ship.moveRight();
                    break;
                case KeyEvent.VK_SPACE:
                    Control.getInstance().getActionList().enqueue(new Bullet(ship), Action.Option.ADD);
                    Sound.playSoundEffect(Sound.SoundEffect.FIRE_BULLET);
                    break;
                case KeyEvent.VK_Y:
                    Control.getInstance().addScoreToList();
                default:
                    System.out.println("Pressing the key: " + KeyEvent.getKeyText(nKey));
                    break;
            }
        }
    }



    public void keyReleased(KeyEvent e) {
        Ship ship = Control.getInstance().getShip();

        int nKey = e.getKeyCode();

        if(ship != null){
            switch(nKey){
                case KeyEvent.VK_LEFT:
                    ship.stop();
                    break;
                case KeyEvent.VK_RIGHT:
                    ship.stop();
                    break;
                default:
                    System.out.println("Releasing the key:" + KeyEvent.getKeyText(nKey));
                    break;
            }
        }
    }

    public void keyTyped(KeyEvent e) {}

    public static void main(String args[]){

        EventQueue.invokeLater(new Runnable() { // uses the Event dispatch thread from Java 5 (refactored)
            public void run() {
                try {
                    //Construct the game controller
                    Game game = new Game();

                    //Start the render loop for the game
                    game.startRenderLoopThread();

                } catch (Exception e) {
                    e.printStackTrace();

                }
            }
        });
    }

}
