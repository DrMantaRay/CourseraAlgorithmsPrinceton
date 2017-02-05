

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int[][] matrix;
    private int dim;
    private int numOpenSites = 0;
    private WeightedQuickUnionUF sites;

    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n must be less than or equal to 0");
        } else {
            dim = n;
            matrix = new int[dim][dim];
            sites = new WeightedQuickUnionUF(dim * dim + 2);
            for (int i = 1; i <= dim; i++) {
                sites.union(0, i);
                sites.union(dim * dim + 1, dim * dim + 1 - i);
            }

        }
    }
    public int numberOfOpenSites() {
        return numOpenSites;
    }
    private boolean isInBounds(int row, int col) {
        return (row >= 1 && col >= 1 && row <= dim && col <= dim);
    }
    public void open(int row, int col) {
        if (isInBounds(row, col)) {
            if (isOpen(row,col)){
                return;
            }
            matrix[row - 1][col - 1] = 1;
            numOpenSites++;
            if (row-1 >= 1 && this.isOpen(row-1, col)) {
                sites.union(coordToArrayInt(row, col), coordToArrayInt(row-1, col));
            }
            if (row+1 <= dim && this.isOpen(row+1, col)) {
                sites.union(coordToArrayInt(row, col), coordToArrayInt(row+1, col));
            }
            if (col-1 >= 1 && this.isOpen(row, col-1)) {
                sites.union(coordToArrayInt(row, col), coordToArrayInt(row, col-1));
            }
            if (col+1 <= dim && this.isOpen(row, col+1)) {
                sites.union(coordToArrayInt(row, col), coordToArrayInt(row, col + 1));
            }
        }
        else {
            throw new IndexOutOfBoundsException("row and column must be greater than 0 and less than or equal to dimension");
        }
    }
    public boolean isOpen(int row, int col) {
        if (isInBounds(row, col)) {
            return matrix[row-1][col-1] == 1;
        }
        else {
            throw new IndexOutOfBoundsException();
        }
    }
    public boolean isFull(int row, int col) {
        if (isInBounds(row, col)) {
            return isOpen(row, col) && sites.connected(0,coordToArrayInt(row, col));
        }
        else {
            throw new IndexOutOfBoundsException();
        }
    }
    private int coordToArrayInt(int i, int j) {
        return dim*(i-1)+j;
    }
    public boolean percolates() {
        return sites.connected(0, dim*dim+1);
    }
    public static void main(String[] args) {
        Percolation pTest = new Percolation(3);
        pTest.open(3, 3);
        pTest.open(3, 2);
        System.out.println(pTest.isFull(1, 3));
        System.out.println(pTest.percolates());

    }
}