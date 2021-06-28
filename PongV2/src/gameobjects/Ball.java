package gameobjects;

import constants.Part;
import util.PaddlePoint;
import util.Point2D;
import util.Vector2D;
import main.ViewPort;

import java.util.Objects;

public class Ball extends GameObject{
    private double dirX;
    private double dirY;
    private int velocity;
    private int collideCounter;
    private final int GAP = 2;

    public Ball(int posX, int posY, int dirX, int dirY) {
        super(posX, posY);
        this.dirX = dirX;
        this.dirY = dirY;
        velocity = (int) Math.sqrt(dirX*dirX + dirY*dirY);
        collideCounter = velocity + 1;
    }

    public void move() {
        ViewPort.grid[posX][posY] = null;

        // is either the point before next collision, or just the next point if no collision
        Point2D contact = collisionContact();

        if (isCorner((int) contact.x, (int) contact.y)) {
            dirX = -dirX;
            dirY = -dirY;
        }
        else if (contact.x > ViewPort.WIDTH-GAP) {
            reset(true);
            ViewPort.collisionPoint = calculateCollision();
        }
        else if (contact.x < GAP) {
            reset(false);
        }
        else if (contact.y > ViewPort.HEIGHT- GAP) {
            dirY = -dirY;
        }
        else if (contact.y < GAP) {
            dirY = -dirY;
        }
        else if (contact.paddleCollision) {

            double newDirX = 0;
            double newDirY = 0;

            if (contact.part.equals(Part.TOP)) {
                newDirX = 5;
                newDirY = -5;
            }
            else if (contact.part.equals(Part.TOPMID)) {
                newDirX = 8;
                newDirY = -5;
            }
            else if (contact.part.equals(Part.MID)) {
                newDirX = 1;
                newDirY = 0;
            }
            else if (contact.part.equals(Part.BOTTOMMID)) {
                newDirX = 8;
                newDirY = 5;
            }
            else if (contact.part.equals(Part.BOTTOM)) {
                newDirX = 5;
                newDirY = 5;
            }

            double len = Math.sqrt(newDirX*newDirX + newDirY*newDirY);
            dirX = newDirX * velocity/len;
            dirY = newDirY * velocity/len;

            if (posX < (ViewPort.WIDTH/2)-10) {
                dirX = Math.abs(dirX);
            } else {
                dirX = -Math.abs(dirX);
            }

            speedUp();

            if (ViewPort.grid[(int) contact.x][(int) contact.y] == ViewPort.player.getPaddle()) {
                ViewPort.collisionPoint = calculateCollision();
                ViewPort.computer.hitWithRandomPart();
            }
            else if (ViewPort.grid[(int) contact.x][(int) contact.y] == ViewPort.computer.getPaddle()) {
                ViewPort.computer.setN(0);
                ViewPort.collisionPoint.setLocation(60, ViewPort.HEIGHT/2);
            }

            posX = (int) contact.x;
            posY = (int) contact.y;

        }

        posX += dirX;
        posY += dirY;

        ViewPort.grid[posX][posY] = ViewPort.ball;
    }

    // calculates where the next collision will be, based on trajectory of the ball
    public Point2D calculateCollision() {
        ViewPort.trajectory.clear();
        double len = Math.sqrt((dirX*dirX) + (dirY*dirY));
        Vector2D normalizedDir = new Vector2D(dirX/len, dirY/len);
        double x = posX + normalizedDir.x;
        double y = posY + normalizedDir.y;

        while (x > ViewPort.PADDLE_SPACING) {
            if (y < 10) {
                normalizedDir.y = -normalizedDir.y;
            }
            if (y > ViewPort.HEIGHT- GAP) {
                normalizedDir.y = -normalizedDir.y;
            }
            x += normalizedDir.x;
            y += normalizedDir.y;
            ViewPort.trajectory.add(new Point2D(x, y));
        }
        return new Point2D(x, y);
    }

    public void reset(boolean winner) {
        if (winner) {
            posX = ViewPort.WIDTH - ViewPort.startPosX;
            dirX = -ViewPort.startDirX;
            ViewPort.computer.gainPoint();
        } else {
            posX = ViewPort.startPosX;
            dirX = ViewPort.startDirX;
            ViewPort.player.gainPoint();
        }

        dirY = ViewPort.startDirY;
        posY = ViewPort.startPosY;
        velocity = (int) Math.sqrt(dirX*dirX + dirY*dirY);
        collideCounter = velocity + 1;
        ViewPort.collisionPoint.setLocation(60, ViewPort.HEIGHT/2);

        System.out.println(ViewPort.computer.getPoints() + " - " + ViewPort.player.getPoints() + " #" + velocity);
    }

    private void speedUp() {
        dirX *= (double) collideCounter / velocity;
        dirY *= (double) collideCounter / velocity;
        dirX = Math.ceil(dirX);
        dirY = Math.ceil(dirY);
        velocity = collideCounter;
        collideCounter++;
    }

    private boolean nextPosIsRoof(int y) {
        return y < GAP;
    }

    private boolean nextPosIsFloor(int y) {
        return y > ViewPort.HEIGHT- GAP;
    }

    // return true if the next position is a paddle
    private boolean nextPosIsPaddle(int x, int y) {
        return x > ViewPort.PADDLE_SPACING-10 && x < ViewPort.WIDTH-ViewPort.PADDLE_SPACING+10 && ViewPort.grid[x][y] instanceof Paddle;
    }

    private boolean isCorner(int x, int y) {
        return x == GAP && y == GAP || x == ViewPort.WIDTH-GAP && y == GAP || x == GAP && y == ViewPort.HEIGHT- GAP || x == ViewPort.WIDTH- GAP && y == ViewPort.HEIGHT- GAP;
    }

    // return the point before next collision
    private Point2D collisionContact() {
        double len = Math.sqrt((dirX*dirX) + (dirY*dirY));
        Vector2D normalizedDir = new Vector2D(dirX/len, dirY/len);
        double x = posX;
        double y = posY;

        for (double i = 0; i < velocity; i++) {
            x += normalizedDir.x;
            y += normalizedDir.y;
            try {
                if (nextPosIsPaddle((int) x, (int) y)) {
                    return new Point2D(x, y, true, part((int) x, (int) y));
                }
            } catch (ArrayIndexOutOfBoundsException ignore) {
            }
            if (nextPosIsRoof((int) y)) {
                return new Point2D(x, y);
            }
            if (nextPosIsFloor((int) y)) {
                return new Point2D(x-normalizedDir.x, y-normalizedDir.y);
            }
        }
        return new Point2D(x, y);
    }

    private Part part(int x, int y) {
        if (ViewPort.grid[x][y] instanceof Paddle) {
            return Objects.requireNonNull(findPoint(x, y)).part;
        }
        return null;
    }

    private PaddlePoint findPoint(int x, int y) {
        for (PaddlePoint p : ViewPort.player.getPaddle().getPositions()) {
            if (p.x == x && p.y == y) {
                return p;
            }
        }
        for (PaddlePoint p : ViewPort.computer.getPaddle().getPositions()) {
            if (p.x == x && p.y == y) {
                return p;
            }
        }
        return null;
    }
}
