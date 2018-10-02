/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs4750hw4;

/**
 *
 * @author Joey Crowe
 */
public class Node {
    private char[][] board;
    private int depth;
    private Node miniMaxChild;
    private double heuristic;
    
    
    //idea: implement heuristic as a double for comparison purposes. First, evaluate heuristic(int function) and set heuristic to float. 
    //Then divide board into strata based of of distance to center. IE center = starts at .7, middle ring = .4 outer ring = .0. 
    //Then add from (0-.3) of rand(). That way the minimax search can just find the min or max based off of one number and ties 
    //are already broken automatically
    
    public Node(){
        board = new char[6][6];//hard coded 6x6, could possible implement for all board sizes, but for simplicity sake
        
    }
    
    public Node(char board[][], int x, int y, char marker){
        this.board = new char[6][6];
        for (int i =0; i < 6; i++){//copy board
            for (int j=0; j < 6; j++){
                this.board[i][j] = board[i][j];
            }
        }
        this.board[x][y] = marker;
    }
    
    
    //algorithm expands  all available moves, updates heuristic based off of max or min, and returns itself to its parent 
    //uses a DFS which uses constant space. 
    // credit to https://en.wikipedia.org/wiki/Minimax for inspiration for the java algorithm
    public Node getNextNode(int depth, boolean isMaxPlayer){
        Node tempNode;
        if (this.isTerminal()){
            return this;
        }
        if (depth == 0){
            this.setHeuristic();
            return this;
        }
        if (isMaxPlayer){
            this.heuristic = Double.NEGATIVE_INFINITY;
            for (int i =0; i < 6; i++ ){//hard coded 6
                for (int j = 0; j < 6 ; j++){
                    if (isValid(board[i][j])){
                        tempNode = new Node(this.board, i,j,'x');//change char to what max plays
                        tempNode = this.getNextNode( depth - 1, false);
                        if (tempNode.heuristic > this.heuristic){
                            this.heuristic = tempNode.heuristic;
                            this.miniMaxChild = tempNode;
                        }
                    }
                }
            }
            return this;
        } else {
            this.heuristic = Double.POSITIVE_INFINITY;
            for (int i =0; i < 6; i++ ){//hard coded 6
                for (int j = 0; j < 6 ; j++){
                    if (isValid(board[i][j])){
                        tempNode = new Node(this.board, i,j,'o');//change char to whatever min plays
                        tempNode = this.getNextNode( depth - 1, true);
                        if (tempNode.heuristic < this.heuristic){
                            this.heuristic = tempNode.heuristic;
                            this.miniMaxChild = tempNode;
                        }
                    }
                }
            }
            return this;
        }
        
    }
    
    public boolean isValid(char x){
        return (x == '-');//char for blank spaces
    }
    
    //evaluate whether this is a "solution", should also set the heuristic of this node to a really large negative or positve number
    // based upon whether player one or player two is moving
    public boolean isTerminal(){
        return false;
    }
    //implement heuristic function, looks like one of the most complicated pieces of code, consider examining comment 
    //above by heuristic for possible implementation for tiebreakers
    public void setHeuristic(){
        
    }
    
}
