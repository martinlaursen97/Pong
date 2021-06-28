package players;

import gameobjects.Paddle;
import main.ViewPort;
import util.PaddlePoint;

public abstract class Player {
    Paddle paddle;
    int points;

    public Player(Paddle paddle) {
        this.paddle = paddle;
    }

    public void moveVertically(int y) {
        for (PaddlePoint p : paddle.getPositions()) {
            ViewPort.grid[p.x][p.y] = null;
            p.translate(0, y);
        }
        for (PaddlePoint p : paddle.getPositions()) {
            ViewPort.grid[p.x][p.y] = paddle;
        }
    }

    public void moveHorizontally(int x) {
        for (PaddlePoint p : paddle.getPositions()) {
            ViewPort.grid[p.x][p.y] = null;
            p.translate(x, 0);
        }
        for (PaddlePoint p : paddle.getPositions()) {
            ViewPort.grid[p.x][p.y] = paddle;
        }
    }

    public Paddle getPaddle() {
        return paddle;
    }

    public void gainPoint() {
        points++;
    }

    public int getPoints() {
        return points;
    }
}
