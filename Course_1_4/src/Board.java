import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Created by pchen on 2/7/2017.
 */
public class Board {
    private int zeroLocationx;
    private int zeroLocationy;
    private int dimension;
    private int[][] boardArray;

    private void exchange(int a , int b, int c, int d) {
        int val = boardArray[a][b];
        boardArray[a][b]=boardArray[c][d];
        boardArray[c][d]=val;
        if (boardArray[a][b] == 0) {
            zeroLocationx = a;
            zeroLocationy = b;
        }
        if (boardArray[c][d] == 0) {
            zeroLocationx = c;
            zeroLocationy = d;
        }
    }
    public Board(int[][] blocks) {
        dimension = blocks[0].length;
        boardArray = new int[dimension][dimension];
        for(int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                boardArray[i][j] = blocks[i][j];
                if (boardArray[i][j] == 0 ) {
                    zeroLocationx=i;
                    zeroLocationy=j;
                }
           }

       }
    }
    public int dimension() {
        return dimension;
    }
    public int hamming() {
        int count = 0;
        for (int i = 0; i < dimension; i++ ) {
            for (int j = 0; j < dimension; j++) {
                if (boardArray[i][j] != i * dimension + j + 1 && boardArray[i][j] != 0) {
                    count++;
                }
            }
        }
        return count;
    }
    public int manhattan() {
        int manhattanSum = 0;
        for (int i = 0; i < dimension; i++ ) {
            for (int j = 0; j < dimension; j++) {
                if (boardArray[i][j] != 0) {
                    manhattanSum+= (Math.abs((boardArray[i][j]-1)/dimension - i) + Math.abs((boardArray[i][j]-1)%dimension -j));
                }
            }
        }
        return manhattanSum;
    }
    public boolean isGoal() {
        return hamming() == 0;
    }
    public Board twin() {
        int first_i = 0;
        int first_j = 0;
        int second_i = 0;
        int second_j = 0;
        outerloop:
        {
            for (int i = 0; i < dimension; i++) {
                for (int j = 0; j < dimension; j++) {
                    if (boardArray[i][j] != 0) {
                        first_i = i;
                        first_j = j;
                        if (i >= 0 && i < dimension -1 && boardArray[i+1][j] != 0) {
                            second_i = i+1;
                            second_j = j;
                            break outerloop;
                        }
                        else if (i < dimension && i> 0 && boardArray[i-1][j] != 0) {
                            second_i = i-1;
                            second_j = j;
                            break outerloop;

                        }
                        else if (j >= 0 && j < dimension -1 && boardArray[i][j+1] != 0) {
                            second_i = i;
                            second_j = j + 1;
                            break outerloop;

                        }
                        else if (j < dimension && j > 0 && boardArray[i][j-1] != 0) {
                            second_i = i;
                            second_j = j - 1;
                            break outerloop;
                        }

                    }
                }
            }
        }
        exchange(first_i, first_j, second_i, second_j);
        Board twinBoard = new Board(boardArray);
        exchange(first_i, first_j, second_i, second_j);
        return twinBoard;
    }
    public boolean equals (Object y) {
        if (y == null) {
            return false;
        }
        else if (y instanceof Board) {
            return Arrays.deepEquals(((Board) y).boardArray, boardArray);
        }
        else {
            return false;
        }
    }
    public Iterable<Board> neighbors() {
        ArrayList<Board> boardList = new ArrayList<>();
        if (zeroLocationx >= 0 && zeroLocationx < dimension-1) {
            Board newBoard = new Board(boardArray);
            newBoard.exchange(zeroLocationx, zeroLocationy, zeroLocationx+1, zeroLocationy);
            boardList.add(newBoard);
        }
        if (zeroLocationx < dimension && zeroLocationx > 0) {
            Board newBoard = new Board(boardArray);
            newBoard.exchange(zeroLocationx, zeroLocationy, zeroLocationx-1, zeroLocationy);
            boardList.add(newBoard);
        }
        if (zeroLocationy >= 0 && zeroLocationy < dimension-1) {
            Board newBoard = new Board(boardArray);
            newBoard.exchange(zeroLocationx, zeroLocationy, zeroLocationx, zeroLocationy + 1);
            boardList.add(newBoard);
        }
        if  (zeroLocationy < dimension && zeroLocationy > 0) {
            Board newBoard = new Board(boardArray);
            newBoard.exchange(zeroLocationx, zeroLocationy, zeroLocationx, zeroLocationy - 1);
            boardList.add(newBoard);
        }
        return boardList;
    }
    public String toString() {
        String stringStore = "";
        stringStore = stringStore + dimension +"\n";
        int max_space = String.valueOf(dimension*dimension - 1).length();
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                int extra_space = (max_space - String.valueOf(boardArray[i][j]).length());
                String spaceString = new String(new char[extra_space]).replace("\0", " ");
                stringStore = stringStore + spaceString + " " + boardArray[i][j];
            }
            if (i < dimension - 1) {
                stringStore = stringStore + "\n";
            }
        }
        return stringStore;
    }
    public static void main (String[] args) {
        int[][] minArray = new int[3][3];
        for (int i = 0; i < 3; i++ ) {
            for (int j = 0; j < 3; j++) {
                minArray[i][j] = 3*i + j;
            }
        }
        Board newBoard = new Board(minArray);
        Board twinBoard = newBoard.twin();
        System.out.println(newBoard);
        System.out.println(twinBoard);
        Board new2Board = twinBoard.twin();
        System.out.println(newBoard.equals(twinBoard));
        System.out.println(newBoard.equals(new2Board));
        System.out.println(new2Board);
        for (Board board :newBoard.neighbors()) {
            System.out.println(board);
        }
        System.out.println(newBoard.manhattan());
    }
}
