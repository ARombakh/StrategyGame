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
    
    public Game(int turnNum) {
        setTurnNum(turnNum);
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
    
    private boolean checkOnePlayerAlive(ActionController ac) {
        int cntAlive = 0;
        for (int i = 0; i < PLAYERS_QTY; i++) {
            if (ac.getPlayers()[i].isAlive()) {
                cntAlive++;
            }
        }
        
        return cntAlive <= 1;
    }
    
    private boolean maxTurnsReached() {
        return getTurnNum() >= MAX_TURNS;
    }
    
    public boolean isGameOver(ActionController ac) {
        return checkOnePlayerAlive(ac) || maxTurnsReached();
    }
    
    public boolean isTurnPossible(ActionData ad) {
        return true;
    }
    
    public boolean makeTurn(ActionData ad) {
        return true;
    }
}