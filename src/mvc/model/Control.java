package mvc.model;

import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Scanner;
import java.io.File;

import sounds.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *  The Control class renders and updates many facets of the game
 *
 * @author Anqi Wu
 * @version 1.0
 * @since 2016-11-27
 */
public class Control {

    /**The number of ships available*/
    private int nNumShip;

    /**The level of the game*/
    private int nLevel;

    /**The scores of the game*/
    private int nScore;

    /**The score list*/
    private ArrayList<Integer> scores = new ArrayList<Integer>();

    /**The ship for the user*/
    private Ship ship;

    /**Whether the game is in play*/
    private boolean bPlaying;

    /**Whether the game is paused*/
    private boolean bPaused;

    /**Whether the user has won*/
    private boolean bWin;

    /**Whether game is Over*/
    private boolean isOver;

    /**List of movable objects Friends and enemies*/
    private List<Movable> movFriends = new ArrayList<Movable>(100);
    private List<Movable> movEnemies = new ArrayList<Movable>(200);

    /**ActionList*/
    private ActionList actionList = new ActionList();

    /**Set default instance to be null*/
    public static Control instance = null;

    /**Constructs a control*/
    private Control(){
    }

    /**Get the instance of the control*/
    public static Control getInstance(){
        if(instance == null){
            instance = new Control();
        }
        return instance;
    }

    /**Initialize the game*/
    public void initGame(){
        setLevel(1);
        setScore(0);
        setNumShips(3);
        launchShip(true);

    }

    /**Decide whether to launch a new ship*/
    public void launchShip(Boolean bFirst){
        if (getNumShips() != 0) {
            this.ship = new Ship();
            actionList.enqueue(this.ship,Action.Option.ADD);
            if (!bFirst) setNumShips(getNumShips() - 1);
        }
        Sound.playSoundEffect(Sound.SoundEffect.THEME_SONG);
    }


    /**Determine whether the game is over*/
    public  boolean isGameOver() {
        if (getNumShips() == 0 || this.bWin == true) {
            return true;
        }
        return false;
    }

    /**Get the number of ships*/
    public int getNumShips() {

        return this.nNumShip;
    }

    /**Set the number of ships*/
    public  void setNumShips(int n) {
        this.nNumShip = n;
    }

    public Ship getShip(){
        return this.ship;
    }

    public  void setFalcon(Ship ship){
        this.ship = ship;
    }

    public  int getLevel() {
        return nLevel;
    }

    public int getScore() {
        return this.nScore;
    }

    public  void setScore(int Score) {
        this.nScore = Score;
    }

    public void addScore(int Score){
        this.nScore += Score;
    }

    public  void setLevel(int n) {
        this.nLevel = n;
    }

    public  boolean isPlaying() {
        return bPlaying;
    }

    public  void setPlaying(boolean bPlaying) {
        this.bPlaying = bPlaying;
    }

    public  boolean isOver() {
        return isOver;
    }

    public void setOver(boolean over){this.isOver = over;}

    public  boolean isPaused() {
        return bPaused;
    }

    public boolean getPaused(){
        return this.bPaused;
    }

    public  void setPaused(boolean paused) {
        this.bPaused = paused;
    }

    public  List<Movable> getMovFriends() {
        return this.movFriends;
    }

    public  List<Movable> getMovEnemies() {
        return this.movEnemies;
    }

    public ActionList getActionList(){
        return this.actionList;
    }

    public void setActionList(ActionList list){
        this.actionList = list;
    }

    public  void clearAll(){
        this.movFriends.clear();
        this.movEnemies.clear();
    }

    public void setWin(){
        if (this.nLevel >= 3){
            this.bWin = true;
        }
    }
    public boolean isWin(){
        return this.bWin;
    }

    public ArrayList<Integer> getScoreList(){
        return this.scores;
    }

    /**
     * Output the score to the file
     */
    public void addScoreToList() {
        InputStream input = Control.class.getClassLoader().getResourceAsStream("output.txt");
        int score = getScore();
        int count = 0;
        try {
            Scanner sc = new Scanner(input);
            while(sc.hasNextInt() && count < 9){
                count++;
                int current = sc.nextInt();
                this.scores.add(current);
            }
            this.scores.add(score);
            Collections.sort(this.scores);
            Collections.reverse(this.scores);
            //Add absolute path to the following
            File file = new File("/Galaga/src/output.txt");
            PrintWriter pr = new PrintWriter(file);
            for(int i = 0; i < this.scores.size(); i++) {
                int current = this.scores.get(i);
                pr.print(current + "\n");
            }
            pr.close();

        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
