
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
        System.out.println("C");
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
            //System.out.println("C Applying My Move: ");
            //System.out.println("C " + myColor + " " + move.moveString);
        }
        else{
            //System.out.println("C Applying Opponent Move: ");
            //System.out.println("C " + oppColor + " " + move.moveString);
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
    
    
    public DeepObjectAndList plusIteration(DeepObjectAndList starterList, int incrementDepth){
        DeepObjectAndList thisDeepAndList = new DeepObjectAndList();

        DeepObject bestDeep = starterList.bestDeepObject;
        bestDeep.deepMoveList.get(bestDeep.deepMoveList.size()-1).moveValue = 7;
        Move bestMove = bestDeep.deepMoveList.get(bestDeep.deepMoveList.size()-1);
        DeepObjectAndList bestDeepAndList = new DeepObjectAndList(bestDeep, new ArrayList<DeepObject>());        

        System.out.println("C Starter List Deep Lists: ");
        for(DeepObject deep:starterList.allDeepObjects){
            //System.out.println("C Next DeepObject");
            for(Move move:deep.deepMoveList){
                //System.out.println("C " + move.moveString);
            }
        }
        for(DeepObject deep:starterList.allDeepObjects){
            Board newBoard = deep.deepBoard;
            thisDeepAndList = iterativeGetMoves(newBoard,0,ME,bestMove.moveValue,Double.MAX_VALUE,incrementDepth,new Move());
            for(DeepObject thisDeep:thisDeepAndList.allDeepObjects){

                thisDeep.deepMoveList.remove(0);
                ArrayList<Move> listToAdd = new ArrayList<>();
                Board boardToAdd = thisDeep.deepBoard;

                for(Move tempMove:thisDeep.deepMoveList){
                    listToAdd.add(tempMove);

                }

                for(Move tempMove:deep.deepMoveList){
                    listToAdd.add(tempMove);

                }

                DeepObject deepToAdd = new DeepObject(listToAdd,boardToAdd);
                bestDeepAndList.allDeepObjects.add(deepToAdd);
                Move tempBestCheck = deepToAdd.deepMoveList.get(deepToAdd.deepMoveList.size()-1);
                if(tempBestCheck.moveValue > bestMove.moveValue){
                    System.out.println("C NEW BEST AND MOVE");
                    System.out.println("C move: " + tempBestCheck.moveString);
                    System.out.println("C move value: " + tempBestCheck.moveValue);
                    bestDeepAndList.bestDeepObject = deepToAdd;
                    bestMove = tempBestCheck;
                }
            }
        }
        
        return bestDeepAndList;
    }
    
    public DeepObjectAndList iterativeGetMoves(Board currentboard, int ply, int player, double alpha, double beta, int incrementDepth, Move lastMove){
        DeepObjectAndList allReturn = new DeepObjectAndList();
        ArrayList<Move> movesList = new ArrayList<>();
        DeepObject returnDeepObject = new DeepObject(movesList, currentboard);
        ArrayList<DeepObject> allDeepObjects = new ArrayList<>();
        if(ply >= incrementDepth){

            ArrayList<DeepObject> newList = new ArrayList<>();
            //System.out.println("C Max Depth Reached: " + ply);
            Move returnMove = new Move();
            returnMove.moveValue = currentboard.evaluateBoard(ME, lastMove);
            //System.out.println("C Move Being Returned: " + returnMove.moveString);
            //System.out.println("C Move Value Being Returned: " + returnMove.getMoveValue());
            movesList.add(returnMove);
            returnDeepObject.deepMoveList = movesList;
            returnDeepObject.deepBoard = currentboard;
            newList.add(returnDeepObject);
            allReturn.allDeepObjects = newList;
            allReturn.bestDeepObject = returnDeepObject;
            return allReturn;
        }
               
        
        else{

            Move bestMove;
            ArrayList<Move> currentMoves = currentboard.getMoves(player);
            if(currentMoves.isEmpty()){
                currentMoves.add(new Move());
            }
            

            //System.out.println("C Ply: " + ply);
            //System.out.println("C " + currentMoves.size() + " moves list for player " + player + ": ");
            //printMoveList(currentMoves);
            
            bestMove = currentMoves.get(0);

            Board defaultBoard = new Board(currentboard);
            if(ply%2 == 0){
                defaultBoard.applyMoveAB(bestMove,ME);
            }
            else{
                defaultBoard.applyMoveAB(bestMove, OPPONENT);
            }
            bestMove.moveValue = defaultBoard.evaluateBoard(ME, bestMove);
            ArrayList<Move> defaultMoveList = new ArrayList<>();
            defaultMoveList.add(bestMove);
            //System.out.println("C DEFAULT MOVE: " + bestMove.moveString);
            DeepObject defaultDeep = new DeepObject(defaultMoveList, defaultBoard);
            allReturn.bestDeepObject = defaultDeep;

            
            
            for(Move move:currentMoves){

                Board newBoard = new Board(currentboard);
                newBoard.applyMoveAB(move, player);
                //System.out.println("C Applying move for player " + player + ": " + move.moveString);
                
                DeepObjectAndList tempDeepObjectList = iterativeGetMoves(newBoard,ply+1,player*-1,alpha,beta,incrementDepth, move);
                //System.out.println(newBoard.toString());
                DeepObject tempDeepObject = tempDeepObjectList.bestDeepObject;
                ArrayList<Move> tempMoveList = tempDeepObject.deepMoveList;
                Move tempMove = tempMoveList.get(tempMoveList.size()-1);
                for(DeepObject thisObject:tempDeepObjectList.allDeepObjects){
                    thisObject.deepMoveList.add(move);
                    allDeepObjects.add(thisObject);
                }
                allReturn = tempDeepObjectList;
                move.moveValue = tempMove.moveValue;
                allReturn.allDeepObjects = allDeepObjects;    
                //System.out.println("C allDeepObjects Size: " + allReturn.allDeepObjects.size());
                //System.out.println("C move: " + move.moveString);
                //System.out.println("C returned move (tempMove): " + move.moveString);
                //Move tempMove = alphaBeta(newBoard,ply+1,player*-1,alpha,beta,maxDepth, move.moveInt);
                
                //System.out.println("C Move: " + move.moveString);
                //System.out.println("C Value: " + tempMove.getMoveValue());
                if(player == ME && move.moveValue > alpha){
                    //allReturn = tempDeepObjectList;
                    //move.moveValue = tempMove.moveValue;
                    //allReturn.bestDeepObject.deepMoveList.add(move);
                    //allReturn.allDeepObjects = allDeepObjects;
                    //System.out.println("C allDeepObjects Size: " + allReturn.allDeepObjects.size());
                    //System.out.println("C New Best Move: " + move.moveString +" for Player " + player+ " of Value: " + tempMove.getMoveValue());
                    //System.out.println("C Ply: " + ply);
                    alpha = move.moveValue;
                    //System.out.println("C Alpha: " + alpha);
                    //System.out.println("C Beta: " + beta);
                    //System.out.println("C");
                    if(alpha >= beta){
                        //System.out.println("C alpha > beta, returning Move: " + move.moveString);
                        return allReturn;
                    }
                }
                else if(player == OPPONENT && move.moveValue < beta){
                    //allReturn = tempDeepObjectList;
                    //move.moveValue = tempMove.moveValue;
                    //allReturn.bestDeepObject.deepMoveList.add(move);
                    //allReturn.allDeepObjects = allDeepObjects;
                    //System.out.println("C allDeepObjects Size: " + allReturn.allDeepObjects.size());
                    //System.out.println("C New Best Move: " + move.moveString +" for Player " + player+ " of Value: " + tempMove.getMoveValue());
                    //System.out.println("C Ply: " + ply);
                    beta = move.moveValue;
                    //System.out.println("C Alpha: " + alpha);
                    //System.out.println("C Beta: " + beta);
                    //System.out.println("C");
                    if(alpha >= beta){
                        //System.out.println("C alpha > beta, returning Move: " + move.moveString);
                        return allReturn;
                    }
                }
            }

            allReturn.allDeepObjects = allDeepObjects;
            //System.out.println("C All deepobjects size: " + allReturn.allDeepObjects.size());
            //System.out.println("C best move list size: " + allReturn.bestDeepObject.deepMoveList.size());
            return allReturn;
        }
    }
    
    public ArrayList<Move> alphaBeta(MoveNode node, int maxDepth, Move lastMove){
        
        Board currentboard = node.thisBoard;
        int ply = node.ply;
        int player = node.player;
        double alpha = node.alpha;
        double beta = node.beta;
        
        ArrayList<Move> movesList = new ArrayList<>();
        if(ply >= maxDepth){
            System.out.println("C Max Depth Reached: " + ply);
            Move returnMove = new Move();
            returnMove.moveValue = currentboard.evaluateBoard(ME, lastMove);
            System.out.println("C Move Value Being Returned: " + returnMove.moveValue);
            movesList.add(returnMove);
            //return returnMove;
            return movesList;
        }
        
        else{

            Move bestMove;

            ArrayList<Move> currentMoves = currentboard.getMoves(player);
            if(currentMoves.isEmpty()){
                currentMoves.add(new Move());
            }
            //System.out.println("C");
            System.out.println("C Ply: " + ply);
            System.out.println("C moves list for player " + player + ": ");
            printMoveList(currentMoves);
            bestMove = currentMoves.get(0);
            Board defaultBoard = new Board(currentboard);
            for(Move move:currentMoves){

                if(bestMove.moveInt == 0){
                    System.out.println("C Badly Handled Pass");
                }
                movesList.add(bestMove);
                if(ply%2 == 0){
                    defaultBoard.applyMoveAB(move,ME);
                }
                else{
                    defaultBoard.applyMoveAB(move, OPPONENT);
                }
                
                move.moveValue = defaultBoard.evaluateBoard(ME, new Move());
                if(move.moveValue > bestMove.moveValue){
                    bestMove = move;
                }
            }
            bestMove.moveValue = Double.MIN_VALUE;
            for(Move move:currentMoves){

                Board newBoard = new Board(currentboard);
                newBoard.applyMoveAB(move, player);
                ArrayList<Move> tempMovesList = alphaBeta(newBoard,ply+1,player*-1,alpha,beta,maxDepth, move);
                Move tempMove = tempMovesList.get(tempMovesList.size()-1);
                //Move tempMove = alphaBeta(newBoard,ply+1,player*-1,alpha,beta,maxDepth, move.moveInt);
                
                System.out.println("C Returned");
                System.out.println("C Move: " + tempMove.moveString);
                System.out.println("C Value: " + tempMove.moveValue);
                if(player == ME && tempMove.moveValue > alpha){
                    bestMove = move;
                    tempMovesList.add(move);
                    bestMove.setMoveValue(tempMove.moveValue);
                    //if(ply == 0)
                        //System.out.println("C *************NEW BEST MOVE AT PLY 0");
                    System.out.println("C New Best Move: " + bestMove.moveString +" for Player " + player+ " of Value: " + tempMove.getMoveValue());
                    System.out.println("C Ply: " + ply);
                    alpha = tempMove.moveValue;
                    System.out.println("C Alpha: " + alpha);
                    System.out.println("C Beta: " + beta);
                    System.out.println("C");
                    if(alpha >= beta){
                        System.out.println("C alpha > beta, returning Move: " + bestMove.moveString);
                        return tempMovesList;
                    }
                }
                else if(player == OPPONENT && tempMove.moveValue < beta){
                    //System.out.println("C value vs beta: " + tempMove.moveValue + " vs " + beta);
                    bestMove = move;
                    tempMovesList.add(move);
                    bestMove.setMoveValue(tempMove.moveValue);
                    System.out.println("C New Best Move: " + bestMove.moveString +" for Player " + player+ " of Value: " + tempMove.getMoveValue());
                    System.out.println("C Ply: " + ply);
                    beta = tempMove.moveValue;
                    System.out.println("C Alpha: " + alpha);
                    System.out.println("C Beta: " + beta);
                    System.out.println("C");
                    if(alpha >= beta){
                        System.out.println("C alpha > beta,  returning Move: " + bestMove.moveString);
                        return tempMovesList;
                    }
                }
            }
            movesList.add(bestMove);
            System.out.println("C Final Move Returned: " + bestMove.moveString);
            System.out.println("C Final Value: " + bestMove.moveValue);
            System.out.println("C Alpha: " + alpha);
            System.out.println("C Beta: " + beta);
            return movesList;
        }
        
    }
    public ArrayList<Move> alphaBeta(Board currentboard, int ply, int player, double alpha, double beta, int maxDepth, Move lastMove){
        ArrayList<Move> movesList = new ArrayList<>();
        if(ply >= maxDepth){
            System.out.println("C Max Depth Reached: " + ply);
            Move returnMove = new Move();
            returnMove.moveValue = currentboard.evaluateBoard(ME, lastMove);
            System.out.println("C Move Value Being Returned: " + returnMove.moveValue);
            movesList.add(returnMove);

            return movesList;
        }
        
        else{

            Move bestMove;

            ArrayList<Move> currentMoves = currentboard.getMoves(player);
            if(currentMoves.isEmpty()){
                currentMoves.add(new Move());
            }
            System.out.println("C");
            System.out.println("C Ply: " + ply);
            System.out.println("C moves list for player " + player + ": ");
            printMoveList(currentMoves);
            bestMove = currentMoves.get(0);
            if(bestMove.moveInt == 0){
                System.out.println("C Badly Handled Pass");
            }
            movesList.add(bestMove);
            Board defaultBoard = new Board(currentboard);
            if(ply%2 == 0){
                defaultBoard.applyMoveAB(bestMove,ME);
            }
            else{
                defaultBoard.applyMoveAB(bestMove, OPPONENT);
            }
            bestMove.moveValue = defaultBoard.evaluateBoard(ME, new Move());
            for(Move move:currentMoves){

                Board newBoard = new Board(currentboard);
                System.out.println("C Applying Move: " + move.moveString);
                newBoard.applyMoveAB(move, player);
                ArrayList<Move> tempMovesList = alphaBeta(newBoard,ply+1,player*-1,alpha,beta,maxDepth, move);
                Move tempMove = tempMovesList.get(tempMovesList.size()-1);
              
                System.out.println("C Returned");
                System.out.println("C Move: " + tempMove.moveString);
                System.out.println("C Value: " + tempMove.moveValue);
                if(player == ME && tempMove.moveValue > alpha){
                    bestMove = move;
                    tempMovesList.add(move);
                    bestMove.setMoveValue(tempMove.moveValue);
                    //if(ply == 0)
                        //System.out.println("C *************NEW BEST MOVE AT PLY 0");
                    System.out.println("C New Best Move: " + bestMove.moveString +" for Player " + player+ " of Value: " + tempMove.getMoveValue());
                    System.out.println("C Ply: " + ply);
                    alpha = tempMove.moveValue;
                    System.out.println("C Alpha: " + alpha);
                    System.out.println("C Beta: " + beta);
                    System.out.println("C");
                    if(alpha >= beta){
                        System.out.println("C alpha > beta, returning Move: " + bestMove.moveString);
                        return tempMovesList;
                    }
                }
                else if(player == OPPONENT && tempMove.moveValue < beta){
                    System.out.println("C value vs beta: " + tempMove.moveValue + " vs " + beta);
                    bestMove = move;
                    tempMovesList.add(move);
                    bestMove.setMoveValue(tempMove.moveValue);
                    System.out.println("C New Best Move: " + bestMove.moveString +" for Player " + player+ " of Value: " + tempMove.getMoveValue());
                    System.out.println("C Ply: " + ply);
                    beta = tempMove.moveValue;
                    System.out.println("C Alpha: " + alpha);
                    System.out.println("C Beta: " + beta);
                    System.out.println("C");
                    if(alpha >= beta){
                        System.out.println("C alpha > beta,  returning Move: " + bestMove.moveString);
                        return tempMovesList;
                    }
                }
            }
            movesList.add(bestMove);
            System.out.println("C Final Move Returned: " + bestMove.moveString);
            System.out.println("C Final Value: " + bestMove.moveValue);
            System.out.println("C Alpha: " + alpha);
            System.out.println("C Beta: " + beta);
            return movesList;
        }
        
    }
    
    /**public double evaluateBoard(int player){
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
    }**/
    
    public double evaluateBoard(int player, Move lastMove){
        //********CHECK FOR END GAME*******************************************
        //********IF WIN, MAKE VALUE VERY HIGH*********************************
        //*********IF LOSS, MAKE VALUE LOW************************************
        //System.out.println(this.toString());
        double value = 1000;
        //int countPieces = 0;
        int boardLocation;
        
        double countMyPieces = countPieces(ME);
        double countOppPieces = countPieces(OPPONENT);
        ArrayList<Move> myMoves = getMoves(ME);
        ArrayList<Move> oppMoves = getMoves(OPPONENT);
        
        if(myMoves.isEmpty() && oppMoves.isEmpty()){
            //System.out.println("C FOUND END GAME*******************************");
            if(countMyPieces > countOppPieces){
                //System.out.println("C I'M GOING TO WIN!");
                //System.out.println("C LAST MOVE:" + lastMove.moveString);
                //System.out.println(this.toString());
                return 10000000;
            }
            else{
                //System.out.println("C I'M GOING TO LOSE!");
                //System.out.println("C LAST MOVE:" + lastMove.moveString);
                return -10000000;
            }
        }
        
        for(int i = 1; i < 9 ; i++){
            for(int j = 1; j < 9; j++){
                boardLocation = i * 10 + j;
                
                
                if(boardLocation == 11 || boardLocation == 18 ||
                        boardLocation == 81 || boardLocation == 88){
                    if(boardArray[boardLocation] == player){
                    //System.out.println("C Found Corner Increasing Value");
                    value *= 1.5;
                    }
                }
                
                if(boardLocation == 53 && boardArray[boardLocation] == OPPONENT){
                    value *= .5;
                    //System.out.println("C OPPONENT AT C5");
                }
                
                if(boardArray[boardLocation] == player){
                    //System.out.println("Found Player Here." + boardLocation);
                    value = evaluateLocation(boardLocation, value);
                }
            }
        }
        return value;
    }
    
    public boolean connectedToCorner(int boardLocation){
        boolean cornerFound = false;
        int newLocation;
        for(int i = 1; i < 8; i+=2){
            newLocation = boardLocation + DIRECTIONS[i];
            if(boardArray[newLocation] == ME){
                if(newLocation == 11 || newLocation == 18 ||
                        newLocation == 81 || newLocation == 88){
                    //System.out.println("C " + boardLocation + " connected to corner!");
                    //System.out.println("C");
                    //System.out.println(this.toString());
                    return true;
                }
                else{
                    cornerFound = directToCorner(newLocation,DIRECTIONS[i]);
                }
            }
        }
        return cornerFound;
    }
    public boolean directToCorner(int boardLocation, int direction){
        boolean cornerFound = false;
        int newLocation = boardLocation + direction;
        if(boardArray[newLocation] == ME){
            if(newLocation == 11 || newLocation == 18 ||
                    newLocation == 81 || newLocation == 88){
                //System.out.println("C " + boardLocation + " connected to corner!");
                //System.out.println("C");
                //System.out.println(this.toString());    
                return true;
            }
            else{
                cornerFound = directToCorner(newLocation,direction);
            }   
        }
        return cornerFound;
    }
    
    public boolean fullRow(int boardLocation){
        boolean fullRow = false;
        
        return fullRow;
    }
    
    public boolean moveOneDirection(){
        boolean end = false;
        
        
        return end;
    }
    
    public double evaluateLocation(int boardLocation, double val){
        double value = val;
        
        switch(boardLocation){
            case 11:
                //corner
                value = value*3;
                //edge
                value *= 1.5;
                
                if(connectedToCorner(boardLocation)){
                    value *=1.5;
                }
                break;
            case 12:
                //edge
                value *= 1.5;
                
                if(connectedToCorner(boardLocation)){
                    value *=1.5;
                }
                break;
            case 13:
                //edge
                value *= 1.5;
                
                if(connectedToCorner(boardLocation)){
                    value *=1.5;
                }
                break;
            case 14:
                //edge
                value *= 1.5;
                
                if(connectedToCorner(boardLocation)){
                    value *=1.5;
                }
                break;
            case 15:
                //edge
                value *= 1.5;
                
                if(connectedToCorner(boardLocation)){
                    value *=1.5;
                }
                break;
            case 16:
                //edge
                value *= 1.5;
                
                if(connectedToCorner(boardLocation)){
                    value *=1.5;
                }
                break;
            case 17:
                //edge
                value *= 1.5;
                
                if(connectedToCorner(boardLocation)){
                    value *=1.5;
                }
                break;
            case 18:
                //corner
                value = value*3;
                //edge
                value *= 1.5;
                
                if(connectedToCorner(boardLocation)){
                    value *=1.5;
                }
                break;
            case 21:
                //edge
                value *= 1.5;
                
                if(connectedToCorner(boardLocation)){
                    value *=1.5;
                }
                break;
            case 22:
                break;
            case 23:
                value *= .5;
                
                break;
            case 24:
                break;
            case 25:
                break;
            case 26:
                break;
            case 27:
                break;
            case 28:
                //edge
                value *= 1.5;
                
                if(connectedToCorner(boardLocation)){
                    value *=1.5;
                }
                break;
            case 31:
                //edge
                value *= 1.5;
                
                if(connectedToCorner(boardLocation)){
                    value *=1.5;
                }
                break;
            case 32:
                break;
            case 33:
                break;
            case 34:
                break;
            case 35:
                break;
            case 36:
                break;
            case 37:
                break;
            case 38:
                //edge
                value *= 1.5;
                
                if(connectedToCorner(boardLocation)){
                    value *=1.5;
                }
                break;
            case 41:
                //edge
                value *= 1.5;
                
                if(connectedToCorner(boardLocation)){
                    value *=1.5;
                }
                break;
            case 42:
                break;
            case 43:
                
                break;
            case 44:
                break;
            case 45:
                break;
            case 46:
                break;
            case 47:
                break;
            case 48:
                //edge
                value *= 1.5;
                
                if(connectedToCorner(boardLocation)){
                    value *=1.5;
                }
                break;
            case 51:
                //edge
                value *= 1.5;
                
                if(connectedToCorner(boardLocation)){
                    value *=1.5;
                }
                break;
            case 52:
                break;
            case 53:
                break;
            case 54:
                break;
            case 55:

                break;
            case 56:

                break;
            case 57:
                break;
            case 58:
                //edge
                value *= 1.5;
                
                if(connectedToCorner(boardLocation)){
                    value *=1.5;
                }
                break;
            case 61:
                //edge
                value *= 1.5;
                
                if(connectedToCorner(boardLocation)){
                    value *=1.5;
                }
                break;
            case 62:
                break;
            case 63:
                break;
            case 64:

                break;
            case 65:
                break;
            case 66:
                break;
            case 67:
                break;
            case 68:
                //edge
                value *= 1.5;
                
                if(connectedToCorner(boardLocation)){
                    value *=1.5;
                }
                break;
            case 71:
                //edge
                value *= 1.5;
                
                if(connectedToCorner(boardLocation)){
                    value *=1.5;
                }
                break;
            case 72:
                break;
            case 73:
                break;
            case 74:
                break;
            case 75:
                break;
            case 76:
                break;
            case 77:
                break;
            case 78:
                //edge
                value *= 1.5;
                
                if(connectedToCorner(boardLocation)){
                    value *=1.5;
                }
                break;
            case 81:
                //corner
                value = value*3;
                //edge
                value *= 1.5;
                
                if(connectedToCorner(boardLocation)){
                    value *=1.5;
                }
                break;
            case 82:
                //edge
                value *= 1.5;
                
                if(connectedToCorner(boardLocation)){
                    value *=1.5;
                }
                break;
            case 83:
                //edge
                value *= 1.5;
                
                if(connectedToCorner(boardLocation)){
                    value *=1.5;
                }
                break;
            case 84:
                //edge
                value *= 1.5;
                
                if(connectedToCorner(boardLocation)){
                    value *=1.5;
                }
                break;
            case 85:
                //edge
                value *= 1.5;
                
                if(connectedToCorner(boardLocation)){
                    value *=1.5;
                }
                break;
            case 86:
                //edge
                value *= 1.5;
                
                if(connectedToCorner(boardLocation)){
                    value *=1.5;
                }
                break;
            case 87:
                //edge
                value *= 1.5;
                
                if(connectedToCorner(boardLocation)){
                    value *=1.5;
                }
                break;
            case 88:
                //corner
                value = value*3;
                //edge
                value *= 1.5;
                
                if(connectedToCorner(boardLocation)){
                    value *=1.5;
                }
                break;
        }
        
        return value;
    }
    
    public double countPieces(int player){
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
        for(int i = 1; i < 9 ; i++){
            for(int j = 1; j < 9; j++){
                boardLocation = i * 10 + j;
                if(myColor == BLACK){
                    if(boardArray[boardLocation] == ME){
                        countPieces++;
                    }
                }
                else{
                    if(boardArray[boardLocation] == OPPONENT){
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
