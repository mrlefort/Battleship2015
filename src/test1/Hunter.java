/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test1;

import java.util.List;
import java.util.Stack;

/**
 *
 * @author Steffen
 */
public class Hunter {
    private final List<Position> shotList;
    private final Stack<Position> stack;
    private Position startPos;

    public Hunter(List<Position> shotList, Position startPos) {
        this.shotList = shotList;
        this.stack = new Stack<>();
        stack.push(startPos);
        
    }
    
    public Position getShot(){
        
    }
    
    
    
}
