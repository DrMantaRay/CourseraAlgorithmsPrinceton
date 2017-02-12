import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Created by pchen on 2/7/2017.
 */
public class Board {
    private int[] zeroLocation = new int[2];
    private int dimension;
    private int[][] boardArray;

    private void exchange(int a , int b, int c, int d) {
        int val = boardArray[a][b];
        boardArray[a][b]=boardArray[c][d];
        boardArray[c][d]=val;
        if (boardArray[a][b] == 0) {
            zeroLocation[0] = a;
            zeroLocation[1] = b;
        }
        if (boardArray[c][d] == 0) {
            zeroLocation[0] = c;
            zeroLocation[1] = d;
        }
    }
    public Board(int[][] blocks) {
        dimension = blocks[0].length;
        boardArray = new int[dimension][dimension];
        for(int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                boardArray[i][j] = blocks[i][j];
                if (boardArray[i][j] == 0 ) {
                    zeroLocation[0]=i;
                    zeroLocation[1]=j;
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
                    manhattanSum+= Math.abs((boardArray[i][j]-1)/dimension - j) + Math.abs(boardArray[i][j]%dimension -i-1);
                }
            }
        }
        return manhattanSum;
    }
    public boolean isGoal() {
        return hamming() == 0;
    }
    public Board twin() {

        int val= boardArray[0][0];
        boardArray[0][0] = boardArray[0][1];
        boardArray[0][1] = val;
        Board twinBoard = new Board(boardArray);
        boardArray[0][1] = boardArray[0][0];
        boardArray[0][0] = val;
        return twinBoard;
    }
    public boolean equals (Object y) {
        return Arrays.deepEquals(((Board) y).boardArray, boardArray);
    }
    public Iterable<Board> neighbors() {
        ArrayList<Board> boardList = new ArrayList<>();
        if (zeroLocation[0] >= 0 && zeroLocation[0] < dimension-1) {
            Board newBoard = new Board(boardArray);
            newBoard.exchange(zeroLocation[0], zeroLocation[1], zeroLocation[0]+1, zeroLocation[1]);
            boardList.add(newBoard);
        }
        if (zeroLocation[0] < dimension && zeroLocation[0] > 0) {
            Board newBoard = new Board(boardArray);
            newBoard.exchange(zeroLocation[0], zeroLocation[1], zeroLocation[0]-1, zeroLocation[1]);
            boardList.add(newBoard);
        }
        if (zeroLocation[1] >= 0 && zeroLocation[1] < dimension-1) {
            Board newBoard = new Board(boardArray);
            newBoard.exchange(zeroLocation[0], zeroLocation[1], zeroLocation[0], zeroLocation[1] + 1);
            boardList.add(newBoard);
        }
        if  (zeroLocation[1] < dimension && zeroLocation[1] > 0) {
            Board newBoard = new Board(boardArray);
            newBoard.exchange(zeroLocation[0], zeroLocation[1], zeroLocation[0], zeroLocation[1] - 1);
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
        int[][] minArray = new int[2][2];
        for (int i = 0; i < 2; i++ ) {
            for (int j = 0; j < 2; j++) {
                minArray[i][j] = 2*i + j;
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
    }
}
