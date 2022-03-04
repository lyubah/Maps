package bearmaps.proj2ab;

import java.util.List;

/**
 * @source: Joshua Hug ( I based the add method of my code of his implementation)
 * @Link: https://www.youtube.com/watch?v=irkJ4gczM0I
 * I based nearestNeighbor off psuedo code:
 * @source: cs61b lecture slides
 * @Link: https://docs.google.com/presentation/d/1lsbD88IP3XzrPkWMQ_SfueEgfiUbxdpo-90X
 *u_mih5U/edit#slide=id.g54b6d82fee_464_1
 */

public class KDTree implements PointSet {
    private Node root;
    private static final boolean HORZ = false;
    private static final boolean VERT = true;

    private class Node {
        private Point p;
        private boolean orientation;
        private Node right;
        private Node left;

        Node(Point point, boolean o) {
            this.p = point;
            this.orientation = o;
        }
    }

    public KDTree(List<Point> points) {
        for (Point p : points) {
            root = add(p, root, HORZ);
        }
    }

    private Node add(Point p, Node n, Boolean o) {
        if (n == null) {
            return new Node(p, o);
        }
        if (p.equals(n.p)) {
            return n;
        }
        double compare = comparePoints(n.p, p, o);
        if (compare < 0) {
            n.left = add(p, n.left, !o);
        } else if (compare >= 0) {
            n.right = add(p, n.right, !o);
        }
        return n;
    }

    //Compares two points based on their orientation
    private double comparePoints(Point np, Point p, Boolean o) {
        if (o == VERT) {
            return Double.compare(p.getY(), np.getY());
        } else {
            return Double.compare(p.getX(), np.getX());
        }
    }


    @Override
    public Point nearest(double x, double y) {
        Point newPoint = new Point(x, y);
        Node nearest = nearestNeighbor(root, newPoint, root, HORZ);
        return new Point(nearest.p.getX(), nearest.p.getY());
    }

    private double distance(Double x1, Double x2) {
        return Math.pow(x1 - x2, 2);
    }

    public boolean isPrune(Node n, Point goal, Double compareLength) {
        if (n == null) {
            return false;
        }
        if (n.orientation == HORZ) {
            return distance(n.p.getX(), goal.getX()) < compareLength;
        } else {
            return distance(n.p.getY(), goal.getY()) < compareLength;
        }
    }

    private Node nearestNeighbor(Node n, Point goal, Node best, boolean orientation) {
        //for pruning method
        Node bad;
        Node good;
        //base case
        if (n == null) {
            return best;
        }
        // current node dist is less than the best dist replace it
        if (Point.distance(n.p, goal) < Point.distance(best.p, goal)) {
            best = n;
        }
        //if not check the childs
        if (comparePoints(n.p, goal, orientation) < 0) {
            good = n.left;
            bad = n.right;
        } else {
            good = n.right;
            bad = n.left;
        }
        best = nearestNeighbor(good, goal, best, !orientation);

        //To prune or not to prune
        double compareLength = Point.distance(best.p, goal);
        if (isPrune(n, goal, compareLength)) {
            best = nearestNeighbor(bad, goal, best, !orientation);
        }

        return best;
    }

}

