import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;

public class SAP {
    private Digraph G;

    // constructor takes a G (not necessarily a DAG)
    public SAP(final Digraph G) {
        if (G == null) {
            throw new IllegalArgumentException();
        }
        this.G = new Digraph(G);
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(final int v, final int w) {
        if (isOutOfRangeGraph(v) || isOutOfRangeGraph(w)) {
            throw new IllegalArgumentException();
        }
        BreadthFirstDirectedPaths pathA = new BreadthFirstDirectedPaths(G, v);
        BreadthFirstDirectedPaths pathB = new BreadthFirstDirectedPaths(G, w);
        return helper(pathA, pathB)[0];
    }

    private boolean isOutOfRangeGraph(int v) {
        return v < 0 || v >= G.V();
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(final int v, final int w) {
        if (isOutOfRangeGraph(v) || isOutOfRangeGraph(w)) {
            throw new IllegalArgumentException();
        }
        BreadthFirstDirectedPaths pathA = new BreadthFirstDirectedPaths(G, v);
        BreadthFirstDirectedPaths pathB = new BreadthFirstDirectedPaths(G, w);
        return helper(pathA, pathB)[1];
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(final Iterable<Integer> v, final Iterable<Integer> w) {
        if (v == null || w == null) {
            throw new IllegalArgumentException();
        }
        BreadthFirstDirectedPaths pathA = new BreadthFirstDirectedPaths(G, v);
        BreadthFirstDirectedPaths pathB = new BreadthFirstDirectedPaths(G, w);
        return helper(pathA, pathB)[0];
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(final Iterable<Integer> v, final Iterable<Integer> w) {
        if (v == null || w == null) {
            throw new IllegalArgumentException();
        }
        BreadthFirstDirectedPaths pathA = new BreadthFirstDirectedPaths(G, v);
        BreadthFirstDirectedPaths pathB = new BreadthFirstDirectedPaths(G, w);
        return helper(pathA, pathB)[1];
    }

    private int[] helper(final BreadthFirstDirectedPaths bfs1, final BreadthFirstDirectedPaths bfs2) {
        int min = Integer.MAX_VALUE;
        int ancestor = 0;
        for (int i = 0; i < G.V(); i++) {
            if (bfs1.hasPathTo(i) && bfs2.hasPathTo(i)) {
                int distance = bfs1.distTo(i) + bfs2.distTo(i);
                if (distance < min) {
                    min = distance;
                    ancestor = i;
                }
            }
        }
        return new int[]{min, ancestor};
    }
}
