import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class BruteCollinearPoints {
    private final LineSegment[] segments;
    private int count;

    public BruteCollinearPoints(final Point[] points) {    // finds all line segments containing 4 points
        if (points == null) {
            throw new IllegalArgumentException(
                    "Argument in constructor is null."
            );
        }
        checkNullPoint(points);
        checkDuplicatePoint(points);
        int size = points.length;
        Point[] copy = new Point[size];
        copyArray(points, copy);
        Arrays.sort(copy);
        segments = new LineSegment[size];
        for (int i = 0; i < size; i++) {
            for (int j = i + 1; j < size; j++) {
                for (int k = j + 1; k < size; k++) {
                    Point first = copy[i];
                    Point second = copy[j];
                    Point third = copy[k];
                    double firstSlope = first.slopeTo(second);
                    double secondSlope = first.slopeTo(third);
                    if (Double.compare(firstSlope, secondSlope) != 0) {
                        continue;
                    }
                    for (int m = k + 1; m < size; m++) {
                        Point fourth = copy[m];
                        double thirdSlope = first.slopeTo(fourth);
                        if (Double.compare(firstSlope, thirdSlope) == 0) {
                            segments[count++] = new LineSegment(first, fourth);
                        }
                    }
                }
            }
        }
    }

    private void copyArray(Point[] source, Point[] dest) {
        for (int i = 0; i < source.length; i++) {
            dest[i] = source[i];
        }
    }

    private void checkNullPoint(Point[] points) {
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new IllegalArgumentException(
                        "Method checkNullPoint detected null in points."
                );
            }
        }
    }

    private void checkDuplicatePoint(Point[] points) {
        int size = points.length;
        for (int i = 0; i < size; i++) {
            Point first = points[i];
            for (int j = i + 1; j < size; j++) {
                Point second = points[j];
                if (first.compareTo(second) == 0) {
                    throw new IllegalArgumentException(
                            "Method checkDuplicatePoint detected duplicate point in points."
                    );
                }
            }
        }
    }

    public int numberOfSegments() {                  // the number of line segments
        return count;
    }

    public LineSegment[] segments() {                // the line segments
        LineSegment[] copy = new LineSegment[count];
        for (int i = 0; i < count; i++) {
            copy[i] = segments[i];
        }
        return copy;
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