import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdDraw;

public class BruteCollinearPoints {
    private List<LineSegment> lineSegments = new ArrayList<>();

    public BruteCollinearPoints(Point[] points) {
        checkForDuplicates(points);
        checkForNulls(points);
        Arrays.sort(points);
        double slope;
        for (int i = 0; i < points.length - 3; i++) {
            for (int j = i + 1; j < points.length - 2; j++) {
                slope = points[i].slopeTo(points[j]);
                for (int k = j + 1; k < points.length - 1; k++) {
                    if (points[i].slopeTo(points[k]) == slope) {
                        for (int m = k + 1; m < points.length; m++) {
                            if (points[i].slopeTo(points[m]) == slope) {
                                lineSegments.add(new LineSegment(points[i], points[m]));
                            }
                        }
                    }
                }
            }
        }
    }   // finds all line segments containing 4 points

    public int numberOfSegments() {
        return lineSegments.size();
    }       // the number of line segments

    public LineSegment[] segments() {
        return lineSegments.toArray(new LineSegment[lineSegments.size()]);
    }                // the line segments

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

    private void checkForNulls(Point[] points) {
        if (points == null)
            throw new NullPointerException();
        for (Point p : points) {
            if (p == null)
                throw new NullPointerException();
        }
    }

    private void checkForDuplicates(Point[] points) {
        for (int i = 0; i < points.length - 1; i++) {
            for (int j = i + 1; j < points.length; j++) {
                if (points[i].compareTo(points[j]) == 0)
                    throw new IllegalArgumentException();
            }
        }
    }
}