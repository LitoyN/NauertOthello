
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
    
    public static final String BLACK = "b";
    public static final String WHITE = "w";
    public static final int EMPTY = 0;
    public static final int ME = 1;
    public static final int OPPONENT = -1;
    public static final int BORDER = -2;
    public static final int[] DIRECTIONS = {-11,-10,-9,-1,1,9,10,11};
    public static final int NUMDIRECTIONS = 8;
    public static final int NOMOVES = -1;
    public static final String[] COLUMNS = {"A","B","C","D","E","F","G","H"};
    
    
    Move thisMove;
    
    int boardArray[];
    String myColor;
    String oppColor;
    int myScore;
    int oppScore;
    boolean gameOver;
   
    
    public Board(String mycolor){
        boardArray = new int[100];

        thisMove = new Move(NOMOVES);
        
        myColor = mycolor;
        oppColor = "b";
        if(mycolor.equalsIgnoreCase("b")){
            oppColor = "w";
        }
        myScore = 2;
        oppScore = 2;
        gameOver = false;
        buildBoard(myColor);
        
    }
    
    public Board(Board oldBoard){
        boardArray = oldBoard.boardArray.clone();
        myColor = oldBoard.myColor;
        oppColor = oldBoard.oppColor;
        myScore = oldBoard.myScore;
        oppScore = oldBoard.oppScore;
        gameOver = oldBoard.gameOver;
    }
    
    private void buildBoard(String myColor){
        Arrays.fill(boardArray,NauertOthelloProject.EMPTY);
        for(int i = 0; i < 10; i++){
            boardArray[i] = NauertOthelloProject.BORDER;
            boardArray[90+i] = NauertOthelloProject.BORDER;
            boardArray[i*10] = NauertOthelloProject.BORDER;
            boardArray[i*10+9] = NauertOthelloProject.BORDER;
        }
        if(myColor.equalsIgnoreCase("b")){
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
    
    public void printMoveList(ArrayList<Move> moveList){
        for(Move moveList1 : moveList) {
            System.out.println("C " + moveList1.moveString + " ");
        }
        System.out.println();
    }
    
    public ArrayList getMoves(int player){
        ArrayList<Move> allMoves = new ArrayList<>();
        int boardLocation;
        int possibleMove;
        int newLocation;
        int direction;
        int numPossibleMoves = 0;
        Move thisMoveTemp;
        
        for(int i = 1; i < 9 ; i++){
            for(int j = 1; j < 9; j++){
                boardLocation = i * 10 + j;
                if(boardArray[boardLocation] == player){
                    //System.out.println("C Found player: " + player + " at location: " + boardLocation);
                    for(int k = 0; k < 8; k++){
                        direction = NauertOthelloProject.DIRECTIONS[k];
                        newLocation = boardLocation + direction;
                        //System.out.println("C Checking location: " + newLocation);
                        if(boardArray[newLocation] == player * -1){
                            possibleMove = lookOneDirection(direction,newLocation,player);
                            if(possibleMove > 0){
                                thisMoveTemp = new Move(possibleMove);
                                if(!allMoves.contains(thisMoveTemp)){
                                    allMoves.add(thisMoveTemp);
                                    numPossibleMoves++;
                                    //System.out.println("C Possible Move Added: " + possibleMove);
                                    //System.out.println("C Number of Possible Moves: " + numPossibleMoves);
                                }
                            }
                        }
                    }
                }
            }
        }
        
        return allMoves;
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
        if(boardLocation > -1){
            if(boardArray[boardLocation] == 0){
                return boardLocation;
            }
            if(boardArray[boardLocation] == player * -1){
                newLocation = boardLocation + direction;
                return lookOneDirection(direction, newLocation, player);
            }
        }
        return NOMOVES;      
    }
    
    public void applyMove(Move move, int player){

        if(player == ME){
            System.out.println("C Applying My Move: ");
            System.out.println(myColor + " " + move.moveString);
        }
        else{
            System.out.println("C Applying Opponent Move: ");
            System.out.println(oppColor + " " + move.moveString);
        }
        boardArray[move.getMoveInt()] = player;
        if(player == NauertOthelloProject.ME){
            myScore++;
        }
        else{
            oppScore++;
        }
        flipEachDirection(move.getMoveInt(), player);
    }
    
    public void applyMoveAB(Move move, int player){

        if(player == ME){
            System.out.println("C Applying My Move: ");
            System.out.println("C " + myColor + " " + move.moveString);
        }
        else{
            System.out.println("C Applying Opponent Move: ");
            System.out.println("C " + oppColor + " " + move.moveString);
        }
        boardArray[move.getMoveInt()] = player;
        if(player == NauertOthelloProject.ME){
            myScore++;
        }
        else{
            oppScore++;
        }
        flipEachDirection(move.getMoveInt(), player);
    }
    
    private void flipEachDirection(int move, int player){
        int newLocation;
        int direction;
        
        for(int i = 0; i < 8; i++){
            direction = NauertOthelloProject.DIRECTIONS[i];
            newLocation = move + direction;
            if(newLocation > 0){
                if(boardArray[newLocation] == player * -1){
                    flipOneDirection(direction, newLocation, player);
                }
            }
        }
        
    }
    
    private boolean flipOneDirection(int direction, int boardLocation, int player){
        boolean flipPiece = false;
        int newLocation = boardLocation + direction;
        if(newLocation > 0){
            if(boardArray[newLocation] == player * -1){
                flipPiece = flipOneDirection(direction, boardLocation + direction, player);
            }
            else if(boardArray[newLocation] == player){
                flipPiece = true;
            }
        }
        if(flipPiece){
            boardArray[boardLocation] = player;
            if(player == NauertOthelloProject.ME){
                myScore++;
                oppScore--;
            }
            else{
                oppScore++;
                myScore--;
            }
        }
        
        return flipPiece;
    }
    
    
    public void getScore(){
        
        System.out.println("C MY SCORE: " + myScore);
        System.out.println("C OPPONENT SCORE: " + oppScore);
        if(myColor.equalsIgnoreCase("b")){
            System.out.println(myScore);
        }
        else{
            System.out.println(oppScore);
        }
        
    }
    
    public Move iterativeGetMoves(Board deepBoard, int ply, int player, double alpha, double beta, int increment){
        Move bestMove = new Move();
        
        return bestMove;
    }
    
    public Move alphaBeta(Board currentboard, int ply, int player, double alpha, double beta, int maxDepth){
        
        if(ply >= maxDepth){
            System.out.println("C Max Depth Reached: " + ply);
            Move returnMove = new Move();
            returnMove.setMoveValue(currentboard.countBlackPieces());
            System.out.println("C Move Value: " + returnMove.getMoveValue());
            return returnMove;
        }
        
        else{
            //System.out.println("C Current Ply: " + ply);
            Move bestMove;
            //System.out.println("C Getting Moves for Player " + player);
            ArrayList<Move> currentMoves = currentboard.getMoves(player);
            if(currentMoves.isEmpty()){
                currentMoves.add(new Move());
            }
            
            System.out.println("C Ply: " + ply);
            System.out.println("C moves list for player " + player + ": ");
            printMoveList(currentMoves);
            bestMove = currentMoves.get(0);
            for(Move move:currentMoves){
                //System.out.println("Old Board Before Applying Move: ");
                //System.out.println(currentboard.toString());
                Board newBoard = new Board(currentboard);
                System.out.println("C Ply: " + ply);
                newBoard.applyMoveAB(move, player);
                //System.out.println("New Board After Appling Move: " + move.getMoveString() + " for player " + player);
                //System.out.println(newBoard.toString());
                Move tempMove = alphaBeta(newBoard,ply+1,player*-1,-beta,-alpha,maxDepth);
                System.out.println("C Value: " + tempMove.getMoveValue());
                if(tempMove.getMoveValue() > alpha){
                    bestMove = move;
                    System.out.println("C New Best Move: " + bestMove.moveString +" for Player " + player+ " of Value: " + tempMove.getMoveValue());
                    System.out.println("C Ply: " + ply);
                    alpha = tempMove.moveValue;
                    if(alpha > beta){
                        return bestMove;
                    }
                }
            }
            return bestMove;
        }
        
    }
    
    public double evaluateBoard(int player){
        //********CHECK FOR END GAME*******************************************
        //********IF WIN, MAKE VALUE VERY HIGH*********************************
        //*********IF LOSS, MAKE VALUE LOW************************************
        int countPieces = 0;
        int boardLocation;
        for(int i = 1; i < 9 ; i++){
            for(int j = 1; j < 9; j++){
                boardLocation = i * 10 + j;
                if(boardArray[boardLocation] == player){
                    countPieces++;
                }
            }
        }
        return countPieces;
    }
    
    public double countBlackPieces(){
        int countPieces = 0;
        int boardLocation;
        if(myColor == BLACK){
            for(int i = 1; i < 9 ; i++){
                for(int j = 1; j < 9; j++){
                    boardLocation = i * 10 + j;
                    if(boardArray[boardLocation] == ME){
                        countPieces++;
                    }

                }
            }
        }
        else{
            for(int i = 1; i < 9 ; i++){
                for(int j = 1; j < 9; j++){
                    boardLocation = i * 10 + j;
                    if(boardArray[boardLocation] == ME){
                        countPieces++;
                    }
                }
            }   
        }
        

        return countPieces;
    }
    
    
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder("C ");
        for(int i = 0; i< 10; i++){
            if(i==0)
                sb.append("\t\t");
            else if(i==9)
                sb.append("\t\nC ");
            else
                sb.append(NauertOthelloProject.COLUMNS[i-1] + "\t");
        }
        sb.append("\t");
        int row = 1;
        for(int i = 0; i < 100; i++){
            sb.append(boardArray[i] + "\t");
            if((i-9)%10==0 && i !=0){
                sb.append("\nC \nC ");
                if(row<9){
                    sb.append("R" +row +"\t");
                    row++;
                }
                else{
                    sb.append("\t");
                }
                
            }
        }
        sb.append("\nC ");

        return sb.toString();
    }
}
