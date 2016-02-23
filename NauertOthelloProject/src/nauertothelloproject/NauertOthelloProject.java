/*

 */
package nauertothelloproject;

import java.util.Scanner;

/**
 *
 * @author elliot
 */
public class NauertOthelloProject {

    public static final String BLACK = "b";
    public static final String WHITE = "w";
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
    
    public static void main(String[] args) {
        Scanner keyboard = new Scanner(System.in);
        int currentPlayer = 1;
        int thisMove;
        //boolean gameOver = false;
        
        boolean colorInit = false;
        while(!colorInit){
            myColor = getColor(keyboard);
            if(myColor.equalsIgnoreCase(BLACK) ||
                    myColor.equalsIgnoreCase(WHITE)){
                colorInit = true;
                System.out.println("C Initialize self as " + myColor);
                System.out.println("R " + myColor);
            }
        }

        gameBoard = new Board(BLACK);
        
        if(myColor.equalsIgnoreCase(BLACK)){
            currentPlayer = ME;
        }
        else{
            currentPlayer = OPPONENT;
        }
        

        //System.out.println("C First Player: " + currentPlayer);
        System.out.print(gameBoard.toString());

        while(!gameBoard.gameOver){
            String inputString = "";
            boolean validMoveTaken;
            gameBoard.scanBoardForMoves(currentPlayer);
            if(currentPlayer == ME){
                validMoveTaken = gameBoard.myTurn(ME);
            }
            else{
                //thisMove = getMoveUI(keyboard);
                //validMoveTaken = gameBoard.opponentTurn(thisMove, OPPONENT);
                boolean validInput = false;
                while(!validInput){
                    inputString = getInput(keyboard);
                    if(!inputString.startsWith("e")){
                        validInput = true;
                    }
                }

                thisMove = stringToMove(inputString);
                validMoveTaken = gameBoard.opponentTurn(thisMove,OPPONENT);
            }
            if(validMoveTaken){
                currentPlayer = currentPlayer * -1;
                System.out.print(gameBoard.toString());
            }
            gameBoard.isGameOver();

        }
        System.out.println("C GAME OVER");
        gameBoard.getScore();
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
                    System.out.println("C Error in initialization input.");
                    oppColor = "e";
                    mycolor = "e";
                    break;
            }
        }
        else{
            System.out.println("C Error in initialization input.");
            mycolor = "e";
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
                System.out.println("C COLUMN: " + COLUMNS[i]);
                foundCol = true;
                columnInt = i +1;
            }
            i++;
        }
        int row = Integer.parseInt(inputString.substring(2,3));
        System.out.println("C ROW: " + row);
        
        moveInt = 10 * row;
        moveInt = moveInt + columnInt;
        System.out.println("C move in int form: " + moveInt);
        return moveInt;
    }
    
    private static String getInput(Scanner input){
        String moveString = input.nextLine();
        if(moveString.isEmpty()){
            moveString = "e";
            System.out.println("C Error in input");
        }
        else{
            System.out.println("C moveString: " + moveString);
            String firstChar = moveString.substring(0,1).toLowerCase();
            System.out.println("C firstChar: " + firstChar);
            if(firstChar.equalsIgnoreCase(oppColor)){
                return moveString.substring(2);
            }
            else{
                moveString = "e";
                System.out.println("C Error in input");
            }
        }


        
        return moveString;
    }
    
    
    
    private static int getMoveUI(Scanner input){
        int columnInt;
        int rowInt;
        int arrayPosition;
        String moveString;
        System.out.print("C Please enter move: ");
        moveString = input.next().toUpperCase();
        if(moveString.equalsIgnoreCase("-1")){
            return -1;
        }
        columnInt = Character.getNumericValue(moveString.charAt(0)) - 9;
        rowInt = Integer.parseInt(moveString.substring(1));
        arrayPosition = 10*rowInt + columnInt;
        //System.out.println("C Input string: " + moveString);
        //System.out.println("C Row: " + rowInt);
        //System.out.println("C Column: " + columnInt);
        System.out.println("C Array Position: " + arrayPosition);
        
        return arrayPosition;  
    }
    
}
