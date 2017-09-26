package mvc.view;

import images.SpriteTexLoader;

import mvc.model.*;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * The GamePanel
 *
 * @author Anqi Wu
 * @version 1.0
 * @since 2016-11-27
 */
public class GamePanel extends JPanel {


    public GamePanel() {

        this.setPreferredSize(GameFrame.FRAME_DIM);

    }

    /**
     * Draw the score of the game
     * @param g2 Graphics2D
     */
    private void drawScore(Graphics2D g2) {

        g2.setColor(Color.white);
        g2.setFont(new Font("Arial", Font.BOLD, 12));
        if (Control.getInstance().getScore() != 0) {
            g2.drawString("Score :  " + Control.getInstance().getScore(), 40, 20);
        } else {
            g2.drawString("Score : 0", 40, 20);
        }
    }

    /**
     * Draw the level of the game is in
     * @param g2 Graphics2D
     */
    private void drawLevel(Graphics2D g2) {

        g2.setColor(Color.white);
        g2.setFont(new Font("Arial", Font.BOLD, 12));
        g2.drawString("Level :  " + (Control.getInstance().getLevel() + 1), 1000, 20);

    }

    /**
     * Draw the number of ships left
     * @param g2 Graphics2D
     */
    private void drawNumberShipsLeft(Graphics2D g2) {
        Ship ship = Control.getInstance().getShip();
        int n = Control.getInstance().getNumShips();
        if (n == 3) {
            g2.drawImage(SpriteTexLoader.load(SpriteTexLoader.SpriteTex.SHIP), 50, 800,
                    (int) ship.getDimension().getWidth(), (int) ship.getDimension().getHeight(), null);
            g2.drawImage(SpriteTexLoader.load(SpriteTexLoader.SpriteTex.SHIP), 80, 800,
                    (int) ship.getDimension().getWidth(), (int) ship.getDimension().getHeight(), null);
        }
        if (n == 2) {
            g2.drawImage(SpriteTexLoader.load(SpriteTexLoader.SpriteTex.SHIP), 80, 800,
                    (int) ship.getDimension().getWidth(), (int) ship.getDimension().getHeight(), null);
        }
    }

    /**
     * Draw main menu
     */
    private void drawMainMenu(Graphics2D g2) {

        g2.drawImage(SpriteTexLoader.load(SpriteTexLoader.SpriteTex.GALAGA), 300, 100, GameFrame.FRAME_DIM.width / 2,
                GameFrame.FRAME_DIM.height / 4, null);
        g2.setColor(Color.red);
        g2.setFont(new Font("Verdana", Font.BOLD, 40));
        g2.drawString("Press S to Start", 400, 400);
        g2.setColor(Color.red);
        g2.setFont(new Font("SansSerif", Font.BOLD, 20));
        g2.drawString("Top Ten Scores:", 300, 450);
    }


   /**Draw the panel when the game is over*/
    private void gameOver(Graphics2D g2) {
        g2.setColor(Color.white);
        g2.setFont(new Font("Verdana", Font.BOLD, 40));
        g2.drawString("GAME OVER",
                GameFrame.FRAME_DIM.width / 3 + 50, GameFrame.FRAME_DIM.height / 3);
        g2.drawString("Your Score:" + Control.getInstance().getScore(),
                GameFrame.FRAME_DIM.width / 3 + 50, GameFrame.FRAME_DIM.height / 2);
        g2.drawString("Press Y to Send Your Score",
                1* GameFrame.FRAME_DIM.width / 4 + 50,2* GameFrame.FRAME_DIM.height / 3);


    }

    /**Iterate through all the movable objects*/
    private void iterateMovables(Graphics g, ArrayList<Movable>... movableMovz) {

        for (ArrayList<Movable> movMovs : movableMovz) {
            for (Movable mov : movMovs) {

                mov.move();
                mov.draw(g);

            }
        }

    }

    /**
     * Paint the panel
     * @param g
     */
    @Override
    public void paintComponent(Graphics g) {
        // Call the super paintComponent of the panel
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        //Draw a black background with space
        g.setColor(Color.black);
        g.fillRect(0, 0, GameFrame.FRAME_DIM.width, GameFrame.FRAME_DIM.height);
        g2.drawImage(SpriteTexLoader.load(SpriteTexLoader.SpriteTex.SPACE_BACKGROUND), 100, 0,
                GameFrame.FRAME_DIM.width - 200, GameFrame.FRAME_DIM.height, null);

        if (Control.getInstance().isPlaying() == false && Control.getInstance().isOver() == false) {
            drawMainMenu(g2);
            drawTopTen(g2);
        } else if (Control.getInstance().isGameOver() == true || Control.getInstance().isWin()) {
            Control.getInstance().setOver(true);
            Control.getInstance().setPlaying(false);
            gameOver(g2);


        } else {

            drawScore(g2);
            drawLevel(g2);
            drawNumberShipsLeft(g2);


            iterateMovables(g, (ArrayList<Movable>) Control.getInstance().getMovEnemies(),
                    (ArrayList<Movable>) Control.getInstance().getMovFriends());

        }
    }

    /**
     * Read the file to draw the top ten scores
     * @param g2 Graphics2D
     */
    private void drawTopTen(Graphics2D g2) {
        InputStream input = GamePanel.class.getClassLoader().getResourceAsStream("output.txt");

        Scanner sc = new Scanner(input);
        int i = 0;
        while (sc.hasNextLine()) {
            try {
                String s = sc.nextLine();
                g2.drawString(s, 600, 450 + 30 * i);
                i++;
            } catch (NumberFormatException e) {
                System.out.println(e.getMessage());
            }
        }
        sc.close();
    }
}












