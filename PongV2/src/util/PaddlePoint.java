package util;

import constants.Part;

public class PaddlePoint {
    public int x;
    public int y;
    public Part part;

    public PaddlePoint(int x, int y, Part part) {
        this.x = x;
        this.y = y;
        this.part = part;
    }

    public void translate(int x, int y) {
        this.x += x;
        this.y += y;
    }

    public void setLocation(int x, int y) {
        this.x = x;
        this.y = y;
    }
}