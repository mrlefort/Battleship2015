/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test1;

import battleship.interfaces.BattleshipsPlayer;
import battleship.interfaces.Fleet;
import battleship.interfaces.Position;
import battleship.interfaces.Board;
import battleship.interfaces.Ship;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import tournament.impl.Participant;
import tournament.impl.ParticipantInfo;

/**
 *
 * @author Tobias
 */
public class Test1ShotPlayer implements BattleshipsPlayer {

    private final static Random rnd = new Random();
    private int sizeX;
    private int sizeY;

    private int nextX;
    private int nextY;

    Position shot;

    int shotIndex = 0;

    boolean whichArrayToUse = false;
    boolean whereToStart = false;
    
    private Hunter hunter;
    
    ParticipantInfo partInfo = new Participant(null);

    ArrayList<Position> notYetShot = new ArrayList<>(sizeX * sizeY);
    ArrayList<Position> firePos1 = new ArrayList<>(sizeX * sizeY);
    ArrayList<Position> firePos2 = new ArrayList<>(sizeX * sizeY);
    
    private Position lastShot;

    public Test1ShotPlayer() {

    }

    @Override
    public void placeShips(Fleet fleet, Board board) {
        nextX = 0;
        nextY = 0;
        sizeX = board.sizeX();
        sizeY = board.sizeY();

        for (int i = 0; i < fleet.getNumberOfShips(); ++i) {
            Ship s = fleet.getShip(i);
            boolean vertical = rnd.nextBoolean();
            Position pos;
            if (vertical) {
                int x = rnd.nextInt(sizeX);
                int y = rnd.nextInt(sizeY - (s.size() - 1));
                pos = new Position(x, y);
            } else {
                int x = rnd.nextInt(sizeX - (s.size() - 1));
                int y = rnd.nextInt(sizeY);
                pos = new Position(x, y);
            }
            board.placeShip(pos, s, vertical);
        }
    }

    @Override
    public void incoming(Position pos) {

        //Do nothing
    }

    public void fillShootArrays() {
        //Fyld et array med objecter for hvert co-ordinat
        
        //skal slettes
//        sizeX = 10;
//        sizeY = 10;
        
        
        firePos1 = new ArrayList<>(sizeX * sizeY);

        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                firePos2.add(new Position(x, y));
                notYetShot.add(new Position(x, y));

            }
        }
        Collections.shuffle(firePos1);

        for (int x = 2; x < 8; x++) {
            for (int y = 2; y < 8; y++) {
                firePos1.add(new Position(x, y));
                firePos2.remove(new Position(x, y));
                notYetShot.add(new Position(x, y));

            }
        }
        Collections.shuffle(firePos2);

    }

    @Override
    public Position getFireCoordinates(Fleet enemyShips) {

        
        
        if (whereToStart == true){
            whichArrayToUse = true;
        }
        
        
        //bruger først firePos1 og derefter firePos2
        if (whichArrayToUse == false) {
            shot = firePos1.get(shotIndex);
            shotIndex++;

            if (shotIndex == firePos1.size()) {
                shotIndex = 0;
                whichArrayToUse = true;

            }

        }

        if (whichArrayToUse == true) {
            shot = firePos2.get(shotIndex);
            shotIndex++;
            
            if (shotIndex == firePos2.size()) {
                shotIndex = 0;
                whichArrayToUse = false;

            }

        }
        
        
        lastShot = shot;
        notYetShot.remove(shot);
        return shot;
    }

    @Override
    public void hitFeedBack(boolean hit, Fleet enemyShips) {
        if (hit){
            if (hunter == null)
                hunter = new Hunter(notYetShot, lastShot);
                hunter.hit();
                }
        }
    

    @Override
    public void startMatch(int rounds) {
        //Do nothing
    }

    @Override
    public void startRound(int round) {
        //Do nothing
    }

    @Override
    public void endRound(int round, int points, int enemyPoints) {
        //Do nothing
    }

    @Override
    public void endMatch(int won, int lost, int draw) {
        //Do nothing
    }

        // skal slettes
    public static void main(String[] args) {
        Test1ShotPlayer p = new Test1ShotPlayer();

        p.fillShootArrays();
    }
}
