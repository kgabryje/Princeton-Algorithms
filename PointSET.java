import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;

public class PointSET {
    private SET<Point2D> set;
    public PointSET() {
        set = new SET<>();
    }

    public boolean isEmpty() {
        return set.isEmpty();
    }

    public int size() {
        return set.size();
    }

    public void insert(Point2D p) {
        if (p == null)
            throw new NullPointerException();
        if (!contains(p))
            set.add(p);
    }

    public boolean contains(Point2D p) {
        if (p == null)
            throw new NullPointerException();
        return set.contains(p);
    }

    public void draw() { // draw all points to standard draw
        for (Point2D p : set)
            p.draw();
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null)
            throw new NullPointerException();
        SET<Point2D> s = new SET<>();
        for (Point2D p : set)
            if (rect.contains(p))
                s.add(p);
        return s;
    }

    public Point2D nearest(Point2D p) {
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

    public static void main(String[] args) {
    }
}