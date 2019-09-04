import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.Stack;

public class Solver {
    private Move lastMove;
    public Solver(final Board initial) {
        MinPQ<Move> moves = new MinPQ<>();
        moves.insert(new Move(initial));

        MinPQ<Move> twinMoves = new MinPQ<>();
        twinMoves.insert(new Move(initial.twin()));

        boolean goNext = true;
        while (goNext) {
            lastMove = expand(moves);
            if (lastMove != null || expand(twinMoves) != null) { // twinMoves change over while loop
                goNext = false;
            }
        }
    }

    private Move expand(final MinPQ<Move> moves) {
        if (moves.isEmpty()) {
            return null;
        }
        Move bestMove = moves.delMin();
        if (bestMove.board.isGoal()) {
            return bestMove;
        }
        for (Board neighbor : bestMove.board.neighbors()) {
            if (bestMove.previous == null || !neighbor.equals(bestMove.previous.board)) {
                moves.insert(new Move(neighbor, bestMove));
            }
        }
        return null;
    }

    public boolean isSolvable() {
        return lastMove != null;
    }

    public int moves() {
        if (isSolvable()) {
            return lastMove.nMoves;
        } else {
            return -1;
        }
    }

    public Iterable<Board> solution() {
        if (!isSolvable()) {
            return null;
        }
        Stack<Board> moves = new Stack<>();
        while (lastMove != null) {
            moves.push(lastMove.board);
            lastMove = lastMove.previous;
        }
        return moves;
    }

    public static void main(final String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                blocks[i][j] = in.readInt();
            }
        }
        Board initial = new Board(blocks);
        // solve the puzzle
        Solver solver = new Solver(initial);
        // print solution to standard output
        if (!solver.isSolvable()) {
            StdOut.println("No solution possible");
        } else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution()) {
                StdOut.println(board);
            }
        }
    }

    private static class Move implements Comparable<Move> {
        private Move previous;
        private final Board board;

        private int nMoves;

        public Move(final Board b) {
            this.board = b;
        }

        public Move(final Board b, final Move p) {
            board = b;
            previous = p;
            nMoves = p.nMoves + 1;  // new Move consists 1 util step (another move)
        }
        /*
        When we move to goal manhattan method reduces sum by 1 (in best case) and nMoves increases to 1.
        Thus we summarize sum from manhattan method and nMoves.
         */
        public int compareTo(final Move move) {
            return (this.board.manhattan() - move.board.manhattan()) + (this.nMoves - move.nMoves);
        }

    }
}
