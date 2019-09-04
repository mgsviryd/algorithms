import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int size;
    private boolean[][] lattice;
    private WeightedQuickUnionUF qu;
    private int top = 0;
    private int bottom;
    private int nOpenSites;

    public Percolation(final int n) {
        if (n < 1) {
            throw new IllegalArgumentException(
                    "Parameter n is less than 1.");
        }
        size = n;
        lattice = new boolean[size][size];
        qu = new WeightedQuickUnionUF(size * size + 2);
        bottom = size * size + 1;
    }

    public void open(final int row, final int col) {
        if (isOutOfBounds(row, col)) {
            throw new IllegalArgumentException(
                    "Parameter row or col is out of lattice.");
        }
        if (!lattice[row - 1][col - 1]) {
            lattice[row - 1][col - 1] = true;
            nOpenSites++;
        } else {
            return;
        }

        int quIndex = getQUIndex(row, col);
        if (row == 1) {
            qu.union(quIndex, top);
        }
        if (row == size) {
            qu.union(quIndex, bottom);
        }
        if (row > 1 && isOpen(row - 1, col)) {
            qu.union(quIndex, getQUIndex(row - 1, col));
        }
        if (row < size && isOpen(row + 1, col)) {
            qu.union(quIndex, getQUIndex(row + 1, col));
        }
        if (col > 1 && isOpen(row, col - 1)) {
            qu.union(quIndex, getQUIndex(row, col - 1));
        }
        if (col < size && isOpen(row, col + 1)) {
            qu.union(quIndex, getQUIndex(row, col + 1));
        }
    }

    private boolean isOutOfBounds(final int row, final int col) {
        if (row < 1 || row > size) {
            return true;
        }
        if (col < 1 || col > size) {
            return true;
        }
        return false;
    }

    private int getQUIndex(final int row, final int col) {
        return size * (row - 1) + col;
    }

    public boolean isOpen(final int row, final int col) {
        if (isOutOfBounds(row, col)) {
            throw new IllegalArgumentException(
                    "Parameter row or col is out of lattice.");
        }
        return lattice[row - 1][col - 1];
    }

    public boolean isFull(final int row, final int col) {
        if (isOutOfBounds(row, col)) {
            throw new IllegalArgumentException(
                    "Parameter row or col is out of lattice.");
        }
        return qu.connected(getQUIndex(row, col), top);
    }

    public int numberOfOpenSites() {     // number of open sites
        return nOpenSites;
    }

    public boolean percolates() {            // does the system percolate?
        return qu.connected(top, bottom);
    }
}
