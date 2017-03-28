import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[][] grid;
    private int openSites = 0;
    private WeightedQuickUnionUF uf;

    public Percolation(int n) { // create n-by-n grid, with all sites blocked
        if (n <= 0)
            throw new IllegalArgumentException();
        uf = new WeightedQuickUnionUF(n*n+2);
        grid = new boolean[n][];
        for (int i = 0; i < n; i++) {
            grid[i] = new boolean[n];
        }
    }

    public void open(int row, int col) { // open site (row, col) if it is not open already
        if (row <= 0 || row > grid.length || col <= 0 || col > grid.length)
            throw new IndexOutOfBoundsException();
        if (isOpen(row, col))
            return;
        grid[row-1][col-1] = true;
        unionWithNeighbours(row, col);
        openSites++;
    }

    public boolean isOpen(int row, int col) { // is site (row, col) open?
        if (row <= 0 || row > grid.length || col <= 0 || col > grid.length)
            throw new IndexOutOfBoundsException();
        return grid[row-1][col-1];
    }

    public boolean isFull(int row, int col) { // is site (row, col) full?
        if (row <= 0 || row > grid.length || col <= 0 || col > grid.length)
            throw new IndexOutOfBoundsException();
        return uf.connected(0, index(row, col));
    }

    public int numberOfOpenSites() { // number of open sites
        return openSites;
    }

    public boolean percolates() {
        return uf.connected(0, grid.length*grid.length+1);
    }    // does the system percolate?

    public static void main(String[] args) {
        Percolation p = new Percolation(3);
        p.open(1,3);
        p.open(2,3);
        p.open(3,3);
        p.open(3,1);
        System.out.print(p.isFull(3,1));
    }
    private void unionWithNeighbours(int row, int col) {
        if (row == 1)
            uf.union(0, index(row, col));
        if (row == grid.length)
            uf.union(grid.length*grid.length+1, index(row, col));
        if (row != 1 && grid[row-2][col-1])
            uf.union(index(row, col), index(row-1, col));
        if (row != grid.length && grid[row][col-1])
            uf.union(index(row, col), index(row+1, col));
        if (col != 1 && grid[row-1][col-2])
            uf.union(index(row, col), index(row, col-1));
        if (col != grid.length && grid[row-1][col])
            uf.union(index(row, col), index(row, col+1));
    }

    private int index(int row, int col) {
        return (row - 1) * grid.length + col;
    }
}
