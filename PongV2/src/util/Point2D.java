package util;

import constants.Part;

public class Point2D {
    public double x;
    public double y;
    public boolean paddleCollision;
    public Part part;

    public Point2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Point2D(double x, double y, boolean flag, Part part) {
        this.x = x;
        this.y = y;
        this.paddleCollision = flag;
        this.part = part;
    }

    public void setLocation(int x, int y) {
        this.x = x;
        this.y = y;
    }
}