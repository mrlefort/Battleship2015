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

    Pos shot;

    boolean whichArrayToUse = false;
    boolean whereToStart = false;

    private Hunter hunter;

    private boolean[][] posHist;

    ParticipantInfo partInfo = new Participant(null);

    ArrayList<Pos> notYetShot = new ArrayList<>(sizeX * sizeY);
    ArrayList<Pos> firePos1;
    ArrayList<Pos> firePos2;
    
    int shotIndex;
    int shotIndex2;
    


    private Pos lastPos;

    public Test1ShotPlayer() {

    }

    @Override
    public void startMatch(int rounds) {
        
        
        
        
    }

    @Override
    public void startRound(int round) { 
        System.out.println("this is start round");
        hunter = null;
        lastPos = null;
        firePos1 = new ArrayList<>();
        firePos2 = new ArrayList<>();

//        decideWhereToStart();
        fillNotYetShot();
        fillShootArrays();
    }

    @Override
    public void placeShips(Fleet fleet, Board board) {
        nextX = 0;
        nextY = 0;
        sizeX = board.sizeX();
        sizeY = board.sizeY();

        posHist = new boolean[sizeX][sizeY];

        for (int i = 0; i < fleet.getNumberOfShips(); ++i) {
            Ship s = fleet.getShip(i);
            placeShip(s, board);
        }
    }

    private void placeShip(Ship s, Board board) {

        //Places a ship
        boolean vertical = rnd.nextBoolean();
        int x;
        int y;

        while (true) {

            if (vertical) {
                x = rnd.nextInt(sizeX);
                y = rnd.nextInt(sizeY - (s.size() - 1));

            } else {
                x = rnd.nextInt(sizeX - (s.size() - 1));
                y = rnd.nextInt(sizeY);
            }
            if (testShip(s, x, y, vertical)) {

                break;

            }

        }
        //Adds it to markShip()
        markShip(s, x, y, vertical);
        //Places it on the board
        board.placeShip(new Position(x, y), s, vertical);

    }

    private boolean testShip(Ship s, int xPos, int yPos, boolean vertical) {

        //Test if there's a ship already
        for (int i = 0; i < s.size(); i++) {

            if (posHist[xPos][yPos]) {

                return false;
            }

            if (xPos < 0 || xPos > 9) {

                if (posHist[xPos + 1][yPos] || (posHist[xPos - 1][yPos])) {
                    return false;
                }
            }

            if (yPos < 0 || yPos > 9) {

                if (posHist[xPos][yPos + 1] || posHist[xPos][yPos - 1]) {
                    return false;
                }
            }

            if (vertical) {

                ++yPos;

            } else {

                ++xPos;

            }
        }

        return true;
    }

    private void markShip(Ship s, int xPos, int yPos, boolean vertical) {

        //marks where my ships are
        for (int i = 0; i < s.size(); i++) {
            posHist[xPos][yPos] = true;

            if (vertical) {

                ++yPos;

            } else {

                ++xPos;

            }
        }
    }

    @Override
    public void incoming(Position pos) {

        //Do nothing
    }

    public void fillNotYetShot() {
        int l = 0;

        while (l < 101) {
            Pos notShot = new Pos(nextX, nextY);
            ++nextX;
            if (nextX >= 10) {
                nextX = 0;
                ++nextY;
                if (nextY >= 10) {
                    nextY = 0;
                }
            }
            l++;
            notYetShot.add(notShot);
        }
    }

    public void fillShootArrays() {
        //Fills 2 arrays, 1 inner and one outer.
        shotIndex = 0;
        shotIndex2 = 0;
        

        int h = 0;
        int g = 0;
        int t = 0;

        

        while (h < 10) {
            for (int f = t; f < 10; f += 2) {
                firePos2.add(new Pos(f, g));
                if (f == 8) {
                    t = 1;
                    g++;
                    h++;
                }
                if (f == 9) {
                    t = 0;
                    g++;
                    h++;
                }
            }
        }

        h = 0;
        g = 0;
        t = 2;

        while (h < 10) {
            for (int f = t; f < 8; f += 2) {
                Pos tmp = new Pos(f, g);
                firePos1.add(tmp);
                if (firePos2.remove(tmp));
                if (f == 6) {
                    t = 3;
                    g++;
                    h++;
                }
                if (f == 7) {
                    t = 2;
                    g++;
                    h++;
                }
            }
        }
        for (int i = 0; i < firePos1.size(); i++) {
            System.out.println("This is firePos1: " + firePos1.get(i).getX() + "," + firePos1.get(i).getY());
        }
        for (int i = 0; i < firePos2.size(); i++) {
            System.out.println("This is firePos2: " + firePos2.get(i).getX() + "," + firePos2.get(i).getY());
        }
    }

//    public void decideWhereToStart() {
//        whichArrayToUse = whereToStart;
//    }

    @Override
    public Position getFireCoordinates(Fleet enemyShips) {

        Position p;
//  systematicshotplayer
//        Position shot = new Position(nextX, nextY);
//        ++nextX;
//        if(nextX >= sizeX)
//        {
//            nextX = 0; 
//            ++nextY;
//            if(nextY >= sizeY)
//            {
//                nextY = 0;
//            }
//        }
//        return shot;

        

        

        if (hunter != null) {
            shot = hunter.getShot();
            if (hunter.getShot() == null) {

                hunter = null;
            }

        } else {


            
            
            //First shoots from firePos1 then afterwards firePos2
            if (shotIndex <= firePos1.size()) {
                shot = firePos1.get(shotIndex++);

                lastPos = shot;
                notYetShot.remove(shot);
                
                System.out.println("shot taken from firePos1 " + shot.getX()+ ",  " + shot.getY());
                


            } else {
                
                    
                
                shot = firePos2.get(shotIndex2++);

                lastPos = shot;
                notYetShot.remove(shot);
                
                System.out.println("shot taken from firePos2 " + shot.getX()+ ",  " + shot.getY());
                

            }

                

        }
        p = new Position(shot.getX(), shot.getY());
        System.out.println("******* Actual shot completed of the arrays " + shot.getX() + ", " + shot.getY());
        return p; 
      
    }

    @Override
    public void hitFeedBack(boolean hit, Fleet enemyShips) {
        if (hit = true) {
            if (hunter == null) {
                hunter = new Hunter(notYetShot, lastPos);
                hunter.hit();

            } else {
                hunter.hit();

            }
        }
    }

    @Override
    public void endRound(int round, int points, int enemyPoints) {

        //Decides if it should start with firePos1 or firePos2.
//        if (round == 200) {
//            if (points > enemyPoints) {
//                whereToStart = false;
//            } else {
//                whereToStart = true;
//            }
//        }
//        if (round == 400) {
//            if (points > enemyPoints) {
//                whereToStart = false;
//            } else {
//                whereToStart = true;
//            }
//        }
//        if (round == 600) {
//            if (points > enemyPoints) {
//                whereToStart = false;
//            } else {
//                whereToStart = true;
//            }
//        }
//        if (round == 800) {
//            if (points > enemyPoints) {
//                whereToStart = false;
//            } else {
//                whereToStart = true;
//            }
//        }
    }

    @Override
    public void endMatch(int won, int lost, int draw) {
        //Do nothing
    }

}
