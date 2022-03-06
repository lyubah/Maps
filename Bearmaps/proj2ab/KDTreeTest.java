package bearmaps;

import org.junit.Test;

import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

public class KDTreeTest {

    /**
     * @source: Joshua Hug ( I based the add method of my code of his implementation)
     * @Link: https://www.youtube.com/watch?v=irkJ4gczM0I
     */


    @Test
    public void nearestClassDemo() {
        Point p1 = new Point(2, 3);
        Point p2 = new Point(4, 2);
        Point p3 = new Point(4, 2);
        Point p4 = new Point(4, 5);
        Point p5 = new Point(3, 3);
        Point p6 = new Point(1, 5);
        Point p7 = new Point(4, 4);
        KDTree kd = new KDTree(List.of(p1, p2, p3, p4, p5, p6, p7));
        Point goal = new Point(1, 5);
        Point actual = kd.nearest(0, 7);
        assertEquals(goal, actual);
    }

    @Test
    public void randomizedTest() {
        List<Point> points = new ArrayList<>();
        Random rand = new Random();
        rand.setSeed(41);
        System.out.println("Starting test");
        for (int i = 0; i < 100000; i++) {
            double x = rand.nextDouble();
            double y = rand.nextDouble();
            points.add(new Point(x, y));
        }
        System.out.println("finished creating random points");
        NaivePointSet naive = new NaivePointSet(points);
        System.out.println("added points to naive");
        KDTree kdt = new KDTree(points);
        System.out.println("added points to kd tree");
        for (int i = 0; i < 100000; i++) {
//            System.out.println(i);
            double testX = rand.nextDouble();
            double testsY = rand.nextDouble();
            Point kdtBest = kdt.nearest(testX, testsY);
//            Point naiveBest = naive.nearest(testX, testsY);
//            assertEquals(naiveBest, kdtBest);
        }
        System.out.println("finished testing");
    }

    private static Random R = new Random(500);



    private Point randomPoint() {
        double x = R.nextDouble();
        double y = R.nextDouble();
        return new Point(x,y);
    }


    private List<Point> randomPoints(int N) {
        List<Point> points = new ArrayList<>();
        for (int i = 0; i<= N; i++) {
            points.add(randomPoint());
        }
        return points;
    }



    @Test
    public void timeTest() {
        List<Point> randomPoints = randomPoints(100000);
        KDTree kd = new KDTree(randomPoints);
        NaivePointSet nps = new NaivePointSet(randomPoints);
        List<Point> queryPoints = randomPoints(10000);

        long start = System.currentTimeMillis();
        for (Point point: queryPoints) {
            nps.nearest(point.getX(),point.getY());
        }
        long end = System.currentTimeMillis();
        System.out.println("After 10000 queries, NaivePointSet spends " + (end - start) +" time.");

        start = System.currentTimeMillis();
        for (Point point: queryPoints) {
            kd.nearest(point.getX(),point.getY());
        }
        end = System.currentTimeMillis();
        System.out.println("After 10000 queries, no spends " + (end - start) +" time.");

    }

    public static void main(String[] args) {

    }

}
