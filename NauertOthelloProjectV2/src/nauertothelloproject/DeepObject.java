/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nauertothelloproject;

import java.util.ArrayList;

/**
 *
 * @author elliot   
 */
public class DeepObject {
    
    ArrayList<Move> deepMoveList;
    Board deepBoard;
    
    DeepObject(ArrayList<Move> deepMoveList, Board deepBoard){
        this.deepMoveList = deepMoveList;
        this.deepBoard = deepBoard;
    }
    
    DeepObject(Board deepBoard){
        this(null,deepBoard);
    }
    
    DeepObject(){
        this(null,null);
    }
    
    DeepObject(DeepObject toCopy){
        deepMoveList = toCopy.deepMoveList;
        deepBoard = toCopy.deepBoard;
    }
    
    public void setMoveList(ArrayList<Move> newList){
        deepMoveList = newList;
    }
    
    public void setBoard(Board newBoard){
        deepBoard = newBoard;
    }
    
}
