import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import static java.lang.Math.*;

public class Manipulator {
    public void bind(int k, ArrayList<Cluster> clusterAr, ArrayList<Point> pointAr) { //привязка точек к центру

        for (int j = 0; j < k; ++j)
            clusterAr.get(j).points.clear(); // Чистим кластер перед использованием
        int size = pointAr.size();
        for (Point point : pointAr) { // Запускаем цикл по всем пикселям множества
            double min = pow(clusterAr.get(0).curX - point.x, 2)
                    + pow(clusterAr.get(0).curY - point.y, 2);  //вычисляем расстояние между и-той точкой и центром нулевого кластера
            Cluster firstCluster = clusterAr.get(0);
            for (int j = 1; j < k; j++) {
                double tmp = pow(clusterAr.get(j).curX - point.x, 2)
                        + pow(clusterAr.get(j).curY - point.y, 2); //вычисляем для всех остальных
                if (tmp < min) { // Ищем близлежащий кластер
                    min = tmp;
                    firstCluster = clusterAr.get(j);
                }
            }
            firstCluster.addPoint(point); // Добавляем в близ лежащий кластер текущий пиксель
        }


    }

    public void initialCenter(int k, ArrayList<Cluster> clusterAr, ArrayList<Point> pointAr) {

        int size = pointAr.size();
        int step = size / k;
        int steper = -step;

        for (int i = 0; i < k; i++) {
            steper += step;
            clusterAr.get(i).curX = pointAr.get(steper).x;
            clusterAr.get(i).curY = pointAr.get(steper).y;
        }
    }

    public int start(int k, ArrayList<Cluster> clusterAr, ArrayList<Point> pointAr) {
        initialCenter(k, clusterAr, pointAr);
        int i;
        for (i = 0; i < 30; ++i) { // Запускаем основной цикл
            int chk = 0;
            bind(k, clusterAr, pointAr); // Связываем точки с кластерами
            for (int j = 0; j < k; ++j) // Высчитываем новые координаты центроидов
                clusterAr.get(j).setCenter();
            for (int p = 0; p < k; ++p) { // Проверяем не совпадают ли они с предыдущими цент-ми
                if (clusterAr.get(p).curX == clusterAr.get(p).lastX && clusterAr.get(p).curY == clusterAr.get(p).lastY)
                    ++chk;
            }
            if (chk == k) break;// Если да выходим с цикла
        }
        return i;
    }

    public void kmeans(ArrayList<Point> points, int k) throws IOException {
        FileWriter frObjects = new FileWriter("src/main/resources/objectID.txt");
        FileWriter frClusters = new FileWriter("src/main/resources/clusterID.txt");
        ArrayList<Cluster> ptr= new ArrayList<Cluster>();
        for (int j = 0; j < k; j++) {
            ptr.add(new Cluster());
        }
        start(k, ptr, points);

        //int m = 0;
        for (int i = 0; i < k; ++i) {
            int s = ptr.get(i).points.size();
            for (int j = 0; j < s; ++j) {
                ptr.get(i).points.get(j).setClusterID(i+1);
                frObjects.write(ptr.get(i).points.get(j).getId() + " " + (i+1) + "\n");
            }
            frClusters.write((i+1) + " " + ptr.get(i).curX + " " + ptr.get(i).curY + "\n") ;
        }
        frClusters.close();
        frObjects.close();
    }

    public void elbow_method(ArrayList<Point> points, int kMin, int kMax) {

        double [] WSS =  new double[kMax];
        for (int k = kMin; k < kMax + 1; ++k) {
            ArrayList<Cluster> ptr= new ArrayList<Cluster>();
            for (int j = 0; j < k; j++) {
                ptr.add(new Cluster());
            }
            start(k, ptr, points);

            double current_sse = 0;

            for (int i = 0; i < k; ++i) {
                int s = ptr.get(i).points.size();
                for (int j = 0; j < s; ++j) {
                    double x = ptr.get(i).points.get(j).x;
                    double y = ptr.get(i).points.get(j).y;
                    current_sse += pow((double) (x - ptr.get(i).curX), 2)
                            + pow((double) (y - ptr.get(i).curY), 2);
                }
            }
            WSS[k - 1] = current_sse;
        }

        for (int i = 0; i < kMax; ++i) {
            System.out.println(WSS[i]);
        }

    }

    public int silhouetteMethod(ArrayList <Point> points, int kMin , int kMax) {
        ArrayList<Double> ss = new ArrayList<Double>();
        double max = Double.MAX_VALUE;
        for (int k = kMin; k < kMax+1; k++) {
            ArrayList<Cluster> ptr= new ArrayList<Cluster>();
            for (int j = 0; j < k; j++) {
                ptr.add(new Cluster());
            }
            start(k, ptr, points);
            double s = 0;
            for(int i = 0; i < k; i++) {  //проходимся по клатсерам
                int amountOfPoints = ptr.get(i).points.size();
                for(int j = 0; j < amountOfPoints; j++) { //проходимся по точкам в каждом кластере
                    double a = 0;
                    double b = 0;
                    double bMin = -10;

                    for (int l = 0; l < k; l++) {
                        int amountOfPoints2 = ptr.get(l).points.size();
                        if (i == l) {
                            for (int m = 0; m < amountOfPoints2 ; m++) {
                                a += sqrt(pow(ptr.get(l).points.get(m).x - ptr.get(l).points.get(j).x, 2) +
                                        pow(ptr.get(l).points.get(m).y - ptr.get(l).points.get(j).y, 2));
                            }
                            if (amountOfPoints != 1) {
                                a /= amountOfPoints - 1;
                            }
                        } else {
                            for (int m = 0; m < amountOfPoints2; m++) {
                                b += sqrt(pow(ptr.get(l).points.get(m).x - ptr.get(i).points.get(j).x, 2) +
                                        pow(ptr.get(l).points.get(m).y - ptr.get(i).points.get(j).y, 2));
                            }
                            b = amountOfPoints2 == 0 ? 0 : b / ((double) amountOfPoints2);
                            bMin = bMin == -10 ? b : min(bMin, b);
                        }

                    }
                    s += max(bMin, a) == 0 ? 0 : (bMin - a) / max(bMin, a);
                }
            }
            s /= points.size();
            ss.add(s); //  =-)))
            System.out.println(k + " " + s);

        }
        double maxS = -2;
        for (Double s : ss) {
            maxS = max(s, maxS);
        }
        return ss.indexOf(maxS) + kMin;
    }

}

