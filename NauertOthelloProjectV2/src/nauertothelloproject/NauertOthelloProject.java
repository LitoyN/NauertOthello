/*

 */
package nauertothelloproject;

import java.util.Scanner;
import java.util.ArrayList;

/**
 *
 * @author elliot
 */
public class NauertOthelloProject {

    public static final String BLACK = "b";
    public static final String WHITE = "w";
    public static final String PASS = "pass";
    public static final int EMPTY = 0;
    public static final int ME = 1;
    public static final int OPPONENT = -1;
    public static final int BORDER = -2;
    public static final int[] DIRECTIONS = {-11,-10,-9,-1,1,9,10,11};
    public static final int NUMDIRECTIONS = 8;
    public static final String[] COLUMNS = {"A","B","C","D","E","F","G","H"};
    
    static Board gameBoard;
    static String myColor;
    static String oppColor;
    static String inputString;
    
    public static void main(String[] args) {
        Scanner keyboard = new Scanner(System.in);
        int currentPlayer = 1;
        Move thisMove;
        //boolean gameOver = false;
        
        boolean colorInit = false;
        while(!colorInit){
            myColor = getColor(keyboard);
            if(myColor.equalsIgnoreCase(BLACK) ||
                    myColor.equalsIgnoreCase(WHITE)){
                colorInit = true;
                //System.out.println("C Initialize self as " + myColor);
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
        

        //System.out.println("C First Player: " + currentPlayer);
        System.out.print(gameBoard.toString());
        
        ArrayList<Move> tempMovesList = gameBoard.getMoves(ME);
        System.out.println("local moves list size: " + tempMovesList.size());
        System.out.print("local moves list: ");
        gameBoard.printMoveList(tempMovesList);
        System.out.print("global moves list: ");
        gameBoard.printMoveList(gameBoard.movesList);

       
    }
    
    private static String getColor(Scanner input){

        String mycolor;
        String inputString = input.nextLine();
        if(inputString.substring(0,1).equalsIgnoreCase("i") &&
                inputString.length() >= 3){
            mycolor = inputString.substring(2,3);
            switch(mycolor.toLowerCase()){
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
    
}
