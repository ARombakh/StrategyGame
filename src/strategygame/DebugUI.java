/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package strategygame;

import static strategygame.StrategyGame.*;

/**
 *
 * @author artyom
 */
public class DebugUI {
    private static int turnNo = 1;

    public static int getTurnNo() {
        return turnNo;
    }

    public boolean getIsAction() {
        boolean action = false;
        switch(turnNo) {
            case 1 -> {
                action = true;
            }
            case 2 -> {
                action = false;
            }
            case 3 -> {
                action = true;
            }
            case 4 -> {
                action = false;
            }
            case 5 -> {
                action = true;
            }
            case 6 -> {
                action = true;
            }
            case 7 -> {
                action = true;
            }
            case 8 -> {
                action = true;
            }
            case 9 -> {
                action = true;
            }
            case 10 -> {
                action = true;
            }
            case 11 -> {
                action = true;
            }
            case 12 -> {    //2
                action = true;
            }
            case 13 -> {
                action = false;                
            }
            case 14 -> {    //3
                action = true;
            }
            case 15 -> {    //2 step
                action = false;                
            }
            case 16 -> {    //4
                action = true;
            }
            case 17 -> {    //3 step
                action = false;                
            }
            case 18 -> {    //5
                action = true;
            }
            case 19 -> {
                action = false;
            }
            case 20 -> {    //6
                action = true;
            }
            case 21 -> {    //6
                action = false;
            }
            case 22 -> {    //7
                action = true;
            }
            case 23 -> {
                action = false;
            }
            case 24 -> {    //8
                action = true;
            }
            case 25 -> {
                action = false;
            }
            case 26 -> {    //9
                action = true;
            }
            case 27 -> {
                action = false;
            }
            case 28 -> {    //10
                action = true;
            }
            case 29 -> {
                action = false;
            }
            case 30 -> {    //11
                action = true;
            }
            case 31 -> {
                action = false;
            }
            case 32 -> {    //12
                action = true;
            }
            case 33 -> {
                action = true;
            }
            case 34 -> {    //13
                action = true;
            }
            case 35 -> {
                action = true;
            }
            case 36 -> {    //14
                action = true;
            }
            case 37 -> {
                action = true;
            }
            case 38 -> {    //15
                action = true;
            }
            case 39 -> {
                action = true;
            }
            case 40 -> {    //16
                action = true;
            }
            case 41 -> {
                action = true;
            }
            case 42 -> {    //17
                action = true;
            }
        }
        
        return action;
    }
    
    public Direction getDirection() {
        Direction dir = Direction.UP;
        switch(turnNo) {
            case 1 -> {
                dir = Direction.DOWN;
            }
            case 2 -> {
                dir = Direction.UP;
            }
            case 3 -> {
                dir = Direction.DOWN;
            }
            case 4 -> {
                dir = Direction.UP;
            }
            case 5 -> {
                dir = Direction.DOWN;
            }
            case 6 -> {
                dir = Direction.LEFT;
            }
            case 7 -> {
                dir = Direction.DOWN;
            }
            case 8 -> {
                dir = Direction.LEFT;
            }
            case 9 -> {
                dir = Direction.DOWN;
            }
            case 10 -> {    //1
                dir = Direction.LEFT;
            }
            case 11 -> {
                dir = Direction.DOWN;
            }
            case 12 -> {    //2
                dir = Direction.LEFT;
            }
            case 13 -> {
                dir = Direction.DOWN;
            }
            case 14 -> {    //3
                dir = Direction.LEFT;
            }
            case 15 -> {
                dir = Direction.DOWN;
            }
            case 16 -> {    //4
                dir = Direction.LEFT;
            }
            case 17 -> {
                dir = Direction.DOWN;
            }
            case 18 -> {    //5
                dir = Direction.LEFT;
            }
            case 19 -> {    //1
                dir = Direction.RIGHT;
            }
            case 20 -> {    //6
                dir = Direction.LEFT;
            }
            case 21 -> {    //2
                dir = Direction.RIGHT;
            }
            case 22 -> {    //7
                dir = Direction.LEFT;
            }
            case 23 -> {    //3
                dir = Direction.RIGHT;
            }
            case 24 -> {    //8
                dir = Direction.LEFT;
            }
            case 25 -> {    //4
                dir = Direction.RIGHT;
            }
            case 26 -> {    //9
                dir = Direction.LEFT;
            }
            case 27 -> {    //4
                dir = Direction.RIGHT;
            }
            case 28 -> {    //10
                dir = Direction.LEFT;
            }
            case 29 -> {
                dir = Direction.RIGHT;
            }
            case 30 -> {    //11
                dir = Direction.LEFT;
            }
            case 31 -> {
                dir = Direction.RIGHT;
            }
            case 32 -> {    //12
                dir = Direction.LEFT;
            }
            case 33 -> {
                dir = Direction.DOWN;
            }
            case 34 -> {    //13
                dir = Direction.UP;
            }
            case 35 -> {
                dir = Direction.DOWN;
            }
            case 36 -> {    //14
                dir = Direction.UP;
            }
            case 37 -> {
                dir = Direction.DOWN;
            }
            case 38 -> {    //15
                dir = Direction.UP;
            }
            case 39 -> {
                dir = Direction.DOWN;
            }
            case 40 -> {    //16
                dir = Direction.UP;
            }
            case 41 -> {
                dir = Direction.DOWN;
            }
            case 42 -> {    //17
                dir = Direction.UP;
            }
        }
        
        turnNo++;
        
        return dir;
    }
}
