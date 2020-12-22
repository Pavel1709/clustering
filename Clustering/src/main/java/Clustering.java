import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaSparkContext;

// $example on$
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.mllib.clustering.KMeans;
import org.apache.spark.mllib.clustering.KMeansModel;
import org.apache.spark.mllib.linalg.Vector;
import org.apache.spark.mllib.linalg.Vectors;
import org.apache.spark.sql.Row;
import scala.Tuple2;
import scala.collection.mutable.WrappedArray;
import scala.runtime.BoxedUnit;

import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Double.NaN;

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




