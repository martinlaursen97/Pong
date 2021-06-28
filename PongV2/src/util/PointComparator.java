package util;

import java.awt.*;
import java.util.Comparator;

public class PointComparator implements Comparator<PaddlePoint> {
    @Override
    public int compare(PaddlePoint o1, PaddlePoint o2) {
        return o2.y - o1.y;
    }
}