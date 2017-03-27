/*

 */
package nauertothelloproject;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author elliot
 */
public class NauertOthelloProject {

    public static final String BLACK = "B";
    public static final String WHITE = "W";
    public static final String PASS = "pass";
    public static final int EMPTY = 0;
    public static final int ME = 1;
    public static final int OPPONENT = -1;
    public static final int BORDER = -2;
    public static final int[] DIRECTIONS = {-11,-10,-9,-1,1,9,10,11};
    public static final int NUMDIRECTIONS = 8;
    public static final String[] COLUMNS = {"A","B","C","D","E","F","G","H"};
    public static final double alpha = Double.MIN_VALUE;
    public static final double beta = Double.MAX_VALUE;
    
    public static final int MAXDEPTH = 2;
    public static final int ITERATIVEDEPTH = 2;
    
    public static double timeRemaining = 600;
    public static Timer timer;
    public static boolean timeUp;
    
    public static MoveNode currentNode;
    public static Move opponentLast;
    
    static double timeAllocation[] = {0.015, 0.015, 0.015, 0.015, 0.025, 0.025, 0.025, 0.025, 0.025, 0.025,
                                    0.048,  0.048, 0.048, 0.048, 0.048, 0.048, 0.050, 0.051, 0.052, 0.053,
                                    0.044,  0.045, 0.049, 0.049, 0.049, 0.051, 0.053, 0.055, 0.057, 0.059,
                                    0.060, 0.060, 0.061, 0.062, 0.063, 0.064, 0.065, 0.065, 0.065, 0.065,
                                    0.167, 0.168, 0.169, 0.169, 0.171, 0.172, 0.173, 0.175, 0.180, 0.180,
                                    0.181, 0.187, 0.196, 0.199, 0.220, 0.220, 0.220, 0.220, 0.220, 0.220,
                                    0.220, 0.250, 0.250, 0.250, 0.250, 0.250, 0.250, 0.250, 0.250, 0.250
                                  };
    
    static Board gameBoard;
    static String myColor;
    static String oppColor;
    static String inputString;
    
    public static int moveNumber;
    
    
    public static void main(String[] args) {
        Scanner keyboard = new Scanner(System.in);
        int currentPlayer = 1;
        Move thisMove;
        moveNumber = 0;
        System.out.println("C Move Number: " + moveNumber);

        //Listen for color assignment and intialize board***********************
        boolean colorInit = false;
        while(!colorInit){
            myColor = getColor(keyboard);
            if(myColor.equalsIgnoreCase(BLACK)){
                oppColor = WHITE;
                colorInit = true;
                System.out.println("C Initialize self as " + myColor);
                System.out.println("R " + myColor);

            }
            if(myColor.equalsIgnoreCase(WHITE)){
                oppColor = BLACK;
                colorInit = true;
                System.out.println("C Initialize self as " + myColor);
                System.out.println("R " + myColor);              
            }
        }

        gameBoard = new Board(myColor);
        
        if(myColor.equalsIgnoreCase(BLACK)){
            currentPlayer = ME;
        }
        else{
            currentPlayer = OPPONENT;
        }
        
        MoveNode rootNode = new MoveNode(gameBoard,currentPlayer);
        currentNode = rootNode;
        //board initialized*****************************************************
        
        //print initial board
        System.out.print(gameBoard.toString());

        //while each player still has moves, alternate turns********************
        while(!gameBoard.gameOver){
            timeUp = false;
            boolean validMoveTaken;
            
            System.out.println("C Starting Player " + currentPlayer + "'s turn");
            System.out.println("C ");
            if(currentPlayer == ME){
                System.out.println("C Compiling moves lists for both players to check end game");
                ArrayList<Move> myMoves = gameBoard.getMoves(ME);
                ArrayList<Move> oppMoves = gameBoard.getMoves(OPPONENT);
                System.out.println("C done with initial move list check");
                System.out.println("C ");
                if(myMoves.isEmpty()){
                    if(oppMoves.isEmpty()){
                        gameBoard.gameOver = true;
                        System.out.println("C GAME OVER");
                        System.out.println(gameBoard.countBlackPieces());
                    }
                    else{
                        System.out.print(myColor);
                    }
                }
                
                else{
                    currentNode.expand(ME);
                    myMoves = gameBoard.alphaBeta(currentNode, MAXDEPTH, new Move());
                    System.out.println("C Heres my moves");
                    for(Move moves:myMoves){
                        System.out.println("C " + moves.moveString);
                    }
                }
                
            }
            else{
                boolean validInput = false;
                while(!validInput){
                    System.out.println("C Entered while loop");
                    inputString = getInput(keyboard);
                    System.out.println("C InputString: " + inputString);
                    if(!inputString.startsWith("q")){
                        validInput = true;
                    }
                }
                thisMove = new Move(inputString);
                opponentLast = thisMove;
                System.out.println("C Move string after converting to Move object: " + thisMove.moveString);
                gameBoard.applyMoveAB(thisMove, OPPONENT);
                System.out.println("C New board after applying final chosen move: " + thisMove.getMoveString() + " for player " + currentPlayer);
            }
            
            currentPlayer = currentPlayer*-1;
            System.out.println(gameBoard.toString());
            moveNumber++;
        }
        

       
    }
    
    
    private static String getColor(Scanner input){

        String mycolor;
        String inputString = input.nextLine();
        if(inputString.substring(0,1).equalsIgnoreCase("i") &&
                inputString.length() >= 3){
            mycolor = inputString.substring(2,3);
            switch(mycolor.toUpperCase()){
                case BLACK:
                    oppColor = WHITE;
                    break;
                case WHITE:
                    oppColor = BLACK;
                    break;
                default:
                    //System.out.println("C Error in initialization input.");
                    oppColor = "q";
                    mycolor = "q";
                    break;
            }
        }
        else{
            System.out.println("C Error in initialization input.");
            mycolor = "q";
        }

        
        return mycolor;
    }
    
    
    private static String getInput(Scanner input){
        String moveString = input.nextLine();
        //System.out.println("C moveString.length: " + moveString.length());
        //System.out.println("C moveString: " + moveString);
        //System.out.println("C firstChar: " + firstChar);
        
        String firstChar = moveString.substring(0,1).toLowerCase();
        if(moveString.length() <= 2){
            return PASS;
        }
        else if(firstChar.equalsIgnoreCase(oppColor)){
            return moveString.substring(2);
        }
        else{
            moveString = "q";
            System.out.println("C Error in input");
        }


        
        return moveString;
    }
    
    static class InterruptTask extends TimerTask {
      public void run() {
	  System.out.println("C ****>timeup");
	  timeUp = true;
          timer.cancel();
      }
    }
    
}
