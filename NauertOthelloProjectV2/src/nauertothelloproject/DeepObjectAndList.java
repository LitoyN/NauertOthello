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
public class DeepObjectAndList {
    DeepObject bestDeepObject;
    ArrayList<DeepObject> allDeepObjects;
    
    DeepObjectAndList(DeepObject best, ArrayList<DeepObject> list){
        this.bestDeepObject = best;
        this.allDeepObjects = list;
    }
    DeepObjectAndList(){
        this(null,null);
    }
}
