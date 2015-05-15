/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test1;

import java.util.ArrayList;
import java.util.Stack;
import battleship.interfaces.Position;

/**
 *
 * @author Steffen
 */
public class Hunter {
    private final ArrayList<Position> shotList;
    private final Stack<Position> stack;
    private Position startPos;

    public Hunter(ArrayList<Position> shotList, Position startPos) {
        this.shotList = shotList;
        this.stack = new Stack<>();
        stack.push(startPos);
        
    }
    
    public Position getShot(){
        if(stack.empty()) return null;
	startPos = stack.pop();
	return startPos;
    }
    
    public void Destroyer(Position p){
        
        //nord
	Position temp = new Position(p.x, p.y+1);
	if(shotList.remove(temp)){
	    stack.push(temp);
	}
	//øst
	temp = new Position(p.x+1, p.y);
	if(shotList.remove(temp)){
	    stack.push(temp);
	}
	//vest
	temp = new Position(p.x-1, p.y);
	if(shotList.remove(temp)){
	    stack.push(temp);
	}
	//syd
	temp = new Position(p.x, p.y-1);
	if(shotList.remove(temp)){
	    stack.push(temp);
	}
    
    }
    
    
    public void hit(){
	Destroyer(startPos);
    }
    
    
    
}
