/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nauertothelloproject;

import java.util.Arrays;

/**
 *
 * @author elliot
 */
public class Board {
    
    int boardArray[];
    int myScore;
    int oppScore;
    int noMovesCount;
    boolean gameOver;
    
    public Board(String myColor){
        boardArray = new int[100];
        myScore = 2;
        oppScore = 2;
        gameOver = false;
        noMovesCount = 0;
        
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
    
    public void getMoves(int player){
        
    }
    
    public void applyMove(int player, int move){
        
    }
    
    public boolean isGameOver(){
        return gameOver;
    }
    
    public void getScore(){
        
    }
    
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder("[");
        for(int i = 0; i < 100; i++){
            sb.append(boardArray[i] + "\t");
            if((i-9)%10==0 && i !=0){
                sb.append("\n");
            }
        }

        return sb.toString();
    }
}
