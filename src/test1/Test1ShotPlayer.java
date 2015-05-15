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

    private boolean[][] posHist;

    ParticipantInfo partInfo = new Participant(null);

    ArrayList<Position> notYetShot = new ArrayList<>(sizeX * sizeY);
    ArrayList<Position> firePos1;
    ArrayList<Position> firePos2 = new ArrayList<>(sizeX * sizeY);

    private Position lastShot;

    public Test1ShotPlayer() {

    }

    @Override
    public void startMatch(int rounds) {
        lastShot = null;
    }

    @Override
    public void startRound(int round) {
        hunter = null;
        lastShot = null;
        fillShootArrays();
    }

    @Override
    public void placeShips(Fleet fleet, Board board) {
        nextX = 1;
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

    public void fillShootArrays() {
        //Fills an array with every coordinate

        firePos1 = new ArrayList<>(50);
        int h = 0;
        int g = 0;
        int t = 0;

        while (h != 50) {
            for (int f = t; f < 10; f += 2) {
                firePos1.add(new Position(f, g));
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
        
        
//
//        for (int x = 0; x < 10; x++) {
//            for (int y = 0; y < 10; y++) {
//                firePos2.add(new Position(x, y));
//                notYetShot.add(new Position(x, y));
//
//            }
//        }
//        Collections.shuffle(firePos1);
//
//        for (int i = 4; i < 7; i++) {
//            for (int k = 4; k < 7; k++) {
//                firePos1.add(new Position(i, k));
//                firePos2.remove(new Position(i, k));
//                notYetShot.add(new Position(i, k));
//
//            }
//        }
//        Collections.shuffle(firePos2);

    }

    public void decideWhereToStart() {
        whichArrayToUse = whereToStart;
    }

    @Override
    public Position getFireCoordinates(Fleet enemyShips) {

        if (hunter != null) {
            shot = hunter.getShot();
            if (hunter.getShot() == null) {

                hunter = null;
            }

        } else {
            shot = firePos1.get(shotIndex);
                shotIndex++;
            
//
//            decideWhereToStart();
//
//            //First shoots from firePos1 then afterwards firePos2
//            if (whichArrayToUse == false) {
//                shot = firePos1.get(shotIndex);
//                shotIndex++;
//
//                if (shotIndex == firePos1.size()) {
//                    shotIndex = 0;
//                    whichArrayToUse = true;
//
//                }
//
//            } else {
//
//                shot = firePos2.get(shotIndex);
//                shotIndex++;
//
//                if (shotIndex == firePos2.size()) {
//                    shotIndex = 0;
//                    whichArrayToUse = false;
//
//                }
//            }
        }

        lastShot = shot;
        notYetShot.remove(shot);
        return shot;
    }

    @Override
    public void hitFeedBack(boolean hit, Fleet enemyShips) {
        if (hit) {
            if (hunter == null) {
                hunter = new Hunter(notYetShot, lastShot);
                hunter.hit();

            } else {
                hunter.hit();

            }
        }
    }

    @Override
    public void endRound(int round, int points, int enemyPoints) {

        //Decides if it should start with firePos1 or firePos2.
        if (round == 200) {
            if (points > enemyPoints) {
                whereToStart = false;
            } else {
                whereToStart = true;
            }
        }
        if (round == 400) {
            if (points > enemyPoints) {
                whereToStart = false;
            } else {
                whereToStart = true;
            }
        }
        if (round == 600) {
            if (points > enemyPoints) {
                whereToStart = false;
            } else {
                whereToStart = true;
            }
        }
        if (round == 800) {
            if (points > enemyPoints) {
                whereToStart = false;
            } else {
                whereToStart = true;
            }
        }

    }

    @Override
    public void endMatch(int won, int lost, int draw) {
        //Do nothing
    }

}
