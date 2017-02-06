/**
 * Created by pchen on 2/5/2017.
 */
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class BruteCollinearPoints {
    private int numberOfSegments;
    private ArrayList<LineSegment> segments;
    private boolean fourOnline(Point a, Point b, Point c, Point d) {
        return a.slopeTo(b) == a.slopeTo(c) && a.slopeTo(b) == a.slopeTo(d);
    }
    public BruteCollinearPoints(Point[] points) {
        segments= new ArrayList<LineSegment>();
        if (points == null)
        { throw new NullPointerException(); }
        Point[] localPoints= new Point[points.length];
        for (int i = 0; i < points.length; i++ ) {
            if (points[i] == null) { throw new NullPointerException(); }
            for (int k = i+1; k < points.length; k++ ) {
                if (points[k] == points[i]) {
                    throw new IllegalArgumentException();
                }
            }
            localPoints[i]=points[i];
        }

        for (int i=0; i < localPoints.length; i++) {
            for (int j = i+1; j < localPoints.length; j++) {
                for (int k = j+1; k < localPoints.length; k++) {
                    for (int l = k+1; l < localPoints.length; l++ ) {
                        if (fourOnline(localPoints[i], localPoints[j], localPoints[k], localPoints[l])) {
                            numberOfSegments++;
                            Point[] s = new Point[4];
                            s[0] = localPoints[i];
                            s[1] = localPoints[j];
                            s[2] = localPoints[k];
                            s[3] = localPoints[l];
                            Arrays.sort(s, Point::compareTo);
                            segments.add(new LineSegment(s[0],s[3]));
                        }
                    }
                }
            }
        }
    }
    public int numberOfSegments() {
        return numberOfSegments;
    }
    public LineSegment[] segments() {
        return segments.toArray(new LineSegment[segments.size()]);
    }
    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
