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
    public boolean isTerminal(){
        int count = 1;
        char prevSpace = '-';
        
        //check for four in a row horizontally
        for(int row = 0; row < 6; row++){
            for(int col = 0; col < 6; col++){
                if (board[row][col] == '-'){
                    prevSpace = '-';
                    count = 1;
                }
                else if (board[row][col] == 'x'){
                    if (prevSpace == 'x'){
                        count++;
                    } else count = 1; //reset counter if changed
                    prevSpace = 'x';
                    if(count == 4){ //four in a row found
                        this.heuristic = Double.NEGATIVE_INFINITY;
                        return true;
                    }
                }
                else if (board[row][col] == 'o'){
                    if (prevSpace == 'o'){
                        count++;
                    } else count = 1; //reset counter if changed
                    prevSpace = 'o';
                    if(count == 4){ //four in a row found
                        this.heuristic = Double.POSITIVE_INFINITY;
                        return true;
                    }
                }
            }
        } 
        //end horizontal chaeck
        
        //check for four in a row vertically
        for(int col = 0; col < 6; col++){
            for(int row = 0; row < 6; row++){
                if (board[row][col] == '-'){
                    prevSpace = '-';
                    count = 1;
                }
                else if (board[row][col] == 'x'){
                    if (prevSpace == 'x'){
                        count++;
                    } else count = 1; //reset counter if changed
                    prevSpace = 'x';
                    if(count == 4){ //four in a row found
                        this.heuristic = Double.NEGATIVE_INFINITY;
                        return true;
                    }
                }
                else if (board[row][col] == 'o'){
                    if (prevSpace == 'o'){
                        count++;
                    } else count = 1; //reset counter if changed
                    prevSpace = 'o';
                    if(count == 4){ //four in a row found
                        this.heuristic = Double.POSITIVE_INFINITY;
                        return true;
                    }
                }
            }
        } 
        //end vertical check
        
        //check for four in a row diagonally using the following five for-loops
         
        for(int i = 0; i < 6; i++){ //check along diagonal line from 0,0 to 5,5
            if (board[i][i] == '-'){
                prevSpace = '-';
                count = 1;
            }
            else if (board[i][i] == 'x'){
                if (prevSpace == 'x'){
                    count++;
                } else count = 1; //reset counter if changed
                prevSpace = 'x';
                if(count == 4){ //four in a row found
                    this.heuristic = Double.NEGATIVE_INFINITY;
                    return true;
                }
            }
            else if (board[i][i] == 'o'){
                if (prevSpace == 'o'){
                    count++;
                } else count = 1; //reset counter if changed
                prevSpace = 'o';
                if(count == 4){ //four in a row found
                    this.heuristic = Double.POSITIVE_INFINITY;
                    return true;
                }
            }
        }

        for(int i = 1; i < 6; i++){ //check along diagonal line from 1,0 to 5,4
            if (board[i][i-1] == '-'){
                prevSpace = '-';
                count = 1;
            }
            else if (board[i][i-1] == 'x'){
                if (prevSpace == 'x'){
                    count++;
                } else count = 1; //reset counter if changed
                prevSpace = 'x';
                if(count == 4){ //four in a row found
                    this.heuristic = Double.NEGATIVE_INFINITY;
                    return true;
                }
            }
            else if (board[i][i-1] == 'o'){
                if (prevSpace == 'o'){
                    count++;
                } else count = 1; //reset counter if changed
                prevSpace = 'o';
                if(count == 4){ //four in a row found
                    this.heuristic = Double.POSITIVE_INFINITY;
                    return true;
                }
            }
        }

        for(int i = 2; i < 6; i++){ //check along diagonal line from 2,0 to 5,3
            if (board[i][i-2] == '-'){
                prevSpace = '-';
                count = 1;
            }
            else if (board[i][i-2] == 'x'){
                if (prevSpace == 'x'){
                    count++;
                } else count = 1; //reset counter if changed
                prevSpace = 'x';
                if(count == 4){ //four in a row found
                    this.heuristic = Double.NEGATIVE_INFINITY;
                    return true;
                }
            }
            else if (board[i][i-2] == 'o'){
                if (prevSpace == 'o'){
                    count++;
                } else count = 1; //reset counter if changed
                prevSpace = 'o';
                if(count == 4){ //four in a row found
                    this.heuristic = Double.POSITIVE_INFINITY;
                    return true;
                }
            }
        }

        for(int i = 0; i < 5; i++){ //check along diagonal line from 0,1 to 4,5
            if (board[i][i+1] == '-'){
                prevSpace = '-';
                count = 1;
            }
            else if (board[i][i+1] == 'x'){
                if (prevSpace == 'x'){
                    count++;
                } else count = 1; //reset counter if changed
                prevSpace = 'x';
                if(count == 4){ //four in a row found
                    this.heuristic = Double.NEGATIVE_INFINITY;
                    return true;
                }
            }
            else if (board[i][i+1] == 'o'){
                if (prevSpace == 'o'){
                    count++;
                } else count = 1; //reset counter if changed
                prevSpace = 'o';
                if(count == 4){ //four in a row found
                    this.heuristic = Double.POSITIVE_INFINITY;
                    return true;
                }
            }
        }

        for(int i = 0; i < 4; i++){ //check along diagonal line from 0,2 to 3,5
            if (board[i][i+2] == '-'){
                prevSpace = '-';
                count = 1;
            }
            else if (board[i][i+2] == 'x'){
                if (prevSpace == 'x'){
                    count++;
                } else count = 1; //reset counter if changed
                prevSpace = 'x';
                if(count == 4){ //four in a row found
                    this.heuristic = Double.NEGATIVE_INFINITY;
                    return true;
                }
            }
            else if (board[i][i+2] == 'o'){
                if (prevSpace == 'o'){
                    count++;
                } else count = 1; //reset counter if changed
                prevSpace = 'o';
                if(count == 4){ //four in a row found
                    this.heuristic = Double.POSITIVE_INFINITY;
                    return true;
                }
            }
        }
        
        //end diagonal check
                        
        //check for four in a row opposite-diagonally using the following five for-loops
        
        for(int i = 0; i < 6; i++){ //check along opposite-diagonal line from 0,5 to 5,0
            if (board[i][5-i] == '-'){
                prevSpace = '-';
                count = 1;
            }
            else if (board[i][5-i] == 'x'){
                if (prevSpace == 'x'){
                    count++;
                } else count = 1; //reset counter if changed
                prevSpace = 'x';
                if(count == 4){ //four in a row found
                    this.heuristic = Double.NEGATIVE_INFINITY;
                    return true;
                }
            }
            else if (board[i][5-i] == 'o'){
                if (prevSpace == 'o'){
                    count++;
                } else count = 1; //reset counter if changed
                prevSpace = 'o';
                if(count == 4){ //four in a row found
                    this.heuristic = Double.POSITIVE_INFINITY;
                    return true;
                }
            }
        }

        for(int i = 1; i < 6; i++){ //check along opposite-diagonal line from 1,5 to 5,1
            if (board[i][6-i] == '-'){
                prevSpace = '-';
                count = 1;
            }
            else if (board[i][6-i] == 'x'){
                if (prevSpace == 'x'){
                    count++;
                } else count = 1; //reset counter if changed
                prevSpace = 'x';
                if(count == 4){ //four in a row found
                    this.heuristic = Double.NEGATIVE_INFINITY;
                    return true;
                }
            }
            else if (board[i][6-i] == 'o'){
                if (prevSpace == 'o'){
                    count++;
                } else count = 1; //reset counter if changed
                prevSpace = 'o';
                if(count == 4){ //four in a row found
                    this.heuristic = Double.POSITIVE_INFINITY;
                    return true;
                }
            }
        }

        for(int i = 2; i < 6; i++){ //check along opposite-diagonal line from 2,5 to 5,2
            if (board[i][7-i] == '-'){
                prevSpace = '-';
                count = 1;
            }
            else if (board[i][7-i] == 'x'){
                if (prevSpace == 'x'){
                    count++;
                } else count = 1; //reset counter if changed
                prevSpace = 'x';
                if(count == 4){ //four in a row found
                    this.heuristic = Double.NEGATIVE_INFINITY;
                    return true;
                }
            }
            else if (board[i][7-i] == 'o'){
                if (prevSpace == 'o'){
                    count++;
                } else count = 1; //reset counter if changed
                prevSpace = 'o';
                if(count == 4){ //four in a row found
                    this.heuristic = Double.POSITIVE_INFINITY;
                    return true;
                }
            }
        }

        for(int i = 0; i < 5; i++){ //check along opposite-diagonal line from 0,4 to 4,0
            if (board[i][4-i] == '-'){
                prevSpace = '-';
                count = 1;
            }
            else if (board[i][4-i] == 'x'){
                if (prevSpace == 'x'){
                    count++;
                } else count = 1; //reset counter if changed
                prevSpace = 'x';
                if(count == 4){ //four in a row found
                    this.heuristic = Double.NEGATIVE_INFINITY;
                    return true;
                }
            }
            else if (board[i][4-i] == 'o'){
                if (prevSpace == 'o'){
                    count++;
                } else count = 1; //reset counter if changed
                prevSpace = 'o';
                if(count == 4){ //four in a row found
                    this.heuristic = Double.POSITIVE_INFINITY;
                    return true;
                }
            }
        }

        for(int i = 0; i < 4; i++){ //check along opposite-diagonal line from 0,3 to 3,0
            if (board[i][3-i] == '-'){
                prevSpace = '-';
                count = 1;
            }
            else if (board[i][3-i] == 'x'){
                if (prevSpace == 'x'){
                    count++;
                } else count = 1; //reset counter if changed
                prevSpace = 'x';
                if(count == 4){ //four in a row found
                    this.heuristic = Double.NEGATIVE_INFINITY;
                    return true;
                }
            }
            else if (board[i][3-i] == 'o'){
                if (prevSpace == 'o'){
                    count++;
                } else count = 1; //reset counter if changed
                prevSpace = 'o';
                if(count == 4){ //four in a row found
                    this.heuristic = Double.POSITIVE_INFINITY;
                    return true;
                }
            }
        }
        
        return false;
    }
    
    
    //implement heuristic function, looks like one of the most complicated pieces of code, consider examining comment 
    //above by heuristic for possible implementation for tiebreakers
    
    public static int heuristic(char whoseTurn, char[][] board) {
        char opponent;
        
        if(whoseTurn == 'x') {
            opponent = 'o';
        } else {
            opponent = 'x';
        }
        
        int heuristic;
        
        
        heuristic = (5 * tsotiar(whoseTurn, board)) - (10 * tsotiar(opponent, board));
        
        heuristic += (3 * osotiar(whoseTurn, board)) - (6 * osotiar(opponent, board));
        
        heuristic += otiar(whoseTurn, board) - otiar(opponent, board);
        
        
        return heuristic;
    } 
    
    public static int tsotiar(char player, char[][] board) {
        
        int count = 0;
        
        for(int i = 0; i < 6; i++) {
            for(int j = 0; j < 6; j++) {
                
                if(board[i][j] == player) {
                    
                    //checking if current board[i][j] is the center of a three in a row diagonal, two sides open
                    if(i > 1 && j > 1 && i < 4 && j < 4) {
                        if(board[i-1][j-1] == player  && board[i+1][j+1] == player) {
                            if(board[i-2][j-2] == '-' && board[i+2][j+2] == '-') {
                                count++;  //one backward-leaning two side open, three in a row 
                            }
                        }
                        if(board[i-1][j+1] == player  && board[i+1][j-1] == player) {
                            if(board[i-2][j+2] == '-'  && board[i+2][j-2] == '-') {
                                count++;  //one forward-learning two side open, three in a row
                            }
                        }
                    }
                    
                    //checking if current board[i][j] is the center of a three in a row vertical, two sides open
                    if(i > 1 && i < 4) {
                        if(board[i-1][j] == player && board[i+1][j] == player) {
                           if(board[i-2][j] == '-' && board[i+2][j] == '-') {
                                count++;  //one vertical two side open, three in a row
                           } 
                        }
                    }
                    
                    //checking if current board[i][j] is the center of a three in a row horizontal, two sides open
                    if(j > 1 && j < 4) {
                        if(board[i][j-1] == player && board[i][j+1] == player) {
                           if(board[i][j-2] == '-' && board[i][j+2] == '-') {
                                count++;  //one horizontal two side open, three in a row
                           } 
                        }
                    }
                     
                }       
                    
            }
        }
        
        return count;
    }
    
    
    
    
    public static int osotiar(char player, char[][] board) {
        int count = 0;
        
        for(int i = 0; i < 6; i++) {
            for(int j = 0; j < 6; j++) {
                
                if(board[i][j] == player) {
                    
                    
                    //checking if current board[i][j] is the center of a three in a row vertical, one side open
                    if(i > 0 && i < 5) {
                        if(board[i-1][j] == player && board[i+1][j] == player) { // if it is three in a row

                            if(i > 1 && i < 4) { //if you are not running into a wall
                                
                                if(board[i-2][j] == '-' && board[i+2][j] == '-') { // making sure it is not two sides open
                                } else if(board[i-2][j] == '-' || board[i+2][j] == '-') {
                                        count++;
                                }
                                
                            } else if(i == 1 && board[i+2][j] == '-') {  //top end of three in a row is wall
                                count++;
                            } else if(i == 4 && board[i-2][j] == '-') {  //bottom end of three in a row is wall
                                count++;
                            } 
   
                        }
                    }
                    
                    
                    //checking if current board[i][j] is the center of a three in a row horizontal, one side open
                    if(j > 0 && j < 5) {
                        if(board[i][j-1] == player && board[i][j+1] == player) { // if it is three in a row

                            if(j > 1 && j < 4) { //if you are not running into a wall
                                
                                if(board[i][j-2] == '-' && board[i][j+2] == '-') { // making sure it is not two sides open
                                } else if(board[i][j-2] == '-' || board[i][j+2] == '-') {
                                        count++;
                                }
                                
                            } else if(j == 1 && board[i][j+2] == '-') {  //left end of three in a row is wall
                                count++;
                            } else if(j == 4 && board[i][j-2] == '-') {  //right end of three in a row is wall
                                count++;
                            } 
   
                        }
                    }
                    
                    
                    //checking if current board[i][j] is the center of a three in a row diagonal, one side open
                    if(i < 5 && i > 0 && j < 5 && j > 0) {

                        if(board[i-1][j-1] == player && board[i+1][j+1] == player) {  // backward-leaning diagonal
                        
                            
                            if(i == 1 && j != 4 && board[i+2][j+2] == '-') {
                                count++;
                            }
                        
                            else if(j == 1 && i != 4 && board[i+2][j+2] == '-') {
                                count++;
                            }
                            
                            else if(i == 4 && j != 1 && board[i-2][j-2] == '-') {
                                count++;
                            }
                            
                            else if(j == 4 && i != 1 && board[i-2][j-2] == '-') {
                                count++;
                            }
                            
                            else if(board[i+2][j+2] == '-' && board[i-2][j-2] == '-') {
                                
                            } else if(board[i+2][j+2] == '-' || board[i-2][j-2] == '-') {
                                count++;
                            }
                        
                        }
                        
                        
                        if(board[i-1][j+1] == player && board[i+1][j-1] == player){  // forward-leaning diagonal
                            
                            if(i == 1 && j != 1 && board[i+2][j-2] == '-') {
                                count++;
                            }
                        
                            else if(j == 4 && i != 4 && board[i+2][j-2] == '-') {
                                count++;
                            }
                            
                            else if(i == 4 && j != 4 && board[i-2][j+2] == '-') {
                                count++;
                            }
                            
                            else if(j == 1 && i != 1 && board[i-2][j+2] == '-') {
                                count++;
                            }
                            
                            else if(board[i-2][j+2] == '-' && board[i+2][j-2] == '-') {
                                
                            } else if(board[i-2][j+2] == '-' || board[i+2][j-2] == '-') {
                                count++;
                            }    
                            
                        }
    
                    }     
                    
                }
                
            }   
        }
        
        return count;
    }
    
    
    
    
    public static int otiar(char player, char[][] board) {
        int count = 0;
        int diff = 0;  //needs to be divided by two
        
        for(int i = 0; i < 6; i++) {
            for(int j = 0; j < 6; j++) {
                
                if(board[i][j] == player) {
                    
                   
                   if(i > 0 && board[i-1][j] == player && i < 5 && board[i+1][j] == '-') {
                           // top is player && opposite is blank 
                           if(i > 1) {
                               if (board[i-2][j] == '-') {
                                   diff++;  //eliminating double counts
                               }
                               if(board[i-2][j] == player) {
                                   count--;  //eliminating three in a rows
                               }
                           }
                           count++;
                   }  //////////////////////////////////////////////////////////////////////////////////
                   
                   
                   if(i > 0 && j > 0 && board[i-1][j-1] == player && i < 5 && j < 5 && board[i+1][j+1] == '-') {
                           //top left is player && opposite is blank 
                            if(i > 1 && j > 1) {
                               if (board[i-2][j-2] == '-') {
                                   diff++;  //eliminating double counts
                               }
                               if(board[i-2][j-2] == player) {
                                   count--;  //eliminating three in a rows
                               }
                           }
                           count++;
                   }  //////////////////////////////////////////////////////////////////////////////////
                   
                   
                   if(i > 0 && j < 5 && board[i-1][j+1] == player && i < 5 && j > 0 && board[i+1][j-1] == '-') {
                           //top right is player && opposite is blank 
                            if(i > 1 && j < 4) {
                               if (board[i-2][j+2] == '-') {
                                   diff++;  //eliminating double counts
                               }
                               if(board[i-2][j+2] == player) {
                                   count--;  //eliminating three in a rows
                               }
                           }
                           count++;
                   }  //////////////////////////////////////////////////////////////////////////////////
                   
                   
                   
                   if(j > 0 && board[i][j-1] == player && j < 5 && board[i][j+1] == '-') {
                           //left is player && opposite is blank 
                           if(j > 1) {
                               if (board[i][j-2] == '-') {
                                   diff++;  //eliminating double counts
                               }
                               if(board[i][j-2] == player) {
                                   count--;  //eliminating three in a rows
                               }
                           }
                           count++;
                   }  //////////////////////////////////////////////////////////////////////////////////
                   
                   
                   
                   if(j < 5 && board[i][j+1] == player && j > 0 && board[i][j-1] == '-') {
                           //right is player && opposite is blank 
                           if(j < 4) {
                               if (board[i][j+2] == '-') {
                                   diff++;  //eliminating double counts
                               }
                               if(board[i][j+2] == player) {
                                   count--;  //eliminating three in a rows
                               }
                           }
                           count++;
                   }  //////////////////////////////////////////////////////////////////////////////////
                   
                   
                   if(i < 5 && board[i+1][j] == player && i > 0 && board[i-1][j] == '-') {
                           // bottom is player && opposite is blank 
                           if(i < 4) {
                               if (board[i+2][j] == '-') {
                                   diff++;  //eliminating double counts
                               }
                               if(board[i+2][j] == player) {
                                   count--;  //eliminating three in a rows
                               }
                           }
                           count++;
                   } //////////////////////////////////////////////////////////////////////////////////
                   
                   
                   
                   if(i < 5 && j > 0 && board[i+1][j-1] == player && i > 0 && j < 5 && board[i-1][j+1] == '-') {
                           //bottom left is player && opposite is blank 
                           if(i < 4 && j > 1) {
                               if (board[i+2][j-2] == '-') {
                                   diff++;  //eliminating double counts
                               }
                               if(board[i+2][j-2] == player) {
                                   count--;  //eliminating three in a rows
                               }
                           }
                           count++;
                   } //////////////////////////////////////////////////////////////////////////////////
                   
                   
                   if(i < 5 && j < 5 && board[i+1][j+1] == player && i > 0 && j > 0 && board[i-1][j-1] == '-') {
                           //bottom right is player && opposite is blank 
                           if(i < 4 && j < 4) {
                               if (board[i+2][j+2] == '-') {
                                   diff++;  //eliminating double counts
                               }
                               if(board[i+2][j+2] == player) {
                                   count--;  //eliminating three in a rows
                               }
                           }
                           count++;
                       
                   }  //////////////////////////////////////////////////////////////////////////////////
                        
                }
        
            }
        }
        
        return count - (diff / 2);
    }
   
  
}
