import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Random;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Board {
    private final int[][] blocks;
    private final int n;
    private final int[][] goalBoard;
    private final int zeroPosition;
    private final int[][] twinBlocks;

    // construct a board from an n-by-n array of blocks
    // (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks) {
        this.n = blocks[0].length;

        goalBoard = new int[n][n]; 
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                goalBoard[i][j] = i*n+j+1;
            }
        }
        goalBoard[n-1][n-1] = 0;

        this.blocks = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                this.blocks[i][j] = blocks[i][j];
            }
        }
        
        zeroPosition = getZeroPosition();
        twinBlocks = produceTwin();
    }
    
    // board dimension n
    public int dimension() {
        return n;
    }

    // number of blocks out of place
    public int hamming() {
        int res = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (blocks[i][j] == 0) {
                    continue;
                }
                if (blocks[i][j] != goalBoard[i][j]) {
                    res += 1;
                }
            }
        }
        return res;
    }

    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        int res = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (blocks[i][j] == 0) {
                    continue;
                }
                int x = (blocks[i][j]-1) / n;
                int y = (blocks[i][j]-1) % n;
                res += Math.abs(i-x)+Math.abs(j-y);
            }
        }
        return res;
    }

    // is this board the goal board?
    public boolean isGoal() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (blocks[i][j] != goalBoard[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    private int[][] produceTwin() {
        Random rand = new Random();
        int randNum1 = 0;
        while (true) {
            randNum1 = rand.nextInt(n*n)+1;
            int x = (randNum1-1) / n;
            int y = (randNum1-1) % n;
            if(blocks[x][y] != 0){
                break;
            }
        }

        int randNum2 = 0;
        while(true){
            randNum2 = rand.nextInt(n*n)+1;
            int x = (randNum2-1)/n;
            int y = (randNum2-1)%n;
            if(blocks[x][y] != 0 && randNum2 != randNum1){
                break;
            }
        }

        int[][] twinBlocks = new int[n][n];
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                twinBlocks[i][j] = blocks[i][j];
            }
        }
        int temp = twinBlocks[(randNum1-1)/n][(randNum1-1)%n];
        twinBlocks[(randNum1-1)/n][(randNum1-1)%n] = twinBlocks[(randNum2-1)/n][(randNum2-1)%n];
        twinBlocks[(randNum2-1)/n][(randNum2-1)%n] = temp;
        return twinBlocks;
    }

    // a board that is obtained by exchanging any pair of blocks
    public Board twin(){
        return new Board(twinBlocks);
    }

    // does this board equal y?
    public boolean equals(Object y){
        if(y == this) {return true;}

        if(y == null){return false;}
        
        if(y.getClass() != this.getClass()) {
        	return false;
        }

        Board that = (Board) y;
        if(Arrays.deepEquals(this.blocks, that.blocks)) {
        	return true;
        }
        return false;
    }

    private int getZeroPosition(){
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                if(blocks[i][j] == 0){
                    return i*n+j+1;
                }
            }
        }
        return -1;
    }

    // all neighboring boards
    public Iterable<Board> neighbors(){
        return new Iterable<Board>(){
            @Override
            public Iterator<Board> iterator()
            {
                return new neighborIterator();
            }
        };
    }

    private class neighborIterator implements Iterator<Board>
    {
        private final int x = (zeroPosition-1)/n;
        private final int y = (zeroPosition-1)%n;
        private int flag1 = 0;
        private int flag2 = 0;
        private int flag3 = 0;
        private int flag4 = 0;
        private int allCount = 0;
        private int count = 0;
        
        public neighborIterator() {
            if(x > 0){
                flag1 = 1;
            }
            if(y > 0){
                flag2 = 1;
            }
            if(x < n-1){
                flag3 = 1;
            }
            if(y < n-1){
                flag4 = 1;
            }

            allCount = flag1 + flag2 + flag3 + flag4;
        }


        public boolean hasNext() { 
            return count != allCount; 
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Board next(){
            if (count == allCount) {
               throw new NoSuchElementException();
            }

            if(flag1 == 1) {
                flag1 = 0;
                count += 1;

                int[][] neighBoard = new int[n][n];
                for(int i = 0; i < n; i++){
                    for(int j = 0; j < n; j++){
                        neighBoard[i][j] = blocks[i][j];
                    }
                }
                int temp = neighBoard[x][y];
                neighBoard[x][y] = neighBoard[x-1][y];
                neighBoard[x-1][y] = temp;
                return new Board(neighBoard);
            }
            if(flag2 == 1) {
                flag2 = 0;
                count += 1;

                int[][] neighBoard = new int[n][n];
                for(int i = 0; i < n; i++){
                    for(int j = 0; j < n; j++){
                        neighBoard[i][j] = blocks[i][j];
                    }
                }
                int temp = neighBoard[x][y];
                neighBoard[x][y] = neighBoard[x][y-1];
                neighBoard[x][y-1] = temp;
                return new Board(neighBoard);
            }
            if(flag3 == 1) {
                flag3 = 0;
                count += 1;

                int[][] neighBoard = new int[n][n];
                for(int i = 0; i < n; i++){
                    for(int j = 0; j < n; j++){
                        neighBoard[i][j] = blocks[i][j];
                    }
                }
                int temp = neighBoard[x][y];
                neighBoard[x][y] = neighBoard[x+1][y];
                neighBoard[x+1][y] = temp;
                return new Board(neighBoard);
            }
            if(flag4 == 1) {
                flag4 = 0;
                count += 1;

                int[][] neighBoard = new int[n][n];
                for(int i = 0; i < n; i++){
                    for(int j = 0; j < n; j++){
                        neighBoard[i][j] = blocks[i][j];
                    }
                }
                int temp = neighBoard[x][y];
                neighBoard[x][y] = neighBoard[x][y+1];
                neighBoard[x][y+1] = temp;
                return new Board(neighBoard);
            }

            return new Board(new int[n][n]);
        }
    }

    // string representation of this board (in the output format specified below)
    public String toString(){
        StringBuilder res = new StringBuilder("\n");
        res.append(n);
        for(int i = 0; i < n; i++){
        	res.append("\n");
            for(int j = 0; j < n; j++){
                res.append(" ");
                res.append(blocks[i][j]);
                res.append(" ");
            }
        }
        return res.toString();
    }

    // unit tests (not graded)
    public static void main(String[] args){
        // create initial board from file
//        In in = new In("C:\\Users\\Arthur Lance\\Downloads\\8puzzle-testing\\8puzzle\\puzzle04.txt");
        In in = new In("C:\\Users\\Arthur Lance\\Downloads\\8puzzle-testing\\8puzzle\\puzzle10.txt");
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);
        
        StdOut.println(initial);
        for (Board board : initial.neighbors()){
            StdOut.println(board);    
        }
        
//        StdOut.println(initial.twin());
//        StdOut.println(initial.hamming());
//        StdOut.println(initial.manhattan());
        
        StringBuilder res = new StringBuilder("");
        for(int i = 0; i < n; i++){
        	res.append("\n");
            for(int j = 0; j < n; j++){
                res.append(" ");
                res.append(initial.goalBoard[i][j]);
                res.append(" ");
            }
        }
        StdOut.println(res);

        if(initial.equals(initial)){
            StdOut.println("true");
        }
        else{
            StdOut.println("false");
        }

        
        In in2 = new In("C:\\Users\\Arthur Lance\\Downloads\\8puzzle-testing\\8puzzle\\puzzle10.txt");
        n = in2.readInt();
        int[][] blocks2 = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks2[i][j] = in2.readInt();
        Board initial2 = new Board(blocks2);
        
        if(initial.equals(initial2)){
            StdOut.println("true");
        }
        else{
            StdOut.println("false");
        }

    }
}