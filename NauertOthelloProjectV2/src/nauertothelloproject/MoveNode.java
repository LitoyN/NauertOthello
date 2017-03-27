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
public class MoveNode {
    
    MoveNode parent;
    Move thisMove;
    ArrayList<MoveNode> children;
    double alpha;
    double beta;
    int ply;
    int player;
    boolean expanded;
    Board thisBoard;
    
    MoveNode(Board board, MoveNode parent, Move thisMove, int player, int ply){
        
        this.parent = parent;
        this.thisMove = thisMove;
        thisBoard = board;
        this.player = player;
        thisBoard.applyMove(thisMove, this.player);
        this.ply = ply;
        
        children = new ArrayList<>();
        alpha = Double.MIN_VALUE;
        beta = Double.MAX_VALUE;
        
        expanded = false;
        
    }
    
    MoveNode(Board board, int player){
        parent = null;
        thisMove = null;
        thisBoard = board;
        children = new ArrayList<>();
        alpha = Double.MIN_VALUE;
        beta = Double.MAX_VALUE;
        ply = 0;
        this.player = player;
        expanded = true;
        
    }
    
    public void expand(MoveNode parent, int ply){
        
    }
    
    public void expand(int player){
        children = thisBoard.getMoves(player);
        expanded = true;
    }
}
