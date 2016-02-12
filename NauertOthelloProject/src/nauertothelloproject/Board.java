
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
    int noMovesCount;
    boolean gameOver;
   
    
    public Board(String myColor){
        boardArray = new int[100];
        turnMovesList = new ArrayList();
        
        thisMove = -1;
        numPossibleMoves =0;
        myScore = 2;
        oppScore = 2;
        gameOver = false;
        noMovesCount = 0;
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
    
    public void myTurn(int player){
        scanBoardForMoves(player);
        thisMove = chooseMove(player);
        turnMovesList.clear();
        numPossibleMoves = 0;
        applyMove(thisMove, player);
    }
    
    public void opponentTurn(int move, int player){
        scanBoardForMoves(player);
        if(turnMovesList.contains(move)){
            applyMove(move, player);
        }
        else{
            System.out.println("INVALID MOVE: " + move);
        }
        turnMovesList.clear();
        numPossibleMoves = 0;
    }
    
    /**
     * 
     * @param player 
     */
    private void scanBoardForMoves(int player){
        for(int i = 0; i < 100; i++){
            if(boardArray[i] == player){
                lookEachDirection(i,player);
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
                    System.out.println("Possible Move Added: " + possibleMove);
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
        //System.out.println("Looking one direction: " + direction);
        if(boardArray[boardLocation] == 0){
            numPossibleMoves++;
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
         ran = new Random();
         ranNum = ran.nextInt(numPossibleMoves);
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
    
    private int flipOneDirection(int direction, int boardLocation, int player){
        int flipValue;
        if(boardArray[boardLocation] == player * -1){
            flipValue = flipOneDirection(direction, boardLocation + direction, player);
        }
        else if(boardArray[boardLocation] == NauertOthelloProject.BORDER ||
                boardArray[boardLocation] == NauertOthelloProject.EMPTY){
            flipValue = player * -1;
            return flipValue;
        }

        else{
            flipValue = boardArray[boardLocation];
        }
        boardArray[boardLocation] = flipValue;
        return flipValue;
    }
    
    public boolean isGameOver(){
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
