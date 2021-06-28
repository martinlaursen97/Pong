package players;

import constants.Difficulty;
import gameobjects.Paddle;
import main.ViewPort;
import java.util.Random;

public class Computer extends Player {
    private Difficulty difficulty;
    private int n = 0;

    public Computer(Paddle paddle, Difficulty difficulty) {
        super(paddle);
        this.difficulty = difficulty;
    }

    public void moveTowardsBall() {
        if (!onTarget()) {
            if (paddle.getMidY() > ViewPort.collisionPoint.y+n) {
                if (paddle.getHighestY()-difficultySpeed() > 0) {
                    moveVertically(-difficultySpeed());
                } else {
                    if (paddle.getHighestY()-1 > 0) {
                        moveVertically(-1);
                    }
                }
            } else {
                if (paddle.getLowestY()+difficultySpeed() < ViewPort.HEIGHT-1) {
                    if (!(paddle.getLowestY()+1 < ViewPort.HEIGHT-1)) {
                        moveVertically(1);
                    }
                    else {
                        moveVertically(difficultySpeed());
                    }
                }
            }
        }
    }

    public void hitWithRandomPart() {
        int r = new Random().nextInt(5);
        switch (r) {
             case 0 -> n = -45;
             case 1 -> n = -25;
             case 2 -> n = 0;
             case 3 -> n = +25;
             case 4 -> n = +45;
        }
    }

    public void setN(int n) {
        this.n = n;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    private boolean onTarget() {
        return inRange((int) ViewPort.collisionPoint.y-3, (int) ViewPort.collisionPoint.y+3, paddle.getMidY()+n)
                || paddle.getMidY() == (int) ViewPort.collisionPoint.y;
    }

    private boolean inRange(int r1, int r2, int a) {
        return a >= r1 && a <= r2;
    }

    private int difficultySpeed() {
        switch (difficulty) {
            case EASY -> {
                return 1;
            }
            case MEDIUM -> {
                return 2;
            }
            case HARD -> {
                return 5;
            }
            case IMPOSSIBLE -> {
                return 10;
            }
        }
        return 1;
    }
}
