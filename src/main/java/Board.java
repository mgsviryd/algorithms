import java.util.LinkedList;

public class Board {
    private static final int ZERO_BLOCK = 0;
    private static final int START_POSITION = 1;
    private final int[][] tiles;
    private final int n;

    public Board(final int[][] blocks) {           // construct a board from an n-by-n array of tiles
        // (where tiles[i][j] = block in row i, column j)
        if (blocks == null){
            throw new IllegalArgumentException(
                    "Argument in Board constructor is null."
            );
        }
        tiles = copyBlocks(blocks);
        n = tiles.length;
    }

    private int[][] copyBlocks(final int[][] source) {
        int size = source.length;
        int[][] copy = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                copy[i][j] = source[i][j];
            }
        }
        return copy;
    }

    public int dimension() {                 // board dimension n
        return n;
    }

    public int hamming() {                   // number of tiles out of place
        int nOut = 0;
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                int currentPosition = tiles[row][col];
                if (currentPosition != ZERO_BLOCK && currentPosition != getPositionInBoard(row, col)) {
                    nOut++;
                }
            }
        }
        return nOut;
    }

    public int manhattan() {                 // sum of Manhattan distances between tiles and goal
        int sum = 0;
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                int currentPosition = tiles[row][col];
                if (currentPosition != ZERO_BLOCK && currentPosition != getPositionInBoard(row, col)) {
                    int rowInBoard = (currentPosition - START_POSITION) / n;
                    int colInBoard = (currentPosition - START_POSITION) % n;
                    int shiftLeftRight = Math.abs(col - colInBoard);
                    int shiftUpDown = Math.abs(row - rowInBoard);
                    sum += (shiftLeftRight + shiftUpDown);
                }
            }
        }
        return sum;
    }

    private int getPositionInBoard(int row, int col) {
        return START_POSITION + row * n + col;
    }

    public boolean isGoal() {                // is this board the goal board?
        return hamming() == 0;
    }

    /*
    When all in necessary order excluding only 2 last(go one by one and have reverse order), we haven't any step.
    In this case we only can render them in the natural order, in other words we swap them.
     */
    public Board twin() {
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n - 1; col++) {
                int leftPosition = tiles[row][col];
                int rightPosition = tiles[row][col + 1];
                if (leftPosition != ZERO_BLOCK && rightPosition != ZERO_BLOCK) {
                    int[][] copy = copyBlocks(tiles);
                    copy = swapRight(copy, row, col);
                    return new Board(copy);
                }
            }
        }
        throw new RuntimeException(
                "Board hasn't tiles that have any left/right neighbor."
        );
    }

    public boolean equals(final Object y) {        // does this board equal y?
        if (y == this) {
            return true;
        }
        if (y == null) {
            return false;
        }
        if (y.getClass() != this.getClass()) {
            return false;
        }
        Board thatBoard = (Board) y;
        if (thatBoard.n != this.n) {
            return false;
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (thatBoard.tiles[i][j] != this.tiles[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    public Iterable<Board> neighbors() {     // all neighboring boards
        LinkedList<Board> list = new LinkedList<>();
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                if (tiles[row][col] == ZERO_BLOCK) {
                    int[][] copy;
                    if (row > 0) {
                        copy = copyBlocks(tiles);
                        copy = swapUp(copy, row, col);
                        list.add(new Board(copy));
                    }
                    if (row < n - 1) {
                        copy = copyBlocks(tiles);
                        copy = swapDown(copy, row, col);
                        list.add(new Board(copy));
                    }
                    if (col > 0) {
                        copy = copyBlocks(tiles);
                        copy = swapLeft(copy, row, col);
                        list.add(new Board(copy));
                    }
                    if (col < n - 1) {
                        copy = copyBlocks(tiles);
                        copy = swapRight(copy, row, col);
                        list.add(new Board(copy));
                    }
                }
            }
        }
        return list;
    }

    private int[][] swapUp(final int[][] copy, final int row, final int col) {
        int temp = copy[row][col];
        copy[row][col] = copy[row - 1][col];
        copy[row - 1][col] = temp;
        return copy;
    }

    private int[][] swapDown(final int[][] copy, final int row, final int col) {
        int temp = copy[row][col];
        copy[row][col] = copy[row + 1][col];
        copy[row + 1][col] = temp;
        return copy;
    }

    private int[][] swapLeft(final int[][] copy, final int row, final int col) {
        int temp = copy[row][col];
        copy[row][col] = copy[row][col - 1];
        copy[row][col - 1] = temp;
        return copy;
    }

    private int[][] swapRight(final int[][] copy, final int row, final int col) {
        int temp = copy[row][col];
        copy[row][col] = copy[row][col + 1];
        copy[row][col + 1] = temp;
        return copy;
    }


    public String toString() {               // string representation of this board (in the output format specified below)
        StringBuilder sb = new StringBuilder();
        sb.append(n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                sb.append(String.format("%2d ", tiles[i][j]));
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
