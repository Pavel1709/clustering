
import java.io.*;
import java.util.*;


 public class Clustering {

    static ArrayList<Point> points = new ArrayList<Point>();

    public static void main(String[] args) throws IOException {
        String token1 = "";
        Scanner inFile1 = new Scanner(new File("src/main/resources/OUTPUT.txt"));
        // while loop
        while (inFile1.hasNext()) {
            token1 = inFile1.nextLine();
            points.add(new Point(Double.parseDouble(token1.split(" ")[0]),Double.parseDouble(token1.split(" ")[1]), 0 ));
        }

        Manipulator m = new Manipulator();
        m.elbow_method(points, 2, 30);
        int k = m.silhouetteMethod(points, 2, 30);
        m.kmeans(points, k);
    }
}




