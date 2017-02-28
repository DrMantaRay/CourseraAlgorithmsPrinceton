import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;

import java.util.ArrayList;

public class PointSET {
    private SET<Point2D> pointSet;
    public PointSET() { pointSet = new SET<>(); }                           // construct an empty set of points
    public boolean isEmpty() { return pointSet.isEmpty(); }                     // is the set empty?
    public int size() { return pointSet.size(); }            // number of points in the set
    public              void insert(Point2D p)      {
        if (p == null) {
            throw new NullPointerException();
        }
        else {
            if (!pointSet.contains(p)) {
                pointSet.add(p);
            }
        }
    }        // add the point to the set (if it is not already in the set)
    public           boolean contains(Point2D p) {
        if (p == null) {
            throw new NullPointerException();
        }
        else {
            return pointSet.contains(p);
        }
    }           // does the set contain point p?
    public              void draw()         {
        for (Point2D p2D : pointSet) {
            p2D.draw();
        }
    }                 // draw all points to standard draw
    public Iterable<Point2D> range(RectHV rect)      {
        if (rect == null) {
            throw new NullPointerException();
        }
        else {
            ArrayList<Point2D> point2DArrayList = new ArrayList<Point2D>();
            for (Point2D point2D : pointSet) {
                if (rect.contains(point2D)) {
                    point2DArrayList.add(point2D);
                }
            }
            return point2DArrayList;
        }
    }        // all points that are inside the rectangle
    public           Point2D nearest(Point2D p) {            // a nearest neighbor in the set to point p; null if the set is empty
        if (p == null) {
            throw new NullPointerException();
        } else {
            double distance = 1000000000;
            Point2D closestPoint = null;
            for (Point2D point2D : pointSet) {
                if (p.distanceTo(point2D) < distance) {
                    distance = p.distanceTo(point2D);
                    closestPoint = point2D;
                }
            }
            return closestPoint;
        }
    }

    public static void main(String[] args) {

    }                  // unit testing of the methods (optional)
}