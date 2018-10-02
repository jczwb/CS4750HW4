/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs4750hw4;

/**
 *
 * @author Joey Crowe, Danny Jakle
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
    // obtained four in a row portion from http://www.ntu.edu.sg/home/ehchua/programming/java/JavaGame_TicTacToe.html, inferred the rest from this
    public boolean isTerminal(boolean isMaxPlayer, int selectedRow, int selectedColumn){
        int count = 0;
        char currentPlayer;
        
        //set which player is currently taking a turn
        if(isMaxPlayer){
            currentPlayer = 'x';
        }
        else{
            currentPlayer = 'o';
        }
        
        //check for four in a row horizontally
        for(int col = 0; col < 6; col++){
            if (board[selectedRow][col] == currentPlayer){
                count++;
                if(count == 4){ //four in a row found
                    return true;
                }
            }
            else{
                count = 0; //reset counter if tokens are not consecutive
            }
            
        }
        
        //check for four in a row vertically
        for(int row = 0; row < 6; row++){
            if (board[row][selectedColumn] == currentPlayer){
                count++;
                if(count == 4){ //four in a row found
                    return true;
                }
            }
            else{
                count = 0; //reset counter if tokens are not consecutive
            }
        }
        
        //check for four in a row diagonally
        switch(selectedRow - selectedColumn){
            case 0: //place along diagonal line from 0,0 to 5,5
                for(int i = 0; i < 6; i++){
                    if (board[i][i] == currentPlayer){
                        count++;
                        if(count == 4){
                            return true;
                        }
                    }
                    else{
                        count = 0;
                    }
                }
                break;
            case 1: //place along diagonal line from 1,0 to 5,4
                for(int i = 1; i < 6; i++){
                    if (board[i][i-1] == currentPlayer){
                        count++;
                        if(count == 4){
                            return true;
                        }
                    }
                    else{
                        count = 0;
                    }
                }
                break;
            case 2: //place along diagonal line from 2,0 to 5,3
                for(int i = 2; i < 6; i++){
                    if (board[i][i-2] == currentPlayer){
                        count++;
                        if(count == 4){
                            return true;
                        }
                    }
                    else{
                        count = 0;
                    }
                }
                break;
            case -1: //place along diagonal line from 0,1 to 4,5
                for(int i = 0; i < 5; i++){
                    if (board[i][i+1] == currentPlayer){
                        count++;
                        if(count == 4){
                            return true;
                        }
                    }
                    else{
                        count = 0;
                    }
                }
                break;
            case -2: //place along diagonal line from 0,2 to 3,5
                for(int i = 0; i < 4; i++){
                    if (board[i][i+2] == currentPlayer){
                        count++;
                        if(count == 4){
                            return true;
                        }
                    }
                    else{
                        count = 0;
                    }
                }
                break;
            default: //place in corner area that does not have 4 diagonal spaces to cover
                break;
        }
        
        //check for four in a row opposite-diagonally
        switch(selectedColumn - (5-selectedRow)){ //adjust for declining order in row
            case 0: //place along opposite-diagonal line from 0,5 to 5,0
                for(int i = 0; i < 6; i++){
                    if (board[i][5-i] == currentPlayer){
                        count++;
                        if(count == 4){
                            return true;
                        }
                    }
                    else{
                        count = 0;
                    }
                }
                break;
            case 1: //place along opposite-diagonal line from 1,5 to 5,1
                for(int i = 1; i < 6; i++){
                    if (board[i][6-i] == currentPlayer){
                        count++;
                        if(count == 4){
                            return true;
                        }
                    }
                    else{
                        count = 0;
                    }
                }
                break;
            case 2: //place along diagonal line from 2,5 to 5,2
                for(int i = 2; i < 6; i++){
                    if (board[i][7-i] == currentPlayer){
                        count++;
                        if(count == 4){
                            return true;
                        }
                    }
                    else{
                        count = 0;
                    }
                }
                break;
            case -1: //place along diagonal line from 0,4 to 4,0
                for(int i = 0; i < 5; i++){
                    if (board[i][4-i] == currentPlayer){
                        count++;
                        if(count == 4){
                            return true;
                        }
                    }
                    else{
                        count = 0;
                    }
                }
                break;
            case -2: //place along diagonal line from 0,3 to 3,0
                for(int i = 0; i < 4; i++){
                    if (board[i][3-i] == currentPlayer){
                        count++;
                        if(count == 4){
                            return true;
                        }
                    }
                    else{
                        count = 0;
                    }
                }
                break;
            default: //place in corner area that does not have 4 diagonal spaces to cover
                break;
        }
        
        return false;
    }
    
    
    //implement heuristic function, looks like one of the most complicated pieces of code, consider examining comment 
    //above by heuristic for possible implementation for tiebreakers
    public void setHeuristic(){
        
    }
    
}
