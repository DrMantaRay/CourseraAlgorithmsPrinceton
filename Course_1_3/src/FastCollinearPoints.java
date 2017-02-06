/**
 * Created by pchen on 2/5/2017.
 */
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.ArrayList;

public class FastCollinearPoints {
    private int numberOfSegments;
    private ArrayList<LineSegment> segments;
    public FastCollinearPoints(Point[] points) {
        segments= new ArrayList<LineSegment>();
        if (points == null)
        { throw new NullPointerException(); }
        Point[] localPoints= new Point[points.length];
        for (int i = 0; i < points.length; i++ ) {
            if (points[i] == null) { throw new NullPointerException(); }
            for (int k = i+1; k < points.length; k++ ) {
                if (points[k].compareTo(points[i]) == 0) {
                    throw new IllegalArgumentException();
                }
            }
            localPoints[i]=points[i];
        }
        double[] slopeArray;
        Point[] pointArray;
        int slopeSequence;
        for (int i = 0; i< points.length; i++) {
            localPoints = new Point[points.length-i];
            for (int m = i; m < points.length; m++) {
                localPoints[m-i]=points[m];
            }
            Arrays.sort(localPoints,points[i].slopeOrder());

            slopeArray = new double[points.length-i];
            for (int j = i; j < points.length; j++) {
                slopeArray[j-i] = points[i].slopeTo(localPoints[j-i]);
            }
            slopeSequence = 1;
            for (int k = 1; k < slopeArray.length; k++) {
                if (slopeArray[k] != slopeArray[k-1]) {
                    if (slopeSequence >= 3) {
                        numberOfSegments++;
                        pointArray = new Point[slopeSequence + 1];
                        for (int l = 0; l < slopeSequence; l++) {
                            pointArray[l] = localPoints[k-l-1];
                        }
                        pointArray[slopeSequence] = points[i];
                        Arrays.sort(pointArray, Point::compareTo);
                        segments.add(new LineSegment(pointArray[0],pointArray[slopeSequence]));
                        slopeSequence = 1;
                    }
                    else {
                        slopeSequence = 1;
                    }
                }
                else {
                    slopeSequence++;
                    if (k == slopeArray.length-1 && slopeSequence>=3) {
                        numberOfSegments++;
                        pointArray = new Point[slopeSequence + 1];
                        for (int l = 0; l < slopeSequence; l++) {
                            pointArray[l] = localPoints[k-l];
                        }
                        pointArray[slopeSequence] = points[i];
                        Arrays.sort(pointArray, Point::compareTo);
                        segments.add(new LineSegment(pointArray[0],pointArray[slopeSequence]));
                        slopeSequence = 1;
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
