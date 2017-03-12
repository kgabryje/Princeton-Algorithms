import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class BruteCollinearPoints {
    private List<LineSegment> lineSegments = new ArrayList<>();
    public BruteCollinearPoints(Point[] points) {
        Arrays.sort(points);
        double slope;
        for (int i = 0; i < points.length - 3; i++) {
            for (int j = i + 1; j < points.length - 2; j++) {
                slope = points[i].slopeTo(points[j]);
                for (int k = j + 1; k < points.length - 1; k++) {
                    if (points[i].slopeTo(points[k]) == slope) {
                        for (int l = k + 1; l < points.length; l++) {
                            if (points[i].slopeTo(points[l]) == slope) {
                                lineSegments.add(new LineSegment(points[i], points[l]));
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
}