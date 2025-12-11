/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package strategygame;

import java.util.Scanner;
import strategygame.StrategyGame.*;
/**
 *
 * @author artyom
 */
public class AskPlActDirState implements GameState {
    private ActionController context;
    
    public DirType askDirection() {
        Scanner sc = new Scanner(System.in);
        String input = null;
        DirType dir = null;
        boolean dirAssigned = false;
        
        while (!dirAssigned) {        
            System.out.println("Enter direction[UP/DOWN/LEFT/RIGHT]:");
            input = sc.next().toUpperCase();

            switch (input) {
                case "UP":
                    dir = DirType.UP;
                    dirAssigned = true;
                    break;
                case "DOWN":
                    dir = DirType.DOWN;
                    dirAssigned = true;
                    break;
                case "LEFT":
                    dir = DirType.LEFT;
                    dirAssigned = true;
                    break;
                case "RIGHT":
                    dir = DirType.RIGHT;
                    dirAssigned = true;
                    break;
                default:
                    System.out.println("Wrong name of direction.");
                    dirAssigned = false;
                    break;
            }
        }
        
        return dir;
    }
    
    @Override
    public void handle(ActionController context) {
        TestAction test = new TestAction(field, src, DirType.UP, ActionType.ACT);
        
        context.getData().setDir(askDirection());
        
        
        
        context.setState(this);
    }
}
