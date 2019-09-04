import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class FastCollinearPoints {
    private final LineSegment[] segments;
    private int countSegment;

    public FastCollinearPoints(final Point[] points) {    // finds all line segments containing 4 points
        if (points == null) {
            throw new IllegalArgumentException(
                    "Argument in constructor is null."
            );
        }
        checkNullPoint(points);
        checkDuplicatePoint(points);
        int size = points.length;
        segments = new LineSegment[size];
        Point[] copy = new Point[size];
        copyArray(points, copy);
        Arrays.sort(copy);
        for (int i = 0; i < size; i++) {
            Point p = points[i];
            Arrays.sort(copy);
            Arrays.sort(copy, p.slopeOrder());
            int min = 0;
            int max = 1;
            Point minPoint = copy[min];
            Point maxPoint = copy[max];
            while (min < size) {
                while (max < size && p.slopeTo(minPoint) == p.slopeTo(copy[min])) max++;
                if (max - min >= 3) {
                    Point pMin = copy[min].compareTo(p) < 0 ? copy[min] : p;
                    Point pMax = copy[max - 1].compareTo(p) > 0 ? copy[max - 1] : p;
                    if (p == pMin){
                        segments[countSegment++] = new LineSegment(pMin, pMax);
                    }
                }
                min = max;
            }
        }
    }

    private void copyArray(final Point[] source, final Point[] dest) {
        for (int i = 0; i < source.length; i++) {
            dest[i] = source[i];
        }
    }

    private void checkNullPoint(final Point[] points) {
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new IllegalArgumentException(
                        "Method checkNullPoint detected null in points."
                );
            }
        }
    }

    private void checkDuplicatePoint(final Point[] points) {
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
        return countSegment;
    }

    public LineSegment[] segments() {                // the line segments
        LineSegment[] copy = new LineSegment[countSegment];
        for (int i = 0; i < countSegment; i++) {
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}