public class Point {
    static int counter = 0;
    int id = 0;
    double x;
    double y;
    int clusterID;

    public Point(double x, double y, int clusterID) {
        this.id = ++counter;
        this.x = x;
        this.y = y;
        this.clusterID = clusterID;
    }

    public int getId() {
        return id;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int getClusterID() {
        return clusterID;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setClusterID(int clusterID) {
        this.clusterID = clusterID;
    }
}
