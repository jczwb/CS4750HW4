/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs4750hw4;

import java.util.ArrayList;

/**
 *
 * @author crowe_000
 */
public class CS4750HW4 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        //comment
        char[][] beginningBoard = new char[6][6];
        for (int i = 0; i  < 6; i++){
            for (int j=0; j < 6; j++){
                beginningBoard[i][j] = '-';
            }
        }
        Node tempNode = new Node(beginningBoard, 3,3,'x');
        ArrayList<Node>  solutionPath = new ArrayList<>();
        boolean isMaxPlayer = true;
        while (!tempNode.isTerminal()){
            Node.printBoard(tempNode.board);
            solutionPath.add(tempNode);
            if (isMaxPlayer){
                tempNode = tempNode.getNextNode(2, isMaxPlayer);
                tempNode = tempNode.miniMaxChild;
            } else {
                tempNode = tempNode.getNextNode(2, isMaxPlayer);
                tempNode = tempNode.miniMaxChild; 
            }
        }
    }
    
}
