import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {
    private class Node {
        private Point2D key;
        private RectHV rect;
        private Node left;
        private Node right;
        Node(Point2D p, RectHV rect) {
            key = p;
            this.rect = rect;
            left = null;
            right = null;
        }
    }

    private Node root;
    private int size;

    public KdTree() { // construct an empty set of points
        root = null;
        size = 0;
    }

    public boolean isEmpty() { // is the set empty?
        return size == 0;
    }

    public int size() { // number of points in the set
        return size;
    }

    public void insert(Point2D p) { // add the point to the set (if it is not already in the set)
        if (p == null)
            throw new NullPointerException();
        if (root == null) {
            root = new Node(p, new RectHV(0, 0, 10, 10));
            size++;
            return;
        }
        root = insert(root, p, root.rect, true);
    }

    public boolean contains(Point2D p) { // does the set contain point p?
        if (p == null)
            throw new NullPointerException();
        return contains(root, p, true);
    }

    public void draw() { // draw all points to standard draw
        StdDraw.clear();
        draw(root, true);
    }

    public Iterable<Point2D> range(RectHV rect) { // all points that are inside the rectangle
        if (rect == null)
            throw new NullPointerException();
        Queue<Point2D> q = new Queue<>();
        range(root, rect, q);
        return q;
    }

    public Point2D nearest(Point2D p) { // a nearest neighbor in the set to point p; null if the set is empty
        if (p == null)
            throw new NullPointerException();
        if (isEmpty())
            return null;
        return nearest(root, p, root.key);
    }

    public static void main(String[] args) { // unit testing of the methods (optional)
        KdTree kd = new KdTree();
        RectHV rect = new RectHV(0.5, 0.0, 2.5, 3.0);
        Point2D[] points = new Point2D[5];
        points[0] = new Point2D(3, 2);
        points[1] = new Point2D(2, 5);
        points[2] = new Point2D(1, 1);
        points[3] = new Point2D(5, 2);
        points[4] = new Point2D(2, 2);
        for (Point2D p : points)
            kd.insert(p);
        kd.nearest(new Point2D(2, 3));
        kd.range(rect);
        kd.draw();
    }

    private Node insert(Node node, Point2D p, RectHV rect, boolean isVertical) {
        if (node == null) {
            size++;
            return new Node(p, rect);
        }
        if (node.key.equals(p))
            return node;

        int cmp;
        RectHV nextRect;
        if (isVertical) {
            cmp = Point2D.X_ORDER.compare(p, node.key);
            if (cmp < 0 && node.left == null)
                nextRect = new RectHV(node.rect.xmin(), node.rect.ymin(), node.key.x(), node.rect.ymax());
            else if (cmp < 0 && node.left != null)
                nextRect = node.left.rect;
            else if (cmp > 0 && node.right == null)
                nextRect = new RectHV(node.key.x(), node.rect.ymin(), node.rect.xmax(), node.rect.ymax());
            else
                nextRect = node.right.rect;
        }
        else {
            cmp = Point2D.Y_ORDER.compare(p, node.key);
            if (cmp < 0 && node.left == null)
                nextRect = new RectHV(node.rect.xmin(), node.rect.ymin(), node.rect.xmax(), node.key.y());
            else if (cmp < 0 && node.left != null)
                nextRect = node.left.rect;
            else if (cmp > 0 && node.right == null)
                nextRect = new RectHV(node.rect.xmin(), node.key.y(), node.rect.xmax(), node.rect.ymax());
            else
                nextRect = node.right.rect;
        }

        if (cmp < 0)
            node.left = insert(node.left, p, nextRect, !isVertical);
        else
            node.right = insert(node.right, p, nextRect, !isVertical);
        return node;
    }

    private boolean contains(Node node, Point2D p, boolean isVertical) {
        if (node == null) return false;
        if (node.key.equals(p)) return true;

        int cmp;
        if (isVertical)
            cmp = Point2D.X_ORDER.compare(p, node.key);
        else
            cmp = Point2D.Y_ORDER.compare(p, node.key);

        if (cmp < 0)
            return contains(node.left, p, !isVertical);
        else
            return contains(node.right, p, !isVertical);
    }

    private void range(Node node, RectHV rect, Queue<Point2D> q) {
        if (node == null) return;
        if (rect.contains(node.key))
            q.enqueue(node.key);
        if (node.left != null && rect.intersects(node.left.rect))
            range(node.left, rect, q);
        if (node.right != null && rect.intersects(node.right.rect))
            range(node.right, rect, q);
    }

    private Point2D nearest(Node node, Point2D p, Point2D min) {
        if (node != null) {
            if (min == null) {
                min = node.key;
            }

            // If the current min point is closer to query than the current point
            if (min.distanceSquaredTo(p)
                    >= node.rect.distanceSquaredTo(p)) {
                if (node.key.distanceSquaredTo(p) < min.distanceSquaredTo(p)) {
                    min = node.key;
                }

                // Check in which order should we iterate
                if (node.right != null && node.right.rect.contains(p)) {
                    min = nearest(node.right, p, min);
                    min = nearest(node.left, p, min);
                }
                else {
                    min = nearest(node.left, p, min);
                    min = nearest(node.right, p, min);
                }
            }
        }
        return min;
    }

    private void draw(Node node, boolean isVertical) {
        if (node != null) {
            draw(node.left, !isVertical);

            StdDraw.setPenRadius();
            if (isVertical) {
                StdDraw.setPenColor(StdDraw.RED);
                StdDraw.line(node.key.x(), node.rect.ymin(), node.key.x(), node.rect.ymax());
            }
            else {
                StdDraw.setPenColor(StdDraw.BLUE);
                StdDraw.line(node.rect.xmin(), node.key.y(), node.rect.xmax(), node.key.y());
            }
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(0.01);
            node.key.draw();

            draw(node.right, !isVertical);
        }
    }
}
