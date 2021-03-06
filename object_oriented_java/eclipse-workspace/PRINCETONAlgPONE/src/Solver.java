import java.util.Collections;
import java.util.LinkedList;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
//    private MinPQ<searchNode> pq;
    private boolean isSolve;
    private int moveNum;
//    private MinPQ<searchNode> pq2;
    private final LinkedList<Board> traceList;
    
    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial){
    	if(initial == null) {
    		throw new IllegalArgumentException();
    	}
    	
    	MinPQ<searchNode> pq = new MinPQ<searchNode>();
        searchNode sn = new searchNode(initial,0,null);
        pq.insert(sn);

        Board twinBoard = initial.twin();
        MinPQ<searchNode> pq2 = new MinPQ<searchNode>();
        searchNode sn2 = new searchNode(twinBoard,0,null);
        pq2.insert(sn2);

        traceList = new LinkedList<Board>();

        while(true) {
            sn = pq.delMin();
//            StdOut.println(sn);
            if(sn.getBoard().isGoal()){ 
                isSolve = true;
                moveNum = sn.getMoveNum();
                break; 
            }
            for(Board neiBoard : sn.getBoard().neighbors()) {
//              StdOut.println(neiBoard);
                searchNode preSN = sn.getPredecessor();
                if(preSN != null && neiBoard.equals(preSN.getBoard())){ continue; }
                searchNode neinode= new searchNode(neiBoard,sn.getMoveNum()+1,sn);
                pq.insert(neinode);
//                StdOut.println(neinode);
            }

            sn2 = pq2.delMin();
            if(sn2.getBoard().isGoal()){ 
                isSolve = false;
                break; 
            }
            for(Board neiBoard : sn2.getBoard().neighbors()) {
                searchNode preSN = sn2.getPredecessor();
                if(preSN != null && neiBoard.equals(preSN.getBoard())){ continue; }
                searchNode neinode= new searchNode(neiBoard,sn2.getMoveNum()+1,sn2);
                pq2.insert(neinode);
            }
        }

        if(isSolve){
            traceList.add(sn.getBoard());
            while(sn.getPredecessor() != null){
                traceList.add(sn.getPredecessor().getBoard());
                sn = sn.getPredecessor();
            }
            Collections.reverse(traceList);
        }
    }

    private class searchNode implements Comparable<searchNode>{
        private final int moveNum;
        private final int manhaValue;
        private final int manhaPri;
        private final searchNode predecessor;
        private final Board board;

        public searchNode(Board board,int moveNum,searchNode predecessor){
            this.moveNum = moveNum;
            this.manhaValue = board.manhattan();
            this.manhaPri = this.moveNum + this.manhaValue;
            this.predecessor = predecessor;
            this.board = board;
        }

        public int getManhaPri(){
            return manhaPri;
        }

        public searchNode getPredecessor(){
            return predecessor;
        }

        public int getMoveNum(){
            return moveNum;
        }

        public Board getBoard(){
            return board;
        }
        
        public int compareTo(searchNode other) {
            int s1 = this.getManhaPri();
            int s2 = other.getManhaPri();
            if (s1 == s2){
                return 0;
            }
            else if (s1 < s2){
                return -1;
            }
            else{
                return 1;
            }
        }

    }

    // is the initial board solvable?
    public boolean isSolvable(){
        return isSolve;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves(){
        if(isSolve) {
            return moveNum;
        }
        else {
            return -1;
        }
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution(){
        if(isSolve) {
            return traceList;
        }
        else {
            return null;
        }
    }

    // solve a slider puzzle (given below)
    public static void main(String[] args){
        // create initial board from file
//    	In in = new In("C:\\Users\\Arthur Lance\\Downloads\\8puzzle-testing\\8puzzle\\puzzle04.txt");
//        In in = new In("C:\\Users\\Arthur Lance\\Downloads\\8puzzle-testing\\8puzzle\\puzzle07.txt");
    	In in = new In("C:\\Users\\Arthur Lance\\Downloads\\8puzzle-testing\\8puzzle\\puzzle10.txt");
//    	In in = new In("C:\\Users\\Arthur Lance\\Downloads\\8puzzle-testing\\8puzzle\\puzzle20.txt");
//        In in = new In("C:\\Users\\Arthur Lance\\Downloads\\8puzzle-testing\\8puzzle\\puzzle3x3-unsolvable.txt");
//    	In in = new In("C:\\Users\\Arthur Lance\\Downloads\\8puzzle-testing\\8puzzle\\puzzle3x3-unsolvable2.txt");
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);
        // for (Board board : solver.ll){
        //     StdOut.println(board);  
        // }
      
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