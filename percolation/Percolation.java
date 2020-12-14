/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 *****************************************************************************/

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.util.Arrays;

public class Percolation {
    private int[][] my_array;
    private int n_global;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) { // don't specify return type of the constructor
        if (n <= 0) {
            throw new IllegalArgumentException("Can't have 0 size array");
        }
        else {
            this.n_global = n;
            this.my_array = new int[n][n];
            // int[][] my_array = new int[n][n];
            // closed is 0
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    this.my_array[i][j] = 0;
                }
            }
        }
    }

    public int[][] returnArray() {
        return my_array;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row < 1 || col < 1 || row > this.n_global || col > this.n_global) {
            throw new IllegalArgumentException("Out of range");
        }
        else if (this.isOpen(row, col)) {
            throw new IllegalArgumentException("Already Open!");
        }
        else {
            this.my_array[row - 1][col - 1] = 1;
            // return my_array;
        }
    }

    // is the site (row, col) open? // 1 or 2
    public boolean isOpen(int row, int col) {
        if (row < 1 || col < 1 || row > this.n_global || col > this.n_global) {
            throw new IllegalArgumentException("Out of range");
        }
        else if (this.my_array[row - 1][col - 1] == 1 || this.my_array[row - 1][col - 1] == 2) {
            return true;
        }
        else {
            return false;
        }
    }

    // is the site (row, col) full? // 2
    public boolean isFull(int row, int col) {
        if (row < 1 || col < 1 || row > this.n_global || col > this.n_global) {
            throw new IllegalArgumentException("Out of range");
        }
        else if (this.my_array[row - 1][col - 1] == 2) {
            return true;
        }
        else {
            return false;
        }
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        int count = 0;
        for (int i = 0; i < this.n_global; i++) {
            for (int j = 0; j < this.n_global; j++) {
                if (this.my_array[i][j] == 0) {
                    count += 1;
                }
            }
        }
        return count;
    }

    // // // does the system percolate?
    public boolean percolates() {
        WeightedQuickUnionUF test_list = new WeightedQuickUnionUF(
                this.n_global * this.n_global + 2);
        for (int k = 0; k < this.n_global; k++) {
            test_list.union(0, k + 1);
            test_list.union(this.n_global * this.n_global + 1,
                            this.n_global * this.n_global - k);
        }
        for (int i = 0; i < this.n_global; i++) {
            for (int j = 0; j < this.n_global; j++) {
                int index = i * n_global + j + 1;
                if (this.isOpen(i + 1, j + 1)) {
                    int lowi = i - 1;
                    int highi = i + 1;
                    int lowj = j - 1;
                    int highj = j + 1;
                    if (lowi > 0 && this.isOpen(lowi + 1, j + 1)) {
                        int index2 = lowi * n_global + j + 1;
                        test_list.union(index, index2);
                    }
                    if (highi < this.n_global && this.isOpen(highi + 1, j + 1)) {
                        int index3 = highi * n_global + j + 1;
                        test_list.union(index, index3);
                    }
                    if (lowj > 0 && this.isOpen(i + 1, lowj + 1)) {
                        int index4 = i * n_global + lowj + 1;
                        test_list.union(index, index4);
                    }
                    if (highj < this.n_global && this.isOpen(i + 1, highj + 1)) {
                        int index5 = i * n_global + highj + 1;
                        test_list.union(index, index5);
                    }
                }
            }
        }
        if (test_list.find(0) == test_list.find(this.n_global * this.n_global + 1)) {
            return true;
        }
        else {
            return false;
        }
    }

    // test client (optional)
    public static void main(String[] args) {
        Percolation test_array = new Percolation(7);
        // int[][] output = test_array.returnArray();
        // System.out.println(Arrays.deepToString(output));
        test_array.open(1, 3);
        test_array.open(2, 3);
        test_array.open(3, 3);
        test_array.open(4, 3);
        test_array.open(5, 3);
        test_array.open(7, 3);
        int[][] output3 = test_array.returnArray();
        System.out.println(Arrays.deepToString(output3));
        System.out.println(test_array.percolates());
    }
}

