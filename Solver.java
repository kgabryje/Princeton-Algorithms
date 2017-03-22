import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
    private class SearchNode implements Comparable<SearchNode> {
        private int moves = 0;
        private SearchNode previousNode = null;
        private Board board;

        public SearchNode(Board board) {
            this.board = board;
        }

        public SearchNode(Board board, SearchNode previousNode) {
            moves = previousNode.moves + 1;
            this.board = board;
            this.previousNode = previousNode;
        }

        public int compareTo(SearchNode node) {
            return board.manhattan() + moves - node.board.manhattan() - node.moves;
        }
    }

    private SearchNode lastNode;

    public Solver(Board initial) { // find a solution to the initial board (using the A* algorithm)
        if (initial == null)
            throw new NullPointerException();
        MinPQ<SearchNode> nodes = new MinPQ<>();
        MinPQ<SearchNode> twinNodes = new MinPQ<>();

        nodes.insert(new SearchNode(initial));
        twinNodes.insert(new SearchNode(initial.twin()));

        while (true) {
            lastNode = findNextNode(nodes);
            if (lastNode != null || findNextNode(twinNodes) != null)
                return;
        }
    }

    public boolean isSolvable() { // is the initial board solvable
        return lastNode != null;
    }

    public int moves() { // min number of moves to solve initial board; -1 if unsolvable
        if (!isSolvable())
            return -1;
        return lastNode.moves;
    }

    public Iterable<Board> solution() { // sequence of boards in a shortest solution; null if unsolvable
        if (!isSolvable())
            return null;
        Stack<Board> moves = new Stack<>();
        SearchNode node = lastNode;

        while (node != null) {
            moves.push(node.board);
            node = node.previousNode;
        }
        return moves;
    }

    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);
        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }

    private SearchNode findNextNode(MinPQ<SearchNode> nodes) {
        SearchNode bestNode = nodes.delMin();
        if (bestNode.board.isGoal())
            return bestNode;
        for (Board neighbor : bestNode.board.neighbors()) {
            if (bestNode.previousNode == null)
                nodes.insert(new SearchNode(neighbor, bestNode));
            else if (!neighbor.equals(bestNode.previousNode.board))
                nodes.insert(new SearchNode(neighbor, bestNode));
        }
        return null;
    }
}