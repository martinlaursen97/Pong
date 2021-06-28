package gameobjects;

import constants.Part;
import util.PaddlePoint;
import util.PointComparator;

import java.util.ArrayList;

public class Paddle extends GameObject {
    private ArrayList<PaddlePoint> positions = new ArrayList<>();
    private PaddlePoint centerPoint;

    public Paddle(int posX, int posY) {
        super(posX, posY);
        addPositions();
    }

    private void addPositions() {
        centerPoint = new PaddlePoint(posX, posY, Part.MID);
        positions.add(centerPoint);
        positions.add(new PaddlePoint(posX+1, posY, Part.MID));
        positions.add(new PaddlePoint(posX+2, posY, Part.MID));
        positions.add(new PaddlePoint(posX+3, posY, Part.MID));
        positions.add(new PaddlePoint(posX+4, posY, Part.MID));

        // MID
        for (int i = 1; i < 10; i++) {
            positions.add(new PaddlePoint(posX, posY -i, Part.MID));
            positions.add(new PaddlePoint(posX+1, posY -i, Part.MID));
            positions.add(new PaddlePoint(posX+2, posY -i, Part.MID));
            positions.add(new PaddlePoint(posX+3, posY -i, Part.MID));
            positions.add(new PaddlePoint(posX+4, posY -i, Part.MID));
        }
        // TOPMID
        for (int i = 10; i < 35; i++) {
            positions.add(new PaddlePoint(posX, posY -i, Part.TOPMID));
            positions.add(new PaddlePoint(posX+1, posY -i, Part.TOPMID));
            positions.add(new PaddlePoint(posX+2, posY -i, Part.TOPMID));
            positions.add(new PaddlePoint(posX+3, posY -i, Part.TOPMID));
            positions.add(new PaddlePoint(posX+4, posY -i, Part.TOPMID));
        }
        // TOP
        for (int i = 35; i <= 60; i++) {
            positions.add(new PaddlePoint(posX, posY -i, Part.TOP));
            positions.add(new PaddlePoint(posX+1, posY -i, Part.TOP));
            positions.add(new PaddlePoint(posX+2, posY -i, Part.TOP));
            positions.add(new PaddlePoint(posX+3, posY -i, Part.TOP));
            positions.add(new PaddlePoint(posX+4, posY -i, Part.TOP));
        }
        // MID
        for (int i = 1; i < 10; i++) {
            positions.add(new PaddlePoint(posX, posY +i, Part.MID));
            positions.add(new PaddlePoint(posX+1, posY +i, Part.MID));
            positions.add(new PaddlePoint(posX+2, posY +i, Part.MID));
            positions.add(new PaddlePoint(posX+3, posY +i, Part.MID));
            positions.add(new PaddlePoint(posX+4, posY +i, Part.MID));
        }
        // BOTTOMMID
        for (int i = 10; i < 35; i++) {
            positions.add(new PaddlePoint(posX, posY +i, Part.BOTTOMMID));
            positions.add(new PaddlePoint(posX+1, posY +i, Part.BOTTOMMID));
            positions.add(new PaddlePoint(posX+2, posY +i, Part.BOTTOMMID));
            positions.add(new PaddlePoint(posX+3, posY +i, Part.BOTTOMMID));
            positions.add(new PaddlePoint(posX+4, posY +i, Part.BOTTOMMID));
        }
        // BOTTOM
        for (int i = 35; i <= 60; i++) {
            positions.add(new PaddlePoint(posX, posY +i, Part.BOTTOM));
            positions.add(new PaddlePoint(posX+1, posY +i, Part.BOTTOM));
            positions.add(new PaddlePoint(posX+2, posY +i, Part.BOTTOM));
            positions.add(new PaddlePoint(posX+3, posY +i, Part.BOTTOM));
            positions.add(new PaddlePoint(posX+4, posY +i, Part.BOTTOM));
        }
    }

    public ArrayList<PaddlePoint> getPositions() {
        return positions;
    }

    public int getLowestY() {
        positions.sort(new PointComparator());
        return positions.get(0).y;
    }

    public int getHighestY() {
        positions.sort(new PointComparator());
        return positions.get(positions.size()-1).y;
    }

    public int getMidY() {
        return centerPoint.y;
    }
}
