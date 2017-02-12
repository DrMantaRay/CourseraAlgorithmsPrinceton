
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by pchen on 2/7/2017.
 */
public class Solver {
    private Node final_node;
    private int moves;
    private boolean solvable;
    private class Node implements Comparable<Node> {
        Board current_board;
        int num_moves;
        Node previous_node;
        public Node() {
            current_board = null;
            previous_node = null;
            num_moves = 0;
        }
        public Node(Board b, int m, Node n) {
            current_board = b;
            num_moves = m;
            previous_node = n;
        }
        public int compareTo(Node b) {
            if (current_board.hamming() > b.current_board.hamming()) {
                return 1;
            } else if (current_board.hamming() < b.current_board.hamming()) {
                return -1;
            } else return 0;
        }
    }
    public Solver(Board initial) {
        moves = 0;
        Node start_node = new Node();
        Node twin_start_node = new Node();
        start_node.current_board = initial;
        start_node.num_moves = moves;
        twin_start_node.current_board = initial.twin();
        twin_start_node.num_moves = moves;
        Node cur_node = start_node;
        Node twin_cur_node = twin_start_node;

        if (initial == null) {
            throw new NullPointerException();
        }
        MinPQ<Node> boardPQ = new MinPQ<>();
        MinPQ<Node> twinboardPQ = new MinPQ<>();
        boardPQ.insert(cur_node);
        twinboardPQ.insert(twin_cur_node);
        Node temp_node;
        while (true) {
            moves++;
            cur_node = boardPQ.delMin();
            System.out.println(cur_node.current_board);
            System.out.println("--");
            twin_cur_node = twinboardPQ.delMin();
            if (cur_node.current_board.isGoal()) {
                solvable = true;
                final_node = cur_node;
                break;
            }
            if (twin_cur_node.current_board.isGoal()) {
                solvable = false;
                break;
            }
            for (Board board: cur_node.current_board.neighbors()) {
                if (moves > 1 && board == cur_node.previous_node.current_board) {
                    continue;
                }
                temp_node = new Node();
                temp_node.current_board = board;
                temp_node.previous_node = cur_node;
                temp_node.num_moves = moves;
                boardPQ.insert(temp_node);
            }

            for (Board twin_board: twin_cur_node.current_board.neighbors()) {
                if (moves > 1 && twin_board == twin_cur_node.previous_node.current_board) {
                    continue;
                }
                temp_node = new Node();
                temp_node.current_board = twin_board;
                temp_node.previous_node = twin_cur_node;
                temp_node.num_moves = moves;
                twinboardPQ.insert(temp_node);
            }
        }
    }
    public boolean isSolvable() {
        return solvable;
    }
    public int moves() {
        return moves;
    }
    public Iterable<Board> solution() {
        if (solvable) {
            ArrayList<Board> solutionList = new ArrayList<>();
            Node cur_node = final_node;
            while (cur_node.previous_node != null) {
                solutionList.add(final_node.current_board);
                cur_node = cur_node.previous_node;
            }
            Collections.reverse(solutionList);
            return solutionList;
        }
        else {
            return null;
        }
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
}
