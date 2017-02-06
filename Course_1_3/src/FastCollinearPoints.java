/**
 * Created by pchen on 2/5/2017.
 */
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

        for (Point point:points) {
            Arrays.sort(localPoints,point.slopeOrder());
            for (int i = 0; i < points.length; i++) {
                
            }
        }
    }
    public int numberOfSegments() {
        return numberOfSegments;
    }
    public LineSegment[] segments() {
        return segments.toArray(new LineSegment[segments.size()]);
    }
}
