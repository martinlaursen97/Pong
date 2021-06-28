package util;

public class Vector2D {
    public double x;
    public double y;
    public boolean paddleCollision;

    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector2D(double x, double y, boolean flag) {
        this.x = x;
        this.y = y;
        this.paddleCollision = flag;
    }
}
