/*

 */
package nauertothelloproject;

import java.util.Scanner;

/**
 *
 * @author elliot
 */
public class NauertOthelloProject {

    public static final int EMPTY = 0;
    public static final int ME = 1;
    public static final int OPPONENT = -1;
    public static final int BORDER = -2;
    public static final int[] DIRECTIONS = {-11,-10,-9,-1,1,9,10,11};
    public static final int NUMDIRECTIONS = 8;
    public static final String[] COLUMNS = {"A","B","C","D","E","F","G","H"};
    
    static Board gameBoard;
    static String myColor;
    
    public static void main(String[] args) {
        Scanner keyboard = new Scanner(System.in);
        int currentPlayer = 1;
        int thisMove;
        //boolean gameOver = false;
        
        myColor = getColor();
        gameBoard = new Board("b");
        
        if(myColor.equalsIgnoreCase("b")){
            currentPlayer = ME;
        }
        else{
            currentPlayer = OPPONENT;
        }
        
        //while(!gameBoard.gameOver){
            
        //}
        System.out.println("First Player: " + currentPlayer);
        System.out.print(gameBoard.toString());

        while(!gameBoard.gameOver){
            boolean validMoveTaken;
            if(currentPlayer == ME){
                validMoveTaken = gameBoard.myTurn(ME);
            }
            else{
                thisMove = getMoveUI(keyboard);
                validMoveTaken = gameBoard.opponentTurn(thisMove, OPPONENT);
            }
            if(validMoveTaken){
                currentPlayer = currentPlayer * -1;
                System.out.print(gameBoard.toString());
            }
            gameBoard.isGameOver();

        }
        System.out.println("GAME OVER");
    }
    
    private static String getColor(){
        String mycolor = "b";
        return mycolor;
    }
    
    
    private static int getMoveUI(Scanner input){
        int columnInt;
        int rowInt;
        int arrayPosition;
        String moveString;
        System.out.print("Please enter move: ");
        moveString = input.next().toUpperCase();
        if(moveString.equalsIgnoreCase("-1")){
            return -1;
        }
        columnInt = Character.getNumericValue(moveString.charAt(0)) - 9;
        rowInt = Integer.parseInt(moveString.substring(1));
        arrayPosition = 10*rowInt + columnInt;
        System.out.println("Input string: " + moveString);
        System.out.println("Row: " + rowInt);
        System.out.println("Column: " + columnInt);
        System.out.println("Array Position: " + arrayPosition);
        
        return arrayPosition;  
    }
    
}
