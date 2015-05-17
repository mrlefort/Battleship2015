/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test1;

import java.util.ArrayList;
import java.util.Stack;
import battleship.interfaces.Position;
import java.util.List;

/**
 *
 * @author Steffen
 */
public class Hunter {
    private final List<Pos> shotList;
    private final Stack<Pos> stack;
    private Pos lastShot;
    

    public Hunter(List<Pos> shotList, Pos startPos) {
        this.shotList = shotList;
        this.stack = new Stack<>();
        stack.push(startPos);
        
    }
    
    public Pos getShot(){
        if(stack.empty()) return null;
	lastShot = stack.pop();     //Removes the object at the top of this stack and returns that object as the value of this function.
	return lastShot;
    }
    
    public void Destroyer(Pos p){
        
        //north
	Pos temp = new Pos(p.x, p.y+1);
	if(shotList.remove(temp)){    //removes from notYetShot
	    stack.push(temp);
	}
	//east
	temp = new Pos(p.x+1, p.y);
	if(shotList.remove(temp)){    //removes from notYetShot
	    stack.push(temp);
	}
	//west
	temp = new Pos(p.x-1, p.y);
	if(shotList.remove(temp)){    //removes from notYetShot
	    stack.push(temp);
	}
	//south
	temp = new Pos(p.x, p.y-1);
	if(shotList.remove(temp)){    //removes from notYetShot
	    stack.push(temp);
	}
    
    }
    
    
    public void hit(){
	Destroyer(lastShot);
    }
    
    
    
}
