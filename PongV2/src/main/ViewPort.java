package main;

import constants.Difficulty;
import gameobjects.Ball;
import gameobjects.GameObject;
import gameobjects.Paddle;
import players.Computer;
import players.Human;
import util.PaddlePoint;
import util.Point2D;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;


public class ViewPort extends JPanel implements ActionListener, KeyListener {
    public final static int WIDTH   = 1200;
    public final static int HEIGHT  = 601;
    private final static int SCALE = 10;

    public static final int PADDLE_SPACING = 60;
    public static Difficulty difficulty = Difficulty.MEDIUM;
    public static GameObject[][] grid = new GameObject[WIDTH][HEIGHT];
    public static Human player = new Human(new Paddle(WIDTH-(PADDLE_SPACING+SCALE), HEIGHT/2));
    public static Computer computer = new Computer(new Paddle(PADDLE_SPACING, HEIGHT/2), difficulty);

    public static int startDirX = 4;
    public static int startDirY = 1;
    public static int startPosX = 100;
    public static int startPosY = 60;;

    public static Ball ball = new Ball(startPosX, startPosY, startDirX, startDirY);
    public static boolean enableTrajectory;
    public static Point2D collisionPoint = new Point2D(60, HEIGHT/2.);
    public static ArrayList<Point2D> trajectory = new ArrayList<>();
    private Timer t = new Timer(1, this);

    void run() {
        JFrame frame = new JFrame("PongV2");
        frame.getContentPane().setPreferredSize(new Dimension(WIDTH, HEIGHT));
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addKeyListener(this);
        frame.pack();
        frame.add(new ViewPort());
        frame.setVisible(true);

        for (PaddlePoint p : player.getPaddle().getPositions()) {
            grid[p.x][p.y] = player.getPaddle();
        }

        for (PaddlePoint p : computer.getPaddle().getPositions()) {
            grid[p.x][p.y] = computer.getPaddle();
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        if (enableTrajectory) {
            if (trajectory != null) {
                g.setColor(Color.GREEN);
                for (Point2D p : trajectory) {
                    g.fillRect((int) p.x, (int) p.y, 1, 1);
                }
            }
           //if (collisionPoint != null) {
           //    g.setColor(Color.RED);
           //    g.fillRect((int) collisionPoint.x, (int) collisionPoint.y, 10, 10);
           //}
        }

        g.setColor(Color.WHITE);
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                if (grid[x][y] == ball) {
                    g.fillOval(x, y, SCALE, SCALE);
                }
                if (grid[x][y] == player.getPaddle()) {
                    g.fillRect(x+SCALE, y, 5, 1);
                }
                if (grid[x][y] == computer.getPaddle()) {
                    g.fillRect(x-SCALE, y, 5, 1);
                }
            }
        }
        t.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
        computer.moveTowardsBall();
        ball.move();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_W -> {
                if (player.getPaddle().getHighestY() > 1) {
                    player.moveVertically(-10);
                }
            }
            case KeyEvent.VK_S -> {
                if (player.getPaddle().getLowestY() < HEIGHT - 2) {
                    player.moveVertically(10);
                }
            }
            case KeyEvent.VK_1 -> computer.setDifficulty(Difficulty.EASY);
            case KeyEvent.VK_2 -> computer.setDifficulty(Difficulty.MEDIUM);
            case KeyEvent.VK_3 -> computer.setDifficulty(Difficulty.HARD);
            case KeyEvent.VK_4 -> computer.setDifficulty(Difficulty.IMPOSSIBLE);
            case KeyEvent.VK_SPACE -> enableTrajectory = !enableTrajectory;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}