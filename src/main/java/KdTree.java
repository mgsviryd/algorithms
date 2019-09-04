import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.ArrayList;
import java.util.List;

public class KdTree {
    private static final boolean RED = true;
    private static final boolean BLUE = false;
    private Node root;
    private int n;

    private static class Node {
        private final Point2D point;
        private final RectHV rect;
        private Node lb;
        private Node rt;

        private Node(final Point2D point, RectHV rectangle) {
            this.point = point;
            this.rect = rectangle;
        }

        private void draw() {
            point.draw();
            if (lb != null) {
                lb.draw();
            }
            if (rt != null) {
                rt.draw();
            }
        }
    }

    public boolean isEmpty() {
        return n == 0;
    }

    public int size() {
        return n;
    }

    public void insert(final Point2D p) {
        if (p == null) {
            return;
        }
        root = insert(root, null, p, RED);
    }

    private Node insert(final Node node, final Node parent, final Point2D p, final boolean color) {
        if (node == null) {
            return createNode(parent, p, color);
        }
        Point2D point = node.point;
        if (point.equals(p)) {
            return node;
        }
        if (color) {
            double x = point.x();
            if (p.x() < x) {
                node.lb = insert(node.lb, node, p, BLUE);
            } else {
                node.rt = insert(node.rt, node, p, BLUE);
            }
        } else {
            double y = point.y();
            if (p.y() < y) {
                node.lb = insert(node.lb, node, p, RED);
            } else {
                node.rt = insert(node.rt, node, p, RED);
            }
        }
        return node;
    }

    private Node createNode(final Node parent, final Point2D p, final boolean color) {
        n++;
        if (parent == null) {
            return new Node(p, new RectHV(0, 0, 1, 1));
        } else {
            return new Node(p, createRectHV(parent, p, !color));
        }
    }

    private RectHV createRectHV(final Node parent, final Point2D pC, final boolean parentColor) {
        Point2D parentP = parent.point;
        RectHV rectangleP = parent.rect;
        if (parentColor) {
            double xP = parentP.x();
            double xC = pC.x();
            if (xC < xP) {
                return new RectHV(rectangleP.xmin(), rectangleP.ymin(), xP, rectangleP.ymax());
            } else {
                return new RectHV(xP, rectangleP.ymin(), rectangleP.xmax(), rectangleP.ymax());
            }
        } else {
            double yP = parentP.y();
            double yC = pC.y();
            if (yC < yP) {
                return new RectHV(rectangleP.xmin(), rectangleP.ymin(), rectangleP.xmax(), yP);
            } else {
                return new RectHV(rectangleP.xmin(), yP, rectangleP.xmax(), rectangleP.ymax());
            }
        }
    }

    public boolean contains(final Point2D p) {
        return p != null && contains(root, p.x(), p.y(), RED);
    }

    private boolean contains(final Node node, final double x, final double y, final boolean color) {
        if (node == null) {
            return false;
        }
        Point2D point = node.point;
        if (color) {
            double px = point.x();
            if (Double.compare(x, px) == -1) {
                return contains(node.lb, x, y, BLUE);
            } else if (Double.compare(x, px) == 1 || Double.compare(y, point.y()) != 0) {
                return contains(node.rt, x, y, BLUE);
            } else {
                return true;
            }
        } else {
            double py = point.y();
            if (Double.compare(y, py) == -1) {
                return contains(node.lb, x, y, RED);
            } else if (Double.compare(y, py) == 1 || Double.compare(x, point.x()) != 0) {
                return contains(node.rt, x, y, RED);
            } else {
                return true;
            }
        }
    }

    public void draw() {
        if (root != null) {
            root.draw();
        }
    }

    public Iterable<Point2D> range(final RectHV rect) {
        if (rect == null) {
            return new ArrayList<>();
        }
        return range(root, rect, RED);
    }

    private List<Point2D> range(final Node node, final RectHV rect, final boolean color) {
        if (node == null) {
            return new ArrayList<>();
        }
        List<Point2D> range = new ArrayList<>();
        if (rect.intersects(node.rect)) {
            if (rect.contains(node.point)) {
                range.add(node.point);
            }
            range.addAll(range(node.lb, rect, !color));
            range.addAll(range(node.rt, rect, !color));
        }
        return range;
    }

    public Point2D nearest(final Point2D p) {
        if (p == null) {
            return null;
        }
        return nearest(root, p, RED);
    }

    private Point2D nearest(final Node node, final Point2D p, final boolean color) {
        if (node == null) {
            return null;
        }
        Point2D np = node.point;
        if (color) {
            if (p.x() < np.x()) {
                np = nearest(node.lb, node.rt, p, BLUE, np);
            } else {
                np = nearest(node.rt, node.lb, p, BLUE, np);
            }
        } else {
            if (p.y() < np.y()) {
                np = nearest(node.lb, node.rt, p, RED, np);
            } else {
                np = nearest(node.rt, node.lb, p, RED, np);
            }
        }
        return np;
    }

    private Point2D nearest(final Node n1, final Node n2, final Point2D p, final boolean color, Point2D parentNp) {
        Point2D auxPoint = nearest(n1, p, color);
        double min = parentNp.distanceSquaredTo(p);
        if (auxPoint != null) {
            double auxMin = auxPoint.distanceSquaredTo(p);
            if (min > auxMin) {
                parentNp = auxPoint;
                min = auxMin;
            }
        }
        if (n2 != null && min > n2.rect.distanceSquaredTo(p)) {
            Point2D auxPoint2 = nearest(n2, p, color);
            if (auxPoint2 != null) {
                double auxMin = auxPoint2.distanceSquaredTo(p);
                if (min > auxMin) {
                    parentNp = auxPoint2;
                }
            }
        }
        return parentNp;
    }
}
