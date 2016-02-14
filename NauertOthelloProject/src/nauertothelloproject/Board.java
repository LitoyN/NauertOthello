
package nauertothelloproject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 *
 * @author elliot
 */
public class Board {
    
    public static final int NOMOVES = -1;
    
    int boardArray[];
    ArrayList turnMovesList;
    
    int thisMove;
    int numPossibleMoves;
    
    Random ran;
    int ranNum;
    
    int myScore;
    int oppScore;
    int passCount;
    boolean gameOver;
   
    
    public Board(String myColor){
        boardArray = new int[100];
        turnMovesList = new ArrayList();
        
        thisMove = -1;
        numPossibleMoves = 0;
        myScore = 2;
        oppScore = 2;
        passCount = 0;
        gameOver = false;
        buildBoard(myColor);
        
    }
    
    private void buildBoard(String myColor){
        Arrays.fill(boardArray,NauertOthelloProject.EMPTY);
        for(int i = 0; i < 10; i++){
            boardArray[i] = NauertOthelloProject.BORDER;
            boardArray[90+i] = NauertOthelloProject.BORDER;
            boardArray[i*10] = NauertOthelloProject.BORDER;
            boardArray[i*10+9] = NauertOthelloProject.BORDER;
        }
        if(myColor == "b"){
            boardArray[45] = NauertOthelloProject.ME;
            boardArray[54] = NauertOthelloProject.ME;
            boardArray[44] = NauertOthelloProject.OPPONENT;
            boardArray[55] = NauertOthelloProject.OPPONENT;
        }
        else{
            boardArray[45] = NauertOthelloProject.OPPONENT;
            boardArray[54] = NauertOthelloProject.OPPONENT;
            boardArray[44] = NauertOthelloProject.ME;
            boardArray[55] = NauertOthelloProject.ME;

        }
    }
    
    public boolean myTurn(int player){
        System.out.println("MY TURN");
        boolean validMove = true;
        scanBoardForMoves(player);
        thisMove = chooseMove(player);
        turnMovesList.clear();
        numPossibleMoves = 0;
        if(thisMove == NOMOVES){
            passCount++;
            System.out.println("PASS " + passCount);
            return validMove;
        }
        passCount = 0;
        applyMove(thisMove, player);
        return validMove;
    }
    
    public boolean opponentTurn(int move, int player){
        System.out.println("OPPONENT TURN");
        boolean validMove;
        scanBoardForMoves(player);
        if(move == NOMOVES){
            passCount++;
            System.out.println("PASS " + passCount);
            validMove = true;
        }
        else if(turnMovesList.contains(move)){
            passCount = 0;
            applyMove(move, player);
            validMove = true;
        }
        else{
            System.out.println("INVALID MOVE: " + move);
            validMove = false;
        }
        turnMovesList.clear();
        numPossibleMoves = 0;
        return validMove;
    }
    
    /**
     * 
     * @param player 
     */
    private void scanBoardForMoves(int player){
        int boardLocation;
        for(int i = 1; i < 9 ; i++)
            for(int j = 1; j < 9; j++){
                boardLocation = i * 10 + j;
                if(boardArray[boardLocation] == player){
                    lookEachDirection(boardLocation,player);
                }
            }
    }
    
    /**
     * 
     * @param boardLocation
     * @param player 
     */
    private void lookEachDirection(int boardLocation, int player){
        int possibleMove;
        int newLocation;
        int direction;
        for(int i = 0; i < 8; i++){
            direction = NauertOthelloProject.DIRECTIONS[i];
            newLocation = boardLocation + direction;
            if(boardArray[newLocation] == player * -1){
                possibleMove = lookOneDirection(direction,newLocation,player);
                if(possibleMove > 0){
                    turnMovesList.add(possibleMove);
                    numPossibleMoves++;
                    System.out.println("Possible Move Added: " + possibleMove);
                    System.out.println("Number of Possible Moves: " + numPossibleMoves);
                }
                else{
                    System.out.println("NOMOVES not added to possible moves");
                }
            }
        }
    }
    
    /**
     * 
     * @param direction
     * @param boardLocation
     * @param player
     * @return 
     * 
     */
    private int lookOneDirection(int direction, int boardLocation, int player){
        int newLocation;
        if(boardArray[boardLocation] == 0){
            return boardLocation;
        }
        if(boardArray[boardLocation] == player * -1){
            newLocation = boardLocation + direction;
            return lookOneDirection(direction, newLocation, player);
        }
        else{
            return NOMOVES;
        }
        
    }
    
    public int chooseMove(int player){
        if(numPossibleMoves == 0){
            return NOMOVES;
        }
         ran = new Random();
         ranNum = ran.nextInt(numPossibleMoves);
         System.out.println("Random Choice Int: " + ranNum);
         System.out.println("Move: " + (int)turnMovesList.get(ranNum));
         return (int)turnMovesList.get(ranNum);
        
    }
    
    public void applyMove(int move, int player){
        System.out.println("Move Chosen: " + move);
        boardArray[move] = player;
        flipEachDirection(move, player);
    }
    
    private void flipEachDirection(int move, int player){
        int newLocation;
        int direction;
        
        for(int i = 0; i < 8; i++){
            direction = NauertOthelloProject.DIRECTIONS[i];
            newLocation = move + direction;
            if(boardArray[newLocation] == player * -1){
                flipOneDirection(direction, newLocation, player);
            }
        }
        
    }
    
    private boolean flipOneDirection(int direction, int boardLocation, int player){
        boolean flipPiece = false;
        int newLocation = boardLocation + direction;
        if(boardArray[newLocation] == player * -1){
            flipPiece = flipOneDirection(direction, boardLocation + direction, player);
        }
        else if(boardArray[newLocation] == player){
            flipPiece = true;
        }
        if(flipPiece){
            boardArray[boardLocation] = player;
        }
        
        return flipPiece;
    }
    
    public boolean isGameOver(){
        if(passCount >=2){
            gameOver = true;
        }
        return gameOver;
    }
    
    public void getScore(){
        
    }
    
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder("[");
        for(int i = 0; i< 10; i++){
            if(i==0)
                sb.append("\t\t");
            else if(i==9)
                sb.append("\t\n");
            else
                sb.append(NauertOthelloProject.COLUMNS[i-1] + "\t");
        }
        sb.append("\t");
        int row = 1;
        for(int i = 0; i < 100; i++){
            sb.append(boardArray[i] + "\t");
            if((i-9)%10==0 && i !=0){
                sb.append("\n\n");
                if(row<9){
                    sb.append("R" +row +"\t");
                    row++;
                }
                else{
                    sb.append("\t");
                }
                
            }
        }
        sb.append("\n");

        return sb.toString();
    }
}
