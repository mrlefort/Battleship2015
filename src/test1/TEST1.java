/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package test1;

import battleship.interfaces.BattleshipsPlayer;
import tournament.player.PlayerFactory;

/**
 *
 * @author Tobias Grundtvig
 */
public class TEST1 implements PlayerFactory<BattleshipsPlayer>
{
    public TEST1(){}
    
    
    @Override
    public BattleshipsPlayer getNewInstance()
    {
        return new Test1ShotPlayer();
    }

    @Override
    public String getID()
    {
        return "TEST1";
    }

    @Override
    public String getName()
    {
        return "Test1ShotPlayer";
    }
    
}
