import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdDraw;

public class FastCollinearPoints {
    private List<LineSegment> lineSegments = new ArrayList<>();

    public FastCollinearPoints(Point[] points) {
        checkForDuplicates(points);
        checkForNulls(points);

        Arrays.sort(points);
        Point[] copy = new Point[points.length];

        for (int i = 0; i < points.length - 1; i++) {
            Point pivot = points[i];
            System.arraycopy(points, 0, copy, 0, points.length);
            Arrays.sort(copy, pivot.slopeOrder());

            LinkedList<Point> collinear = new LinkedList<>();
            collinear.add(pivot);
            collinear.add(copy[1]);
            double slope = pivot.slopeTo(copy[1]);
            for (int j = 2; j < copy.length; j++) {
                if (pivot.slopeTo(copy[j]) == slope) {
                    collinear.add(copy[j]);
                }
                else {
                    if (collinear.size() >= 3) {
                        Collections.sort(collinear);
                        if (collinear.getFirst() == pivot)
                            lineSegments.add(new LineSegment(pivot, collinear.get(collinear.size() - 1)));
                    }
                    collinear.clear();
                    collinear.add(pivot);
                    collinear.add(copy[j]);
                    slope = pivot.slopeTo(copy[j]);
                }
            }
            if (collinear.size() >= 3) {
                Collections.sort(collinear);
                if (collinear.getFirst() == pivot)
                    lineSegments.add(new LineSegment(pivot, collinear.get(collinear.size() - 1)));
            }
            collinear.clear();
        }
    }    // finds all line segments containing 4 or more points

    public int numberOfSegments() {
        return lineSegments.size();
    }       // the number of line segments

    public LineSegment[] segments() {
        return lineSegments.toArray(new LineSegment[lineSegments.size()]);
    } // the line segments

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
