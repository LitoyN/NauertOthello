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
    
    public static final int MAXDEPTH = 4;
    public static final int ITERATIVEDEPTH = 10;
    
    public static double timeRemaining;
    Timer timer;
    public static boolean timeUp;
    
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
        timeRemaining = 600;
        
        
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
        //board initialized*****************************************************
        
        //print initial board
        System.out.print(gameBoard.toString());
        
        
        long startTime;
        long endTime;
        long totalTime = 0;
        //while each player still has moves, alternate turns********************
        while(!gameBoard.gameOver){
            boolean validMoveTaken;
            startTime = System.nanoTime();
            
            System.out.println("C Starting Player " + currentPlayer + "'s turn");
            System.out.println("C ");
            if(currentPlayer == ME){
                System.nanoTime();
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
                        System.out.println("C Total Time For My Turns: " + (totalTime*.000000001)/60);
                    }
                    else{
                        System.out.print(myColor);
                    }
                }
                else{
                    double alpha = Double.MIN_VALUE;
                    double beta = Double.MAX_VALUE;
                    ArrayList<Move> moveChain = new ArrayList<>();
                    //DeepObjectAndList thisDeepAndList = gameBoard.iterativeGetMoves(gameBoard, 0, ME, alpha, beta, ITERATIVEDEPTH, new Move());
                    //thisMove = thisDeepAndList.bestDeepObject.deepMoveList.get(0);
                    moveChain = gameBoard.alphaBeta(gameBoard, 0, ME, alpha, beta, MAXDEPTH, new Move());
                    thisMove = moveChain.get(moveChain.size()-1);
                    //thisMove = gameBoard.alphaBeta(gameBoard, 0, ME, alpha, beta, MAXDEPTH,0);
                    System.out.println("C New board before applying final chosen move: " + thisMove.getMoveString() + " for player " + currentPlayer);
                    System.out.println(gameBoard.toString());
                    if(thisMove.moveValue == 0){
                        System.out.print(myColor);
                    }
                    gameBoard.applyMove(thisMove, ME);
                    System.out.println("C New board after applying final chosen move: " + thisMove.getMoveString() + " for player " + currentPlayer);

                }
                endTime = System.nanoTime();
                totalTime = totalTime + (endTime-startTime);
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
                //NEED TO HANDLE PASSES BETTER.
                thisMove = new Move(inputString);
                System.out.println("C Move string after converting to Move object: " + thisMove.moveString);
                gameBoard.applyMoveAB(thisMove, OPPONENT);
                System.out.println("C New board after applying final chosen move: " + thisMove.getMoveString() + " for player " + currentPlayer);
            }
            
            currentPlayer = currentPlayer*-1;
            System.out.println(gameBoard.toString());
        }
        

       
    }
    
    public Move getMyMove(){
        moveNumber++;
        timeUp = false;
        timer = new Timer();
        
        int timeForMove = (int)(timeAllocation[moveNumber]*(double)timeRemaining);
        
        System.out.print("C Move Time: " + timeForMove);
        timer.schedule(new InterruptTask(), timeForMove*1000);
        
        ArrayList<Move> myMoves = gameBoard.getMoves(ME);
        gameBoard.applyMove((Move)myMoves.get(0), ME);
        if (!timeUp){
            timer.cancel();
        }
        timeRemaining -= timeForMove;  //update the time remaining 

        System.out.print("C Remaining Time: " + timeRemaining);
        Move selectedMove = (Move)(myMoves.get(0));
        return selectedMove;
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
    
    private static int stringToMove(String inputString){
        int moveInt = 0;
        String column = inputString.substring(0,1);
        int columnInt = 0;
        boolean foundCol = false;
        int i = 0;
        while(!foundCol){
            if(COLUMNS[i].equalsIgnoreCase(column)){
                //System.out.println("C COLUMN: " + COLUMNS[i]);
                foundCol = true;
                columnInt = i +1;
            }
            i++;
        }
        int row = Integer.parseInt(inputString.substring(2,3));
        //System.out.println("C ROW: " + row);
        
        moveInt = 10 * row;
        moveInt = moveInt + columnInt;
        //System.out.println("C move in int form: " + moveInt);
        return moveInt;
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
    
    class InterruptTask extends TimerTask {
      public void run() {
	  System.out.println("(C ****>timeup)");
	  timeUp = true;
          timer.cancel();
      }
    }
    
}
