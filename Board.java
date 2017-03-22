import edu.princeton.cs.algs4.StdRandom;

import java.util.LinkedList;

public class Board {
    private int[][] board;

    public Board(int[][] blocks) {
        board = copy(blocks);
    }

    public int dimension() {
        return board.length;
    }

    public int hamming() { // number of blocks out of place
        int outOfPlace = 0;
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                if (board[i][j] == 0 && (i != dimension() - 1 || j != dimension() -1))
                    outOfPlace++;
                else if (board[i][j] != fieldValue(i, j))
                    outOfPlace++;
            }
        }
        return outOfPlace;
    }

    public int manhattan() { // sum of Manhattan distances between blocks and goal
        int manhattanDistances = 0;
        for (int i = 0; i < dimension(); i++)
            for (int j = 0; j < dimension(); j++)
                manhattanDistances += getDistance(i, j);
        return manhattanDistances;
    }

    public boolean isGoal() { // is this board the goal board?
        if (board[dimension() - 1][dimension() - 1] != 0)
            return false;
        for (int i = 0; i < dimension(); i++)
            for (int j = 0; j < dimension(); j++)
            if (board[i][j] != fieldValue(i, j))
                return false;
        return true;
    }

    public Board twin() { // a board that is obtained by exchanging any pair of blocks
        int[][] twin = null;
        int row = StdRandom.uniform(dimension() - 1);
        int col = StdRandom.uniform(dimension() - 1);

        if (board[row][col] != 0 && board[row][col + 1] != 0)
            twin = swap(row, col, row, col + 1);
        else if (board[row][col] == 0)
            twin = swap(row + 1, col + 1, row, col + 1);
        else if (board[row][col + 1] == 0)
            twin = swap(row, col, row + 1, col);
        return new Board(twin);
    }

    public boolean equals(Object y) { // does this board equal y?
        if (y == null || !(y instanceof Board))
            return false;
        if (this == y)
            return true;
        Board b = (Board) y;

        if (dimension() != b.dimension())
            return false;

        for (int i = 0; i < dimension(); i++)
            for (int j = 0; j < dimension(); j++)
                if (board[i][j] != b.board[i][j])
                    return false;

        return true;
    }
    public Iterable<Board> neighbors() { // all neighboring boards
        LinkedList<Board> neighbors = new LinkedList<>();
        int[] location = findZero();
        int row = location[0];
        int col = location[1];

        if (row > 0)
            neighbors.add(new Board(swap(row, col, row - 1, col)));
        if (row < dimension() - 1)
            neighbors.add(new Board(swap(row, col, row + 1, col)));
        if (col > 0)
            neighbors.add(new Board(swap(row, col, row, col - 1)));
        if (col < dimension() - 1)
            neighbors.add(new Board(swap(row, col, row, col + 1)));

        return neighbors;
    }

    public String toString() { // string representation of this board (in the output format specified below)
        StringBuilder str = new StringBuilder();
        str.append(dimension() + "\n");

        for (int[] row : board) {
            for (int cell : row)
                str.append(String.format("%2d ", cell));
            str.append("\n");
        }
        return str.toString();
    }

    public static void main(String[] args) {
    } // unit tests (not graded)


    private int getDistance(int row, int column) {
        int val = board[row][column];
        return Math.abs(row - destRow(val)) + Math.abs(column - destColumn(val));
    }

    private int fieldValue(int row, int column) {
        return row * dimension() + column + 1;
    }

    private int destRow(int val) {
        if (val == 0)
            return dimension() - 1;
        return (val - 1) / dimension();
    }

    private int destColumn(int val) {
        if (val == 0)
            return dimension() - 1;
        return (val - 1) % dimension();
    }

    private int[][] swap(int row1, int col1, int row2, int col2) {
        int[][] copy = copy(board);
        copy[row1][col1] = board[row2][col2];
        copy[row2][col2] = board[row1][col1];
        return copy;
    }

    private int[][] copy(int[][] matrix) {
        int[][] copy = new int[matrix.length][];
        for (int i = 0; i < matrix.length; i++)
            copy[i] = matrix[i].clone();
        return copy;
    }

    private int[] findZero() {
        int[] index = new int[2];
        for (int i = 0; i < dimension(); i++)
            for (int j = 0; j < dimension(); j++)
                if (board[i][j] == 0) {
                    index[0] = i;
                    index[1] = j;
                }
        return index;
    }
}