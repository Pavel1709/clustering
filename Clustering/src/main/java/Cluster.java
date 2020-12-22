
import java.util.ArrayList;

public class Cluster {
    ArrayList<Point> points = new ArrayList<Point>();
    double curX , curY; // координаты текущего центроида
    double lastX, lastY; // координаты предыдущего центроида

    public int Size() {
        return points.size();
    }
    public void addPoint(Point point) {
        points.add(point);
    }


    public void setCenter() {
        double sumX = 0, sumY = 0;
        int size = Size();
        for (int i = 0; i < size;++i) {
            sumX += points.get(i).x;
        }
        for (int i = 0; i < size; ++i){
            sumY += points.get(i).y;
        }
        lastX = curX;
        lastY = curY;
        curX = sumX / size;
        curY = sumY / size;

    }
    public void clear() {
        points = new ArrayList<Point>();
    }
}

