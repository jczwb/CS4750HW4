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
    private double heuristic;
    //idea: implement heuristic as a double for comparison purposes. First, evaluate heuristic(int function) and set heuristic to float. 
    //Then divide board into strata based of of distance to center. IE center = starts at .7, middle ring = .4 outer ring = .0. 
    //Then add from (0-.3) of rand(). That way the minimax search can just find the min or max based off of one number and ties 
    //are already broken automatically
    public Node(){
        board = new char[6][6];
        
       
    }
    
    
    //algorithm expands  all available moves, updates heuristic based off of max or min, and returns itself to its parent 
    //uses a DFS which uses constant space. 
    // credit to https://en.wikipedia.org/wiki/Minimax for inspiration for the java algorithm
    public Node getNextNode(int depth, boolean isMaxPlayer){
        if (this.isTerminal()){
            return this;
        }
        if (depth == 0){
            this.setHeuristic();
            return this;
        }
        if (isMaxPlayer){
            return this;
        } else {
            return this;
        }
        
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
