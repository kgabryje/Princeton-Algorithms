import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;

public class PointSET {
    private SET<Point2D> set;
    public PointSET() { // construct an empty set of points
        set = new SET<>();
    }

    public boolean isEmpty() { // is the set empty?
        return set.isEmpty();
    }

    public int size() { // number of points in the set
        return set.size();
    }

    public void insert(Point2D p) { // add the point to the set (if it is not already in the set)
        if (p == null)
            throw new NullPointerException();
        if (!contains(p))
            set.add(p);
    }

    public boolean contains(Point2D p) { // does the set contain point p?
        if (p == null)
            throw new NullPointerException();
        return set.contains(p);
    }

    public void draw() { // draw all points to standard draw
        for (Point2D p : set)
            p.draw();
    }

    public Iterable<Point2D> range(RectHV rect) { // all points that are inside the rectangle
        if (rect == null)
            throw new NullPointerException();
        SET<Point2D> s = new SET<>();
        for (Point2D p : set)
            if (rect.contains(p))
                s.add(p);
        return s;
    }

    public Point2D nearest(Point2D p) { // a nearest neighbor in the set to point p; null if the set is empty
        if (p == null)
            throw new NullPointerException();
        if (isEmpty())
            return null;
        double minDistance = Double.POSITIVE_INFINITY;
        Point2D minPoint = null;
        for (Point2D pp : set) {
            if (p.distanceTo(pp) < minDistance) {
                minDistance = p.distanceTo(pp);
                minPoint = pp;
            }
        }
        return minPoint;
    }

    public static void main(String[] args) { // unit testing of the methods (optional)
        PointSET set = new PointSET();
        RectHV rect = new RectHV(0.5, 0.0, 2.5, 3.0);
        Point2D[] points = new Point2D[5];
        points[0] = new Point2D(1, 1);
        points[1] = new Point2D(2, 5);
        points[2] = new Point2D(3, 2);
        points[3] = new Point2D(5, 2);
        points[4] = new Point2D(2, 2);
        for (Point2D p : points)
            set.insert(p);
        set.nearest(new Point2D(2, 3));
        set.range(rect);
        set.draw();
    }
}