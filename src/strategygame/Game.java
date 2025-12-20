/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package strategygame;

import strategygame.ActionController.*;
import static strategygame.ActionController.*;

/**
 *
 * @author artyom
 */
public class Game {
    public static final int MAX_TURNS = 40;
    private int turnNum;
    private boolean oneAlive;
    
    public Game() {
        setTurnNum(0);
    }

    public int getTurnNum() {
        return turnNum;
    }

    public boolean isOneAlive() {
        return oneAlive;
    }

    public void setTurnNum(int turnNum) {
        this.turnNum = turnNum;
    }

    public void setOneAlive(boolean oneAlive) {
        this.oneAlive = oneAlive;
    }
    
    public void nextTurn() {
        setTurnNum(getTurnNum() + 1);
    }
    
    private boolean checkOnePlayerAlive(Player[] players) {
        int cntAlive = 0;
        // подумать над тем, что игроки могут умирать и кол-во живых поменяется
        for (int i = 0; i < PLAYERS_QTY; i++) {
            if (players[i].isAlive()) {
                cntAlive++;
            }
        }
        
        return cntAlive <= 1;
    }
    
    private boolean maxTurnsReached() {
        return getTurnNum() >= MAX_TURNS;
    }
    
    public boolean isGameOver(Player[] players) {
        return checkOnePlayerAlive(players) || maxTurnsReached();
    }
    
    public boolean isTurnPossible(ActionData ad) {
        return true;
    }
    
    public boolean makeTurn(ActionData ad) {
        return true;
    }
}